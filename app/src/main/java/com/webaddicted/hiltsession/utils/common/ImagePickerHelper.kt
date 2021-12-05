package com.webaddicted.hiltsession.utils.common

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.webaddicted.hiltsession.R
import com.webaddicted.hiltsession.utils.constant.AppConstant
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

object ImagePickerHelper {
    private lateinit var selectedImgUri: Uri
    private const val IMG_FILE_NAME_FORMAT = "yyyyMMdd_HHmmss"
    private const val IMG_FILE_EXT = "jpeg"
    private const val IMGS_DIR = "app_imgs_dir"
    private var isCamera: Boolean = false
    private var mCurrentPhotoPath: String = ""
    private var mPerpermissionListener: ImagePickerListener? = null

    enum class ImgPickerType(val value: Int) {
        CHOOSER_CAMERA_GALLERY(5002),
        CROP_IMAGE(5003)
    }

    fun getImage(
        mActivity: Activity,
        pickerType: ImgPickerType,
        permissionListener: ImagePickerListener
    ) {
        mPerpermissionListener = permissionListener
        val locationList = ArrayList<String>()
        locationList.add(Manifest.permission.CAMERA)
        locationList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        locationList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        PermissionHelper.requestMultiplePermission(
            mActivity,
            locationList,
            object : PermissionHelper.PermissionListener {
                override fun onPermissionGranted(mCustomPermission: List<String>) {
                    openIntentChooser(mActivity)
                }

                override fun onPermissionDenied(mCustomPermission: List<String>) {

                }
            })
    }

    private fun openIntentChooser(mActivity: Activity) {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            val photoFile = createImgFile(mActivity)

            selectedImgUri = FileProvider.getUriForFile(
                mActivity, mActivity.packageName + ".fileprovider",
                photoFile
            )
            takePicture.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            takePicture.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, selectedImgUri)

            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )

            val chooser =
                Intent.createChooser(pickPhoto, mActivity.getString(R.string.upload_photo))
            chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePicture))
            mActivity.startActivityForResult(chooser, ImgPickerType.CHOOSER_CAMERA_GALLERY.value)
        } catch (e: IOException) {
            GlobalUtils.logPrint(msg = e.toString())
            DialogUtils.showDialog(
                mActivity,
                message = mActivity.getString(R.string.something_went_wrong) + e.toString()
            )
        }
    }


    fun onActivityResult(mActivity: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            ImgPickerType.CHOOSER_CAMERA_GALLERY.value -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        //Picked image from Gallery
                        isCamera = false
                        selectedImgUri = data.data as Uri
                        postCropImg(mActivity, data, resultCode)
                    } else {
                        isCamera = true
                        performCrop(mActivity)
                    }
                }
            }

            ImgPickerType.CROP_IMAGE.value ->
                postCropImg(mActivity, data, resultCode)
        }
    }

    private fun createImgFile(mActivity: Activity): File {
        val imgFileName =
            "${SimpleDateFormat(IMG_FILE_NAME_FORMAT, Locale.US).format(Date())}.$IMG_FILE_EXT"
        val folder = File(mActivity.filesDir, IMGS_DIR)
        folder.mkdir()
        val image = File(folder, imgFileName)
        image.createNewFile()
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.absolutePath
        updateGallery(mActivity, mCurrentPhotoPath)
        return image
    }

    fun getFile(mContext: Context, bmp: Bitmap?): File? {
        val imgFileName = "${
            SimpleDateFormat(
                AppConstant.IMG_FILE_NAME_FORMAT,
                Locale.US
            ).format(Date())
        }.${AppConstant.IMG_FILE_EXT}"
        val folder = File(mContext.filesDir, AppConstant.IMGS_DIR)
        folder.mkdir()
        val file = File(folder, imgFileName)
        file.createNewFile()

        val outStream: OutputStream?
        try {
            outStream = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return null
        }
        return file
    }

    private fun performCrop(mActivity: Activity) {
        val cropIntent = Intent("com.android.camera.action.CROP")
        cropIntent.setDataAndType(selectedImgUri, "image/*")
        cropIntent.putExtra("crop", "true")
        cropIntent.putExtra("aspectX", 1)
        cropIntent.putExtra("aspectY", 1)
        cropIntent.putExtra("outputX", 256)
        cropIntent.putExtra("outputY", 256)
        cropIntent.putExtra("return-data", true)
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImgUri)

        val cropApps = mActivity.packageManager.queryIntentActivities(
            cropIntent, 0
        )

        if (isCamera) {
            for (resolveInfo in cropApps) {
                val packageName = resolveInfo.activityInfo.packageName
                mActivity.grantUriPermission(
                    packageName,
                    selectedImgUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                mActivity.grantUriPermission(
                    packageName,
                    selectedImgUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            }
        }

        //If there is no image cropper app, display warning message
        if (cropApps.isEmpty()) {
            Toast.makeText(
                mActivity, mActivity.getString(R.string.cannot_find_image_crop_app),
                Toast.LENGTH_SHORT
            ).show()
            return
        } else {
            mActivity.startActivityForResult(cropIntent, ImgPickerType.CROP_IMAGE.value)
        }
    }


//  @Throws(IOException::class)
//  private fun createImageFile(activity: Activity): File {
//    // Create an image file name
//    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//    val imageFileName = "JPEG_" + timeStamp + "_"
//    val storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//    val image = File.createTempFile(
//      imageFileName, /* prefix */
//      ".jpg", /* suffix */
//      storageDir   /* directory */
//    )
//
//    // Save a file: path for use with ACTION_VIEW intents
//    mCurrentPhotoPath = image.absolutePath
//    return image
//  }


    private fun postCropImg(mActivity: Activity, data: Intent?, resultCode: Int) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            val imageBitmap = if (data.extras == null) {
                //for google photos
                getGooglePhoto(mActivity, selectedImgUri)
            } else {
                data.extras?.let {
                    selectedImgUri = try {
                        val bmp = it.getParcelable<Uri>("data") as Bitmap
                        getImageUri(mActivity, bmp)
                    } catch (e: java.lang.Exception) {
                        data.data as Uri
                    }
                }

                GlobalUtils.uriToBitmap(mActivity, selectedImgUri)
            }
            mPerpermissionListener?.onSuccess(getFile(mActivity, imageBitmap)!!, imageBitmap)
        }
    }

    fun getGooglePhoto(context: Context, uri: Uri): Bitmap? {
        var bitmap: Bitmap? = null
        uri.authority?.let {
            try {
                val content = context.contentResolver.openInputStream(uri)
                bitmap = BitmapFactory.decodeStream(content)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bitmap
    }

    private fun getImageUri(mActivity: Activity, bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(
            mActivity.contentResolver,
            bitmap,
            "fileName",
            null
        )
        return Uri.parse(path)
    }

    interface ImagePickerListener {
        fun onSuccess(mFile: File, imageBitmap: Bitmap?)
//    fun onPermissionDenied(mCustomPermission: List<String>)

    }

    fun updateGallery(context: Context, imagePath: String?) {
        val file = File(imagePath)
        MediaScannerConnection.scanFile(
            context, arrayOf(file.toString()),
            null, null
        )
    }
}