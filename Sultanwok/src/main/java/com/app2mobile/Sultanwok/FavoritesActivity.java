package com.app2mobile.Sultanwok;

import java.io.File;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.Sultanwok.fragment.Cart_favroite_fragment;
import com.app2mobile.Sultanwok.fragment.FavroiteFragment_Tab;
import com.app2mobile.Sultanwok.fragment.Product_favroite_fragment;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
/*
 * Add FavouritesFragment to FavoritesActivity
 */
public class FavoritesActivity extends FragmentActivity {
	private ViewPager mViewPager;
    private FavoriteTabAdapter mTabAdapter;
    private FavroiteFragment_Tab mTabLayout;
    protected TextView mTitleTxt, mCartCountTxt,circle;

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
	RelativeLayout rel;
	
	 @Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.fragment_favourites);
			mCartCountTxt = (TextView) findViewById(R.id.cartItems);
//			LayoutParams params = (LayoutParams) mCartCountTxt.getLayoutParams();
//			mCartCountTxt.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
//			int maximumWidth = Math.max(mCartCountTxt.getMeasuredWidth(),
//					mCartCountTxt.getMeasuredHeight());
//			params.width = maximumWidth;
//			params.height = maximumWidth;
//			mCartCountTxt.setLayoutParams(params);
			
			mMenuImg=(ImageView)findViewById(R.id.menuImage);
			mCartImg=(ImageView)findViewById(R.id.cartImage);
			mCartCountTxt=(TextView)findViewById(R.id.cartItems);
			circle=(TextView)findViewById(R.id.circle);
			circle.setVisibility(View.INVISIBLE);
			mCartCountTxt.setVisibility(View.INVISIBLE);
			mMenuImg.setVisibility(View.INVISIBLE);
			
			rel=(RelativeLayout)findViewById(R.id.root_view_main);
			File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
			//mCartImg.setVisibility(View.INVISIBLE);
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
			Drawable dr = new BitmapDrawable(bmp);
			rel.setBackground(dr);
			mAppPrefs = BaseActivity.mAppPref;
			mAppPref = getSharedPreferences(ConstantsUrl.APP_PREF, 0);
			setUpMenu();
			mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
			TextView mTitleTxt=(TextView)findViewById(R.id.title);
			mTitleTxt.setText(getString(R.string.favourites));
			mTabLayout = (FavroiteFragment_Tab) findViewById(R.id.tab_layout);
			mTabAdapter = new FavoriteTabAdapter(getSupportFragmentManager());
			mViewPager.setAdapter(mTabAdapter);
			mTabLayout.createTabs();
			mViewPager.addOnPageChangeListener((new TabLayout.TabLayoutOnPageChangeListener(mTabLayout)));
			 mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
		            @Override
		            public void onTabSelected(TabLayout.Tab tab) {
		                mViewPager.setCurrentItem(tab.getPosition());
		                TabLayout.Tab tab1=mTabLayout.getTabAt(tab.getPosition());
		               tab1.select();
		            }
		 
		            @Override
		            public void onTabUnselected(TabLayout.Tab tab) {
		                //  nop
		            	
		            }
		 
		            @Override
		            public void onTabReselected(TabLayout.Tab tab) {
		            }
		        });
			 mCartImg.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
			 mMenuImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getApplicationContext(),
							CheckOutProductListActivity.class);
					startActivity(intent);
				}
			});
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
				resideMenu.attachToActivity(FavoritesActivity.this);
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

//				findViewById(R.id.cartImage).setOnClickListener(
//						new View.OnClickListener() {
//							@Override
//							public void onClick(View view) {
//								resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//							}
//						});
//				findViewById(R.id.title_bar_right_menu).setOnClickListener(
//						new View.OnClickListener() {
//							@Override
//							public void onClick(View view) {
//								resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//							}
//						});
			}
		  private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		        @Override
		        public void openMenu() {
		          //  Toast.makeText(getActivity(), "Menu is opened!", Toast.LENGTH_SHORT).show();
//		        	if (mAppPref.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
//		        		resideMenu.setMenuItems(menuItems, direction)
//		        		resideMenu.addMenuItem(changed_pass, ResideMenu.DIRECTION_LEFT);
//		        		}
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
