package com.app2mobile.Sultanwok.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.acplibrary.ACProgressBaseDialog;
import com.app2mobile.metadata.RestaurantCategoryMetadata;
import com.app2mobile.metadata.RestaurantMetadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.special.ResideMenu.ResideMenu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCategoryNProductsFragment extends BaseFragment {
	ImageView	category_image;
	private RestaurantCategoryMetadata category;
	private RestaurantMetadata restaruant;
	private ArrayList<RestaurantCategoryMetadata> subCategoryList;
	private ViewPager mViewPager,submViewPager;
	private ArrayList<RestaurantCategoryMetadata> categorylist;
	private Integer position;
	ACProgressBaseDialog progressDialog;
	public static SubCategoryNProductsFragment newInstance() {
		return new SubCategoryNProductsFragment();
	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_subcategory2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);


		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle != null) {
			position=bundle.getInt("position");
			category = (RestaurantCategoryMetadata) bundle.get("category");
			categorylist=(ArrayList<RestaurantCategoryMetadata>) bundle.get("categorylist");

		}

		if (category != null) {

			          subCategoryList = category.getSubCategoryList();
		}
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//Global.getInstance().trackScreenView("Subcategory Page");

	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		// mTabLayout = (TabLayout) view.findViewById(R.id.tabs);
		mTabLayout.setVisibility(View.VISIBLE);
		mTabLayout.setTabTextColors(Color.GRAY,Color.WHITE);

		mViewPager =   (ViewPager) view.findViewById(R.id.viewPager);
		submViewPager= (ViewPager) view.findViewById(R.id.subviewPager);
		category_image=(ImageView) view.findViewById(R.id.imageView1);

		Picasso.with(getActivity())
				.load(categorylist.get(position).getCatImg())
				.error(R.drawable.place_holder)
				.into(category_image);
		mViewPager.setLeft(10);
		submViewPager.setLeft(10);

		if (category != null) {

			((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(
					ConstantsUrl.STORE_NAME);

			//if (subCategoryList == null || subCategoryList.size() <= 0) {

			if (category.getProductList() != null  || HasSubCategory() ) {
				if(HasSubCategory())
				{
					mViewPager.setVisibility(View.GONE);
					mViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(),1));
					mTabLayout.setupWithViewPager(mViewPager);
					submViewPager.setVisibility(View.VISIBLE);
					submViewPager.setAdapter(new SubPagerAdapter(getChildFragmentManager()));
					subTabLayout.setupWithViewPager(submViewPager);


				}
				else {
					submViewPager.setVisibility(View.GONE);
					mViewPager.setVisibility(View.VISIBLE);
					mViewPager.setAdapter(new PagerAdapter(getChildFragmentManager(), 1));
					mTabLayout.setupWithViewPager(mViewPager);

				}

			}

	          if(HasSubCategory())
			  {
				  if (submViewPager.getAdapter() != null && submViewPager.getAdapter().getCount() > 0) {
					  mTitleTxt.setText(ConstantsUrl.STORE_NAME);



					  final TabLayout.Tab tab = mTabLayout.getTabAt(position);
					  //tab.select();


					  new Handler().postDelayed(
							  new Runnable(){
								  @Override
								  public void run() {
									  //tab.getTabAt(position).select();\
									  tab.select();

								  }
							  } ,10);


					      //TabLayout.Tab subtab = subTabLayout.getTabAt(0);
						  //subtab.select();

						  subTabLayout.setVisibility(View.VISIBLE);
						  subTabLayout.setTabTextColors(Color.GRAY, Color.WHITE);

					  }


				  }

             else {
				  if (mViewPager.getAdapter() != null && mViewPager.getAdapter().getCount() > 0) {
					  mTitleTxt.setText(ConstantsUrl.STORE_NAME);

					  TabLayout.Tab tab = mTabLayout.getTabAt(position);
					  tab.select();


				  }
			  }

			changeTabsFont(1);
			changeTabsFont(2);

			((Global)SubCategoryNProductsFragment.this.getActivity().getApplication()).setFavroite_order_id("");
			mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
//					mTitleTxt.setText(mViewPager.getAdapter()
//							.getPageTitle(arg0));
					Picasso.with(getActivity())
							.load(categorylist.get(arg0).getCatImg())
							.error(R.drawable.place_holder)
							.into(category_image);


					subCategoryList=categorylist.get(arg0).getSubCategoryList();
					if(!HasSubCategory())
					{
						submViewPager.setVisibility(View.GONE);
						subTabLayout.setVisibility(View.GONE);
						mViewPager.setVisibility(View.VISIBLE);
					}else
					{
						mViewPager.setVisibility(View.GONE);
						submViewPager.setVisibility(View.VISIBLE);
						subTabLayout.setVisibility(View.VISIBLE);
						submViewPager.setAdapter(new SubPagerAdapter(getChildFragmentManager()));
						new SubPagerAdapter(getChildFragmentManager()).notifyDataSetChanged();//new line
						subTabLayout.setupWithViewPager(submViewPager);
					}



				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});

			submViewPager.setOnPageChangeListener(new OnPageChangeListener() {
				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

				}

				@Override
				public void onPageSelected(int arg0)
				{
					Picasso.with(getActivity())
							.load(subCategoryList.get(arg0).getCatImg())
							.error(R.drawable.place_holder)
							.into(category_image);



					mViewPager.setVisibility(View.GONE);
					subTabLayout.setVisibility(View.VISIBLE);


				}






				@Override
				public void onPageScrollStateChanged(int state) {

				}
			});

		}
	}

	private boolean HasSubCategory()
	{
		if(subCategoryList !=null && subCategoryList.size() !=0)
			return  true;
		else
			return  false;
	}

	private class PagerAdapter extends FragmentPagerAdapter {
		private int value;

		public PagerAdapter(FragmentManager fm, int caseValue) {
			super(fm);
			// TODO Auto-generated constructor stub
			this.value = caseValue;

		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub

			if (value == 2) {

				return ProductFragment.newInstance(subCategoryList.get(arg0).getProductList());
			} else {
//				Picasso.with(getActivity())
//				.load(categorylist.get(arg0).getCatImg())
//				.error(R.drawable.place_holder)
//				.into(category_image);
//				DisplayImageOptions option= new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.drawable.place_holder)
//				.showImageOnLoading(R.drawable.place_holder)
//				.cacheInMemory(true)
//				.cacheOnDisc(true)
//				.build();
//				ImageLoader imageLoader = ImageLoader.getInstance();
//				imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
//				imageLoader.displayImage(categorylist.get(arg0).getCatImg(),category_image,option);

				return ProductFragment.newInstance(categorylist.get(arg0).getProductList());

			}

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			if (value == 2) {
				return subCategoryList.size();
			} else {
				return categorylist.size();
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			if (value == 2) {
				return  subCategoryList.get(position).getCategoryName();
			} else {
				return  categorylist.get(position).getCategoryName();
			}
		}
	}
	private class SubPagerAdapter extends FragmentStatePagerAdapter {

		public SubPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return ProductFragment.newInstance(subCategoryList.get(arg0).getProductList());

		}
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}



		@Override
		public int getCount() {
			// TODO Auto-generated method stub

			return subCategoryList.size();



		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return  subCategoryList.get(position).getCategoryName();

		}
	}

	private void changeTabsFont(int flag) {
		ViewGroup vg=null;
		if(flag==1)
		 vg = (ViewGroup) mTabLayout.getChildAt(0);
		else
			vg = (ViewGroup) subTabLayout.getChildAt(0);

		int tabsCount = vg.getChildCount();
		Typeface type= Typeface.createFromAsset(getActivity().getAssets(), "Sintony-Regular.ttf");

		for (int j = 0; j < tabsCount; j++) {
			ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
			int tabChildsCount = vgTab.getChildCount();
			for (int i = 0; i < tabChildsCount; i++) {
				View tabViewChild = vgTab.getChildAt(i);
				if (tabViewChild instanceof TextView)
				{
				     ((TextView) tabViewChild).setTypeface(type);
				}
			}
		}
	}


}
