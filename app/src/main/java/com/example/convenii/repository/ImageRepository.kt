package com.example.convenii.repository

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ImageRepository {
    fun uriToMultipart(context: Context, uri: Uri, partName: String): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        val mimeType = contentResolver.getType(uri) ?: "image/*"
        val inputStream = contentResolver.openInputStream(uri)
        val byteArray = inputStream?.readBytes()
        inputStream?.close()

        val requestBody = byteArray?.toRequestBody(mimeType.toMediaTypeOrNull())
        val fileName = uri.lastPathSegment ?: "image"

        return requestBody?.let {
            MultipartBody.Part.createFormData(partName, fileName, it)
        }
        
    }
}