package com.app2mobile.Sultanwok;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout.LayoutParams;

import com.app2mobile.Sultanwok.fragment.ProductDetailFragment;

public class ProductDetailActivity extends FragmentActivity{
//	private ProductDetailFragment productDetailFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_home2);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		Bundle bundle = getIntent().getExtras();
		if(savedInstanceState==null)
		{
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
//		productDetailFragment=new ProductDetailFragment();
//		if (bundle != null) {
//			productDetailFragment.setArguments(bundle);
//		}
		transaction.replace(R.id.category_fragment, ProductDetailFragment.newInstance());
		transaction.commit();
		}
	}

}
