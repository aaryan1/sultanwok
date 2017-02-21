package com.app2mobile.Sultanwok;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.app2mobile.Sultanwok.fragment.UserProfileFragment;

public class UserProfileActivity extends BaseActivity {
	private UserProfileFragment profileframent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

	
		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			
			transaction.replace(R.id.content_frame, UserProfileFragment.newInstance());
			transaction.commit();
		} 
		
//		mListview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//System.out.print("click on navigation");
//				Intent intent;
//				if (position < 6) {
//					// if (savedInstanceState == null) {
//					Fragment fragment = null;
//
//					// }
//					switch (position) {
//					case 0:
//					
//						intent = new Intent(getBaseContext(),
//								CategoryActivity.class);
//						startActivity(intent);
//						finish();
//					
//
//					break;
//					case 1:
//						// fragment = new UserProfileFragment();
//						if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
//								false)) {
//							intent = new Intent(getBaseContext(),
//									UserProfileActivity.class);
//							startActivity(intent);
//							finish();
//						} else {
//							intent = new Intent(getBaseContext(),
//									LoginSignupActivity.class);
//							startActivityForResult(intent,
//									ConstantsUrl.PROFILE);
//						}
//
//						break;
//
//					case 2:
//						// fragment = FavoritesFragment.newInstance();
//						intent = new Intent(getBaseContext(),
//								FavoritesActivity.class);
//						startActivity(intent);
//						finish();
//						break;
//					case 3:
//						// fragment = LocationFragment.newInstance();
//						if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
//								false)) {
//							// fragment =
//							// OrderHistoryFragment.newInstance();
//							intent = new Intent(getBaseContext(),
//									OrderHistoryActivity.class);
//							startActivity(intent);
//							finish();
//						} else {
//							intent = new Intent(getBaseContext(),
//									LoginSignupActivity.class);
//							startActivityForResult(intent,
//									ConstantsUrl.ORDERHISTORY);
//						}
//						break;
//					case 4:
//						intent = new Intent(getBaseContext(),
//								LocationActivity.class);
//						startActivity(intent);
//						finish();
//						break;
//					
//					
//					}
//					// getSupportFragmentManager().popBackStackImmediate();
//					// if (fragment != null)
//					// getSupportFragmentManager().beginTransaction()
//					// .add(R.id.content_frame, fragment)
//					// // .addToBackStack(null)
//					// .commit();
//
//				} else if (position == (mNavigationAdapter.getCount() - 2)) {
//					intent = new Intent(getBaseContext(),
//							ChangePasswordActivity.class);
//					startActivity(intent);
//				} else if (position == (mNavigationAdapter.getCount() - 1)) {
//					if (!mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
//						// getSupportFragmentManager().beginTransaction()
//						// .add(R.id.content_frame,
//						// LoginFragment.newInstance())
//						// // .addToBackStack(null)
//						// .commit();
//						intent = new Intent(getBaseContext(),
//								LoginSignupActivity.class);
//						startActivityForResult(intent, ConstantsUrl.LOGIN);
//
//					} else {
//						AppUtiles.getInstance().clearPrefrence(mAppPref);
//						mNavigationAdapter.notifyDataSetChanged();
//						// navDrawerItems.remove(navDrawerItems.size() - 2);
//						// mNavigationAdapter.notifyDataSetChanged();
//					}
//				}
//				mDrawerLayout.closeDrawer(Gravity.LEFT);
//			}
//		});
	}
	

	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	((Global)UserProfileActivity.this.getApplicationContext()).setIs_from_payment_information_page("false");

}
	
}
