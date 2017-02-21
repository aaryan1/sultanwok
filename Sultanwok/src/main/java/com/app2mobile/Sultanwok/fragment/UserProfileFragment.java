package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.util.List;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.Sultanwok.Address_Adapter;
import com.app2mobile.Sultanwok.CreditCard_Adapter;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.Sultanwok.User_Tablayout;
import com.app2mobile.metadata.Credit_Card_Metadata;
import com.app2mobile.metadata.Single_Address_Metadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.special.ResideMenu.ResideMenu;

public class UserProfileFragment extends BaseFragment {
	private TextView  streetView, cityView,stateView, countryView;
	private EditText profileName, profilePhone,profileLName;
	private Button save_change,add_card,add_address;
	private RecyclerView recycle;
	private CreditCard_Adapter credit_adapter;
	  private List<Credit_Card_Metadata> list;
	  Address_Adapter single_address;
	  List<Single_Address_Metadata> address_list;
	  RecyclerView address_recycle;
	  TextWatcher mDateEntryWatcher;
	  Dialog	expressDialog;
	  boolean isValid = true;
		private SharedPreferences appPrefs;
		private SharedPreferences.Editor prefs_editor;
		TextInputLayout cardExpLayout;
		ViewPager mViewPager;
		RelativeLayout rel;
UserProfile_Tab mTabAdapter;
User_Tablayout TabLayout;
private static UserProfileFragment instance=null;
 EditText exp;
	public static UserProfileFragment newInstance() {
		if(instance==null)
		{
		instance= new UserProfileFragment();
		}
		return instance;
	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_profile;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mTitleTxt.setText("My Profile");
		mCartImg.setVisibility(View.INVISIBLE);
		mCartCountTxt.setVisibility(View.INVISIBLE);
		circle.setVisibility(View.INVISIBLE);
		// spaceImageView.setVisibility(View.GONE);
//		

//		
//		add_card=(Button)view.findViewById(R.id.add_card);
//		add_address=(Button)view.findViewById(R.id.add_address);
//		recycle= (RecyclerView)view.findViewById(R.id.card_list);
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
		appPrefs = getActivity().getSharedPreferences(ConstantsUrl.APP_PREF, 0);
		prefs_editor = appPrefs.edit();
		
//		address_recycle=(RecyclerView)view.findViewById(R.id.address);
//		save_change.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
//		add_card.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
//		add_address.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
//		list= new ArrayList<Credit_Card_Metadata>();
//		address_list= new ArrayList<Single_Address_Metadata>();
		rel=(RelativeLayout)view.findViewById(R.id.rel1);
		 File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
			
			Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
			Drawable dr = new BitmapDrawable(bmp);
			
			rel.setBackgroundDrawable(dr);
			
			mViewPager = (ViewPager) view.findViewById(R.id.viewpager_main);
			
			TabLayout = (User_Tablayout) view.findViewById(R.id.tab_layout);
			
			mTabAdapter = new UserProfile_Tab(getActivity().getSupportFragmentManager());
			mViewPager.setAdapter(mTabAdapter);
			TabLayout.createTabs();
			changeTabsFont();
			mViewPager.addOnPageChangeListener((new TabLayout.TabLayoutOnPageChangeListener(TabLayout)));
			 TabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
		            @Override
		            public void onTabSelected(TabLayout.Tab tab) {
		                mViewPager.setCurrentItem(tab.getPosition());
		                TabLayout.Tab tab1=TabLayout.getTabAt(tab.getPosition());
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
	}
	  public class UserProfile_Tab extends FragmentStatePagerAdapter {
	        public UserProfile_Tab(FragmentManager fm) {
	            super(fm);
	        }
	     
	        @Override
	        public Fragment getItem(int position) {
	            switch (position) {
	                case 0:
	                    return new User_Profile_Tab();
	                case 1:
	                    return new User_Address_Tab();
	                case 2:
	                	return new User_SavedCard_Tab();
	                default:
	                    return null;
	            }
	        }
	     
	        @Override
	        public int getCount() {
	            return 3;
	        }
	        
	    }
	  private void changeTabsFont() {

	        ViewGroup vg = (ViewGroup) TabLayout.getChildAt(0);
	        int tabsCount = vg.getChildCount();
			Typeface type= Typeface.createFromAsset(getActivity().getAssets(), "Sintony-Regular.ttf");

	        for (int j = 0; j < tabsCount; j++) {
	            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
	            int tabChildsCount = vgTab.getChildCount();
	            for (int i = 0; i < tabChildsCount; i++) {
	                View tabViewChild = vgTab.getChildAt(i);
	                if (tabViewChild instanceof TextView) {
	                    ((TextView) tabViewChild).setTypeface(type);
	                }
}
	        }
	  }
}
