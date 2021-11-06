package com.example.dice_throw

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.io.IOException

class GalleryActivity : AppCompatActivity()
{
    var imageView: ImageView? = null
    var takePictureButton: Button? = null
    var uploadPictureButton: Button? = null
    var imageURI: Uri? = null
    val CAMERA_REQUEST = 1
    val MY_CAMERA_PERMISSION_CODE = 2
    val GALLERY_IMAGE = 3

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        imageView = findViewById(R.id.imageView)
        takePictureButton = findViewById(R.id.takePictureButton);
        uploadPictureButton = findViewById(R.id.uploadPictureButton);

        takePictureButton?.setOnClickListener(View.OnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        })
        uploadPictureButton?.setOnClickListener(View.OnClickListener {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(galleryIntent, "Select image"), GALLERY_IMAGE)
        })

        imageURI?.let { contentResolver.notifyChange(it,null) }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK && data != null) {
                val photo = data.extras!!["data"] as Bitmap?
                imageView!!.setImageBitmap(photo)
            }
            if (requestCode == GALLERY_IMAGE && resultCode == RESULT_OK) {
                if (data != null) {
                    imageURI = data.data
                }
                try {
                    val galleryPhoto = MediaStore.Images.Media.getBitmap(contentResolver, imageURI)
                    imageView!!.setImageBitmap(galleryPhoto)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}