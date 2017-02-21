package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.OrderHistoryActivity;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.CheckOutMetadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;


public class ReorderFragment extends BaseFragment {
	private LayoutInflater mInflater;
	private TextView totalAmount, checkoutView, total_tax, total_with_tax,edit_item;
	private ListView cartListObj;
	CartAdapter cartAdapterObj;
	private ArrayList<CheckOutMetadata> cartItemsList = new ArrayList<CheckOutMetadata>();
	private HashMap<String, ArrayList<CheckOutMetadata>> productList = new HashMap<String, ArrayList<CheckOutMetadata>>();
	private Double totalSalesTax = 0d, totalServiceTax = 0d;
	int isClearCart=1;
	String state,msg = null;
	public static ReorderFragment newInstance() {

		return new ReorderFragment();
	}
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(getActivity());
		
		Intent in = getActivity().getIntent();

		if (Intent.ACTION_VIEW.equals(in.getAction())) {
			
		  Uri ur = in.getData();
		String data = ur.getQueryParameter("json");
		 JSONObject obj;
			
				try {
					obj = new JSONObject(data);
					 state= obj.getString("state");
					    msg= obj.getString("msg");
					    if(state.equals("1")){
					    	isClearCart=2;
					    }
					  Log.e("user_iddd", data);
					  Log.e("user_iddd", msg);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				   
				if (state.equals("1")) {
					final Dialog dialog = new Dialog(getActivity(),
							R.style.CustomDialog1);
					
					dialog.setContentView(R.layout.dialog_order_successfully);
					RelativeLayout rel=(RelativeLayout)dialog.findViewById(R.id.rel);
					 File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
						
						Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
						Drawable dr = new BitmapDrawable(bmp);
						
						rel.setBackgroundDrawable(dr);
						dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT);
						Button cancelBtn = (Button) dialog
								.findViewById(R.id.cancel);
						TextView message = (TextView) dialog
								.findViewById(R.id.message);
						message.setText(msg);

						cancelBtn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								try {
									dialog.dismiss();
									// if (isClearCart == 2) {
									// DatabaseHelper databaseHelper =
									// DatabaseHelper
									// .newInstance(getActivity());
									// databaseHelper.openDataBase();
									// databaseHelper.clearCart();
									// databaseHelper.close();
									// }
									
										DatabaseHelper databaseHelper = DatabaseHelper
												.newInstance(getActivity());
										databaseHelper.openDataBase();
										databaseHelper.clearCart();
										databaseHelper.close();
										Intent intent = new Intent(
												getActivity(),
												OrderHistoryActivity.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
												| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
										getActivity().startActivity(intent);
										getActivity().finish();
									
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						});

						dialog.show();

				}else{
					final Dialog dialog = new Dialog(getActivity(),
				
						R.style.CustomDialog1);
					
				dialog.setContentView(R.layout.dialog_alert_message);
				dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
				Button cancelBtn = (Button) dialog
						.findViewById(R.id.cancel);
				TextView message = (TextView) dialog
						.findViewById(R.id.message);
				message.setText(msg);

				cancelBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					
							dialog.dismiss();
							
							

					}
				});

				dialog.show();
		}
		}
	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		totalAmount = (TextView) view.findViewById(R.id.totalAmount);
		total_tax=(TextView)view.findViewById(R.id.vat_amount);
		total_with_tax=(TextView)view.findViewById(R.id.total_amount);
		cartListObj = (ListView) view.findViewById(R.id.list);
		cartAdapterObj = new CartAdapter();
		cartListObj.setAdapter(cartAdapterObj);	
		calculateTotalProduct();
	}
	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_reorder;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
	//	Global.getInstance().trackScreenView("Reorder Page");

		super.onResume();
		 getAllCartItems();
		 calculateTotalProduct();

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
			if (convertView == null) {
				holderObj = new ViewHolder();
				convertView = mInflater.inflate(R.layout.reorder_cart, parent,
						false);
				
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
				holderObj.quantity = (TextView) convertView
						.findViewById(R.id.quantity);
				
				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();
			}
			
			

			final CheckOutMetadata data = getItem(position);


			holderObj.productName.setText(data.getProductName());
			if (data.getProductDesc() != null
					&& !data.getProductDesc().equals("null")) {
				holderObj.productDesc.setText(data.getProductDesc());
			} else {
				holderObj.productDesc.setText("");
			}
			holderObj.quantity.setText(data.getQuantity());
			BigDecimal bigDecimal = new BigDecimal(data.getAddTotalAmt());
			holderObj.price.setText(getString(R.string.usd) + " "
					+ bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));

			

			return convertView;
		}
		
	}
	private class ViewHolder {
		private ImageView deleteIcon, plusView, minusView;
		private TextView quantity, price, productName, productDesc;
	}
	
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
		BigDecimal servicetax = new BigDecimal(String.valueOf(totalSalesTax));
		total_tax.setText(getString(R.string.usd) + " "
				+ servicetax.setScale(2, BigDecimal.ROUND_HALF_UP));
	double tot= totalPrice+totalSalesTax;
	BigDecimal tot_decimal = new BigDecimal(String.valueOf(tot));

		total_with_tax.setText(getString(R.string.usd) + " "
				+ tot_decimal.setScale(2, BigDecimal.ROUND_HALF_UP));
		((Global)ReorderFragment.this.getActivity().getApplicationContext()).setTotal_amt(tot_decimal.toString());

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
						data.setTotalServiceTax(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.CHECKOut_SERVICETAXAMT)));
						data.setSaleTaxUnit(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT)));
						data.setServiceTaxUnit(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT)));

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
}
