package com.leiteorz.xcrashtest

import android.os.Bundle
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
import com.leiteorz.xcrashtest.ui.theme.XCrashTestTheme
import xcrash.XCrash

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
