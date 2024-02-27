package com.leiteorz.xcrashtest

import android.content.Context
import xcrash.ICrashCallback
import xcrash.XCrash

object XCrashTool {
    private val initParams: XCrash.InitParameters = XCrash.InitParameters() // 初始参数

    /**
     * 初始化XCrash
     */
    fun initXCrash(context: Context){
        XCrash.init(context, initParams)
    }

    /**
     * 设置日志输出路径
     */
    fun setCrashLogPath(path: String?){
        initParams.setLogDir(path)
    }

    /**
     * 异常回调函数
     */
    fun setCrashCallBack(action: (() -> Unit)){
        val crashCallback = ICrashCallback { _, _ ->
            action()
        }

        initParams.setJavaCallback(crashCallback)
        initParams.setNativeCallback(crashCallback)
        initParams.setAnrCallback(crashCallback)
    }


    /**
     * 设置ANR崩溃日志最大保存数
     */
    fun setAnrLogMaxCount(count: Int){
        initParams.setAnrLogCountMax(count)
    }

    /**
     * 设置JVM崩溃日志最大保存数
     */
    fun setJvmLogMaxCount(count: Int){
        initParams.setJavaLogCountMax(count)
    }

    /**
     * 设置Native崩溃日志最大保存数
     */
    fun setNativeLogMaxCount(count: Int){
        initParams.setNativeLogCountMax(count)
    }
}