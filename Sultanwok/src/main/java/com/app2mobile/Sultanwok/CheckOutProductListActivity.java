package com.app2mobile.Sultanwok;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.app2mobile.Sultanwok.fragment.CheckOutProductListFragment;

/*
 * Add CheckOutProductListFragment toActivity
 */
public class CheckOutProductListActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		super.onCreate(savedInstanceState);
	//	Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.content_frame,
					CheckOutProductListFragment.newInstance());

			transaction.commit();
		}
	}

	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}
}
