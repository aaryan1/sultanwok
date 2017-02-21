package com.app2mobile.Sultanwok.fragment;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.CategoryActivity;
import com.app2mobile.Sultanwok.ChangePasswordActivity;
import com.app2mobile.Sultanwok.CheckOutProductListActivity;
import com.app2mobile.Sultanwok.FavoritesActivity;
import com.app2mobile.Sultanwok.LoginSignupActivity;
import com.app2mobile.Sultanwok.Manual_Location;
import com.app2mobile.Sultanwok.OrderHistoryActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.Sultanwok.UserProfileActivity;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;




public abstract class BaseFragment extends Fragment {
	private int mLayout;
	// protected HomeActivity homeActivity;
	public Toolbar mToolbar;
	 
	protected TextView mTitleTxt, mCartCountTxt,circle;
	protected ImageView mMenuImg, mEditImg, mCartImg;
	public TabLayout mTabLayout,subTabLayout;
	public SharedPreferences mAppPrefs;
	public ListView mListViewObj;
	public ResideMenu resideMenu;
	private ResideMenuItem home;
	private ResideMenuItem order_history;
	private ResideMenuItem profile;
	private ResideMenuItem fav;
	private ResideMenuItem location;
	private ResideMenuItem changed_pass;
	public ResideMenuItem login;
	private ResideMenuItem cart;
	Drawable bitmapOrg,bitmapOrg1;
	
	public SharedPreferences mAppPref, mRestaurantDetailPrefs;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// homeActivity = (HomeActivity) getActivity();
		mAppPrefs = ((BaseActivity) getActivity()).mAppPref;
		mAppPref = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);

		setUpMenu();
	
	}
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mLayout = getLayout();
		mAppPref = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		mRestaurantDetailPrefs = getActivity().getSharedPreferences(
				ConstantsUrl.RESTAURANTDETAILPREFS, 0);
		
		if (mLayout != 0) {
			return inflater.inflate(mLayout, container, false);
		}
		return null;
	}

	@SuppressLint("NewApi") @Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
		mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
        subTabLayout=(TabLayout)view.findViewById(R.id.subtabs);
		
		//mListViewObj = ((BaseActivity) getActivity()).mListview;
		if (mToolbar != null){
			((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
		}
		Window window = getActivity().getWindow();

		// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

		// finally change the color
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP){
		    // Do something for lollipop and above versions
			window.setStatusBarColor(Color.parseColor("#000000"));
			window.setNavigationBarColor(Color.parseColor("#000000"));
		} 
		
		mTabLayout.setVisibility(View.GONE);
		//subTabLayout.setVisibility(View.GONE);
		mTitleTxt = (TextView) view.findViewById(R.id.title);
		mTitleTxt.setText(ConstantsUrl.STORE_NAME);
		Typeface type= Typeface.createFromAsset(getActivity().getAssets(), "Sintony-Regular.ttf");
		mTitleTxt.setTypeface(type);
		mTitleTxt.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		mCartCountTxt = (TextView) view.findViewById(R.id.cartItems);
		LayoutParams params = (LayoutParams) mCartCountTxt.getLayoutParams();
		mCartCountTxt.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int maximumWidth = Math.max(mCartCountTxt.getMeasuredWidth(),
				mCartCountTxt.getMeasuredHeight());
		params.width = maximumWidth;
		params.height = maximumWidth;
		mCartCountTxt.setLayoutParams(params);
		mMenuImg = (ImageView) view.findViewById(R.id.cartImage);
		mCartImg = (ImageView) view.findViewById(R.id.menuImage);
		mEditImg = (ImageView) view.findViewById(R.id.editImage);
		
			bitmapOrg = this.getResources().getDrawable(R.drawable.shopping2);
			bitmapOrg1=this.getResources().getDrawable(R.drawable.setting);
			bitmapOrg.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.MULTIPLY);
			bitmapOrg1.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.MULTIPLY);
			mMenuImg.setImageDrawable(bitmapOrg1);
			mCartImg.setImageDrawable(bitmapOrg);
			 
		circle=(TextView)view.findViewById(R.id.circle);
		
		circle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentCart = new Intent(getActivity(),
						CheckOutProductListActivity.class);
				startActivity(intentCart);
			}
		});
		mCartCountTxt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentCart = new Intent(getActivity(),
						CheckOutProductListActivity.class);
						startActivity(intentCart);	
			}
		});
		
		mMenuImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//((BaseActivity) getActivity()).OpenDrawer();
				Log.e("sliding", "sliding menu click");
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});
		mCartImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentCart = new Intent(getActivity(),
						CheckOutProductListActivity.class);
				startActivity(intentCart);
			}
		});
	}

	
	@SuppressLint("NewApi") private void setUpMenu() {

		// attach to current activity;
		resideMenu = new ResideMenu(getActivity());
		resideMenu.setUse3D(true);
	File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
		
		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		Drawable dr = new BitmapDrawable(bmp);
		resideMenu.setBackground(dr);
	//	resideMenu.setElevation(10);
		
		resideMenu.setShadowVisible(true);
		resideMenu.attachToActivity(getActivity());
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.8f);

		// create menu items;
		home = new ResideMenuItem(getActivity(), R.drawable.icon_home, "Home");
		
		location = new ResideMenuItem(getActivity(), R.drawable.loc,
				"Location");
		order_history = new ResideMenuItem(getActivity(), R.drawable.history, "Order History");
		profile = new ResideMenuItem(getActivity(), R.drawable.login, "My Profile");
		fav = new ResideMenuItem(getActivity(), R.drawable.fav, "Favorites");
		cart=new ResideMenuItem(getActivity(),R.drawable.rewards,"Cart");
		//changed_pass = new ResideMenuItem(getActivity(), R.drawable.icon_home, "Change Password");

		/*if(mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,false)) {
			changed_pass = new ResideMenuItem(getActivity(), R.drawable.icon_home, "Change Password");
		}*/

		login = new ResideMenuItem(getActivity(), R.drawable.login, "Login");
		if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,false)) {
			login.setTitle("Logout");
			login.setIcon(R.drawable.logout);
		}else{
			login.setTitle("Login");
			login.setIcon(R.drawable.login);
		}
		
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						CategoryActivity.class);
				startActivity(intent);
				resideMenu.closeMenu();
			}
		});
		fav.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
						false)) {
					Intent intent = new Intent(getActivity(),
							LoginSignupActivity.class);
					startActivity(intent);
					resideMenu.closeMenu();
				}else{
				Intent intent = new Intent(getActivity(),
						FavoritesActivity.class);
				startActivity(intent);
				resideMenu.closeMenu();
				}
			}
		});
		cart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						CheckOutProductListActivity.class);
				startActivity(intent);
				resideMenu.closeMenu();
			}
		});
		order_history.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
						false)) {
					Intent intent = new Intent(getActivity(),
							LoginSignupActivity.class);
					startActivity(intent);
					resideMenu.closeMenu();
				}else{
				Intent intent = new Intent(getActivity(),
						OrderHistoryActivity.class);
				startActivity(intent);
				resideMenu.closeMenu();
				}
			}
		});
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
						false)) {
					Intent intent = new Intent(getActivity(),CategoryActivity.class);
					//startActivityForResult(intent, ConstantsUrl.LOGIN);
				AppUtiles.getInstance().clearPrefrence(mAppPref);
				resideMenu.closeMenu();
				login.setTitle("Login");
				}else{
					Intent intent = new Intent(getActivity(),LoginSignupActivity.class);
					//startActivityForResult(intent, ConstantsUrl.LOGIN);
					startActivity(intent);
					resideMenu.closeMenu();
					login.setTitle("Logout");
					
				}
			}
		});
		/*
		if(mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,false)) {
			changed_pass.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (!mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
						// getSupportFragmentManager().beginTransaction()
						// .add(R.id.content_frame,
						// LoginFragment.newInstance())
						// // .addToBackStack(null)
						// .commit();
						Intent intent = new Intent(getActivity(),
								LoginSignupActivity.class);
						startActivityForResult(intent, ConstantsUrl.LOGIN);
						resideMenu.closeMenu();
					} else {
						Intent intent = new Intent(getActivity(),
								ChangePasswordActivity.class);
						startActivity(intent);
						resideMenu.closeMenu();
						//mNavigationAdapter.notifyDataSetChanged();
						// navDrawerItems.remove(navDrawerItems.size() - 2);
						// mNavigationAdapter.notifyDataSetChanged();
					}
				}
			});
		}*/

location.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getActivity(),
				Manual_Location.class);
		startActivity(intent);
		resideMenu.closeMenu();
	}
});
profile.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
				false)) {
			Intent intent = new Intent(getActivity(),
					UserProfileActivity.class);
			startActivity(intent);
			resideMenu.closeMenu();
		} else {
			Intent intent = new Intent(getActivity(),
					LoginSignupActivity.class);
			startActivityForResult(intent,
					ConstantsUrl.PROFILE);
			resideMenu.closeMenu();
		}

	}
});

		resideMenu.addMenuItem(home, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(location, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(cart, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(order_history, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(profile, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(fav, ResideMenu.DIRECTION_LEFT);
		if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
    	//	resideMenu.addMenuItem(changed_pass, ResideMenu.DIRECTION_LEFT);
    		}
	
		resideMenu.addMenuItem(login, ResideMenu.DIRECTION_LEFT);

		// You can disable a direction by setting ->
		 resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

//		findViewById(R.id.cartImage).setOnClickListener(
//				new View.OnClickListener() {
//					@Override
//					public void onClick(View view) {
//						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//					}
//				});
//		findViewById(R.id.title_bar_right_menu).setOnClickListener(
//				new View.OnClickListener() {
//					@Override
//					public void onClick(View view) {
//						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//					}
//				});
	}
	
	 private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
	        @Override
	        public void openMenu() {
	          //  Toast.makeText(getActivity(), "Menu is opened!", Toast.LENGTH_SHORT).show();
//	        	if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
//	        		resideMenu.setMenuItems(menuItems, direction)
//	        		resideMenu.addMenuItem(changed_pass, ResideMenu.DIRECTION_LEFT);
//	        		}
	        	if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN,
						false)) {
					
	        		login.setTitle("Logout");
				}else{
					login.setTitle("Login");
				}
	        }

	        @Override
	        public void closeMenu() {
	           // Toast.makeText(getActivity(), "Menu is closed!", Toast.LENGTH_SHORT).show();
	        }
	    };
	
	  public ResideMenu getResideMenu(){
	        return resideMenu;
	    }
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mCartCountTxt != null) {
			AppUtiles.getInstance().setCartItems(mCartCountTxt, getActivity());
		}
	}

	public void closeNavigationDrawer() {
	//	((BaseActivity) getActivity()).closeDrawer();
	}

	public abstract int getLayout();

	
	
}
