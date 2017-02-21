package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.DetailActivity;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.LoginSignupActivity;
import com.app2mobile.Sultanwok.Manual_Location;
import com.app2mobile.Sultanwok.Payment_InformationActivity;
import com.app2mobile.Sultanwok.PickupnDeliveryDialog;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.CheckOutMetadata;
import com.app2mobile.metadata.DeliveryTimingList;
import com.app2mobile.metadata.RestaurantDeliveryTimeMetadata;
import com.app2mobile.metadata.RestaurantDetailMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;
import com.app2mobile.utiles.JsonParser;
import com.google.gson.Gson;

public class CheckOutProductListFragment extends BaseFragment {
	private boolean isEditMode = false, isLeft = false;
	private ArrayList<CheckOutMetadata> cartItemsList = new ArrayList<CheckOutMetadata>();
	private CartAdapter cartAdapterObj;
	private ListView cartListObj;
	private TextView totalAmount, checkoutView, total_tax, total_with_tax,edit_item,subtotal;
	private TextView time,time_txt;
	private LayoutInflater mInflater;
	View listviewFooter;
	JSONArray productJsonArray;
	private EditText specailCartIns,name;
	GregorianCalendar finalTime, mCalendarOpeningTime, mCalendarClosingTime;
	private Dialog expressDialog;
	private HashMap<String, ArrayList<CheckOutMetadata>> productList = new HashMap<String, ArrayList<CheckOutMetadata>>();
	int slidePosition = -1;
	private Double totalSalesTax = 0d, totalServiceTax = 0d;
	private String discountAmt, discountType, discoWithDelivery, businessHrStr,fav_order_id;
	RelativeLayout footer,cart_null,rel,relative,reltiv,total_container,subtotal_layout;
	private RestaurantDetailMetadata restaurantDetailData;
	int isClearCart=1;
	private HashMap<String, RestaurantDeliveryTimeMetadata> restaurantDeliveryTimeList;
	private HashMap<String, ArrayList<DeliveryTimingList>> timinglist;
	private static CheckOutProductListFragment instance = null;
	private RelativeLayout scrollview;
	public ImageView edit_time,favorite;
	public Dialog fav_dialog;
	public Button shopping;
	private String[] arrTemp;
	public TextWatcher addtextchange;
	public static CheckOutProductListFragment newInstance() {
	
		if (instance == null) {
			
			instance = new CheckOutProductListFragment();
		}
		return instance;
	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		System.out.print("fragment_checkout");
		return R.layout.fragment_checkout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity());
//		getAllCartItems();
		
	
	}

	// @Override
	// public void onStart() {
	// // TODO Auto-generated method stub
	// super.onStart();
	// getAllCartItems();
	// }

	 @Override
	 public void onResume() {
	 // TODO Auto-generated method stub
	 super.onResume();
		Global.getInstance().trackScreenView("Cart Page");

	 time_txt.setText(((Global) CheckOutProductListFragment.this
				.getActivity().getApplicationContext()).getDelivery_type()+" Time:");
	 time.setText(((Global) CheckOutProductListFragment.this
				.getActivity().getApplicationContext()).getDelvery_time());
	 getAllCartItems();
	 calculateTotalProduct();
	 }

	 @Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	 
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mTitleTxt.setText("Cart");
		//mCartImg.setImageResource(R.drawable.ic_edit_background);
	//	mCartCountTxt.setVisibility(View.GONE);
		mTabLayout.setVisibility(View.GONE);
		checkoutView = (TextView) view.findViewById(R.id.checkOut);
		cartListObj = (ListView) view.findViewById(R.id.list);
		cartAdapterObj = new CartAdapter();
		cartListObj.setAdapter(cartAdapterObj);
//		cartListObj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				((SwipeLayout)(cartListObj.getChildAt(position - cartListObj.getFirstVisiblePosition()))).open(true);
//			}
//			
//		});
		specailCartIns = (EditText) view.findViewById(R.id.putIns);
		totalAmount = (TextView) view.findViewById(R.id.totalAmount);
		total_tax=(TextView)view.findViewById(R.id.vat_amount);
		total_with_tax=(TextView)view.findViewById(R.id.total_amount);
		edit_item=(TextView)view.findViewById(R.id.edit);
		footer=(RelativeLayout)view.findViewById(R.id.footer);		
		time=(TextView)view.findViewById(R.id.time);
		scrollview=(RelativeLayout)view.findViewById(R.id.scrollview);
		edit_time=(ImageView)view.findViewById(R.id.edit_time);
		favorite=(ImageView)view.findViewById(R.id.fav);
		time_txt=(TextView)view.findViewById(R.id.time_text);
		cart_null=(RelativeLayout)view.findViewById(R.id.cart_null);
		reltiv=(RelativeLayout)view.findViewById(R.id.reltiv);
		relative=(RelativeLayout)view.findViewById(R.id.relative);
		subtotal=(TextView)view.findViewById(R.id.subtotal);
			rel=(RelativeLayout)view.findViewById(R.id.rel);
			shopping=(Button)view.findViewById(R.id.shopping);
			total_container=(RelativeLayout)view.findViewById(R.id.total_container);
			subtotal_layout=(RelativeLayout)view.findViewById(R.id.subtotal_layout);
		 File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
		Typeface tf1= Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Bold.ttf");
		Log.e(" cartItemsList.size()", ""+cartItemsList.size());
		time.setTypeface(tf1);
		circle.setVisibility(View.INVISIBLE);
		mCartCountTxt.setVisibility(View.INVISIBLE);
		mCartImg.setVisibility(View.INVISIBLE);
		
		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		Drawable dr = new BitmapDrawable(bmp);
		scrollview.setBackgroundDrawable(dr);
		
		((Global)getActivity().getApplicationContext()).setIs_guest_user("false");
		try{
		fav_order_id=((Global)CheckOutProductListFragment.this.getActivity().getApplication()).getFavroite_order_id().toString();
		if(fav_order_id.equals("")||fav_order_id==null|| fav_order_id.length()==0 ||fav_order_id.isEmpty()){
			
		}
		else{
			favorite.setVisibility(View.VISIBLE);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		Log.e("carttttt item no", mCartCountTxt.getText().toString());
		DatabaseHelper helper = DatabaseHelper.newInstance(getContext());
		helper.openDataBase();
		 Integer items = helper.getTotalCartItems();
		 helper.close();
		 LayoutParams lp = (LayoutParams) cartListObj.getLayoutParams();
		 lp.height = items*150;
		 Log.e("lppp height", ""+lp.height);
		 cartListObj.setLayoutParams(lp);
		if(items==0){
			
			rel.setVisibility(View.GONE);
			relative.setVisibility(View.GONE);
			reltiv.setVisibility(View.GONE);
			footer.setVisibility(View.GONE);
			total_container.setVisibility(View.GONE);
			subtotal_layout.setVisibility(View.GONE);
			cart_null.setVisibility(View.VISIBLE);
		}
		 Typeface tf= Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
		 Typeface tf2= Typeface.createFromAsset(getActivity().getAssets(), "Sintony-Bold.ttf");

		 checkoutView.setTypeface(tf2);
		 totalAmount.setTypeface(tf);
		 total_tax.setTypeface(tf);
		 total_with_tax.setTypeface(tf);
		 edit_item.setTypeface(tf);
		 subtotal.setTypeface(tf);
		 footer.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		 shopping.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		 try{
		Gson gson = new Gson();
		String restaurantDetail = ((BaseActivity) getActivity()).mRestaurantDetailPrefs
				.getString(ConstantsUrl.RESTAURANT_DETAILS, "");
		System.out.println("restaurant Detail"+restaurantDetail);
		restaurantDetailData = gson.fromJson(restaurantDetail,
				RestaurantDetailMetadata.class);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
//		if (restaurantDetail != null) {
//			restaurantDeliveryTimeList = restaurantDetailData
//					.getmDeliveryList();
//
//		}
		shopping.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						Manual_Location.class);
				startActivity(intent);
			}
		});
		edit_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						PickupnDeliveryDialog.class);
				startActivity(intent);

			}
		});
		favorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 fav_dialog= new  Dialog(getContext(), R.style.CustomDialog);
				fav_dialog.setContentView(R.layout.fav_name);
				fav_dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				
				 name=(EditText)fav_dialog.findViewById(R.id.fav_name);
				Button btn= (Button)fav_dialog.findViewById(R.id.sub);
				btn.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						new Save_Fav().execute();
					}
				});
				fav_dialog.show();
			}
		});
		calculateTotalProduct();
		finalTime = new GregorianCalendar();
		
		//arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		time.setText(((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).getDelvery_time());
		edit_item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				isEditMode = !isEditMode;
				if (isEditMode) {
				//	mCartImg.setImageResource(R.drawable.ic_save_background);
					edit_item.setText("Save Order");

				} else {
				//	mCartImg.setImageResource(R.drawable.ic_edit_background);
					edit_item.setText("Edit Order");
					isLeft = false;
					slidePosition = -1;
					AppUtiles.getInstance().setCartItems(mCartCountTxt, getActivity());
					if(mCartCountTxt.getText().toString().equals("0")){
						rel.setVisibility(View.GONE);
						relative.setVisibility(View.GONE);
						reltiv.setVisibility(View.GONE);
						footer.setVisibility(View.GONE);
						total_container.setVisibility(View.GONE);
						subtotal_layout.setVisibility(View.GONE);
						cart_null.setVisibility(View.VISIBLE);
					}

				}
				cartAdapterObj.notifyDataSetChanged();
			}
		});
//		listviewFooter = mInflater.infliate(R.layout.cart_listview_footer, null);
//		if (cartItemsList.size() > 0) {
//			cartListObj.addFooterView(listviewFooter);
//		}
		
		footer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(((Global)getActivity().getApplication()).getIs_restaurant_open().equals("0")){
					//Toast.makeText(getActivity(), "Restaurant Is Closed",2).show();
					Snackbar.make(getView(), "Restaurant Is Closed", 5).show();
				}else {
					
				

				if (cartItemsList != null && cartItemsList.size() > 0) {
					String storeDeliveryStatus = null;
					String minimumDelivery = null;
					if (restaurantDetailData != null) {
						storeDeliveryStatus = restaurantDetailData
								.getmStoreDeliveryStatus();
						minimumDelivery = restaurantDetailData
								.getmStoreMinDeliveryValue();

					}

					String totalAmt = totalAmount.getText().toString()
							.replace(getString(R.string.usd), "").trim();
					((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).setTotalamout_without_text(totalAmt);
					((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).setSpecial_instruction(specailCartIns.getText().toString());

					BigDecimal minimumDeliveryDec, totalAmtDec;

					try {
						minimumDeliveryDec = new BigDecimal(minimumDelivery);
					} catch (Exception e) {
						minimumDeliveryDec = new BigDecimal(0);
					}
					try {
						totalAmtDec = new BigDecimal(totalAmt);
					} catch (Exception e) {
						// e.printStackTrace();
						totalAmtDec = new BigDecimal(0);
					}
					minimumDeliveryDec = minimumDeliveryDec.setScale(2,
							BigDecimal.ROUND_HALF_UP);
					totalAmtDec = totalAmtDec.setScale(2,
							BigDecimal.ROUND_HALF_UP);

					if (storeDeliveryStatus == null	|| storeDeliveryStatus.equals("")|| storeDeliveryStatus.equals("0")) {
//						AppUtiles
//								.getInstance()
//								.showToast(getActivity(),
//										"Currently Restaurant has not accept orders.Sorry!");
						Snackbar.make(getView(),"Currently Restaurant has not accept orders.Sorry!", Snackbar.LENGTH_LONG).show();
						
					} else if ((totalAmtDec.compareTo(new BigDecimal(0)) == 0 && minimumDeliveryDec
							.compareTo(new BigDecimal(0)) == 0)
							|| (minimumDeliveryDec.compareTo(totalAmtDec) == 1)) {
//						AppUtiles.getInstance().showToast(
//								getActivity(),
//								"Minimum Order: $ "
//										+ minimumDeliveryDec.setScale(2,
//												BigDecimal.ROUND_HALF_UP));
						Snackbar.make(getView(),"Minimum Order: $ "
								+ minimumDeliveryDec.setScale(2,
										BigDecimal.ROUND_HALF_UP), Snackbar.LENGTH_LONG).show();
					}
//					else if(time.getText()==null|| time.getText().length()==0)
//					{
//					
//					
//						AppUtiles.getInstance().showToast(
//								getActivity(),
//								"Please Enter Delivery Time");
//					}
					else {
						// .........
						//Log.e("cartttttttt", productJsonArray.toString());
						if (!mAppPrefs.getBoolean(ConstantsUrl.IS_LOGIN, false)) {
							if(((Global)getActivity().getApplicationContext()).getIs_guest_user().equals("true")){
//								Intent in = new Intent(getActivity(), DetailActivity.class);
//								((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).setProduct_cart(productJsonArray.toString());
//								in.putExtra("totalcart",productJsonArray.toString() );
//								
//								startActivity(in);
								sendDataOnServer();

							}
							else{
							Intent checkOutIntent = new Intent(getActivity(),
									LoginSignupActivity.class);
							checkOutIntent.putExtra("profile", "checkOut");
							checkOutIntent.putExtra("isCheckout",true);
							checkOutIntent.putExtra("minimumDelivery",minimumDeliveryDec);
							checkOutIntent.putExtra("totalAmt", totalAmtDec);
							startActivityForResult(checkOutIntent,ConstantsUrl.CHECKOUT_);
							}
							} else {
							totalcart();
							Intent in = new Intent(getActivity(), Payment_InformationActivity.class);
							in.setData(null);
							in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).setProduct_cart(productJsonArray.toString());
						//	in.putExtra("totalcart",productJsonArray.toString() );
							Log.e("cartttttttt", productJsonArray.toString());
							startActivity(in);
							//sendDataOnServer();
						}
						// ...........
					}
				} else {
//					AppUtiles.getInstance().showToast(getActivity(),
//							"No products in cart.");
					Snackbar.make(getView(),"No products in cart.", Snackbar.LENGTH_LONG).show();
				}
			}
			}
			
		});

		
		
		
		}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == getActivity().RESULT_OK) {
			switch (requestCode) {
			case ConstantsUrl.CHECKOUT_:
				System.out.println("out switch case");
				if (data != null) {
					System.out.println("in switch case");
					BigDecimal minimumDeliveryDec, totalAmtDec;
					minimumDeliveryDec = (BigDecimal) data.getExtras().get("minimumDelivery");
					totalAmtDec = (BigDecimal) data.getExtras().get("totalAmt");
					sendDataOnServer();
				//	sendDataOnServer(minimumDeliveryDec, totalAmtDec);
				}

				break;

			}
		}
	}
	private void sendDataOnServer() {
		


					try {
						JSONArray productJsonArray = new JSONArray();
						for (int i = 0; i < cartItemsList.size(); i++) {
							CheckOutMetadata data = cartItemsList.get(i);
							if (data != null) {
								JSONObject productJson = new JSONObject();
								productJson.put("product_id",data.getProductId());
								productJson.put("product_qty",data.getQuantity());
								// productJson.put("product_rate",
								// data.getPricePerUnit());
								// productJson.put("product_price",
								// data.getAddTotalAmt());
								productJson.put("product_instruction",
										data.getSpecialInstruction());

								productJson.put("order_product_addons",
										data.getAttributesSKU());
								HashMap<Integer, ArrayList<String>> bundleList = data
										.getBundleProductHashList();
								JSONObject groupId = new JSONObject();
								if (bundleList != null) {
									for (Entry<Integer, ArrayList<String>> entry : bundleList
											.entrySet()) {
										ArrayList<String> valuesList = entry
												.getValue();
										String key = entry.getKey().toString();
										if (valuesList != null) {
											JSONArray jsonBundleArr = new JSONArray();
											for (int k = 0; k < valuesList
													.size(); k++) {
												jsonBundleArr.put(valuesList
														.get(k));
											}
											groupId.put(key, jsonBundleArr);
										}

									}
									productJson.put("order_product_bundle",
											groupId);
								} else {
									productJson.put("order_product_bundle", "");
								}

								// productJson.put("order_product_bundl11",
								// data.getBundleProductSKU());
								productJsonArray.put(productJson);

							}
						}
						((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).setProduct_cart(productJsonArray.toString());

						Intent checkOutIntent = new Intent(getActivity(),
								DetailActivity.class);
						checkOutIntent.putExtra("isCart", 2);
						//checkOutIntent.putExtra("totalcart",productJsonArray.toString());
						//checkOutIntent.putExtra("OrderType", orderTypeStr);
//						checkOutIntent.putExtra("deliveryTime",DateFormat.format("yyyy-MM-dd HH:mm",finalTime.getTime()));
//						checkOutIntent.putExtra("instruction", specailCartIns
//								.getText().toString());
//						checkOutIntent.putExtra("amount", totalAmount.getText().toString().replace(getString(R.string.usd), "").trim());
//						checkOutIntent.putExtra("serviceTax", totalServiceTax);
//						checkOutIntent.putExtra("salesTax", totalSalesTax);
//						checkOutIntent.putExtra("discountType", discountType);
//						checkOutIntent.putExtra("discoWithDelivery",
//								discoWithDelivery);
					
						
							startActivity(checkOutIntent);
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
	
		
	
	

	private void getAllCartItems() {
		cartItemsList.clear();
		productList.clear();
		
		try {
			DatabaseHelper databaseHelper = DatabaseHelper
					.newInstance(getActivity());
			databaseHelper.openDataBase();
			Cursor cursor = databaseHelper.getAllCartItems();
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						CheckOutMetadata data = new CheckOutMetadata();
						data.setCheckOutId(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_Id)));
						data.setProductId(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_ProductId)));
						data.setProductName(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_ProductName)));

						data.setProductDesc(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_Instruction)));
						data.setQuantity(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_ProductQuantity)));
						data.setPricePerUnit(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_PricePerUnit)));
						data.setCreatedOn(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_CreatedOn)));
						data.setSpecialInstruction(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_Instruction)));
						data.setTotalAmount(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CheckOut_TotalAmount)));
						data.setProductSKU(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.CHECKOut_ProductSKU)));
						data.setParentId(cursor.getInt(cursor
								.getColumnIndex(DatabaseHelper.CHECKOut_ISPARENT)));
						data.setIsAttribute(cursor.getInt(cursor
								.getColumnIndex(DatabaseHelper.CHECKOUT_PTYPE)));
						data.setTotalSalesTax(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.CHECKOut_SALESTAXAMT)));
						data.setTotalServiceTax(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.CHECKOut_SERVICETAXAMT)));
						data.setSaleTaxUnit(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT)));
						data.setServiceTaxUnit(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT)));

						data.setAddTotalAmt(data.getTotalAmount());
						data.setAttributesSKU("");
						data.setAddPricePerUnit(data.getPricePerUnit());
						ArrayList<CheckOutMetadata> attributesList = new ArrayList<CheckOutMetadata>();
						attributesList.add(0, data);

						/**
						 * Attributes ,crossell. upsell
						 * 
						 * 
						 */

						if (data.getIsAttribute() == 1
								|| data.getIsAttribute() == 2) {
							BigDecimal productPrice = new BigDecimal(
									data.getTotalAmount());
							productPrice = productPrice.setScale(2,
									BigDecimal.ROUND_HALF_UP);
							BigDecimal unitPrice = new BigDecimal(
									data.getPricePerUnit());
							Double serviceTaxUnit, saleTaxUnit, totalService, totalSales;
							serviceTaxUnit = data.getServiceTaxUnit();
							saleTaxUnit = data.getSaleTaxUnit();
							totalSales = data.getTotalSalesTax();
							totalService = data.getTotalServiceTax();
							BigDecimal productItemPrice;
							String projectDesc = "";
							String attributesSkus = "", bundleProductSku = "";
							ArrayList<String> skuList = new ArrayList<String>();
							ArrayList<String> bundleSKUList = new ArrayList<String>();
							HashMap<Integer, ArrayList<String>> bundleHasList = new HashMap<Integer, ArrayList<String>>();
							Cursor cursor1 = databaseHelper.getCartAttributes(
									data.getCheckOutId(), 1);

							if (cursor1 != null) {
								if (cursor1.moveToFirst()) {
									do {
										CheckOutMetadata data1 = new CheckOutMetadata();
										data1.setCheckOutId(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_Id)));
										data1.setProductId(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_ProductId)));
										data1.setProductName(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_ProductName)));
										data1.setProductDesc(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_ProductDescription)));
										data1.setQuantity(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_ProductQuantity)));
										data1.setPricePerUnit(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_PricePerUnit)));
										data1.setCreatedOn(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_CreatedOn)));
										data1.setSpecialInstruction(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_Instruction)));
										data1.setTotalAmount(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CheckOut_TotalAmount)));
										data1.setProductSKU(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.CHECKOut_ProductSKU)));
										data1.setParentId(cursor1.getInt(cursor1
												.getColumnIndex(DatabaseHelper.CHECKOut_ISPARENT)));
										data1.setTotalSalesTax(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.CHECKOut_SALESTAXAMT)));
										data1.setTotalServiceTax(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.CHECKOut_SERVICETAXAMT)));
										data1.setSaleTaxUnit(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT)));
										data1.setServiceTaxUnit(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT)));
										data1.setIsAttribute(cursor1.getInt(cursor1
												.getColumnIndex(DatabaseHelper.CHECKOUT_PTYPE)));
										int mGroupId = cursor1
												.getInt(cursor1
														.getColumnIndex(DatabaseHelper.CHECKOut_GroupId));
										attributesList.add(data1);
										String desc = data1.getProductName();

										BigDecimal quant = new BigDecimal(
												data1.getQuantity());
										productItemPrice = new BigDecimal(
												data1.getPricePerUnit());
										unitPrice = unitPrice
												.add(productItemPrice);
										unitPrice = unitPrice.setScale(2,
												BigDecimal.ROUND_HALF_UP);
										productItemPrice = productItemPrice
												.multiply(quant);
										productItemPrice = productItemPrice
												.setScale(
														2,
														BigDecimal.ROUND_HALF_UP);

										productPrice = productPrice
												.add(productItemPrice);

										if (desc != null && !desc.equals("")) {
											projectDesc += desc + ", ";

										}
										String productSku = data1
												.getProductId();
										if (productSku != null
												&& !productSku.equals("")
												&& !productSku.equals("null")) {
											if (data1.getIsAttribute() == 4) {
												bundleProductSku += productSku
														+ ",";
												bundleSKUList.add(productSku);
												if (bundleHasList
														.containsKey(mGroupId)) {

													bundleHasList.get(mGroupId)
															.add(productSku);

												} else {
													ArrayList<String> bundleSkuList = new ArrayList<String>();
													bundleSkuList
															.add(productSku);
													bundleHasList.put(mGroupId,
															bundleSkuList);
												}
											} else {
												attributesSkus += productSku
														+ ",";
												skuList.add(productSku);
											}
										}
										serviceTaxUnit += data1
												.getServiceTaxUnit();
										saleTaxUnit += data1.getSaleTaxUnit();
										//totalSales = saleTaxUnit* quant.intValue();
										totalSales= totalSalesTax;
										totalService = serviceTaxUnit
												* quant.intValue();

									} while (cursor1.moveToNext());
								}
							}
							cursor1.close();

							if (projectDesc != null && projectDesc.length() > 0) {
								projectDesc = projectDesc.trim().substring(0,
										projectDesc.length() - 2);
							}

							if (attributesSkus != null
									&& attributesSkus.length() > 0) {
								attributesSkus = attributesSkus.trim()
										.substring(0,
												attributesSkus.length() - 1);
							}
							if (bundleProductSku != null
									&& bundleProductSku.length() > 0) {
								bundleProductSku = bundleProductSku.trim()
										.substring(0,
												bundleProductSku.length() - 1);
							}

							data.setAddTotalAmt(productPrice.toString());
							data.setProductDesc(projectDesc);

							data.setAddPricePerUnit(unitPrice.toString());

							data.setSaleTaxUnit(saleTaxUnit);
							data.setServiceTaxUnit(serviceTaxUnit);
							data.setTotalSalesTax(totalSales);
							data.setTotalServiceTax(totalService);
							data.setAttributesSKU(attributesSkus);
							data.setProductSKUList(skuList);
							data.setBundleProductSKU(bundleProductSku);
							data.setBundleSKUList(bundleSKUList);
							data.setBundleProductHashList(bundleHasList);

						}
						/**
						 * 
						 */
						cartItemsList.add(data);
						productList.put(data.getCheckOutId(), attributesList);
					} while (cursor.moveToNext());

				}
				cursor.close();
			}
			databaseHelper.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (cartAdapterObj != null)
			cartAdapterObj.notifyDataSetChanged();
	}

	private class CartAdapter extends BaseAdapter {
		// private LayoutInflater mInflater;
		private BigDecimal pricePerQuantity, salesTaxPerQuant,
				serviceTaxPerQuant;

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cartItemsList.size();
		}

		@Override
		public CheckOutMetadata getItem(int position) {
			// TODO Auto-generated method stub
			return cartItemsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final ViewHolder holderObj;
			 View currentFocus = ((Activity)getActivity()).getCurrentFocus();
			    if (currentFocus != null) {
			        currentFocus.clearFocus();
			    }
				arrTemp = new String[cartItemsList.size()];
				
			if (convertView == null) {
				holderObj = new ViewHolder();
				holderObj.hold=position;
				convertView = mInflater.inflate(R.layout.row_cart, parent,
						false);
				holderObj.deleteIcon = (ImageView) convertView
						.findViewById(R.id.deleteIcon);
				holderObj.plusView = (ImageView) convertView
						.findViewById(R.id.plus);
				holderObj.minusView = (ImageView) convertView
						.findViewById(R.id.minus);
				holderObj.productName = (TextView) convertView
						.findViewById(R.id.productName);
				holderObj.productDesc = (TextView) convertView
						.findViewById(R.id.productDesc);
				holderObj.price = (TextView) convertView
						.findViewById(R.id.price);
				holderObj.quantity = (EditText) convertView
						.findViewById(R.id.quantity);
				holderObj.deleteItem = (LinearLayout) convertView
						.findViewById(R.id.delete);
				holderObj.slideLayout = (LinearLayout) convertView
						.findViewById(R.id.slide);
				 Typeface tf= Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
				 holderObj.productName.setTypeface(tf);
				 holderObj.productDesc.setTypeface(tf);
				 holderObj.price.setTypeface(tf);
				 //holderObj.quantity.setRawInputType(Configuration.KEYBOARD_12KEY);
				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();
			}
			if (isEditMode) {
				holderObj.deleteIcon.setVisibility(View.VISIBLE);
				holderObj.quantity.setCursorVisible(true);
				holderObj.quantity.setFocusableInTouchMode(true);
				holderObj.quantity.setInputType(InputType.TYPE_CLASS_TEXT);
				holderObj.quantity.requestFocus();
			} else {
				
				holderObj.deleteIcon.setVisibility(View.GONE);
				holderObj.quantity.setCursorVisible(false);
				holderObj.quantity.setFocusableInTouchMode(false);
			}
			holderObj.deleteItem.measure(MeasureSpec.UNSPECIFIED,
					MeasureSpec.UNSPECIFIED);
			final CheckOutMetadata data = getItem(position);

			holderObj.quantity.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub
					holderObj.quantity.setSelection(count);
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					holderObj.quantity.setSelection(holderObj.quantity.getText().length());
					arrTemp[holderObj.hold] = s.toString();
					int temp=1;
					try {
						   int iLen=s.length();
						    
						    if (iLen>1){
						        s.delete(0, 1);
						        Selection.setSelection(s, s.length());
						    } 
						
						
						temp = Integer.parseInt(arrTemp[position]);
						if(holderObj.quantity.getText().toString().equals("")|| holderObj.quantity.getText().toString()==null){
							temp = 1;

						}
						if(temp==0){
							temp = 1;
						}
						
					} catch (Exception e) {
						// TODO: handle exception
						temp = 1;
						e.printStackTrace();
					}
					try {
						DatabaseHelper helper = DatabaseHelper
								.newInstance(getActivity());
						helper.openDataBase();
						helper.updateCartQuantity(data.getCheckOutId(),
								String.valueOf(temp));
						helper.close();
						data.setQuantity(String.valueOf(temp));
						pricePerQuantity = new BigDecimal(data
								.getAddPricePerUnit());
						BigDecimal price = pricePerQuantity
								.multiply(new BigDecimal(temp));
						data.setAddTotalAmt(price.setScale(2,
								BigDecimal.ROUND_HALF_UP).toString());

						salesTaxPerQuant = new BigDecimal(data.getSaleTaxUnit());
						serviceTaxPerQuant = new BigDecimal(data
								.getServiceTaxUnit());

						price = salesTaxPerQuant.multiply(new BigDecimal(temp));
						data.setTotalSalesTax(price.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue());
						price = serviceTaxPerQuant
								.multiply(new BigDecimal(temp));

						data.setTotalServiceTax(price.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue());

						calculateTotalProduct();
					} catch (Exception e) {
						e.printStackTrace();
					}

					notifyDataSetChanged();

					// }
				
				}
			});

			slideRight(holderObj.slideLayout, 0, holderObj.deleteItem);

			holderObj.productName.setText(data.getProductName());
			if (data.getProductDesc() != null
					&& !data.getProductDesc().equals("null")) {
				holderObj.productDesc.setText(data.getProductDesc());
			} else {
				holderObj.productDesc.setText("");
			}
			holderObj.quantity.setText(data.getQuantity());
			BigDecimal bigDecimal = new BigDecimal(data.getAddPricePerUnit());
			holderObj.price.setText(getString(R.string.usd) + " "
					+ bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));

			holderObj.plusView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// if (isEditMode) {
					int temp;
					try {
						temp = Integer.parseInt(holderObj.quantity.getText()
								.toString());
					} catch (Exception e) {
						// TODO: handle exception
						temp = 1;
						e.printStackTrace();
					}
					++temp;
					try {
						DatabaseHelper helper = DatabaseHelper
								.newInstance(getActivity());
						helper.openDataBase();
						helper.updateCartQuantity(data.getCheckOutId(),
								String.valueOf(temp));
						helper.close();
						data.setQuantity(String.valueOf(temp));
						pricePerQuantity = new BigDecimal(data
								.getAddPricePerUnit());
						BigDecimal price = pricePerQuantity
								.multiply(new BigDecimal(temp));
						data.setAddTotalAmt(price.setScale(2,
								BigDecimal.ROUND_HALF_UP).toString());

						salesTaxPerQuant = new BigDecimal(data.getSaleTaxUnit());
						serviceTaxPerQuant = new BigDecimal(data
								.getServiceTaxUnit());

						price = salesTaxPerQuant.multiply(new BigDecimal(temp));
						data.setTotalSalesTax(price.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue());
						price = serviceTaxPerQuant
								.multiply(new BigDecimal(temp));

						data.setTotalServiceTax(price.setScale(2,
								BigDecimal.ROUND_HALF_UP).doubleValue());

						calculateTotalProduct();
					} catch (Exception e) {
						e.printStackTrace();
					}

					notifyDataSetChanged();

					// }
				}
			});
			holderObj.minusView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					int temp;
					int quantity;
					try {
						temp = Integer.parseInt(holderObj.quantity.getText()
								.toString());

						pricePerQuantity = new BigDecimal(data
								.getAddPricePerUnit());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						temp = 1;
					}
					--temp;
					if (temp <= 0) {
						quantity = 1;

					} else {
						quantity = temp;

					}
					try {
						try {
							DatabaseHelper helper = DatabaseHelper
									.newInstance(getActivity());

							helper.openDataBase();
							helper.updateCartQuantity(data.getCheckOutId(),
									String.valueOf(quantity));
							helper.close();
							data.setQuantity(String.valueOf(quantity));
							BigDecimal price = pricePerQuantity
									.multiply(new BigDecimal(quantity));
							data.setAddTotalAmt(price.setScale(2,
									BigDecimal.ROUND_HALF_UP).toString());

							salesTaxPerQuant = new BigDecimal(data
									.getSaleTaxUnit());
							serviceTaxPerQuant = new BigDecimal(data
									.getServiceTaxUnit());
							price = salesTaxPerQuant.multiply(new BigDecimal(
									temp));
							data.setTotalSalesTax(price.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue());
							price = serviceTaxPerQuant.multiply(new BigDecimal(
									temp));
							data.setTotalServiceTax(price.setScale(2,
									BigDecimal.ROUND_HALF_UP).doubleValue());
							calculateTotalProduct();

						} catch (Exception e) {

						}
					} catch (Exception e) {

					}
					notifyDataSetChanged();

					// }
				}
			});
			holderObj.deleteIcon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isEditMode) {

						if (!isLeft) {
							slideLeft(holderObj.slideLayout,
									holderObj.deleteItem.getMeasuredWidth(),
									holderObj.deleteItem);

							data.setLeft(true);
							// notifyDataSetChanged();
							slidePosition = position;
						} else {
							notifyDataSetChanged();
						}
						isLeft = !isLeft;
					}
				}
			});
			holderObj.deleteItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isEditMode) {
						try {
							DatabaseHelper helper = DatabaseHelper
									.newInstance(getActivity());
							helper.openDataBase();
							int count = helper.deleteCartItem(data
									.getCheckOutId());
							if (count > 0) {

								cartItemsList.remove(position);

								if (productList.containsKey(data
										.getCheckOutId()))
									productList.remove(data.getCheckOutId());

								calculateTotalProduct();
								if (cartItemsList.size() <= 0
										&& cartListObj != null
										&& listviewFooter != null)
									cartListObj
											.removeFooterView(listviewFooter);
							}

							isLeft = !isLeft;
							notifyDataSetChanged();

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (isLeft && isEditMode) {
						notifyDataSetChanged();
						isLeft = !isLeft;
					}

				}
			});

			return convertView;
		}
	}

	private class ViewHolder {
		private ImageView deleteIcon, plusView, minusView;
		private TextView  price, productName, productDesc;
		private LinearLayout slideLayout, deleteItem;
		private EditText quantity;
		int hold;
	}

	private void slideLeft(View startView, int slide, View v) {
		// v.setClickable(true);
		v.setVisibility(View.VISIBLE);
		int startViewLocation[] = new int[2];
		int startX = startViewLocation[0];
		int startY = startViewLocation[1];
		startView.getLocationInWindow(startViewLocation);
		TranslateAnimation anim = new TranslateAnimation(startX,
				startX - slide, startY, startY);
		anim.setDuration(600);
		anim.setFillAfter(true);
		startView.startAnimation(anim);

	}

	private void slideRight(View startView, int slide, View v) {
		v.setVisibility(View.GONE);
		int startViewLocation[] = new int[2];
		int startX = startViewLocation[0];
		int startY = startViewLocation[1];
		startView.getLocationInWindow(startViewLocation);
		TranslateAnimation anim = new TranslateAnimation(startX,
				startX + slide, startY, startY);
		anim.setDuration(600);
		anim.setFillAfter(true);
		startView.startAnimation(anim);

	}

	// private void slideLeft(final View startView, final int slide, View v) {
	// params = (android.widget.RelativeLayout.LayoutParams) startView
	// .getLayoutParams();
	// System.out.println(" left Animitan " + params.leftMargin);
	// v.setVisibility(View.VISIBLE);
	//
	// int startViewLocation[] = new int[2];
	// int startX = startViewLocation[0];
	// int startY = startViewLocation[1];
	// startView.getLocationInWindow(startViewLocation);
	// TranslateAnimation anim = new TranslateAnimation(startX,
	// startX - slide, startY, startY);
	// anim.setDuration(600);
	// anim.setFillAfter(true);
	// anim.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// // TODO Auto-generated method stub
	// // startView.clearAnimation();
	// // RelativeLayout.LayoutParams lp = new
	// //
	// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
	// // RelativeLayout.LayoutParams.WRAP_CONTENT);
	// // // lp.addRule(RelativeLayout.BELOW,
	// // R.id.branchFinderIncludeHeader);
	// // startView.setLayoutParams(lp);
	// startView.clearAnimation();
	// params.rightMargin = slide;
	// params.leftMargin = -slide;
	// startView.setLayoutParams(params);
	//
	// }
	// });
	// startView.startAnimation(anim);
	// }

	// private void slideRight(final View startView, final int slide, View v) {
	// params = (android.widget.RelativeLayout.LayoutParams) startView
	// .getLayoutParams();
	// System.out.println(" right Animitan " + params.leftMargin);
	// v.setVisibility(View.GONE);
	// int startViewLocation[] = new int[2];
	// int startX = startViewLocation[0];
	// int startY = startViewLocation[1];
	// startView.getLocationInWindow(startViewLocation);
	// TranslateAnimation anim = new TranslateAnimation(startX,
	// startX + slide, startY, startY);
	// anim.setDuration(600);
	// anim.setFillAfter(true);
	// anim.setAnimationListener(new AnimationListener() {
	//
	// @Override
	// public void onAnimationStart(Animation animation) {
	// // TODO Auto-generated method stub
	// params.leftMargin = 0;
	// params.rightMargin = 0;
	// startView.setLayoutParams(params);
	// }
	//
	// @Override
	// public void onAnimationRepeat(Animation animation) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// // TODO Auto-generated method stub
	// // startView.clearAnimation();
	// // RelativeLayout.LayoutParams lp = new
	// //
	// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
	// // RelativeLayout.LayoutParams.WRAP_CONTENT);
	// // // lp.addRule(RelativeLayout.BELOW,
	// // R.id.branchFinderIncludeHeader);
	// // startView.setLayoutParams(lp);
	//
	// }
	// });
	// // startView.startAnimation(anim);
	// startView.startAnimation(anim);
	// }

	// http://www.mysamplecode.com/2011/10/android-sqlite-bulk-insert-update.html

	private void calculateTotalProduct() {
		double totalPrice = 0d;
		totalSalesTax = 0d;
		totalServiceTax = 0d;
		for (int i = 0; i < cartItemsList.size(); i++) {

			totalSalesTax += cartItemsList.get(i).getTotalSalesTax();
			totalServiceTax += cartItemsList.get(i).getTotalServiceTax();
			try {
				double productPrice = Double.parseDouble(cartItemsList.get(i)
						.getAddTotalAmt());
				totalPrice += productPrice;
			} catch (Exception e) {
				totalPrice = 0.0d;
			}

		}
		BigDecimal bigDecimal = new BigDecimal(String.valueOf(totalPrice));
		

		totalAmount.setText(getString(R.string.usd) + " "
				+ bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
		totalSalesTax=totalPrice*7/100;
		BigDecimal totalSalesTax_decimal= new BigDecimal(totalSalesTax);
		((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).setSales_tax(totalSalesTax_decimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

		BigDecimal servicetax = new BigDecimal(String.valueOf(totalSalesTax));
		total_tax.setText(getString(R.string.usd) + " "
				+ servicetax.setScale(2, BigDecimal.ROUND_HALF_UP));
	double tot= totalPrice+totalSalesTax;
	BigDecimal tot_decimal = new BigDecimal(String.valueOf(tot));

		total_with_tax.setText(getString(R.string.usd) + " "
				+ tot_decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
		((Global)CheckOutProductListFragment.this.getActivity().getApplicationContext()).setTotal_amt(tot_decimal.toString());

	}

//	private class CalculateDiscount extends AsyncTask<String[], Void, String> {
//		
//		private ImageView mImg;
//		private ProgressBar mProgress;
//		private TextView mTextview;
//
//		public CalculateDiscount(ImageView imgView, ProgressBar bar,
//				TextView mText) {
//			// TODO Auto-generated constructor stub
//			mImg = imgView;
//			mProgress = bar;
//			mTextview = mText;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			mImg.setVisibility(View.GONE);
//			mProgress.setVisibility(View.VISIBLE);
//		}
//
//		@Override
//		protected String doInBackground(String[]... params) {
//			// TODO Auto-generated method stub
//
//			return JsonParser.Webserice_Call_URL(ConstantsUrl.APPLYCOUPONCODE,
//					params[0], params[1]);
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			// AppUtiles.parseResponse(result, getActivity(), mAppPrefs,
//			// expressDialog, 2, couponCodeStr, status);
//			mImg.setVisibility(View.VISIBLE);
//			mProgress.setVisibility(View.GONE);
//
//			parseDiscountResponse(mTextview, result);
//		}
//	}

	private void parseDiscountResponse(TextView view, String response) {
		if (response != null) {
			try {
				JSONObject mainJson = new JSONObject(response);
				if (mainJson.has("state")) {
					String state = mainJson.getString("state");
					String msg = mainJson.getString("msg");
					if (view != null) {
						view.setText(msg);
						view.setVisibility(View.VISIBLE);
					}
					if (state != null && state.equals("1")) {
						if (mainJson.has("data")) {
							JSONObject dataJson = mainJson
									.getJSONObject("data");
							discountAmt = JsonParser.getkeyValue_Str(dataJson,
									"discount");
							discountType = JsonParser.getkeyValue_Str(dataJson,
									"discount_type");
							discoWithDelivery = JsonParser.getkeyValue_Str(
									dataJson, "discount_with_delivery");

						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	
	
	
	 private void totalcart(){
		 productJsonArray = new JSONArray();
		for (int i = 0; i < cartItemsList.size(); i++) {
			CheckOutMetadata data = cartItemsList.get(i);
			if (data != null) {
				JSONObject productJson = new JSONObject();
				try {
					productJson.put("product_id",
							data.getProductId());
					productJson.put("product_instruction",
							data.getSpecialInstruction());
					
					// productJson.put("product_rate",
					// data.getPricePerUnit());
					// productJson.put("product_price",
					// data.getAddTotalAmt());
				

					productJson.put("order_product_addons",
							data.getAttributesSKU());
					HashMap<Integer, ArrayList<String>> bundleList = data
							.getBundleProductHashList();
					JSONObject groupId = new JSONObject();
					if (bundleList != null) {
						for (Entry<Integer, ArrayList<String>> entry : bundleList
								.entrySet()) {
							ArrayList<String> valuesList = entry
									.getValue();
							String key = entry.getKey().toString();
							if (valuesList != null) {
								JSONArray jsonBundleArr = new JSONArray();
								for (int k = 0; k < valuesList
										.size(); k++) {
									jsonBundleArr.put(valuesList
											.get(k));
								}
								groupId.put(key, jsonBundleArr);
							}

						}
						productJson.put("order_product_bundle",
								groupId);
					} else {
						productJson.put("order_product_bundle", "");
					}
					productJson.put("product_qty",
							data.getQuantity());
					// productJson.put("order_product_bundl11",
					// data.getBundleProductSKU());
					productJsonArray.put(productJson);

				}
				 catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
 }
	private void update_quantity(){
		
	}
	
	public class Save_Fav extends AsyncTask<String, Void, String>{
		ACProgressCustom progressDialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			progressDialog = new Dialog(getActivity());
//			progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			progressDialog.setCancelable(false);
//			progressDialog.setCanceledOnTouchOutside(false);
//			progressDialog.setContentView(R.layout.custom_progress_layout);
//			progressDialog.getWindow().setBackgroundDrawable(
//					new ColorDrawable(android.graphics.Color.TRANSPARENT));
//			progressDialog.show();
			
			progressDialog = new ACProgressCustom.Builder(getActivity())
		    .useImages(R.drawable.loading1, R.drawable.loading2,R.drawable.loading3, R.drawable.loading4,R.drawable.loading5,R.drawable.loading6).build();
			progressDialog.setCanceledOnTouchOutside(true);

			progressDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.SAVE_FAV_ORDER, new String[]{"order_id","fav_title"}, new String[]{((Global)CheckOutProductListFragment.this.getActivity().getApplication()).getFavroite_order_id(),name.getText().toString()});
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialog.dismiss();
			try {
				JSONObject obj= new JSONObject(result);
				String state=obj.getString("state");
				String msg= obj.getString("msg");
				//AppUtiles.getInstance().showToast(getActivity(), msg);
				Snackbar.make(getView(),msg, Snackbar.LENGTH_LONG).show();
				fav_dialog.dismiss();
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
