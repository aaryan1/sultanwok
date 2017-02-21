package com.app2mobile.Sultanwok;

import com.app2mobile.Sultanwok.fragment.Manual_Location_Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

public class Manual_Location extends BaseActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			System.out.println("Manual_Location_Fragment");
			transaction.replace(R.id.content_frame,
					Manual_Location_Fragment.newInstance());
			transaction.commit();
		}
	}
	
	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}
	
	
}

