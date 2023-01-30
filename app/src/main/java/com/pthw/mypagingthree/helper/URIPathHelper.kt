package com.pthw.mypagingthree.helper

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import timber.log.Timber
import java.io.File

class URIPathHelper {

    fun getPath(context: Context, uri: Uri): String? {
        val isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = (docId.split((":".toRegex()))).toTypedArray()
                val type = split[0]
                val p = "primary"
                if (p.equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory()
                        .toString() + File.separator + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                Timber.w("Uri id is : $id")
                return if (id.startsWith("msf")) {
                    val split = id.split(":")
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(split[1])
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        getDataColumn(
                            context,
                            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                            selection,
                            selectionArgs
                        )
                    } else null
                } else {
                    try {
                        val parse = Uri.parse("content://downloads/public_downloads")
                        val contentUri = ContentUris.withAppendedId(parse, java.lang.Long.valueOf(id))
                        return getDataColumn(context, contentUri, null, null)
                    } catch (e: Exception) {
                        Timber.e(e)
                    }
                    val fileName = getFilePath(context, uri)
                    if (fileName != null) {
                        Environment.getExternalStorageDirectory()
                            .toString() + "/Download/" + fileName
                    } else null
                }
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    private fun getDataColumn(
        context: Context,
        uri: Uri?,
        selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor =
                context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun getFilePath(context: Context, uri: Uri?): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(
            MediaStore.MediaColumns.DISPLAY_NAME
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, null, null,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        val doc = "com.android.externalstorage.documents"
        return (doc == uri.authority)
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        val doc = "com.android.providers.downloads.documents"
        return doc == uri.authority
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}
