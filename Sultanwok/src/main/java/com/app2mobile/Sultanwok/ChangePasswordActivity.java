package com.app2mobile.Sultanwok;

import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;

import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;

/*
 * Activity for change password
 */
public class ChangePasswordActivity extends Activity {
	private Button cancelBtn, submitBtn;
	private EditText newPassEdt, oldPassEdt, confirmPassEdt;
	private SharedPreferences mAppPrefs;
	private String[] param = new String[] { "cust_id", "cpassword", "password" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_change_password);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// emailEdt=(EditText) findViewById(R.id.email);
		mAppPrefs = getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		newPassEdt = (EditText) findViewById(R.id.newPass);
		oldPassEdt = (EditText) findViewById(R.id.oldPassword);
		confirmPassEdt = (EditText) findViewById(R.id.confirmNewPass);
		cancelBtn = (Button) findViewById(R.id.cancel);
		submitBtn = (Button) findViewById(R.id.submit);
		cancelBtn.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		submitBtn.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		/*
		 * Apply validation on fields & value are correct then send details to
		 * server
		 */
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// String emailStr=emailEdt.getText().toString();
				String userId = mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID,
						"");
				String newPassStr = newPassEdt.getText().toString();
				String oldPassStr = oldPassEdt.getText().toString();
				String confirmPassStr = confirmPassEdt.getText().toString();

				// if(emailStr==null||emailStr.equals(""))
				// {
				// AppUtiles.showToast(getBaseContext(),
				// getString(R.string.email_required));
				// }
				// else

				if (userId == null || userId.equals("") || userId.equals("0")) {
					AppUtiles.getInstance().showToast(getCurrentFocus(),
							"Please Login");
				} else if (oldPassStr == null || oldPassStr.equals("")) {
					AppUtiles.getInstance().showToast(getCurrentFocus(),
							getString(R.string.enter_previous));
				} else if (newPassStr == null || newPassStr.equals("")) {
					AppUtiles.getInstance().showToast(getCurrentFocus(),
							getString(R.string.enter_new));
				} else if (confirmPassStr == null || confirmPassStr.equals("")) {
					AppUtiles.getInstance().showToast(getCurrentFocus(),
							getString(R.string.enter_new_confirm));
				} else if (!newPassStr.equals(confirmPassStr)) {
					AppUtiles.getInstance().showToast(getCurrentFocus(),
							getString(R.string.password_confirm_match1));
				} else {
					/*
					 * send new details to server
					 */
					new ChangePassAsync().execute(new String[] { userId,
							oldPassStr, newPassStr });
				}
			}
		});

	}

	private class ChangePassAsync extends AsyncTask<String[], Void, String> {

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.CHANGE_PASSWORD,
					param, params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			parseResponse(result);
		}

	}

	/*
	 * parse server response that get as result of change password & close the
	 * Activity
	 */
	private void parseResponse(String result) {
		if (result != null) {
			try {
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser
							.getkeyValue_Str(mainJson, "state");
					String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
					AppUtiles.getInstance().showToast(getCurrentFocus(), msg);
					if (state != null && state.equals("1")) {
						// if (mainJson.has("data")) {
						// JSONObject dataJson = mainJson
						// .getJSONObject("data");
						// if (dataJson.has("cust_id")) {
						// Editor editor = mAppPrefs.edit();
						// editor.putString(ConstantsUrl.CUSTOMER_ID,
						// JsonParser.getkeyValue_Str(dataJson,
						// "cust_id"));
						// editor.commit();
						// }
						// }
						finish();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
