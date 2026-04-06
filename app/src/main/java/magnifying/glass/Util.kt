/*
 * Modified fork of https://github.com/kloener/visor-android
 * Copyright (c) 2015 Christian Illies
 * File modified by cuzi <https://github.com/cvzi/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package magnifying.glass

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream


class Util {

    companion object {
        // https://stackoverflow.com/a/57265702/6585346 and
        // https://stackoverflow.com/a/38858040/6585346
        @JvmStatic
        // @param folderName can be your app's {name}
        fun saveImageOnAllAPIs(bitmap: Bitmap, context: Context, folderName: String, fileName: String, quality: Int) : Uri? {
            var uri: Uri? = null
            val picturesPath = if (folderName.isBlank()) {
                Environment.DIRECTORY_PICTURES
            } else {
                "${Environment.DIRECTORY_PICTURES}/$folderName"
            }
            val values = ContentValues()
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            values.put(MediaStore.Images.Media.RELATIVE_PATH, picturesPath)
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.
            uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri), quality)
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }
            return uri
        }

        private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?, quality: Int) {
            if (outputStream != null) {
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }
}
