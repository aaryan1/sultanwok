package com.app2mobile.Sultanwok;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.app2mobile.Sultanwok.fragment.ProductAttributesDetailFragment;

public class ProductDetailAttributeActivity extends BaseActivity {
	private ProductAttributesDetailFragment productDetailAttributeFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_home2);
		Bundle bundle = getIntent().getExtras();
		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			productDetailAttributeFragment = new ProductAttributesDetailFragment();
			if (bundle != null) {
				productDetailAttributeFragment.setArguments(bundle);
			}
			transaction.replace(R.id.content_frame,
					productDetailAttributeFragment);
			transaction.commit();
		} else {
			productDetailAttributeFragment = (ProductAttributesDetailFragment) getSupportFragmentManager()
					.getFragment(savedInstanceState, "mContent");
		}
	}

	@Override
	protected int getLayoutResource() {
		// TODO Auto-generated method stub
		return R.layout.activity_home;
	}

	// @Override
	// public void onBackPressed() {
	// // TODO Auto-generated method stub
	//
	// productDetailAttributeFragment.closeNavigationDrawer();
	// }
	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// // TODO Auto-generated method stub
	// super.onSaveInstanceState(outState);
	// getSupportFragmentManager().putFragment(outState, "mContent",
	// productDetailAttributeFragment);
	// }
}
