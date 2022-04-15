package com.chooongg.utils

import android.content.Context
import android.text.format.Formatter
import java.io.File

object ClearCacheUtils {

    fun getAppCacheSize(context: Context): String {
        var fileSize = 0L
        val filesDir = context.filesDir
        val cacheDir = context.cacheDir
        fileSize += getDirSize(filesDir)
        fileSize += getDirSize(cacheDir)
        return Formatter.formatShortFileSize(context, fileSize)
    }

    /**
     * 清除缓存没有处理线程
     */
    fun clearCache(context: Context): Int {
        var deleteFiles = 0
        deleteFiles += clearCacheFolder(context.filesDir, System.currentTimeMillis())
        deleteFiles += clearCacheFolder(context.cacheDir, System.currentTimeMillis())
        return deleteFiles
    }

    private fun getDirSize(dir: File?): Long {
        if (dir == null) return 0
        if (!dir.isDirectory) return 0
        var dirSize = 0L
        dir.listFiles()?.forEach {
            if (it.isFile) dirSize += it.length()
            else if (it.isDirectory) {
                dirSize += it.length()
                dirSize += getDirSize(it)
            }
        }
        return dirSize
    }

    private fun clearCacheFolder(dir: File?, currentTime: Long): Int {
        var deleteFiles = 0
        if (dir != null && dir.isDirectory) {
            try {
                dir.listFiles()?.forEach {
                    if (it.isDirectory) {
                        deleteFiles = clearCacheFolder(it, currentTime)
                    }
                    if (it.lastModified() < currentTime) {
                        if (it.delete()) {
                            deleteFiles++
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return deleteFiles
    }
}