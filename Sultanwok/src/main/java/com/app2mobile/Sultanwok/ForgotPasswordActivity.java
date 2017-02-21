package com.app2mobile.Sultanwok;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.EmailPatternMatcher;
import com.app2mobile.utiles.JsonParser;

public class ForgotPasswordActivity extends Activity {
	private Button submitBtn, dismissBtn;
	private EditText editTextObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_forgot_password);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		submitBtn = (Button) findViewById(R.id.submit);
		editTextObj = (EditText) findViewById(R.id.email);
		dismissBtn = (Button) findViewById(R.id.cancel);
		submitBtn.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		dismissBtn.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		/*
		 * onClick Dismiss button, Navigate to LoginSignUpActivity
		 */
		dismissBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loginIntent = new Intent(ForgotPasswordActivity.this,
						LoginSignupActivity.class);
				startActivityForResult(loginIntent, 1005);
				finish();
			}
		});
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String emailStr = editTextObj.getText().toString();
				if (emailStr == null || emailStr.equals("")) {
					AppUtiles.getInstance().showToast(
							getCurrentFocus(),
							getString(R.string.email_required));
				} else if (!EmailPatternMatcher.EMAIL_ADDRESS_PATTERN.matcher(
						emailStr).matches()) {
					AppUtiles.getInstance().showToast(
							getCurrentFocus(),
							getString(R.string.emailFormat_required));
				} else {
					new SubmitOrderAsy().execute(emailStr);
				}
			}
		});

	}

	private class SubmitOrderAsy extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// try {
			// return JsonParser.Webserice_Call_URL(
			// ConstantsUrl.FORGOT_PASSWORD + "emailid="
			// + URLEncoder.encode(params[0], "utf-8"), null,
			// null);
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			return JsonParser.Webserice_Call_URL(ConstantsUrl.FORGOT_PASSWORD,
					new String[] { "store_id", "email" }, new String[] {
							ConstantsUrl.STOREID, params[0] });
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			parseResponse(result);
			System.out.println("forgot " + result);

		}
	}

	private void parseResponse(String result) {
		if (result != null) {
			try {
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					final String state = JsonParser.getkeyValue_Str(mainJson,
							"state");
					String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
					// AppUtiles.showToast(getBaseContext(), msg);
					// if (state != null && state.equals("1")) {
					final Dialog dialog = new Dialog(
							ForgotPasswordActivity.this, R.style.CustomDialog1);
					dialog.setContentView(R.layout.dialog_alert_message);
					Button cancelBtn = (Button) dialog
							.findViewById(R.id.cancel);
					TextView message = (TextView) dialog
							.findViewById(R.id.message);
					message.setText(msg);

					cancelBtn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							if (state != null && state.equals("1")) {
								finish();
							}
						}
					});
					dialog.show();
				}
				// }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	// private void parseResponse(String result) {
	// try {
	// if (result != null) {
	// JSONObject mainJson = new JSONObject(result);
	// String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
	// final String status = JsonParser.getkeyValue_Str(mainJson,
	// "status");
	//
	// final Dialog dialog = new Dialog(ForgotPasswordActivity.this,
	// R.style.CustomDialog1);
	// dialog.setContentView(R.layout.dialog_alert_message);
	// Button cancelBtn = (Button) dialog.findViewById(R.id.cancel);
	// TextView message = (TextView) dialog.findViewById(R.id.message);
	// message.setText(msg);
	//
	// cancelBtn.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	// dialog.dismiss();
	// if (status.equals("1")) {
	// finish();
	// }
	// }
	// });
	//
	// dialog.show();
	// }
	// } catch (Exception e) {
	//
	// }
	// }
}
