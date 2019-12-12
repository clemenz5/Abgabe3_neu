package com.example.abgabe3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private Button fibButton, camButton, persButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fibButton = findViewById(R.id.fibonacci_button);
		camButton = findViewById(R.id.camera_button);
		persButton = findViewById(R.id.persons_button);

		fibButton.setOnClickListener(this);
		camButton.setOnClickListener(this);
		persButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.fibonacci_button:
				startActivity(new Intent(MainActivity.this, FibonacciActivity.class));
				break;
			case R.id.camera_button:
				startActivity(new Intent(MainActivity.this, CameraActivity.class));
				break;
			case R.id.persons_button:
				startActivity(new Intent(MainActivity.this, PersonActivity.class));
				break;
		}
	}
}
