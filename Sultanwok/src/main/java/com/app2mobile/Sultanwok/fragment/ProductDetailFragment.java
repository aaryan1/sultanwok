package com.app2mobile.Sultanwok.fragment;

import java.math.BigDecimal;

import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.RestaurantProductMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;
import com.app2mobile.utiles.JsonParser;

import com.squareup.picasso.Picasso;

public class ProductDetailFragment extends Fragment {

	private ImageView favImageView, placeHolderImageView;
	ImageView  product_img;
	Button plusImageView,minusImageView;
	private EditText instuctionEditText;
	private TextView descTextView, amountTextView, quantityView, headerTitle,
			totalamount,add_txt;
	private RelativeLayout addTocart;
	private RestaurantProductMetadata productDetailData;
	private String cust_id;
	private int lowerLimit = 1, upperLimit;
	BigDecimal priceBigDecimal, pricePerQuantity;
	private ProgressBar progressbar;
	Drawable bitmapOrg,bitmaporg_hov;
	private static ProductDetailFragment instance = null;

	public static ProductDetailFragment newInstance() {
		if (instance == null) {

			instance = new ProductDetailFragment();

		}
		return instance;
	}

	// public static ProductDetailFragment newInstance() {
	//
	// // Bundle args = new Bundle();
	// // args.putSerializable("productDetail", productDetail);
	// ProductDetailFragment fragment = new ProductDetailFragment();
	// // fragment.setArguments(args);
	// return fragment;
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_product_detail, container,
				false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle != null) {
			productDetailData = (RestaurantProductMetadata) bundle
					.get("productDetail");
		}
		
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//		Drawable drawable=this.getResources().getDrawable(R.drawable.ic_fav_active);
//		drawable.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.DST_IN);

		favImageView = (ImageView) view.findViewById(R.id.favourite);
		plusImageView = (Button) view.findViewById(R.id.plus);
		minusImageView = (Button) view.findViewById(R.id.minus);
		//favImageView.setBackgroundDrawable(drawable);
		// placeHolderImageView = (ImageView)
		// view.findViewById(R.id.placeHolder);
		instuctionEditText = (EditText) view
				.findViewById(R.id.specialInstruction);
		descTextView = (TextView) view.findViewById(R.id.description);
		addTocart = (RelativeLayout) view.findViewById(R.id.AddToCart);
		amountTextView = (TextView) view.findViewById(R.id.productAmount);
		amountTextView.setTextColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		quantityView = (TextView) view.findViewById(R.id.quantit);
		headerTitle = (TextView) view.findViewById(R.id.headerTitle);
		totalamount = (TextView) view.findViewById(R.id.total_amount);
		product_img = (ImageView) view.findViewById(R.id.imageView1);
			add_txt=(TextView)view.findViewById(R.id.checkOut);
		Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Bold.ttf");
		Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(),
				"Quattrocento-Regular.ttf");
		Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(),
				"OpenSans-Regular.ttf");
		Typeface tf3 = Typeface.createFromAsset(getActivity().getAssets(),
				"Sintony-Bold.ttf");
		headerTitle.setTypeface(tf);
		descTextView.setTypeface(tf1);
		amountTextView.setTypeface(tf2);
		add_txt.setTypeface(tf3);
		totalamount.setTypeface(tf3);
		bitmapOrg = this.getResources().getDrawable(R.drawable.fav);
		bitmapOrg.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.MULTIPLY);
		favImageView.setBackground(bitmapOrg);
		addTocart.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		if (productDetailData != null) {

			// new
			// PlaceHolderImageAsyn().execute(productDetailData.getpImage());
			// placeHolderImageView.measure(MeasureSpec.UNSPECIFIED,
			// MeasureSpec.UNSPECIFIED);
			//
			// Picasso.with(getActivity())
			// .load(productDetailData.getpImage())
			// .resize(placeHolderImageView.getMeasuredWidth(),
			// placeHolderImageView.getMeasuredHeight())
			// .placeholder(R.drawable.place_holder)
			// .into(placeHolderImageView);
			DatabaseHelper databaseHelper = DatabaseHelper
					.newInstance(getActivity());
			databaseHelper.openDataBase();
			Cursor cartIds = databaseHelper.getAllFavourites();
			while(cartIds.moveToNext()){
				String fav_selceted_pId=cartIds.getString(cartIds.getColumnIndex(DatabaseHelper.FAV_ProductName));
			if(productDetailData.getpName().equals(fav_selceted_pId)){
				//favImageView.setImageResource(R.drawable.ic_fav_active);
				bitmaporg_hov = this.getResources().getDrawable(R.drawable.fav_active);
				bitmaporg_hov.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.SRC_IN);
				favImageView.setBackground(bitmaporg_hov);
			}
			}
	
			databaseHelper.close();
			String img = productDetailData.getpImage();
			if (img.equals("") || img == null) {
				Picasso.with(getActivity()).load(R.drawable.place_holder)
						.error(R.drawable.place_holder)
						.into(product_img);

			} else {
				Log.e("imageee", img);
//				Glide.with(getActivity()).load(img)
//						.error(R.drawable.place_holder)
//						.placeholder(R.drawable.loader)
//						.
//						.into(product_img);
				Picasso.with(getActivity()).load(img)
				.error(R.drawable.place_holder)
				.placeholder(R.drawable.place_holder)
				.into(product_img);
			}
			if(productDetailData.getpDesc().equals("") || productDetailData.getpDesc().length()==0){
				descTextView.setVisibility(View.GONE);
			}
			descTextView.setText(productDetailData.getpDesc());
			headerTitle.setText(productDetailData.getpName());

			priceBigDecimal = new BigDecimal(productDetailData.getpPrice());
			pricePerQuantity = priceBigDecimal.setScale(2,
					BigDecimal.ROUND_HALF_UP);
			totalamount.setText("$ "
					+ priceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
			amountTextView.setText("$ "
					+ priceBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));

		}

		addTocart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * insert product to cart table in database
				 * 
				 */

				try {
					String instruction = instuctionEditText.getText()
							.toString();
					DatabaseHelper databaseHelper = DatabaseHelper
							.newInstance(getActivity());
					databaseHelper.openDataBase();
					int quant = databaseHelper.getProductQuantityInCart(
							String.valueOf(productDetailData.getpId()),
							instruction);

					int updateQuantity = Integer.parseInt(quantityView
							.getText().toString());
					ContentValues values = new ContentValues();
					if (quant <= 0) {
						values.put(DatabaseHelper.CheckOut_ProductId,
								productDetailData.getpId());
						values.put(DatabaseHelper.CheckOut_ProductName,
								productDetailData.getpName());
						values.put(DatabaseHelper.CheckOut_ProductDescription,
								"");
						values.put(DatabaseHelper.CheckOut_Instruction,
								instruction);
						values.put(DatabaseHelper.CheckOut_ProductQuantity,
								updateQuantity);
						values.put(DatabaseHelper.CheckOut_PricePerUnit,
								productDetailData.getpPrice());
						values.put(DatabaseHelper.CHECKOut_ProductSKU,
								productDetailData.getpSKU());
						values.put(
								DatabaseHelper.CheckOut_TotalAmount,
								pricePerQuantity.multiply(
										new BigDecimal(updateQuantity))
										.toString());
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMT,
								productDetailData.getSalesTaxAmt()
										* updateQuantity);
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMT,
								productDetailData.getServiceTaxAmt()
										* updateQuantity);
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
								productDetailData.getSalesTaxAmt());
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
								productDetailData.getServiceTaxAmt());
						// values.put(DatabaseHelper.CheckOut_Currencytype,
						// productDetailData.getpCurrency());
						//
						long insertedId = databaseHelper.insetCartItem(values);
						if (insertedId != -1) {
//							AppUtiles.getInstance().showToast(getActivity(),
//									"Product successfully added in cart.");
							
							Snackbar.make(getView(),"Product successfully added in cart.", Snackbar.LENGTH_LONG).show();

						} else {
							AppUtiles.getInstance().showToast(getView(),
									"Product not added in cart.");
						//	Snackbar.make(getView(),"Product not added in cart.", Snackbar.LENGTH_LONG).show();
						}
					} else {
						//
						String cartId = databaseHelper.getCartId(
								String.valueOf(productDetailData.getpId()),
								instruction);
						values.put(DatabaseHelper.CheckOut_Instruction,
								instruction);
						values.put(DatabaseHelper.CheckOut_ProductQuantity,
								(updateQuantity + quant));
						values.put(
								DatabaseHelper.CheckOut_TotalAmount,
								pricePerQuantity
										.multiply(
												new BigDecimal(
														(updateQuantity + quant)))
										.toString());
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMT,
								productDetailData.getSalesTaxAmt()
										* (updateQuantity + quant));
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMT,
								productDetailData.getServiceTaxAmt()
										* (updateQuantity + quant));
						int status = databaseHelper.updateCartItem(values,
								cartId);
						if (status > 0) {
							AppUtiles.getInstance().showToast(getView(),
									"Cart successfully updated.");
							Snackbar.make(getView(),"Cart successfully updated.", Snackbar.LENGTH_LONG).show();

						} else {
//							AppUtiles.getInstance().showToast(getActivity(),
//									"Cart updation failed.");
							Snackbar.make(getView(),"Cart updation failed.", Snackbar.LENGTH_LONG).show();

						}
						//
					}
					//
					databaseHelper.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				getActivity().finish();
			}

		});

		favImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**
				 * insert product details to favourite table
				 */
				System.out.println("favorite");
//				new Favourite().execute(
//						new String[] { "store_id", "cust_id", "product_id" },
//						new String[] {
//								ConstantsUrl.STOREID,
//								((Global) ProductDetailFragment.this
//										.getActivity().getApplicationContext())
//										.getCust_id(),
//								String.valueOf(productDetailData.getpId()) });
				
				 try {
				
	
				 if (productDetailData != null) {
				 String instruction = instuctionEditText.getText()
				 .toString();
				
				 DatabaseHelper databaseHelper = DatabaseHelper
				 .newInstance(getActivity());
				 databaseHelper.openDataBase();
				
				 int copy = databaseHelper.getFavCopy(
				 String.valueOf(productDetailData.getpId()),
				 null);
				
				 if (copy <= 0) {
				 ContentValues contentValues = new ContentValues();
				 contentValues.put(DatabaseHelper.FAV_ProductId,
				 productDetailData.getpId());
				 contentValues.put(DatabaseHelper.FAV_ProductName,
				 productDetailData.getpName());
				 contentValues.put(
				 DatabaseHelper.FAV_ProductDescription,
				 instruction);
				 contentValues.put(DatabaseHelper.FAV_Instruction,
				 instuctionEditText.getText().toString());
				 contentValues.put(
				 DatabaseHelper.FAV_ProductQuantity,
				 String.valueOf(1));
				 contentValues.put(DatabaseHelper.FAV_PricePerUnit,
				 productDetailData.getpPrice());
				 contentValues.put(DatabaseHelper.FAV_ProductSKU,
				 productDetailData.getpSKU());
				 contentValues.put(DatabaseHelper.FAV_TotalAmount,
				 pricePerQuantity
				 .multiply(new BigDecimal(1))
				 .toString());
				 // contentValues.put(DatabaseHelper.FAV_Currency,
				 // productDetailData.getpCurrency());
				
				 contentValues.put(
				 DatabaseHelper.CHECKOut_SALESTAXAMT,
				 productDetailData.getSalesTaxAmt());
				 contentValues.put(
				 DatabaseHelper.CHECKOut_SERVICETAXAMT,
				 productDetailData.getServiceTaxAmt());
				 contentValues.put(
				 DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
				 productDetailData.getSalesTaxAmt());
				 contentValues.put(
				 DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
				 productDetailData.getServiceTaxAmt());
				 long id = databaseHelper.insertFav(contentValues);
				 if (id != -1) {
					// favImageView.setImageResource(R.drawable.fav_active);
					    bitmaporg_hov =getResources().getDrawable(R.drawable.fav_active);
						bitmaporg_hov.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.SRC_IN);
						favImageView.setBackground(bitmaporg_hov);
				 AppUtiles.getInstance()
				 .showToast(getView(),
				 "Product successfully added to favorite");
				
				 } else {
				 AppUtiles.getInstance().showToast(getView(),
				 "Problem occured!");
				 
				 }
				 } else {
					
					int result= databaseHelper.deleteFav1(productDetailData.getpId());
					if(result!=0){
					 AppUtiles.getInstance().showToast(getView(),
							 "Product successfully remove from favorite");
					 bitmapOrg =getResources().getDrawable(R.drawable.fav);
						bitmapOrg.setColorFilter(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE), android.graphics.PorterDuff.Mode.MULTIPLY);
						favImageView.setBackground(bitmapOrg);
					
					}else{
						AppUtiles.getInstance().showToast(getView(),
								 "Problem occured!");
					}
					
				 }
				 databaseHelper.close();
				 } else {
				 AppUtiles.getInstance().showToast(getView(),
				 "Problem occured!");
				 }
				 } catch (Exception e) {
				 e.printStackTrace();
				 }
			}
		});
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
				// if (temp > upperLimit) {
				// quantityView.setText("1");
				// amountTextView.setText("$ "
				// + String.valueOf(pricePerQuantity
				// .multiply(new BigDecimal(1))));
				// } else {
				quantityView.setText(String.valueOf(temp));
				totalamount.setText("$ "
						+ String.valueOf(pricePerQuantity
								.multiply(new BigDecimal(temp))));
				// }

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
				if (temp <= lowerLimit - 1) {
					quantityView.setText(String.valueOf(1));
					totalamount.setText("$ "
							+ String.valueOf(pricePerQuantity
									.multiply(new BigDecimal(1))));
				} else {
					quantityView.setText(String.valueOf(temp));
					totalamount.setText("$ "
							+ String.valueOf(pricePerQuantity
									.multiply(new BigDecimal(temp))));
				}
			}
		});
	}

	class Favourite extends AsyncTask<String[], Void, String> {
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
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub
			return JsonParser.Webserice_Call_URL(ConstantsUrl.ADDFAVORITE,
					params[0], params[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println("response " + result);
			parseCheckoutResponse(result);
			progressDialog.dismiss();
		}
	}

	private void parseCheckoutResponse(String result) {
		if (result != null) {
			try {
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser
							.getkeyValue_Str(mainJson, "state");
					String msg = JsonParser.getkeyValue_Str(mainJson, "msg");
					AppUtiles.getInstance().showToast(getView(), msg);

				} else {
					AppUtiles.getInstance().showToast(getView(),
							"Somthing Is Wrong. Please Try Again.");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {

			AppUtiles.getInstance().showToast(getView(),
					"Somthing is Wrong. Please Try Again.");
		}
		
	}
}
