package com.app2mobile.Sultanwok.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.RestaurantProductMetadata;

public class ProductAttributesDetailFragment2 extends BaseFragment {
	// private LayoutInflater mInflater;
	// private RecyclerView recycleListView;
	// private LinearLayout containerObj;
	// private boolean isImageFound;
	// // private ProductAttributesAdapter adapterObj;
	// private int maxSelected;
	// ArrayList<RestaurantVarientsMetadata> varientList;
	// ArrayList<RestaurantAddOnMetadata> addonList;
	// ArrayList<RestaurantAddOnsMetadata> bundleProductList;
	// ArrayList<RestaurantBundleMetadata> bundleList = new
	// ArrayList<RestaurantBundleMetadata>();
	// public static final int ADDONS = 1;
	// public static final int VARIENTS = 2;
	// public static final int BUNDLE = 3;
	// private int productType;
	// private boolean isVarient, isSelectedProperly;
	// private RecyclerView.Adapter mAdapter;
	// private RecyclerView.LayoutManager mLayoutManager;
	private RestaurantProductMetadata productDetailData;
	private static ProductAttributesDetailFragment2 instance = null;

	public static ProductAttributesDetailFragment2 newInstance(
			RestaurantProductMetadata productDetail) {
		Bundle args = new Bundle();
		args.putSerializable("productDetail", productDetail);
		if (instance == null) {

			instance = new ProductAttributesDetailFragment2();

		}
		instance.setArguments(args);
		return instance;
	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_product_attribute;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			productDetailData = (RestaurantProductMetadata) bundle
					.get("productDetail");
		}
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		// recycleListView = (RecyclerView) view.findViewById(R.id.itemsList);
		// recycleListView.setHasFixedSize(true);
		// mLayoutManager = new LinearLayoutManager(getActivity());
		// recycleListView.setLayoutManager(mLayoutManager);
		// containerObj = (LinearLayout) view.findViewById(R.id.container);
		// if (productDetailData != null) {
		// varientList = productDetailData.getVarientsList();
		// addonList = productDetailData.getAddOnList();
		// bundleList = productDetailData.getBundleList();
		// addCategories();
		//
		// }

	}

	// private void addCategories() {
	// BitmapDrawable bitmap = (BitmapDrawable) ContextCompat.getDrawable(
	// getActivity(), R.drawable.ic_attribute_category);
	// // AppUtiles.productDetailsAttributesImageList();
	// if (addonList != null && addonList.size() > 0) {
	// addView(R.drawable.ic_attribute_category, null, "Addons", "", 0,
	// bitmap.getBitmap().getWidth(), null, addonList, null);
	// }
	// if (varientList != null && varientList.size() > 0) {
	// addView(R.drawable.ic_attribute_category, null, "Varients", "", 1,
	// bitmap.getBitmap().getWidth(), varientList, null, null);
	//
	// }
	// if (bundleList != null && bundleList.size() > 0) {
	// System.out.println("bundle list sizw " + bundleList.size());
	// for (int i = 0; i < bundleList.size(); i++) {
	// RestaurantBundleMetadata bundleData = bundleList.get(i);
	// if (i == 0) {
	// bundleProductList = bundleData.getBundleAddOnsList();
	// }
	// addView(R.drawable.ic_attribute_category, null,
	// bundleData.getmGroupName(), "",
	// bundleData.getmGroupSeletOption(), bitmap.getBitmap()
	// .getWidth(), null, null,
	// bundleData.getBundleAddOnsList());
	// }
	// }
	// }
	//
	// private void addView(int image,
	// final ArrayList<ProductOptionsMetadata> optionList1,
	// final String label, final String frontEndStr,
	// final int maxSelection, int width,
	// final ArrayList<RestaurantVarientsMetadata> varientList1,
	// final ArrayList<RestaurantAddOnMetadata> addOnList,
	// final ArrayList<RestaurantAddOnsMetadata> bundleProductList1) {
	// ViewGroup view = (ViewGroup) mInflater.inflate(
	// R.layout.row_attribute_category, containerObj, false);
	// final TextView categoryTitle = (TextView) view
	// .findViewById(R.id.category);
	// LinearLayout linearObj = (LinearLayout) view.findViewById(R.id.layout);
	// if (!isImageFound || image == 0) {
	//
	// if (label != null)
	// categoryTitle.setText(label);
	// categoryTitle.setVisibility(view.VISIBLE);
	// }
	// view.setBackgroundResource(image);
	// LayoutParams params = linearObj.getLayoutParams();
	// params.width = width;
	// // view.setTag(label);
	// view.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// // TODO Auto-generated method stub
	//
	// // listviewHeaderTitle.setText(getString(R.string.select_one));
	// // optionsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	// //
	// // if (maxSelection == 0) {
	// // listviewHeaderTitle
	// // .setText(getString(R.string.select_multiple));
	// //
	// // } else {
	// // listviewHeaderTitle.setText("Select " + maxSelection
	// // + " item");
	// //
	// // }
	// // maxSelected = maxSelection;
	// // optionsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
	//
	// if (label.equals("Addons")) {
	// productType = ADDONS;
	// addonList = addOnList;
	// } else if (label.equals("Varients")) {
	// productType = VARIENTS;
	// varientList = varientList1;
	// } else {
	// productType = BUNDLE;
	// bundleProductList = bundleProductList1;
	// }
	// // optionsList = optionList1;
	//
	// // adapterObj.notifyDataSetChanged();
	// }
	// });
	//
	// if (containerObj.getChildCount() == 0) {
	// //
	// System.out.println(label+" count container obj1 "+containerObj.getChildCount());
	// if (label.equals("Addons")) {
	// productType = ADDONS;
	//
	// } else if (label.equals("Varients")) {
	// productType = VARIENTS;
	//
	// } else {
	// productType = BUNDLE;
	// }
	// // if (maxSelection == 0) {
	// // listviewHeaderTitle
	// // .setText(getString(R.string.select_multiple));
	// // // maxSelected = 0;
	// // } else {
	// // listviewHeaderTitle.setText("Select " + maxSelection + " item");
	// //
	// // }
	// // maxSelected = maxSelection;
	// // adapterObj = new ProductAttributesAdapter();
	// // optionsListView.setAdapter(adapterObj);
	// }
	// // else {
	// // adapterObj.notifyDataSetChanged();
	// // }
	// containerObj.addView(view);
	// }
	//
	// public class ProductAdapter extends
	// RecyclerView.Adapter<ProductAdapter.ViewHolder> {
	// // private ArrayList<RestaurantProductMetadata> productList;
	// private static final int TYPE_HEADER = 0;
	// private static final int TYPE_ITEM = 1;
	//
	// public class ViewHolder extends RecyclerView.ViewHolder {
	// // each data item is just a string in this case
	// public TextView mProductNameTxt;
	// public TextView mProductDesc;
	// public TextView mPrice;
	// public RelativeLayout mRelative;
	//
	// public ViewHolder(View v) {
	// super(v);
	// mProductNameTxt = (TextView) v.findViewById(R.id.productName);
	// mProductDesc = (TextView) v.findViewById(R.id.productDesc);
	// mPrice = (TextView) v.findViewById(R.id.price);
	// mRelative = (RelativeLayout) v.findViewById(R.id.Product);
	// // mStaticesTxt = (TextView) v.findViewById(R.id.status);
	// }
	// }
	//
	// public class ViewHolderHeader extends RecyclerView.ViewHolder {
	// // each data item is just a string in this case
	// public TextView listviewHeaderTitle;
	//
	// public ViewHolderHeader(View v) {
	// super(v);
	// listviewHeaderTitle = (TextView) v.findViewById(R.id.LeftSide);
	//
	// }
	// }
	//
	// @Override
	// public int getItemCount() {
	// // TODO Auto-generated method stub
	// return bundleList.size();
	// }
	//
	// @Override
	// public void onBindViewHolder(ViewHolder holder, int arg1) {
	// // TODO Auto-generated method stub
	// // RestaurantProductMetadata product = productList.get(arg1);
	// // if (product != null) {
	// // holder.mProductNameTxt.setText(product.getpName());
	// // BigDecimal bigDecimal = new BigDecimal(product.getpPrice());
	// // holder.mPrice.setText("$ "
	// // + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
	// // if (arg1 % 2 == 0) {
	// // holder.mRelative.setBackgroundColor(getResources()
	// // .getColor(R.color.white));
	// //
	// // } else {
	// // holder.mRelative.setBackgroundColor(getResources()
	// // .getColor(R.color.grey));
	// // }
	// // }
	//
	// }
	//
	// @Override
	// public ViewHolder onCreateViewHolder(final ViewGroup parent,
	// int viewType) {
	// // TODO Auto-generated method stub
	// ViewHolder vh = null;
	// View v;
	// if (viewType == TYPE_HEADER) {
	// v = LayoutInflater.from(parent.getContext()).inflate(
	// R.layout.listview_header, parent, false);
	//
	// vh = new ViewHolder(v);
	// } else if (viewType == TYPE_ITEM) {
	// v = LayoutInflater.from(parent.getContext()).inflate(
	// R.layout.row_product, parent, false);
	//
	// vh = new ViewHolder(v);
	// }
	//
	// return vh;
	// }
	// }
}
