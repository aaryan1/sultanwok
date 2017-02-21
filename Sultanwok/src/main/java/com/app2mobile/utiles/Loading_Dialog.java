package com.app2mobile.utiles;

import com.app2mobile.Sultanwok.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Loading_Dialog extends Activity{
	 private static final String TAG = "ResultActivity";
     private AnimationDrawable loadingViewAnim=null;
     private TextView loadigText = null;
     private ImageView loadigIcon = null;
     private LinearLayout loadingLayout = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_loading);

        loadingLayout = (LinearLayout)findViewById(R.id.LinearLayout1);
        loadingLayout.setVisibility(View.GONE);

        loadigText = (TextView) findViewById(R.id.textView111);
        loadigText.setVisibility(View.GONE);

        loadigIcon = (ImageView) findViewById(R.id.imageView111);
        loadigIcon.setVisibility(View.GONE);

        loadigIcon.setBackgroundResource(R.anim.progress_animation);
        loadingViewAnim = (AnimationDrawable) loadigIcon.getBackground();

        // This line is to start Asyn Task only when OnCreate Method get completed, So Loading Icon Rotation Animation work properly
        loadigIcon.post(new Starter());

	}
	
	
	
	class Starter implements Runnable {
        public void run() {
          //start Asyn Task here   
          new LongOperation().execute("");
        }
    }

  private class LongOperation extends AsyncTask<String, Void, String> {

      @Override
      protected String doInBackground(String... params) {
          //ToDo your Network Job/Request etc. here 
          return "Executed";
      }

      @Override
      protected void onPostExecute(String result) {
      //ToDo with result you got from Task

      //Stop Loading Animation
          loadingLayout.setVisibility(View.GONE);
      loadigText.setVisibility(View.GONE);
      loadigIcon.setVisibility(View.GONE);
      loadingViewAnim.stop();
      }

      @Override
      protected void onPreExecute() {
      //Start  Loading Animation
      loadingLayout.setVisibility(View.VISIBLE);
      loadigText.setVisibility(View.VISIBLE);
      loadigIcon.setVisibility(View.VISIBLE);
      loadingViewAnim.start();
      }

      @Override
      protected void onProgressUpdate(Void... values) {}
  }
}
