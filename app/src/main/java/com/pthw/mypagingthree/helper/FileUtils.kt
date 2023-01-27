package com.pthw.mypagingthree.helper

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


fun Activity.getUriAndResize(imageUri: Uri): Uri {
    val pfd = this.contentResolver.openFileDescriptor(imageUri, "r")
    val bitmap =
        BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor, null, null)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
    val resized = bitmap.resizeBitmap(800)
    val stream = this.contentResolver.openInputStream(imageUri)
    val exitInterface = stream?.let { ExifInterface(it) }
    val orientation = exitInterface?.getAttributeInt(ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL)
    val rotated = when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 ->
            rotateImage(resized, 90)
        ExifInterface.ORIENTATION_ROTATE_180 ->
            rotateImage(resized, 180)
        ExifInterface.ORIENTATION_ROTATE_270 ->
            rotateImage(resized, 270)
        else -> resized
    }

    val path = MediaStore.Images.Media.insertImage(
        this.contentResolver,
        rotated, "Profile_${Calendar.SECOND}", null)
    pfd?.close()
    return Uri.parse(path)
}

fun Activity.getResizeBitmap(imageUri: Uri): File? {
    val pfd = this.contentResolver.openFileDescriptor(imageUri, "r")
    val bitmap =
        BitmapFactory.decodeFileDescriptor(pfd?.fileDescriptor, null, null)

    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
    val resized = bitmap.resizeBitmap(800)
    val stream = this.contentResolver.openInputStream(imageUri)
    val exitInterface = stream?.let { ExifInterface(it) }
    val orientation = exitInterface?.getAttributeInt(
        ExifInterface.TAG_ORIENTATION,
        ExifInterface.ORIENTATION_NORMAL
    )
    val rotated = when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 ->
            rotateImage(resized, 90)
        ExifInterface.ORIENTATION_ROTATE_180 ->
            rotateImage(resized, 180)
        ExifInterface.ORIENTATION_ROTATE_270 ->
            rotateImage(resized, 270)
        else -> resized
    }
    pfd?.close()
    return convertBitmapToFile(rotated)
}

fun Activity.rotateImageIfRequired(bitmap: Bitmap, imageUri: Uri): Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = contentResolver.openInputStream(imageUri)
        val exitInterface = inputStream?.let {
            ExifInterface(it)
        }
        val orientation = exitInterface?.getAttributeInt(ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_NORMAL)
        val rotated = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 ->
                rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 ->
                rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 ->
                rotateImage(bitmap, 270)
            else -> bitmap
        }
        return rotated
    }
    catch (e: IOException) {
        return null
    }
    finally {
        inputStream?.close()
    }
}

fun Activity.createImageFile(): File {
    var currentPhotoPath: String = ""
// Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "JPEG_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = absolutePath
    }
}

fun Activity.convertBitmapToFile(bitmap: Bitmap): File? {
    val resize = createResizeImageFile()
    try {
        resize.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80 /*ignored for PNG*/, bos)

        val bitmapdata: ByteArray = bos.toByteArray()
        val fos = FileOutputStream(resize)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return resize
}

fun Activity.createResizeImageFile(): File {
    var currentPhotoPath: String = ""
// Create an image file name
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    return File.createTempFile(
        "Resized_${timeStamp}_", /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = absolutePath
    }
}


fun Bitmap.resizeBitmap(maxSize: Int): Bitmap {
    var width = this.width
    var height = this.height
    val ratio = width.toFloat()/height.toFloat()
    if (ratio>0) {
        width = maxSize
        height = (width/ ratio).toInt()
    }
    else {
        height = maxSize
        width = (height * ratio).toInt()
    }
    return Bitmap.createScaledBitmap(this, width, height, true)
}





