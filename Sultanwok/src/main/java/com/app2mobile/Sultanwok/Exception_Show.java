package com.app2mobile.Sultanwok;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Exception_Show extends Activity {
	TextView error;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

	setContentView(R.layout.exception);
	 error= (TextView)findViewById(R.id.error);
	error.setText(getIntent().getStringExtra("error"));
}
}
