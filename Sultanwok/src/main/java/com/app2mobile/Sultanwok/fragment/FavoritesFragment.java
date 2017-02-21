package com.app2mobile.Sultanwok.fragment;

import java.io.File;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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



public class FavoritesFragment extends FragmentActivity {
	   private ViewPager mViewPager;
		protected TextView mTitleTxt, mCartCountTxt,circle;

	    private FavoriteTabAdapter mTabAdapter;
	    private FavroiteFragment_Tab mTabLayout;
	    private ImageView mMenuImg,mCartImg;
		public SharedPreferences mAppPref, mRestaurantDetailPrefs,mAppPrefs;
		public ResideMenu resideMenu;
		private ResideMenuItem home;
		private ResideMenuItem order_history;
		private ResideMenuItem profile;
		private ResideMenuItem fav;
		private ResideMenuItem location;
		private ResideMenuItem changed_pass;
		public ResideMenuItem login;
		private ResideMenuItem cart;
	    public static FavoritesFragment newInstance() {

			return new FavoritesFragment();
		}
	 @SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_favourites);
		mCartCountTxt = (TextView) findViewById(R.id.cartItems);
		
		 mTitleTxt = (TextView) findViewById(R.id.title);
		mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
		mTabLayout = (FavroiteFragment_Tab) findViewById(R.id.tab_layout);
		mMenuImg=(ImageView)findViewById(R.id.menuImage);
		mCartImg=(ImageView)findViewById(R.id.cartImage);
		circle=(TextView)findViewById(R.id.circle);
		mMenuImg.setVisibility(View.INVISIBLE);
		mCartCountTxt.setVisibility(View.INVISIBLE);
		circle.setVisibility(View.INVISIBLE);
		mAppPrefs = ((BaseActivity) getApplicationContext()).mAppPref;
		mAppPref = getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		Window window = getWindow();

		// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

		// finally change the color
		window.setStatusBarColor(Color.parseColor("#000000"));
		window.setNavigationBarColor(Color.parseColor("#000000"));
		setUpMenu();
		 mTitleTxt.setText("Favorites");
		 mCartImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});
		 
		mTabAdapter = new FavoriteTabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mTabAdapter);
	}
	 
	  public class FavoriteTabAdapter extends FragmentStatePagerAdapter {
	        public FavoriteTabAdapter(FragmentManager fm) {
	            super(fm);
	        }
	     
	        @Override
	        public Fragment getItem(int position) {
	            switch (position) {
	                case 0:
	                    return new Product_favroite_fragment();
	                case 1:
	                    return new Cart_favroite_fragment();
	                default:
	                    return null;
	            }
	        }
	     
	        @Override
	        public int getCount() {
	            return 2;
	        }
	    }
	  
	  private void setUpMenu() {

			// attach to current activity;
			resideMenu = new ResideMenu(getApplicationContext());
			resideMenu.setUse3D(true);
		File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
			
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
			Drawable dr = new BitmapDrawable(bmp);
			resideMenu.setBackground(dr);
			resideMenu.attachToActivity(FavoritesFragment.this);
			resideMenu.setMenuListener(menuListener);
			// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
			// 150dip.
			resideMenu.setScaleValue(0.8f);

			// create menu items;
			home = new ResideMenuItem(getApplicationContext(), R.drawable.icon_home, "Home");
			
			location = new ResideMenuItem(getApplicationContext(), R.drawable.loc,
					"Location");
			order_history = new ResideMenuItem(getApplicationContext(), R.drawable.history, "Order History");
			profile = new ResideMenuItem(getApplicationContext(), R.drawable.login, "My Profile");
			fav = new ResideMenuItem(getApplicationContext(), R.drawable.fav, "Favorites");
			cart=new ResideMenuItem(getApplicationContext(),R.drawable.rewards,"cart");
			changed_pass = new ResideMenuItem(getApplicationContext(), R.drawable.icon_home, "Changed Password");
			login = new ResideMenuItem(getApplicationContext(), R.drawable.login, "Login");
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
					Intent intent = new Intent(getApplicationContext(),
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
						Intent intent = new Intent(getApplicationContext(),
								LoginSignupActivity.class);
						startActivity(intent);
						resideMenu.closeMenu();
					}else{
					Intent intent = new Intent(getApplicationContext(),
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
					Intent intent = new Intent(getApplicationContext(),
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
						Intent intent = new Intent(getApplicationContext(),
								LoginSignupActivity.class);
						startActivity(intent);
						resideMenu.closeMenu();
					}else{
					Intent intent = new Intent(getApplicationContext(),
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
						Intent intent = new Intent(getApplicationContext(),CategoryActivity.class);
						//startActivityForResult(intent, ConstantsUrl.LOGIN);
					AppUtiles.getInstance().clearPrefrence(mAppPref);
					resideMenu.closeMenu();
					login.setTitle("Login");
					}else{
						Intent intent = new Intent(getApplicationContext(),LoginSignupActivity.class);
						startActivityForResult(intent, ConstantsUrl.LOGIN);
						resideMenu.closeMenu();
					
						
					}
				}
			});
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
						Intent intent = new Intent(getApplicationContext(),
								LoginSignupActivity.class);
						startActivityForResult(intent, ConstantsUrl.LOGIN);
						resideMenu.closeMenu();
					} else {
						Intent intent = new Intent(getApplicationContext(),
								ChangePasswordActivity.class);
						startActivity(intent);
						resideMenu.closeMenu();
						//mNavigationAdapter.notifyDataSetChanged();
						// navDrawerItems.remove(navDrawerItems.size() - 2);
						// mNavigationAdapter.notifyDataSetChanged();
					}
				}
			});
	location.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getApplicationContext(),
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
				Intent intent = new Intent(getApplicationContext(),
						UserProfileActivity.class);
				startActivity(intent);
				resideMenu.closeMenu();
			} else {
				Intent intent = new Intent(getApplicationContext(),
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
	    		resideMenu.addMenuItem(changed_pass, ResideMenu.DIRECTION_LEFT);
	    		}
		
			resideMenu.addMenuItem(login, ResideMenu.DIRECTION_LEFT);

			// You can disable a direction by setting ->
			 resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

//			findViewById(R.id.cartImage).setOnClickListener(
//					new View.OnClickListener() {
//						@Override
//						public void onClick(View view) {
//							resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//						}
//					});
//			findViewById(R.id.title_bar_right_menu).setOnClickListener(
//					new View.OnClickListener() {
//						@Override
//						public void onClick(View view) {
//							resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//						}
//					});
		}
	  private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
	        @Override
	        public void openMenu() {
	          //  Toast.makeText(getActivity(), "Menu is opened!", Toast.LENGTH_SHORT).show();
//	        	if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
//	        		resideMenu.setMenuItems(menuItems, direction)
//	        		resideMenu.addMenuItem(changed_pass, ResideMenu.DIRECTION_LEFT);
//	        		}
	        }

	        @Override
	        public void closeMenu() {
	           // Toast.makeText(getActivity(), "Menu is closed!", Toast.LENGTH_SHORT).show();
	        }
	    };
	
	  public ResideMenu getResideMenu(){
	        return resideMenu;
	    }
}
