package kz.post.jcourier.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.loader.content.CursorLoader
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Utility methods for working with images.
 */
object ImageUtils {
    // Maximum image dimension used in the `shrinkImageFile` method below.
    private const val MAX_IMAGE_DIMENSION = 1500

    // JPEG compression quality to be used when shrinking an image.
    //    private static final int JPEG_QUALITY = 80;
    private const val JPEG_QUALITY = 30

    @Throws(IOException::class)
    fun shrinkImageFile(context: Context?, imageFile: File?): File {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(FileInputStream(imageFile), null, options)
        var scale = 1
        while (options.outWidth / scale >= MAX_IMAGE_DIMENSION ||
            options.outHeight / scale >= MAX_IMAGE_DIMENSION
        ) {
            scale += 1
        }
        val opt2 = BitmapFactory.Options()
        opt2.inDensity = scale
        opt2.inTargetDensity = 1
        val bitmap = BitmapFactory.decodeStream(FileInputStream(imageFile), null, opt2)
        val outputFile = FileUtils.createImageFile(context)
        val stream = FileOutputStream(outputFile)
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, stream)
        return outputFile
    }
}