package com.app2mobile.Sultanwok;

import com.app2mobile.Sultanwok.fragment.PayementInformationFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

public class Payment_InformationActivity extends BaseActivity {
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

	getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	if (savedInstanceState == null) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.content_frame,
				PayementInformationFragment.newInstance());
		transaction.commit();
	}
}
	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}

}
