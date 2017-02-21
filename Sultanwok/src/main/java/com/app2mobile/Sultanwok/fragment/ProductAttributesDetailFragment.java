	package com.app2mobile.Sultanwok.fragment;


	import java.math.BigDecimal;
	import java.util.ArrayList;

	import android.annotation.SuppressLint;
	import android.content.ContentValues;
	import android.content.res.ColorStateList;
	import android.database.Cursor;
	import android.graphics.Color;
	import android.graphics.Typeface;
	import android.graphics.drawable.BitmapDrawable;
	import android.graphics.drawable.Drawable;
	import android.os.Build;
	import android.os.Bundle;
	import android.support.design.widget.Snackbar;
	import android.support.v4.content.ContextCompat;
	import android.util.Log;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.WindowManager;
	import android.view.View.OnClickListener;
	import android.view.ViewGroup;
	import android.view.ViewGroup.LayoutParams;
	import android.widget.BaseAdapter;
	import android.widget.Button;
	import android.widget.CheckBox;
	import android.widget.EditText;
	import android.widget.ImageView;
	import android.widget.LinearLayout;
	import android.widget.ListView;
	import android.widget.RelativeLayout;
	import android.widget.TextView;
	import android.widget.Toast;

	import com.app2mobile.Sultanwok.R;
	import com.app2mobile.metadata.ProductOptionsMetadata;
	import com.app2mobile.metadata.RestaurantAddOnMetadata;
	import com.app2mobile.metadata.RestaurantAddOnsMetadata;
	import com.app2mobile.metadata.RestaurantBundleMetadata;
	import com.app2mobile.metadata.RestaurantProductMetadata;
	import com.app2mobile.metadata.RestaurantVarientsMetadata;
	import com.app2mobile.utiles.ConstantsUrl;
	import com.app2mobile.utiles.DatabaseHelper;

	import com.squareup.picasso.Picasso;

	public class ProductAttributesDetailFragment extends BaseFragment {

		private RestaurantProductMetadata productDetailData;
		private LayoutInflater mInflater;
		private ListView optionsListView;
		private LinearLayout containerObj;
		private RelativeLayout addToCartTxt;
		private TextView listviewHeaderTitle,req,quantityView,amountTextView,add_txt;
		private boolean isImageFound;
		private EditText product_instruction;
		private ProductAttributesAdapter adapterObj;
		private RelativeLayout require_layout;
		private int maxSelected;
		ArrayList<RestaurantVarientsMetadata> varientList;
		ArrayList<RestaurantAddOnMetadata> addonList;
		ArrayList<RestaurantAddOnsMetadata> bundleProductList;
		ArrayList<RestaurantBundleMetadata> bundleList = new ArrayList<RestaurantBundleMetadata>();
		public static final int ADDONS = 1;
		public static final int VARIENTS = 2;
		public static final int BUNDLE = 3;
		public ImageView icAttribute;
		private int productType;
		private int lowerLimit = 1, upperLimit;
		private boolean isVarient, isSelectedProperly;
		public BigDecimal pricePerQuantity,priceperquantitybundle;
		private ImageView img;
		private Button plusImageView,minusImageView;
		private int tag=-1;
		public TextView circle;

		public static ProductAttributesDetailFragment newInstance(
				RestaurantProductMetadata productDetail) {

			Bundle args = new Bundle();
			args.putSerializable("productDetail", productDetail);
			ProductAttributesDetailFragment fragment = new ProductAttributesDetailFragment();
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getLayout() {
			// TODO Auto-generated method stub
			return R.layout.fragment_product_detail2;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			mInflater = LayoutInflater.from(getActivity());
			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		}

		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onViewCreated(view, savedInstanceState);
			Bundle bundle = getArguments();
			if (bundle != null) {
				productDetailData = (RestaurantProductMetadata) bundle
						.get("productDetail");
			}
			mTabLayout.setVisibility(View.GONE);
			bitmapOrg = this.getResources().getDrawable(R.drawable.fav);
			bitmapOrg.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.MULTIPLY);
			mCartImg.setImageDrawable(bitmapOrg);
			//mCartImg.setImageResource(R.drawable.ic_favorite_background);
			//mCartImg.setVisibility(View.INVISIBLE);
			mCartCountTxt.setVisibility(View.GONE);
			if (productDetailData != null)
				mTitleTxt.setText(productDetailData.getpName());
			TextView name=(TextView)view.findViewById(R.id.name);

			containerObj = (LinearLayout) view.findViewById(R.id.container);
			addToCartTxt = (RelativeLayout) view.findViewById(R.id.addToCart);
			optionsListView = (ListView) view.findViewById(R.id.itemsList);
			img=(ImageView)view.findViewById(R.id.categoryImg);
			require_layout=(RelativeLayout)view.findViewById(R.id.require);
			req=(TextView)view.findViewById(R.id.req);
			plusImageView=(Button)view.findViewById(R.id.plus);
			minusImageView=(Button)view.findViewById(R.id.minus);
			quantityView=(TextView)view.findViewById(R.id.quantity);
			amountTextView=(TextView)view.findViewById(R.id.total_amount);
			product_instruction=(EditText)view.findViewById(R.id.instruction);
			circle=(TextView)view.findViewById(R.id.circle);
			circle.setVisibility(View.INVISIBLE);
			add_txt=(TextView)view.findViewById(R.id.add);

			View listviewHeader = mInflater.inflate(R.layout.listview_header, null);
			listviewHeaderTitle = (TextView) listviewHeader
					.findViewById(R.id.LeftSide);
			listviewHeaderTitle.setVisibility(View.VISIBLE);
			Typeface tf= Typeface.createFromAsset(getActivity().getAssets(),"Sintony-Bold.ttf");
			listviewHeaderTitle.setTypeface(tf);
			add_txt.setTypeface(tf);
			name.setText(productDetailData.getpName());
			optionsListView.addHeaderView(listviewHeader);
			addToCartTxt.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			pricePerQuantity= new BigDecimal(productDetailData.getpPrice());
			BigDecimal price=new BigDecimal(productDetailData.getpPrice());
			amountTextView.setText("$ "+ price.setScale(2,BigDecimal.ROUND_HALF_UP));
//		Picasso.with(getActivity())
//		.load(productDetailData.getpImage())
//		.error(R.drawable.place_holder)
//		.into(img);
			// addcategories();
			if (productDetailData != null) {
				varientList = productDetailData.getVarientsList();
				addonList = productDetailData.getAddOnList();
				bundleList = productDetailData.getBundleList();
				addCategories();

			}
			if(productDetailData.getpImage().toString()==null || productDetailData.getpImage().equals("")){
//			require_layout.setVisibility(View.GONE);
//			img.setVisibility(View.GONE);
				Picasso.with(getActivity())
						.load(R.drawable.place_holder)
						.error(R.drawable.place_holder)
						.into(img);
			}else{
				Picasso.with(getActivity())
						.load(productDetailData.getpImage())
						.error(R.drawable.place_holder)
						.placeholder(R.drawable.place_holder)
						.into(img);
//			ImageLoader imageLoader = ImageLoader.getInstance();
//			imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
//			imageLoader.displayImage(productDetailData.getpImage().toString(),img);
//
			}
			plusImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					int temp;
					try {
						temp = Integer.parseInt(quantityView.getText().toString());
					} catch (Exception e) {
						// TODO: handle exception
						temp = 1;
					}
					++temp;
//				 if (temp > upperLimit) {
//				 quantityView.setText("1");
//				 amountTextView.setText("$ "
//				 + String.valueOf(pricePerQuantity
//				 .multiply(new BigDecimal(1))));
//				 } else {

					Log.e("aaaaa", ""+priceperquantitybundle);
					quantityView.setText(String.valueOf(temp));
					BigDecimal de;
					if(priceperquantitybundle==null ){
						BigDecimal tot=pricePerQuantity
								.multiply(new BigDecimal(temp));
						amountTextView.setText("$ "
								+ tot.setScale(2,BigDecimal.ROUND_HALF_UP));
					}else{
						// de=priceperquantitybundle.multiply(new BigDecimal(temp));
						de=pricePerQuantity.add(priceperquantitybundle);

						BigDecimal tot=de
								.multiply(new BigDecimal(temp));
						amountTextView.setText("$ "
								+ tot.setScale(2,BigDecimal.ROUND_HALF_UP));
						// }

					}
				}
			});
			minusImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int temp;
					try {
						temp = Integer.parseInt(quantityView.getText().toString());
					} catch (Exception e) {
						// TODO: handle exception
						temp = 1;
					}
					--temp;
//				if (temp <= lowerLimit - 1) {
//					quantityView.setText(String.valueOf(1));
//					amountTextView.setText("$ "
//							+ String.valueOf(pricePerQuantity
//									.multiply(new BigDecimal(1))));
//				} else {

					if(temp>=1){

						BigDecimal de;
						if(priceperquantitybundle==null ){
							BigDecimal tot_minus=pricePerQuantity
									.multiply(new BigDecimal(temp));
							quantityView.setText(String.valueOf(temp));
							amountTextView.setText("$ "
									+ tot_minus.setScale(2,BigDecimal.ROUND_HALF_UP));
						}else{
							de=pricePerQuantity.add(priceperquantitybundle);

							BigDecimal tot_minus=de
									.multiply(new BigDecimal(temp));
							quantityView.setText(String.valueOf(temp));
							amountTextView.setText("$ "
									+ tot_minus.setScale(2,BigDecimal.ROUND_HALF_UP));
						}
					}
				}


				//}
			});

			addToCartTxt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (bundleList != null) {
						for (int i = 0; i < bundleList.size(); i++) {
							RestaurantBundleMetadata bundelData = bundleList.get(i);
							if (bundelData != null
									&& bundelData.isSelectionRequired()) {
								ArrayList<RestaurantAddOnsMetadata> groupList = bundelData
										.getBundleAddOnsList();
								boolean isSelectedFound = false;
								for (int j = 0; j < groupList.size(); j++) {
									RestaurantAddOnsMetadata addOnsMetadata = groupList
											.get(j);
									if (addOnsMetadata.isSelected()) {
										isSelectedFound = true;

										break;
									}
									isSelectedFound = false;
								}
								if (!isSelectedFound) {
//								AppUtiles.getInstance().showToast(
//										getActivity(),
//										"Please select from "
//												+ bundelData.getmGroupName()
//												+ " category.");
									require_layout.setVisibility(View.VISIBLE);
									req.setText("Required:Select an option from"
											+ bundelData.getmGroupName()
									);
									isSelectedProperly = false;
									break;
								} else {
									isSelectedProperly = true;
									require_layout.setVisibility(View.INVISIBLE);
								}
							} else if (!bundelData.isSelectionRequired()) {
								isSelectedProperly = true;
								// break;
							}
						}
					}

					if (isSelectedProperly) {
						try {
							// calculatePrice(1);

							addCartNFav(1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			mCartImg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					/**
					 * Add favorite dtails
					 */
					try {
						// addfavourite();
						// calculatePrice(2);
						addCartNFav(2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		private void addCartNFav(int state) {
			ArrayList<String> pIds = new ArrayList<String>();
			// String productSku=productDetailData.getpSKU();
			String productId = String.valueOf(productDetailData.getpId());
			isVarient = false;
			RestaurantVarientsMetadata varientData = null;
			if (varientList != null) {
				for (int i = 0; i < varientList.size(); i++) {
					varientData = varientList.get(i);
					if (varientData != null) {
						// productSku=varientData.get
						if (varientData.isSelected()) {
							productId = varientData.getId();
							isVarient = true;
						}
					}
				}
			}
			// pIds.add(productId);
			if (addonList != null) {
				for (int i = 0; i < addonList.size(); i++) {
					RestaurantAddOnMetadata addOnData = addonList.get(i);
					if (addOnData.isSelected()) {

						pIds.add(addOnData.getId());

					}
				}
			}
			if (bundleList != null) {
				for (int i = 0; i < bundleList.size(); i++) {
					RestaurantBundleMetadata bundleData = bundleList.get(i);
					if (bundleData != null) {
						ArrayList<RestaurantAddOnsMetadata> bundleProductList = bundleData.getBundleAddOnsList();
						if (bundleProductList != null) {

							for (int j = 0; j < bundleProductList.size(); j++) {
								RestaurantAddOnsMetadata addOnsData = bundleProductList
										.get(j);
								if (addOnsData != null) {
									if (addOnsData.isSelected()) {
										pIds.add(addOnsData.getId());

									}
								}
							}
						}

					}
				}
			}
			// System.out.println("pids " + pIds);
			insertValues(productId, pIds, varientData, state);
		}

		private void insertValues(String productId, ArrayList<String> ids,
								  RestaurantVarientsMetadata varient, int state) {
			boolean isFind = false;
			String cartId = null, id;
			DatabaseHelper databaseHelper = DatabaseHelper
					.newInstance(getActivity());
			databaseHelper.openDataBase();
			ArrayList<String> cartIds = databaseHelper.getAllCartIds(productId,
					state);

			if (cartIds != null) {
				for (int i = 0; i < cartIds.size(); i++) {
					cartId = cartIds.get(i);
					isFind = false;
					ArrayList<String> valueList = databaseHelper
							.getAllAttributesNameByCartId(cartIds.get(i), state);

					if (valueList != null && ids != null) {
						if (valueList.size() == 0 && ids.size() == 0) {
							isFind = true;
						} else if (valueList.size() != ids.size()) {

							isFind = false;
						} else {
							for (int j = 0; j < ids.size(); j++) {

								for (int k = 0; k < valueList.size(); k++) {

									if (ids.get(j).equals(valueList.get(k))) {
										isFind = true;
										break;
									}
									isFind = false;
								}
							}
						}
					}

					if (isFind)
						break;
				}
				ContentValues values = new ContentValues();
				int updateQuantity = Integer.valueOf(quantityView.getText().toString());

				if (!isFind) {

					long insertedId = -1;
					if (state == 1) {
						if (isVarient) {
							if (varient != null) {
								values.put(DatabaseHelper.CheckOut_ProductId,
										varient.getId());
								values.put(DatabaseHelper.CheckOut_ProductName,
										varient.getpName());
								values.put(
										DatabaseHelper.CheckOut_ProductDescription,
										"");
								values.put(DatabaseHelper.CheckOut_Instruction, "");
								values.put(DatabaseHelper.CheckOut_ProductQuantity,
										updateQuantity);
								values.put(DatabaseHelper.CheckOut_PricePerUnit,
										varient.getpPrice());
								values.put(DatabaseHelper.CHECKOut_ProductSKU, "");
								values.put(DatabaseHelper.CheckOut_TotalAmount,
										varient.getpPrice());
								values.put(DatabaseHelper.CHECKOUT_PTYPE, 2);
								values.put(DatabaseHelper.CHECKOut_SALESTAXAMT,
										varient.getSalesTaxAmt());
								values.put(DatabaseHelper.CHECKOut_SERVICETAXAMT,
										varient.getServiceTaxAmt());
								values.put(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
										varient.getSalesTaxAmt());
								values.put(
										DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
										varient.getServiceTaxAmt());

							}
						} else {
							values.put(DatabaseHelper.CheckOut_ProductId,
									productDetailData.getpId());
							values.put(DatabaseHelper.CheckOut_ProductName,
									productDetailData.getpName());
							values.put(DatabaseHelper.CheckOut_ProductDescription,
									"");
							values.put(DatabaseHelper.CheckOut_ProductQuantity,
									updateQuantity);
							values.put(DatabaseHelper.CheckOut_PricePerUnit,
									productDetailData.getpPrice());
							values.put(DatabaseHelper.CHECKOut_ProductSKU,
									productDetailData.getpSKU());
							values.put(DatabaseHelper.CheckOut_TotalAmount,
									productDetailData.getpPrice());
							values.put(DatabaseHelper.CHECKOUT_PTYPE, 1);
							values.put(DatabaseHelper.CHECKOut_SALESTAXAMT,
									productDetailData.getSalesTaxAmt());
							values.put(DatabaseHelper.CHECKOut_SERVICETAXAMT,
									productDetailData.getServiceTaxAmt());
							values.put(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
									productDetailData.getSalesTaxAmt());
							values.put(DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
									productDetailData.getServiceTaxAmt());
							values.put(DatabaseHelper.CheckOut_Instruction,
									product_instruction.getText().toString());
						}

						insertedId = databaseHelper.insetCartItem(values);
						if (insertedId != -1) {
							databaseHelper.addAddons(addonList, insertedId, 3, 1);
							databaseHelper.addBundleAddons(bundleList, insertedId,
									4, 1);
//						AppUtiles.getInstance().showToast(getActivity(),
//								"Product successfully added in cart.");
							Snackbar.make(getView(),"Product successfully added in cart.", Snackbar.LENGTH_LONG).show();

						} else {
//						AppUtiles.getInstance().showToast(getActivity(),
//								"Product not added in cart.");
							Snackbar.make(getView(),"Product not added in cart.", Snackbar.LENGTH_LONG).show();

						}
					} else if (state == 2) {
						insertedId = -1;
						if (isVarient) {
							if (varient != null) {
								values.put(DatabaseHelper.FAV_ProductId,
										varient.getId());
								values.put(DatabaseHelper.FAV_ProductName,
										varient.getpName());
								values.put(DatabaseHelper.FAV_ProductDescription,
										"");
								values.put(DatabaseHelper.FAV_Instruction, "");
								values.put(DatabaseHelper.FAV_ProductQuantity,
										updateQuantity);
								values.put(DatabaseHelper.FAV_PricePerUnit,
										varient.getpPrice());
								values.put(DatabaseHelper.FAV_ProductSKU, "");
								values.put(DatabaseHelper.FAV_TotalAmount,
										varient.getpPrice());
								values.put(DatabaseHelper.FAV_PTYPE, 2);
								values.put(DatabaseHelper.FAV_SALESTAXAMT,
										varient.getSalesTaxAmt());
								values.put(DatabaseHelper.FAV_SERVICETAXAMT,
										varient.getServiceTaxAmt());
								values.put(DatabaseHelper.FAV_SALESTAXAMTUNIT,
										varient.getSalesTaxAmt());
								values.put(DatabaseHelper.FAV_SERVICETAXAMTUNIT,
										varient.getServiceTaxAmt());

							}
						} else {
							values.put(DatabaseHelper.FAV_ProductId,
									productDetailData.getpId());
							values.put(DatabaseHelper.FAV_ProductName,
									productDetailData.getpName());
							values.put(DatabaseHelper.FAV_ProductDescription, "");
							values.put(DatabaseHelper.FAV_Instruction, "");
							values.put(DatabaseHelper.FAV_ProductQuantity,
									updateQuantity);
							values.put(DatabaseHelper.FAV_PricePerUnit,
									productDetailData.getpPrice());
							values.put(DatabaseHelper.FAV_ProductSKU,
									productDetailData.getpSKU());
							values.put(DatabaseHelper.FAV_TotalAmount,
									productDetailData.getpPrice());
							values.put(DatabaseHelper.FAV_PTYPE, 1);
							values.put(DatabaseHelper.FAV_SALESTAXAMT,
									productDetailData.getSalesTaxAmt());
							values.put(DatabaseHelper.FAV_SERVICETAXAMT,
									productDetailData.getServiceTaxAmt());
							values.put(DatabaseHelper.FAV_SALESTAXAMTUNIT,
									productDetailData.getSalesTaxAmt());
							values.put(DatabaseHelper.FAV_SERVICETAXAMTUNIT,
									productDetailData.getServiceTaxAmt());

						}
						insertedId = databaseHelper.insertFav(values);
						if (insertedId != -1) {
							databaseHelper.addAddons(addonList, insertedId, 3, 2);
							databaseHelper.addBundleAddons(bundleList, insertedId,
									4, 2);
							Toast.makeText(getActivity(),
									"Product added in favorite list.",Toast.LENGTH_LONG).show();
							Snackbar.make(getView(),"Product added in favorite list.", Snackbar.LENGTH_LONG).show();


						} else {
//						AppUtiles.getInstance().showToast(getActivity(),
//								"Product not added in favorite list.");
							Toast.makeText(getActivity(),
									"Product not added in favorite list.",Toast.LENGTH_LONG).show();
							Snackbar.make(getView(),"Product not added in favorite list.", Snackbar.LENGTH_LONG).show();

						}
					}

				} else {
					// update cart
					if (state == 2) {
//					AppUtiles.getInstance().showToast(getActivity(),
//							"Product already added in favorite list.");
						int result= databaseHelper.deleteFav1(productDetailData.getpId());
						if(result!=0){
							Snackbar.make(getView(),"Product successfully remove from favorite .", Snackbar.LENGTH_LONG).show();
						}

					} else if (state == 1) {

						int quant = databaseHelper.getQuantityByCartId(cartId);
						BigDecimal previous;
						if (isVarient) {
							previous = new BigDecimal(varient.getpPrice());
						} else {
							previous = new BigDecimal(productDetailData.getpPrice());
						}

						values.put(DatabaseHelper.CheckOut_ProductQuantity,
								(updateQuantity + quant));
						values.put(DatabaseHelper.CheckOut_TotalAmount, previous
								.multiply(new BigDecimal((updateQuantity + quant)))
								.toString());
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMT,
								productDetailData.getSalesTaxAmt()
										* (updateQuantity + quant));
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMT,
								productDetailData.getServiceTaxAmt()
										* (updateQuantity + quant));

						int status = databaseHelper.updateCartAttributeItem(values,
								String.valueOf(cartId));

						databaseHelper.updateAttributeQuantity(
								(updateQuantity + quant), cartId);

					}
				}

				databaseHelper.close();
				getActivity().finish();
			}
		}

		private void addCategories() {
			DatabaseHelper databaseHelper = DatabaseHelper
					.newInstance(getActivity());
			databaseHelper.openDataBase();
			Cursor cartIds = databaseHelper.getAllFavourites();
			while(cartIds.moveToNext()){
				String fav_selceted_pId=cartIds.getString(cartIds.getColumnIndex(DatabaseHelper.FAV_ProductName));
				if(productDetailData.getpName().equals(fav_selceted_pId)){
					Drawable bitmaporg_hov = this.getResources().getDrawable(R.drawable.fav_active);
					bitmaporg_hov.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.SRC_IN);
					mCartImg.setBackground(bitmaporg_hov);
					//	mCartImg.setImageResource(R.drawable.ic_fav_active);

				}
			}
			databaseHelper.close();
			BitmapDrawable bitmap = (BitmapDrawable) ContextCompat.getDrawable(
					getActivity(), R.drawable.ic_attribute_category);
			// AppUtiles.getInstance().productDetailsAttributesImageList();

			if (addonList != null && addonList.size() > 0) {
				addView(null, R.color.grey, null, "Addons", "",
						0, bitmap.getBitmap().getWidth(), null, addonList, null,
						null,++tag,null);
			}
			if (varientList != null && varientList.size() > 0) {

				addView(null, R.drawable.ic_attribute_category, null, "Varients",
						"", 1, bitmap.getBitmap().getWidth(), varientList, null,
						null, "single",++tag,null);

			}
			if (bundleList != null && bundleList.size() > 0) {

				for (int i = 0; i < bundleList.size(); i++) {
					RestaurantBundleMetadata bundleData = bundleList.get(i);
					if (i == 0) {
						bundleProductList = bundleData.getBundleAddOnsList();
					}

//

					addView(bundleData.getmGroupImage(),
							R.color.grey, null,
							bundleData.getmGroupName(), "",
							bundleData.getmGroupSeletOption(), bitmap.getBitmap()
									.getWidth(), null, null,
							bundleData.getBundleAddOnsList(),
							bundleData.getmGroupSelectType(),++tag,bundleData.isSelectionRequired());


				}
			}
		}



		private void addView(String imagePath, int image,
							 final ArrayList<ProductOptionsMetadata> optionList1,
							 final String label, final String frontEndStr,
							 final int maxSelection, int width,
							 final ArrayList<RestaurantVarientsMetadata> varientList1,
							 final ArrayList<RestaurantAddOnMetadata> addOnList,
							 final ArrayList<RestaurantAddOnsMetadata> bundleProductList1,
							 final String groupSelectionType,int tag,Boolean groupRequired) {

			final ViewGroup view = (ViewGroup) mInflater.inflate(
					R.layout.row_attribute_category, containerObj, false);
			final TextView categoryTitle = (TextView) view
					.findViewById(R.id.category);
			Typeface gf= Typeface.createFromAsset(getActivity().getAssets(),"Sintony-Bold.ttf" );
			categoryTitle.setTypeface(gf);
			icAttribute = (ImageView) view.findViewById(R.id.image);
			LinearLayout linearObj = (LinearLayout) view.findViewById(R.id.layout);

			if (imagePath == null || imagePath.equals("")
					|| imagePath.equalsIgnoreCase("null")) {
				if (!isImageFound || image == 0) {

					if (label != null)
						categoryTitle.setText(label);
					categoryTitle.setVisibility(view.VISIBLE);
				}
				view.setBackgroundResource(image);

				LayoutParams params = linearObj.getLayoutParams();
				params.width = width;
			} else {
				Picasso.with(getActivity()).load(imagePath).resize(width, width)
						.placeholder(R.drawable.ic_attribute_category)
						.into(icAttribute);
			}
			if(groupRequired){
				categoryTitle.setTextColor(Color.parseColor("#f00b0b"));
			}
			// view.setTag(label);
			view.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.e("dafsdafdsfdsfds", ""+containerObj.getChildCount());
					//v.setPressed(true);
					for(int i =0;i<containerObj.getChildCount();i++){
						if(v.getTag()==containerObj.getChildAt(i).getTag())    {
							Log.e("v get id", ""+v.getTag());
							Log.e("container get id", ""+containerObj.getChildAt(i).getTag());
							containerObj.getChildAt(i).setBackgroundResource(R.drawable.ic_attribute_category_white);
							// categoryTitle.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));

						}else{
							Log.e("else    get id", ""+v.getTag());
							Log.e("else    container get id", ""+containerObj.getChildAt(i).getTag());
							//.setTextColor(Color.parseColor("#666666"));
							containerObj.getChildAt(i).setBackgroundColor(Color.parseColor("#F4EFEF"));
						}
					}

					listviewHeaderTitle.setText(getString(R.string.select_one));
					optionsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
					if (groupSelectionType != null
							&& groupSelectionType.equalsIgnoreCase("single")) {
						listviewHeaderTitle.setText(getString(R.string.select_one));
						maxSelected = 1;
						optionsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


					} else {
						if (maxSelection == 0) {
							listviewHeaderTitle
									.setText(getString(R.string.select_multiple));
							// maxSelected = 0;
						} else {
							listviewHeaderTitle.setText("Select " + maxSelection
									+ " item");

						}
						maxSelected = maxSelection;
						optionsListView
								.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
					}

					if (label.equals("Addons")) {
						productType = ADDONS;
						addonList = addOnList;
					} else if (label.equals("Varients")) {
						productType = VARIENTS;
						varientList = varientList1;
					} else {
						productType = BUNDLE;
						bundleProductList = bundleProductList1;
					}
					// optionsList = optionList1;

					adapterObj.notifyDataSetChanged();
				}
			});
			// System.out.println(label + " count container obj "
			// + containerObj.getChildCount());
			if (containerObj.getChildCount() == 0) {
				// System.out.println(label+" count container obj1 "+containerObj.getChildCount());
				if (label.equals("Addons")) {
					productType = ADDONS;

				} else if (label.equals("Varients")) {
					productType = VARIENTS;

				} else {
					productType = BUNDLE;
				}
				if (groupSelectionType != null
						&& groupSelectionType.equalsIgnoreCase("single")) {
					listviewHeaderTitle.setText(getString(R.string.select_one));
					maxSelected = 1;
					optionsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				} else {
					if (maxSelection == 0) {
						listviewHeaderTitle
								.setText(getString(R.string.select_multiple));
						// maxSelected = 0;
					} else {
						listviewHeaderTitle.setText("Select " + maxSelection
								+ " item");

					}
					maxSelected = maxSelection;
					optionsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
				}
				adapterObj = new ProductAttributesAdapter();
				optionsListView.setAdapter(adapterObj);
			}
			// else {
			// adapterObj.notifyDataSetChanged();
			// }
			view.setTag(tag);

			containerObj.addView(view);
			containerObj.getChildAt(0).setBackgroundResource(R.drawable.ic_attribute_category_white);

		}

		private class ProductAttributesAdapter extends BaseAdapter {
			BigDecimal bigDecimal;

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				if (productType == ADDONS) {
					// if (optionsList != null)
					return addonList.size();
				} else if (productType == VARIENTS) {
					return varientList.size();
				} else if (productType == BUNDLE) {
					return bundleProductList.size();
				} else
					return 0;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				if (productType == ADDONS) {
					return addonList.get(position);
				} else if (productType == VARIENTS) {
					return varientList.get(position);
				} else if (productType == BUNDLE) {
					return bundleProductList.get(position);
				} else
					return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			@SuppressLint("NewApi") @Override
			public View getView(final int position, View convertView,
								ViewGroup parent) {

				// TODO Auto-generated method stub
				final ViewHolder holderObj ;
				if (convertView == null) {
					holderObj = new ViewHolder();
					convertView = mInflater.inflate(R.layout.row_product_attribute,
							parent, false);
					holderObj.chkbView = (CheckBox) convertView
							.findViewById(R.id.checkbox);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
						holderObj.chkbView.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE)));
					}
					holderObj.titleView = (TextView) convertView
							.findViewById(R.id.name);
					holderObj.priceLayout = (LinearLayout)convertView
							.findViewById(R.id.price_tag);
					holderObj.price = (TextView) convertView
							.findViewById(R.id.price);

					Typeface tf= Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
//				holderObj.titleView.setTypeface(tf);
//				holderObj.price.setTypeface(tf);

					holderObj.price.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));


					convertView.setTag(holderObj);
				} else {
					holderObj = (ViewHolder) convertView.getTag();

				}
				if (productType == BUNDLE) {

					final RestaurantAddOnsMetadata data = (RestaurantAddOnsMetadata) getItem(position);

					// System.out.println("groupId "
					// + bundleProductList.get(position).getmGroupId());
					// System.out.println("groupName "
					// + bundleProductList.get(position).getaName());
					// System.out.println("Attribute Id "
					// + bundleProductList.get(position).getId());
					if (data != null) {
						if (data.getaPrice() != null
								&& !data.getaPrice().equals("")
								&& !data.getaPrice().equalsIgnoreCase("null")
								&& !data.getaPrice().equals("0")
								&& !data.getaPrice().equals("0.0")) {
							bigDecimal = new BigDecimal(data.getaPrice());

							if (data.isSelected()) {
								holderObj.chkbView.setChecked(data.isSelected());
								//	holderObj.chkbView.setVisibility(View.VISIBLE);
								//	holderObj.priceLayout.setVisibility(View.GONE);

								holderObj.price.setText(getString(R.string.usd)
										+ " "
										+ bigDecimal.setScale(2,
										BigDecimal.ROUND_HALF_UP));




							}
							else {


								holderObj.chkbView.setChecked(data.isSelected());
								//holderObj.chkbView.setVisibility(View.GONE);
								holderObj.priceLayout.setVisibility(View.VISIBLE);
								holderObj.price.setText(getString(R.string.usd)
										+ " "
										+ bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP));
								//pricePerQuantity=pricePerQuantity.subtract(bigDecimal);
							}

						} else {
							//	holderObj.priceLayout.setVisibility(View.GONE);
							holderObj.chkbView.setChecked(data.isSelected());
							//	holderObj.chkbView.setVisibility(View.VISIBLE);



						}
						holderObj.titleView.setText(data.getaName());
						convertView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (maxSelected == 0) {
//								pricePerQuantity=new BigDecimal(data.getaPrice());
//
									//	Log.e("total", dec.toString());
									Log.e("convertview if", "convertview max");

									data.setSelected(!data.isSelected());
									calc_add_total();
								} else {
									if (data.isSelected()) {

//									if(maxSelected==1){
//										holderObj.chkbView.setChecked(false);
//										data.setSelected(!data.isSelected());
//									}
										Log.e("convertview if", "convertview if");


										data.setSelected(!data.isSelected());
										calc_add_total();
										notifyDataSetChanged();

									} else {
										/**
										 * first count selectd items. if less than 5
										 * then checked else prompt message
										 */
										int count = 0;
										for (int i = 0; i < bundleProductList
												.size(); i++) {

											if (bundleProductList.get(i)
													.isSelected()) {
												count++;


											}

										}

										if (count < maxSelected) {
											data.setSelected(!data.isSelected());
											require_layout.setVisibility(View.INVISIBLE);
											Log.e("bundle.....iff", ""+maxSelected);

											Log.e("convertview if", "convertview else");
											calc_add_total();
											notifyDataSetChanged();
										} else {

											notifyDataSetChanged();
//										AppUtiles.getInstance().showToast(getActivity(),
//												"Not selected more than "
//														+ maxSelected
//														+ "  elements");
											Log.e("bundle.....else", ""+maxSelected);



											if (bundleList != null) {
												for (int i = 0; i < bundleList.size(); i++) {
													RestaurantBundleMetadata bundleData = bundleList.get(i);
													if (bundleData != null) {
														ArrayList<RestaurantAddOnsMetadata> bundleProductList = bundleData.getBundleAddOnsList();
														if (bundleProductList != null) {

															for (int j = 0; j < bundleProductList.size(); j++) {
																RestaurantAddOnsMetadata addOnsData = bundleProductList
																		.get(j);
																if (addOnsData != null) {
																	if (addOnsData.isSelected()) {
																		addOnsData.setSelected(false);



																	}
																}
															}
														}

													}
												}
											}
											data.setSelected(true);
											holderObj.chkbView.setChecked(data.isSelected());
											//require_layout.setVisibility(View.VISIBLE);
//										req.setText("Not selected more than "
//												+ maxSelected
//												+ "  elements");
										}
									}
								}

								notifyDataSetChanged();
							}
						});
					}
				}

				return convertView;
			}

			private class ViewHolder {
				// private ImageView categoryView;
				private CheckBox chkbView;
				private TextView titleView, price;
				private LinearLayout priceLayout;

			}

		}
		public void calc_add_total(){
			if (bundleList != null) {

				BigDecimal b;
				priceperquantitybundle= BigDecimal.ZERO;
				for (int i = 0; i < bundleList.size(); i++) {
					RestaurantBundleMetadata bundleData = bundleList.get(i);
					if (bundleData != null) {
						ArrayList<RestaurantAddOnsMetadata> bundleProductList = bundleData.getBundleAddOnsList();
						if (bundleProductList != null) {

							for (int j = 0; j < bundleProductList.size(); j++) {
								RestaurantAddOnsMetadata addOnsData = bundleProductList
										.get(j);
								if (addOnsData != null) {
									if (addOnsData.isSelected()) {

										priceperquantitybundle=priceperquantitybundle.add(new BigDecimal(addOnsData.getaPrice()));
//									BigDecimal add= new BigDecimal(addOnsData.getaPrice());
//												b=priceperquantitybundle.add(new BigDecimal(addOnsData.getaPrice()));
										Log.e("adon name", addOnsData.getaName());
										Log.e("priceperquantity", priceperquantitybundle.toString());
										//Log.e("bbbbbb", b.toString());

									}
								}

							}
						}

					}
				}

			}
			BigDecimal de;
			if(priceperquantitybundle==null ){
				de= new BigDecimal("0.00");
			}else{
				de=priceperquantitybundle.multiply(new BigDecimal(quantityView.getText().toString()));
			}
			Log.e("priceperquantity", priceperquantitybundle.toString());
			BigDecimal tot=pricePerQuantity
					.add(de);
			amountTextView.setText("$ "
					+ tot.setScale(2,BigDecimal.ROUND_HALF_UP));
		}
	}
