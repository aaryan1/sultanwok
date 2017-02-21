package com.app2mobile.utiles;

import com.app2mobile.Sultanwok.R;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MyTextWatcher implements TextWatcher {
	private EditText editView;
	private TextInputLayout inputLayout;
	private String msg;
	private int value;
	private Context mContext;
//	private int cardId = -1;

	public MyTextWatcher(TextInputLayout inputlayoutObj, String message,
			int value, Context context) {
		inputLayout = inputlayoutObj;
		msg = message;
		this.value = value;
		mContext = context;
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		inputLayout.setErrorEnabled(false);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		if (s.toString() == null || s.toString().equals("")) {
			inputLayout.setErrorEnabled(true);
			inputLayout.setError(msg);
		} else if (!EmailPatternMatcher.EMAIL_ADDRESS_PATTERN.matcher(
				s.toString()).matches()
				&& value == 1) {
			inputLayout.setErrorEnabled(true);
			inputLayout.setError(mContext
					.getString(R.string.emailFormat_required));
		} 
//		else if (value == 2) {
//			if (cardId != -1) {
//				CreditCardValidator cardValidator = new CreditCardValidator(
//						mContext);
//				if (!cardValidator.isCreditCardValid(s.toString(), cardId)) {
//					inputLayout.setErrorEnabled(true);
//					inputLayout.setError("Card type/card number is not valid.");
//				}else{
//					inputLayout.setErrorEnabled(false);
//				}
//			} else {
//				inputLayout.setErrorEnabled(true);
//				inputLayout.setError(mContext
//						.getString(R.string.cardType_required));
//			}
//
//		}
	}
}
