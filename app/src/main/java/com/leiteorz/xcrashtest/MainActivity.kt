package com.leiteorz.xcrashtest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.leiteorz.xcrashtest.ui.theme.XCrashTestTheme
import xcrash.XCrash
import java.io.File

class MainActivity : ComponentActivity() {
    companion object{
        const val TAG = "XCrashTest"
    }

    // 需要申请的权限
    private val permissions = arrayListOf<String>().apply {
        add(Manifest.permission.READ_EXTERNAL_STORAGE)
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPermission()    // 申请权限
        initXCrash()

        setContent {
            XCrashTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F5F5)
                ) {
                    CrashTest()
                }
            }
        }
    }

    /**
     * 动态申请权限
     */
    private fun initPermission(){
        val unPermissions = ArrayList<String>()
        for (permission in permissions){
            if (ContextCompat.checkSelfPermission(applicationContext, permission) != PackageManager.PERMISSION_GRANTED){
                unPermissions.add(permission)
            }
        }
        if (unPermissions.size > 0){
            ActivityCompat.requestPermissions(this, unPermissions.toTypedArray(), 0)
        }
    }

    /**
     * 初始化XCrash
     */
    private fun initXCrash(){
        val path = "${Environment.getExternalStorageDirectory().path}/Documents/XCrash"
        XCrashTool.setCrashLogPath(path)
        XCrashTool.setCrashCallBack { Log.e(TAG, "XCrash接口回调") }
        XCrashTool.setJvmLogMaxCount(3)
        XCrashTool.setNativeLogMaxCount(3)
        XCrashTool.setAnrLogMaxCount(3)
        XCrashTool.initXCrash(applicationContext)
    }
}

@Composable
fun CrashTest() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CrashTopBar()
        CrashButton(onClick = { XCrash.testJavaCrash(true) }, text = "JVM异常")
        CrashButton(onClick = { XCrash.testNativeCrash(true) }, text = "Native异常")
        CrashButton(onClick = { Thread.sleep(1000000) }, text = "ANR异常")
    }
}

@Composable
fun CrashButton(onClick: (() -> Unit), text: String?){
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = Color(0xFFFF8A80)
    )

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .fillMaxWidth()
            .height(60.dp),
        colors = buttonColors
    ) {
        text?.let { Text(text = text) }
    }
}

@Composable
fun CrashTopBar(){
    Row(modifier = Modifier
        .padding(bottom = 10.dp)
        .fillMaxWidth()
        .height(40.dp)
        .background(Color.White)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "XCrash 测试",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
