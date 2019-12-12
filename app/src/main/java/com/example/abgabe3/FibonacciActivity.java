package com.example.abgabe3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FibonacciActivity extends AppCompatActivity {

	private Button submitButton;
	private TextView resultView;
	private ProgressBar progressBar;
	private EditText numberInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fibonacci);

		submitButton = findViewById(R.id.submit_button);
		resultView = findViewById(R.id.result_view);
		progressBar = findViewById(R.id.progress_bar);
		numberInput = findViewById(R.id.number_input);

		progressBar.setMax(100);

		submitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(numberInput.getText().toString().isEmpty()){
					FibonacciCalculator fibonacciCalculator = new FibonacciCalculator(Integer.valueOf(numberInput.getText().toString()));
					fibonacciCalculator.execute(1);
				}else{
					FibonacciCalculator fibonacciCalculator = new FibonacciCalculator(Integer.valueOf(numberInput.getText().toString()));
					fibonacciCalculator.execute(Integer.valueOf(numberInput.getText().toString()));
				}
			}
		});
	}


	private class FibonacciCalculator extends AsyncTask<Integer, Integer, Integer> {
		private int iterations;

		public FibonacciCalculator(int iterations) {
			this.iterations = iterations;
		}

		@Override
		protected Integer doInBackground(Integer... integers) {
			return fibonacci(1, 1, integers[0]);
		}

		private int fibonacci(int value, int lastValue, int index) {
			if (index > 0) {
				publishProgress(index);
				index--;
				return fibonacci(value + lastValue, value, index);
			} else {
				publishProgress(index);
				return value;
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressBar.setProgress((int) (100 - (100 * ((float) values[0] / (float) iterations))));

		}

		@Override
		protected void onPostExecute(Integer integer) {
			String s = String.format("Result: %s", String.valueOf(integer));
			resultView.setText(s);
		}
	}

}
