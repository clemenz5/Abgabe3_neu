package com.example.abgabe3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends AppCompatActivity {
	private static final int REQUEST_THUMBNAIL = 420;
	private Button takePictureButton, savePictureButton;
	private ImageView imageView;
	private String currentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		takePictureButton = findViewById(R.id.take_picture_button);
		savePictureButton = findViewById(R.id.save_picture_button);
		imageView = findViewById(R.id.image_view);

		if (ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA}, 7);
		} else {
			setClickListeners();
		}


	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case 7:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					setClickListeners();
				} else {
					Toast.makeText(this, "No permission to use camera", Toast.LENGTH_LONG).show();
				}
				break;

		}
	}

	private void setClickListeners() {
		takePictureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startPhotoIntent(false);
			}
		});

		savePictureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startPhotoIntent(true);
			}
		});
	}


	private void startPhotoIntent(boolean save) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			File photoFile = null;
			try {
				photoFile = createImageFile();

			} catch (IOException ex) {
				Toast.makeText(this, "Couldnot open file!", Toast.LENGTH_SHORT).show();

			}
			if (photoFile != null) {
				if (save) {
					Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.example.abgabe3.fileprovider", photoFile);
					takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				}
				startActivityForResult(takePictureIntent, REQUEST_THUMBNAIL);
			}
		}
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		currentPhotoPath = image.getAbsolutePath();
		return image;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		if (requestCode == REQUEST_THUMBNAIL && resultCode == RESULT_OK) {
			if (data != null) {
				Bundle extras = data.getExtras();
				Bitmap imageBitmap = (Bitmap) extras.get("data");
				imageView.setImageBitmap(imageBitmap);
			} else {

				Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
				bitmap = Bitmap.createScaledBitmap(bitmap, imageView.getWidth(), imageView.getHeight(), false);
				imageView.setImageBitmap(bitmap);

			}

		}
	}
}