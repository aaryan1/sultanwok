package com.app2mobile.Sultanwok;

import com.app2mobile.Sultanwok.fragment.DetailFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

public class DetailActivity extends BaseActivity{
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	//Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

	if (savedInstanceState == null) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content_frame,
				DetailFragment.newstance());

		transaction.commit();
	}
}
	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}

}
