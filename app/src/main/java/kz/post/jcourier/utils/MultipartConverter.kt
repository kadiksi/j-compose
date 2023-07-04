package kz.post.jcourier.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.core.net.toUri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


fun File?.toMultipart(
    context: Context,
    name: String,
    fileName: String,
    shouldShrink: Boolean = true
): MultipartBody.Part? {
    if (this == null) return null

    try {
        val file = if (shouldShrink) ImageUtils.shrinkImageFile(context, this) else this
        val requestBody = file.asRequestBody(
            context.contentResolver.getType(file.toUri())?.toMediaTypeOrNull()
        )

        return MultipartBody.Part.createFormData(
            name,
            fileName,
            requestBody
        )
    } catch (e: FileNotFoundException) {
        Timber.d("File not found ${e.message}")
    } catch (e: IOException) {
        Timber.d("IOException ${e.message}")
    }

    return null
}

fun Bitmap?.toFile(
    context: Context,
    fileName: String,
): File {
    //create a file to write bitmap data
    val file = File(context.cacheDir, fileName)
    file.createNewFile()

    //Convert bitmap to byte array
    val bos = ByteArrayOutputStream()
    this?.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
    val bitMapData = bos.toByteArray()

    //write the bytes in file
    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(file)
        fos.write(bitMapData)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        fos?.flush()
        fos?.close()
    }

    return file
}