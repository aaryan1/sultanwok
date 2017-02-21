package com.app2mobile.Sultanwok;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;

import com.app2mobile.Sultanwok.fragment.LoginFragment;

public class LoginSignupActivity extends BaseActivity {
	private FragmentTabHost mTabHost;
//	public static String isProfile;
//public static int isCart=0;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

	super.onCreate(savedInstanceState);
	
//	getWindow().setLayout(LayoutParams.MATCH_PARENT,
//		LayoutParams.MATCH_PARENT);
//
//	Bundle bundle = getIntent().getExtras();
	if (savedInstanceState == null) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content_frame,
				LoginFragment.newInstance());

		transaction.commit();
	}
	}

	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_login_signup);
//		getWindow().setLayout(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT);
//		
//		Bundle bundle = getIntent().getExtras();
//
//		
//		View view = createTabView(this, getString(R.string.login));
//		// view.setFocusable(true);
//		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
//		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//
//		mTabHost.addTab(mTabHost.newTabSpec("Login").setIndicator(view),
//				LoginFragment.class, null);
//
//		view = createTabView(this, getString(R.string.registerNewUser));
//		view.setBackgroundResource(R.drawable.tab_selector_right);
//		// view.setFocusable(true);
//
//		mTabHost.addTab(mTabHost.newTabSpec("Signup").setIndicator(view),
//				SignupFragment.class, null);
//		// view = new MyView(this, R.drawable.viewlist_tab_sel,
//		// R.drawable.viewlist_tab, "");
//
//		mTabHost.setCurrentTab(0);
//		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
//
//			@Override
//			public void onTabChanged(String tabId) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//
//	}
//
//
//
//	private View createTabView(final Context context, final String text) {
//		View view = LayoutInflater.from(context).inflate(R.layout.tab_widget,
//				null);
//		TextView tv = (TextView) view.findViewById(R.id.tabsText);
//		tv.setText(text);
//		return view;
//	}
}
