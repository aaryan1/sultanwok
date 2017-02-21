package com.app2mobile.Sultanwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.app2mobile.Sultanwok.fragment.SubCategoryNProductsFragment;
import com.app2mobile.utiles.ConstantsUrl;

public class ProductActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			System.out.println("ProductActivity");
			transaction.replace(R.id.content_frame,
					SubCategoryNProductsFragment.newInstance());
			transaction.commit();
		}
//		mListview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				System.out.println("click out.....");
//				Intent intent;
//				if (position < 4) {
//					// if (savedInstanceState == null) {
//					Fragment fragment = null;
//
//					// }
//					switch (position) {
//					case 0:
//						// fragment = new UserProfileFragment();
//						if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
//							intent = new Intent(getBaseContext(),
//									UserProfileActivity.class);
//							startActivity(intent);
//						} else {
//							intent = new Intent(getBaseContext(),
//									LoginSignupActivity.class);
//							startActivityForResult(intent, ConstantsUrl.PROFILE);
//						}
//
//						break;
//
//					case 1:
//						// fragment = FavoritesFragment.newInstance();
//						intent = new Intent(getBaseContext(),
//								FavoritesActivity.class);
//						startActivity(intent);
//						break;
//					case 2:
//						// fragment = LocationFragment.newInstance();
//						intent = new Intent(getBaseContext(),
//								LocationActivity.class);
//						startActivity(intent);
//						break;
//					case 3:
//						if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
//							// fragment =
//							// OrderHistoryFragment.newInstance();
//							intent = new Intent(getBaseContext(),
//									OrderHistoryActivity.class);
//							startActivity(intent);
//						} else {
//							intent = new Intent(getBaseContext(),
//									LoginSignupActivity.class);
//							startActivityForResult(intent,
//									ConstantsUrl.ORDERHISTORY);
//						}
//						break;
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		System.out.println("return text back.......");
		mNavigationAdapter.notifyDataSetChanged();
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case ConstantsUrl.LOGIN:

				break;

			case ConstantsUrl.ORDERHISTORY:

				Intent orderHistoryIntent = new Intent(getBaseContext(),
						OrderHistoryActivity.class);
				startActivity(orderHistoryIntent);
				break;
			case ConstantsUrl.PROFILE:
				System.out.println("profile..........");
				Intent intent = new Intent(getBaseContext(),
						UserProfileActivity.class);
				startActivity(intent);
				break;
			}
		}
	}
}
