package com.app2mobile.Sultanwok.fragment;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.Sultanwok.ProductDetailActivity;
import com.app2mobile.Sultanwok.ProductDetailAttributeActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.RestaurantCategoryMetadata;
import com.app2mobile.metadata.RestaurantProductMetadata;


public class ProductFragment extends Fragment {
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private RecyclerView.LayoutManager mLayoutManager;
	private ArrayList<RestaurantProductMetadata> productList;
	private RestaurantCategoryMetadata category;
	private static ProductFragment instance = null;
    private int mPreviousPosition = 0;
	private ArrayList<RestaurantCategoryMetadata> subCategoryList;
	@SuppressWarnings("unchecked")
	public static ProductFragment newInstance(ArrayList<RestaurantProductMetadata> products) {
		Bundle args = new Bundle();
		//args.putParcelableArrayList("products", (ArrayList<? extends Parcelable>) products);//commented by takendra as not working in studio
		args.putSerializable("products",products);

//		if (instance == null) {

			instance = new ProductFragment();

//		}
		instance.setArguments(args);
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		productList = (ArrayList<RestaurantProductMetadata>) getArguments().get("products");

		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle != null) {
			category = (RestaurantCategoryMetadata) bundle.get("category");
		}
		
		if (category != null) {

			subCategoryList = category.getSubCategoryList();
		}
	}

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.products_fragments, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		System.out.println(" ..................................");
	ImageView category_image=(ImageView)view.findViewById(R.id.imageView1);
	TextView cat_name= (TextView)view.findViewById(R.id.catname);
	cat_name.setText(category.getCategoryName());
//		Picasso.with(getActivity())
//		.load(category.getCatImg())
//		.error(R.drawable.place_holder)
//		.placeholder(R.drawable.custom_progress_dialog)
//		.into(category_image);
		mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		if (productList != null) {
			//
			mAdapter = new ProductAdapter();
			mRecyclerView.setAdapter(mAdapter);


		}
	}

	
	public class ProductAdapter extends
			RecyclerView.Adapter<ProductAdapter.ViewHolder> {
		// private ArrayList<RestaurantProductMetadata> productList;

		public class ViewHolder extends RecyclerView.ViewHolder{
			// each data item is just a string in this case
			public TextView mProductNameTxt;
			public TextView mProductDesc;
			public TextView mPrice;
			public RelativeLayout mRelative;

			public ViewHolder(View v) {
				super(v);
				mProductNameTxt = (TextView) v.findViewById(R.id.productName);
				mProductDesc = (TextView) v.findViewById(R.id.productDesc);
				mPrice = (TextView) v.findViewById(R.id.price);
				mRelative = (RelativeLayout) v.findViewById(R.id.Product);
				
				
				// mStaticesTxt = (TextView) v.findViewById(R.id.status);
			}
		}

		@Override
		public int getItemCount() {
			// TODO Auto-generated method stub
			return productList.size();
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int arg1) {
			// TODO Auto-generated method stub
			RestaurantProductMetadata product = productList.get(arg1);
			if (product != null) {
				String s=product.getpName();
				final StringBuilder result = new StringBuilder(s.length());
				String[] words = s.split(" ");
//				for(int i=0,l=words.length;i<l;++i) {
//				  if(i>0) result.append(" ");      
//				  result.append(Character.toUpperCase(words[i].charAt(0)))
//				        .append(words[i].substring(1));
//
//				}
	
				Typeface tf= Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
				Typeface tf1=Typeface.createFromAsset(getActivity().getAssets(),"OpenSans-Light.ttf");
				holder.mProductNameTxt.setTypeface(tf);
				holder.mProductDesc.setTypeface(tf1);
				holder.mPrice.setTypeface(tf);
				holder.mProductNameTxt.setText(s.toString());
				holder.mProductDesc.setText(product.getpDesc());
				BigDecimal bigDecimal = new BigDecimal(product.getpPrice());
				holder.mPrice.setText("$ "
						+ bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
//				 if (arg1 > mPreviousPosition) {
//			            com.a2m.utiles.AnimationUtils.animateSunblind(holder, true);
////			            AnimationUtils.animateSunblind(holder, true);
////			            AnimationUtils.animate1(holder, true);
////			            AnimationUtils.animate(holder,true);
//			        } else {
//			            com.a2m.utiles.AnimationUtils.animateSunblind(holder, false);
//			            
////			            AnimationUtils.animateSunblind(holder, false);
////			            AnimationUtils.animate1(holder, false);
////			            AnimationUtils.animate(holder, false);
//			        }
//			        mPreviousPosition = arg1;
//				if (arg1 % 2 == 0) {
//					holder.mRelative.setBackgroundColor(getResources()
//							.getColor(R.color.white));
//
//				} else {
//					holder.mRelative.setBackgroundColor(getResources()
//							.getColor(R.color.grey));
//				}
			}

		}

		@Override
		public ViewHolder onCreateViewHolder(final ViewGroup parent,
				int viewType) {
			// TODO Auto-generated method stub
			System.out.println("inflacete 1");
			View v = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.row_product, parent, false);
			System.out.println("inflacete 12");
			ViewHolder vh = new ViewHolder(v);
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = mRecyclerView.indexOfChild(parent);
					System.out.println("intex " + position);
					position = mRecyclerView.getChildLayoutPosition(v);
//					Fragment fragment = new ProductDetailFragment();
					// Bundle bundle = new Bundle();
					// bundle.putSerializable("product",
					// productList.get(position));
					// fragment.setArguments(bundle);
					// getActivity().getSupportFragmentManager()
					// .beginTransaction()
					// .replace(R.id.content_frame, fragment)
					// .addToBackStack(null).commit();
//					Intent pDetailIntent = new Intent(getActivity(),
//							ProductBundleDetailActivity.class);
					RestaurantProductMetadata data = productList.get(position);
					System.out.println(data);
					if(data.getBundleList()!=null && data.getBundleList().size()>0){
						System.out.println("Product Fragment  if");
					
						Intent intent = new Intent(getActivity(),
								ProductDetailAttributeActivity.class);
						intent.putExtra("productDetail", data);
						startActivity(intent);
					} else {
						System.out.println("Product Fragment  else");

						Intent intent = new Intent(getActivity(),
								ProductDetailActivity.class);
						intent.putExtra("productDetail", data);
						startActivity(intent);
					}
				}
			});
			return vh;
		}
	}
}
