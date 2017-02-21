package com.app2mobile.Sultanwok.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.Sultanwok.CategoryActivity;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.ProductActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.metadata.RestaurantCategoryMetadata;
import com.app2mobile.metadata.RestaurantMetadata;
import com.app2mobile.metadata.ViewHolder;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;
import com.carouseldemo.controls.Carousel;
import com.carouseldemo.controls.CarouselAdapter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.special.ResideMenu.ResideMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.app2mobile.Sultanwok.R.styleable.Carousel;

public class CategoryFragment extends BaseFragment implements android.support.v4.app.FragmentManager.OnBackStackChangedListener {
	private CategoryActivity homeActivity;
	private ImageView mMenuImg, mCartImg,backbtn;
	private RelativeLayout offer_layout;
	// private SharedPreferences mAppPref;
	private GridView mGridView;
	private int orientation;
	private RestaurantMetadata restaurantData;
	private ArrayList<RestaurantCategoryMetadata> categoryList;
	private GridViewAdpater mGridViewAdapter;
	private TextView mCartCount,offer;
	private Carousel carousel;
	private TextView circle;
	private ACProgressCustom progressDialog;
	private RestaurantCategoryMetadata griddata;
	private int gPosition;

	public static CategoryFragment newInstance() {

		return new CategoryFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		

		homeActivity = (CategoryActivity) getActivity();
		orientation = getActivity().getResources().getConfiguration().orientation;
		if(getActivity().getIntent().getExtras()!=null){
		restaurantData = (RestaurantMetadata) getActivity().getIntent()
				.getExtras().get("restaurant");
		}
		
		//new GetOffer().execute();
		if (restaurantData != null) {
			categoryList = restaurantData.getCategoryList();

		}

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	    GoogleAnalytics.getInstance(getActivity()).reportActivityStart(getActivity());

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	    GoogleAnalytics.getInstance(getActivity()).reportActivityStop(getActivity());

	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mCartImg = (ImageView) view.findViewById(R.id.cartImage);
		mMenuImg = (ImageView) view.findViewById(R.id.menuImage);
		
		circle=(TextView)view.findViewById(R.id.circle);
		
		circle.setVisibility(View.INVISIBLE);
		if (Configuration.ORIENTATION_PORTRAIT == orientation) {
			mGridView = (GridView) view.findViewById(R.id.gridView1);
			// if (restaurantData != null) {
			// categoryList = restaurantData.getCategoryList();
			// System.out.println("category List size " +
			// categoryList.size());
			if (categoryList != null) {
				mGridViewAdapter = new GridViewAdpater();
				mGridView.setAdapter(mGridViewAdapter);
				mGridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						//commented code is now  in redirect class to display  progressdialog
                 /*
						Intent intent = new Intent(getActivity(),
								ProductActivity.class);
						intent.putExtra("category",mGridViewAdapter.getItem(position));
						intent.putExtra("position", position);
						intent.putExtra("categorylist",categoryList);
						startActivity(intent);
                */

					      griddata	=mGridViewAdapter.getItem(position);
                         gPosition=position;
						if(griddata!=null) {
							new Redirect().execute();
						}



					}
				});
					//mTitleTxt.setText("The Salad House");
				// }
			}
		} 
		else if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
			if (categoryList != null && categoryList.size() > 0) {
				carousel = (Carousel) view.findViewById(R.id.carousel);
				carousel.initilize(categoryList, categoryList.size() - 1);
				carousel.setOnItemClickListener(new CarouselAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(CarouselAdapter<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						if (view.getVisibility() == View.VISIBLE) {

							if (position == carousel.getSelectedItemPosition()) {

								Intent intent = new Intent(getActivity(),
										ProductActivity.class);
								intent.putExtra("category",categoryList.get(categoryList.size()-1-position));
								intent.putExtra("position", categoryList.size()-1-position);

								intent.putExtra("categorylist",categoryList);
								startActivity(intent);
							} else {

							}
						}
					}
				});
			}
		}
		offer_layout=(RelativeLayout)view.findViewById(R.id.offer_layout);
		mCartCount = (TextView) view.findViewById(R.id.cartItems);
		offer=(TextView)view.findViewById(R.id.offer_title);
		
		LayoutParams params = (LayoutParams) mCartCount.getLayoutParams();
		mCartCount.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int maximumWidth = Math.max(mCartCount.getMeasuredWidth(),
				mCartCount.getMeasuredHeight());
		params.width = maximumWidth;
		params.height = maximumWidth;
		mCartCount.setLayoutParams(params);
		mMenuImg.setVisibility(View.INVISIBLE);
		mCartCount.setVisibility(View.INVISIBLE);
		offer_layout.setVisibility(View.INVISIBLE);
		((Global)CategoryFragment.this.getActivity().getApplication()).setFavroite_order_id("");

		// AppUtiles.getInstance().setCartItems(mCartCount, getActivity());
		mMenuImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//((BaseActivity)getActivity()).OpenDrawer();
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});
		mCartImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent intentCart = new Intent(getActivity(),
//						CheckOutProductListActivity.class);
//				startActivity(intentCart);
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);

			}
		});
	}
	private class Redirect extends AsyncTask
	{
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ACProgressCustom.Builder(getActivity())
					.useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);
			progressDialog.show();
		}

		@Override
		protected Object doInBackground(Object[] params)
		{

			Intent intent = new Intent(getActivity(),
					ProductActivity.class);
			intent.putExtra("category",griddata);
			intent.putExtra("position", gPosition);
			intent.putExtra("categorylist",categoryList);
			startActivity(intent);
			return null;
		}

		@Override
		protected void onPostExecute(Object o) {
			super.onPostExecute(o);
			progressDialog.dismiss();
		}
	}


	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Global.getInstance().trackScreenView("Category Page");

		if (mCartCount != null) {
			AppUtiles.getInstance().setCartItems(mCartCount, getActivity());
		}
	}

	private class GridViewAdpater extends BaseAdapter {
		private LayoutInflater mInflater;
		public ViewHolder holderObj = null;
		

		public GridViewAdpater() {
			// TODO Auto-generated constructor stub
			mInflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return categoryList.size();
		}

		@Override
		public RestaurantCategoryMetadata getItem(int position) {
			// TODO Auto-generated method stub
			return categoryList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			if (convertView == null) {
				holderObj = new ViewHolder();
				convertView = mInflater.inflate(R.layout.gridview_item, null);
				holderObj.mCatImg = (ImageView) convertView
						.findViewById(R.id.categoryImg);
				holderObj.mCatName=(TextView)convertView.findViewById(R.id.item_name);
				holderObj.view= (View)convertView.findViewById(R.id.view1);
				holderObj.view1= (View)convertView.findViewById(R.id.view2);
				holderObj.view.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
			            "OpenSans-Semibold.ttf");
				holderObj.mCatName.setTypeface(tf);
				
				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();

			}
			// holderObj.mCatImg=
			RestaurantCategoryMetadata catData = getItem(position);
			System.out.println("cateImg " + catData.getCatImg());
			holderObj.mCatName.setText(catData.getCategoryName());
			Picasso.with(getActivity())
					.load(catData.getCatImg())
					.resize((int) getResources().getDimension(
							R.dimen.category_width_grid),
							(int) getResources().getDimension(
									R.dimen.category_height_grid))
						.placeholder(R.drawable.ic_launcher)
						.error(R.drawable.ic_launcher)
					.into(holderObj.mCatImg);
			Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.transparent);
			
//			final Target mTarget = new Target() {
//			    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//			    @Override
//			    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
//			        Log.d("DEBUG", "onBitmapLoaded");
//			        BitmapDrawable mBitmapDrawable = new BitmapDrawable(getResources(), bitmap);
//			        holderObj.mCatImg.setBackground(mBitmapDrawable);
//			    }
//
//			    @Override
//			    public void onBitmapFailed(Drawable drawable) {
//			        Log.d("DEBUG", "onBitmapFailed");
//			    }
//
//
//			    @Override
//			    public void onPrepareLoad(Drawable drawable) {
//			        Log.d("DEBUG", "onPrepareLoad");
//			    }
//			};
//			Picasso.with(getActivity()).load(catData.getCatImg()).into(mTarget);
			return convertView;
		}

	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_dashboard;
	}
	private class CustomFilter extends Filter {

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// TODO Auto-generated method stub
			
		}
	
	}
	
	public class GetOffer extends AsyncTask<String[], Void, String>{

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(
					ConstantsUrl.GETOFFER,new String[]{"store_id","today","user_type"},new String[]{ConstantsUrl.STOREID,"1","4"});

		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null){
			JSONObject mainJson = null;
			try {
				mainJson = new JSONObject(result);
			Log.e("Offer result", result);
				// TODO Auto-generated catch block
				
			
			if (mainJson.has("state")) {
				String state = JsonParser
						.getkeyValue_Str(mainJson, "state");
				String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
				if(msg.contains("No Offer Found")){
					offer_layout.setVisibility(View.INVISIBLE);

						
		}
				else{
					offer_layout.setVisibility(View.VISIBLE);
					System.out.println("mainjson");
							if(mainJson.has("data")){
								JSONObject dataobj;
								String offer = null,percent = null;
								
									dataobj = mainJson.getJSONObject("data");
									JSONArray arr= dataobj.getJSONArray("Discount Offers");
									for(int a=0;a<arr.length();a++){
										JSONObject obj= arr.getJSONObject(a);
										 offer=obj.getString("discount_title");
										 percent=obj.getString("Discount_value");
									}
									CategoryFragment.this.offer.setText(offer+" "+percent);
									System.out.println("second");
								} 
				}
	}
			}catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		}
	
	}

	@Override
	public void onBackStackChanged() {
		// TODO Auto-generated method stub
		int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
		  if(backStackEntryCount > 0){
		  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		  }else{
			  getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
		  }
	}
}
