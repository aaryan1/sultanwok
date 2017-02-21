package com.app2mobile.Sultanwok;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app2mobile.Sultanwok.fragment.ReorderFragment;

public class Reoder_Activity extends BaseActivity{
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null) {
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.content_frame,
					ReorderFragment.newInstance());
			

			transaction.commit();
		}
	}
	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}

}
