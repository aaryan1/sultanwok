package com.app2mobile.Sultanwok;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.app2mobile.Sultanwok.fragment.OrderHistoryFragment;

public class OrderHistoryActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			
			
			transaction.replace(R.id.content_frame, OrderHistoryFragment.newInstance());
			transaction.commit();
		}
		
	}

	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}
	
}
