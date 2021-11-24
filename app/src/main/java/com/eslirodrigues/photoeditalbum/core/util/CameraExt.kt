package com.eslirodrigues.photoeditalbum.core.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.eslirodrigues.photoeditalbum.R
import com.eslirodrigues.photoeditalbum.core.util.Constants.FILENAME_FORMAT
import com.eslirodrigues.photoeditalbum.data.model.Photo
import com.eslirodrigues.photoeditalbum.presentation.viewmodel.PhotoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { future ->
        future.addListener({
            continuation.resume(future.get())
        }, executor)
    }
}

val Context.executor: Executor
    get() = ContextCompat.getMainExecutor(this)

private fun getOutputDirectory(context: Context): File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() } }
    return if (mediaDir != null && mediaDir.exists())
        mediaDir else context.filesDir
}

suspend fun ImageCapture.takePhoto(viewModel: PhotoViewModel, context: Context, executor: Executor): File {
    val outputDirectory = getOutputDirectory(context)

    val photoFile = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            File(
                outputDirectory,
                SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
            )
        }.getOrElse { ex ->
            Log.e("TakePicture", "Failed to create file", ex)
            File("/dev/null")
        }
    }

    return suspendCoroutine { continuation ->
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                Toast.makeText(context, "Photo Saved", Toast.LENGTH_SHORT).show()
                val photoUri = photoFile.toURI().toString()
                val photo = Photo(name = photoFile.name, uri = photoUri)
                viewModel.savePhoto(photo)
                continuation.resume(photoFile)
            }
            override fun onError(ex: ImageCaptureException) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
                continuation.resumeWithException(ex)
            }
        })
    }
}