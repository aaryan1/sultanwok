package com.app2mobile.Sultanwok;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.Sultanwok.fragment.BaseFragment;
import com.app2mobile.metadata.NavDrawerItem;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;


/**
 * It' a base class for all activity
 * 
 * 
 */
public abstract class BaseActivity extends AppCompatActivity  {
	protected abstract int getLayoutResource();

	private int mLayoutId = -1;
	protected DrawerLayout mDrawerLayout;
	public ListView mListview;
	public FrameLayout mFrameLayout;
	public static SharedPreferences mAppPref;
	public SharedPreferences mRestaurantDetailPrefs;
	protected ActionBarDrawerToggle mDrawerToggle;
	protected ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
	public NavigationAdapter mNavigationAdapter;
	private BaseFragment mContext;
	private BaseActivity context;
//	private ResideMenu resideMenu;
//	private ResideMenuItem home;
//	private ResideMenuItem order_history;
//	private ResideMenuItem profile;
//	private ResideMenuItem fav;
//	private ResideMenuItem location;
//	private ResideMenuItem changed_pass;
//	private ResideMenuItem login;
	private ImageView mCartImg ;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mLayoutId = getLayoutResource();
		mAppPref = getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		mRestaurantDetailPrefs = getSharedPreferences(
				ConstantsUrl.RESTAURANTDETAILPREFS, 0);
		context = this;
		if (mLayoutId != -1) {

			setContentView(mLayoutId);
			mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
			mListview = (ListView) findViewById(R.id.right_drawer);
			
			//addMenuElements();
			mNavigationAdapter = new NavigationAdapter();
			mListview.setAdapter(mNavigationAdapter);
			mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
					R.string.app_name, R.string.app_name) {
				@Override
				public void onDrawerClosed(View drawerView) {
					// TODO Auto-generated method stub
					super.onDrawerClosed(drawerView);
					 if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)
					 || navDrawerItems.size() >= 6) {
					
					 navDrawerItems.remove(navDrawerItems.size() - 2);
					
					 }
					 navDrawerItems.remove(navDrawerItems.size() - 1);

				}

				@Override
				public void onDrawerOpened(View drawerView) {
					// TODO Auto-generated method stub
					super.onDrawerOpened(drawerView);

					 navDrawerItems
					 .add(new NavDrawerItem(
					 mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
					 false) ? getString(R.string.sign_Out)
					 : getString(R.string.sign_In),
					 false));
					 if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
					
					 navDrawerItems.add(navDrawerItems.size() - 1,
					 new NavDrawerItem("Change Password", false));
					
					 }
					mNavigationAdapter.notifyDataSetChanged();

				}
			};
			mDrawerLayout.setDrawerListener(mDrawerToggle);
			mListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

					Intent intent;
					if (position < 6) {
						// if (savedInstanceState == null) {
						Fragment fragment = null;

						// }
						switch (position) {
						case 0:
						
							intent = new Intent(getBaseContext(),
									CategoryActivity.class);
							startActivity(intent);
							finish();
						

						break;
						case 1:
							// fragment = new UserProfileFragment();
							if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
									false)) {
								intent = new Intent(getBaseContext(),
										UserProfileActivity.class);
								startActivity(intent);
								finish();
							} else {
								intent = new Intent(getBaseContext(),
										LoginSignupActivity.class);
								startActivityForResult(intent,
										ConstantsUrl.PROFILE);
							}

							break;

						case 2:
							// fragment = FavoritesFragment.newInstance();
							intent = new Intent(getBaseContext(),
									FavoritesActivity.class);
							startActivity(intent);
							finish();
							break;
						case 3:
							// fragment = LocationFragment.newInstance();
							if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
									false)) {
								// fragment =
								// OrderHistoryFragment.newInstance();
								intent = new Intent(getBaseContext(),
										OrderHistoryActivity.class);
								startActivity(intent);
								finish();
							} else {
								intent = new Intent(getBaseContext(),
										LoginSignupActivity.class);
								startActivityForResult(intent,
										ConstantsUrl.ORDERHISTORY);
							}
							break;
						case 4:
							intent = new Intent(getBaseContext(),
									LocationActivity.class);
							startActivity(intent);
							finish();
							break;
						
						
						}
						// getSupportFragmentManager().popBackStackImmediate();
						// if (fragment != null)
						// getSupportFragmentManager().beginTransaction()
						// .add(R.id.content_frame, fragment)
						// // .addToBackStack(null)
						// .commit();

					} else if (position == (mNavigationAdapter.getCount() - 2)) {
						intent = new Intent(getBaseContext(),
								ChangePasswordActivity.class);
						startActivity(intent);
					} else if (position == (mNavigationAdapter.getCount() - 1)) {
						if (!mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
							// getSupportFragmentManager().beginTransaction()
							// .add(R.id.content_frame,
							// LoginFragment.newInstance())
							// // .addToBackStack(null)
							// .commit();
							intent = new Intent(getBaseContext(),
									LoginSignupActivity.class);
							startActivityForResult(intent, ConstantsUrl.LOGIN);

						} else {
							AppUtiles.getInstance().clearPrefrence(mAppPref);
							mNavigationAdapter.notifyDataSetChanged();
							// navDrawerItems.remove(navDrawerItems.size() - 2);
							// mNavigationAdapter.notifyDataSetChanged();
						}
					}
					mDrawerLayout.closeDrawer(Gravity.LEFT);
				}
			});
		}
	}

	
	public void OpenDrawer() {
	//	mDrawerLayout.openDrawer(Gravity.LEFT);
	}

	public void closeDrawer() {
		//if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
			//mDrawerLayout.closeDrawer(Gravity.LEFT);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		mNavigationAdapter.notifyDataSetChanged();
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case ConstantsUrl.LOGIN:

				break;

			case ConstantsUrl.ORDERHISTORY:

				Intent orderHistoryIntent = new Intent(getBaseContext(),
						OrderHistoryActivity.class);
				startActivity(orderHistoryIntent);
				finish();
				break;
			case ConstantsUrl.PROFILE:
				Intent intent = new Intent(getBaseContext(),
						UserProfileActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	protected void addMenuElements() {
		navDrawerItems
		.add(new NavDrawerItem("Home" ,R.drawable.home_active,true));
		navDrawerItems
		.add(new NavDrawerItem(getString(R.string.profile),R.drawable.profile, true));
		navDrawerItems.add(new NavDrawerItem(getString(R.string.my_favourites),R.drawable.fav,
				true));
		navDrawerItems.add(new NavDrawerItem(getString(R.string.order_history),R.drawable.history,
				true));
		navDrawerItems.add(new NavDrawerItem(getString(R.string.location),R.drawable.history,
				true));
		navDrawerItems.add(new NavDrawerItem("Change Password", false));
		
		
//		navDrawerItems.add(new NavDrawerItem("Rewards Points",R.drawable.rewards,
//				true));
		
		navDrawerItems.add(new NavDrawerItem(mAppPref.getBoolean(
				ConstantsUrl.IS_LOGIN, false) ? getString(R.string.sign_Out)
				: getString(R.string.sign_In),R.drawable.history ,true));
		
		
		
		// if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
		
		// }
	
	}

	public class NavigationAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public NavigationAdapter() {
			// TODO Auto-generated constructor stub
			mInflater = LayoutInflater.from(getBaseContext());
		}

		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			// TODO Auto-generated method stub
			NavDrawerItem data = getItem(position);
			return data.isHeader() ? 0 : 1;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return navDrawerItems.size();
		}

		@Override
		public NavDrawerItem getItem(int position) {
			// TODO Auto-generated method stub
			return navDrawerItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder mHolderObj;
			if (convertView == null) {
				mHolderObj = new ViewHolder();
				if (getItemViewType(position) == 0) {
					convertView = mInflater.inflate(
							R.layout.item_composer_header, null);
					mHolderObj.mTitle = (TextView) convertView
							.findViewById(R.id.header);
				} else {
					convertView = mInflater.inflate(R.layout.row_navigation,
							null);
					mHolderObj.mIcon = (ImageView) convertView
							.findViewById(R.id.icon);
					mHolderObj.mTitle = (TextView) convertView
							.findViewById(R.id.title);
					mHolderObj.mCounerCount = (TextView) convertView
							.findViewById(R.id.counter);
					mHolderObj.mLayout = (RelativeLayout) convertView
							.findViewById(R.id.layout);
				}
				convertView.setTag(mHolderObj);
			} else {
				mHolderObj = (ViewHolder) convertView.getTag();
			}
			NavDrawerItem data = getItem(position);
			if (getItemViewType(position) == 1) {
				// mHolderObj.mIcon.setVisibility(View.GONE);
				// mHolderObj.mCounerCount.setVisibility(View.GONE);
				// mHolderObj.mTitle.setTypeface(Typeface.DEFAULT_BOLD);
				//
				// } else {
				mHolderObj.mIcon.setVisibility(View.VISIBLE);
				mHolderObj.mCounerCount.setVisibility(View.VISIBLE);
				if (data.isIcon()) {
					mHolderObj.mIcon.setImageResource(data.getmIcon());
				} else {
					mHolderObj.mIcon.setVisibility(View.GONE);
				}
				if (data.isCounterVisible()) {
					mHolderObj.mCounerCount.setText(data.getmCount());
				} else {
					mHolderObj.mCounerCount.setVisibility(View.GONE);
				}

			}

			if (position == getCount() - 1) {
				mHolderObj.mTitle
						.setText(mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
								false) ? getString(R.string.sign_Out)
								: getString(R.string.sign_In));
			} else {
				mHolderObj.mTitle.setText(data.getmTitle());
			}
			if (!mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)
					&& (position == getCount() - 2)) {
				mHolderObj.mTitle.setVisibility(View.GONE);

				if (mHolderObj.mLayout != null)
					mHolderObj.mLayout.setVisibility(View.GONE);
				convertView.setVisibility(View.GONE);
			} else {
				mHolderObj.mTitle.setVisibility(View.VISIBLE);
				if (mHolderObj.mLayout != null)
					mHolderObj.mLayout.setVisibility(View.VISIBLE);
				convertView.setVisibility(View.VISIBLE);
			}
			return convertView;
		}
	}

	private class ViewHolder {
		private ImageView mIcon;
		private TextView mTitle, mHeader;
		private TextView mCounerCount;
		private RelativeLayout mLayout;
	}
}
