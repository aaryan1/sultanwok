package com.app2mobile.Sultanwok;

import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.EmailPatternMatcher;
import com.app2mobile.utiles.JsonParser;

public class VarifyUserActivity extends Activity {
	private EditText emailEdt, verificationEdt;
	private Button submitBtn, dismissBtn;
	private String[] names = new String[] { "email", "verifycode" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_verify_user);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		emailEdt = (EditText) findViewById(R.id.email);
		verificationEdt = (EditText) findViewById(R.id.verifyCode);
		submitBtn = (Button) findViewById(R.id.submit);
		dismissBtn = (Button) findViewById(R.id.cancel);
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailStr = emailEdt.getText().toString();
				String verifyCode = verificationEdt.getText().toString();
				if (emailStr == null || emailStr.equals("")) {
//					AppUtiles.getInstance().showToast(getBaseContext(),
//							getString(R.string.email_required));
					Toast.makeText(getApplicationContext(), getString(R.string.email_required), 2).show();

				} else if (!EmailPatternMatcher.EMAIL_ADDRESS_PATTERN.matcher(
						emailStr).matches()) {
//					AppUtiles.getInstance().showToast(getBaseContext(),
//							getString(R.string.emailFormat_required));
					Toast.makeText(getApplicationContext(),getString(R.string.emailFormat_required), 2).show();

				} else if (verifyCode == null || verifyCode.equals("")) {
//					AppUtiles.getInstance().showToast(getBaseContext(),
//							"Enter verification code.");
					Toast.makeText(getApplicationContext(), "Enter verification code.", 2).show();

				} else {
					new VerifyUserAsync().execute(new String[] { emailStr,
							verifyCode });
				}
			}
		});
		dismissBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private class VerifyUserAsync extends AsyncTask<String[], Void, String> {

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.VERIFY_USER,
					names, params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("verification user " + result);
			parseResponse(result);
		}
	}

	private void parseResponse(String result) {
//		01-16 10:12:27.083: I/System.out(5819): verification user
//		{"state":0,"msg":"Email Id and Verify Code does not match","data":""}

		if (result != null) {
			try {
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser.getkeyValue_Str(mainJson, "state");
					String msg=JsonParser.getkeyValue_Str(mainJson, "msg");
				//	AppUtiles.getInstance().showToast(getBaseContext(), msg);
					Toast.makeText(getApplicationContext(), msg, 2).show();

					if(state!=null&&state.equals("1"))
					{
//						if(mainJson.has("data"))
//						{
//							
//						}
						finish();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
