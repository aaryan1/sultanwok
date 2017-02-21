package com.app2mobile.Sultanwok.fragment;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.BaseActivity;
import com.app2mobile.Sultanwok.CheckOutProductListActivity;
import com.app2mobile.Sultanwok.Global;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.OrderHistoryMetadata;
import com.app2mobile.metadata.OrderItemsMetadata;
import com.app2mobile.metadata.RestaurantAddOnMetadata;
import com.app2mobile.metadata.RestaurantDeliveryTimeMetadata;
import com.app2mobile.metadata.RestaurantDetailMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;
import com.app2mobile.utiles.JsonParser;
import com.google.gson.Gson;

public class OrderHistoryFragment extends BaseFragment {
	private ListView orderDetailListView;
	private ArrayList<OrderHistoryMetadata> orderList = new ArrayList<OrderHistoryMetadata>();
	private OrderHistoryAdapter orderAdapter;
	public static int LOGIN = 1001;
	GregorianCalendar finalTime, mCalendarOpeningTime, mCalendarClosingTime;
	private Dialog expressDialog;
	private String discountAmt, discountType, discoWithDelivery, businessHrStr;
	private Dialog dialog;
	private RelativeLayout rel;
	private RestaurantDetailMetadata restaurantDetailData;
	private HashMap<String, RestaurantDeliveryTimeMetadata> restaurantDeliveryTimeList;
	private static OrderHistoryFragment instance = null;

	public static OrderHistoryFragment newInstance() {
		if (instance == null)
			instance = new OrderHistoryFragment();
		return instance;
	}

	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.fragment_order_history;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		orderDetailListView = (ListView) view.findViewById(R.id.orderHistory);
		rel=(RelativeLayout)view.findViewById(R.id.rel);
		mTitleTxt.setText(getString(R.string.order_history));
		File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
		
		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
		Drawable dr = new BitmapDrawable(bmp);
		
		rel.setBackgroundDrawable(dr);
		orderAdapter = new OrderHistoryAdapter();
		orderDetailListView.setAdapter(orderAdapter);
		Gson gson = new Gson();
		String restaurantDetail = ((BaseActivity) getActivity()).mRestaurantDetailPrefs
				.getString(ConstantsUrl.RESTAURANT_DETAILS, "");
		restaurantDetailData = gson.fromJson(restaurantDetail,
				RestaurantDetailMetadata.class);
		if (restaurantDetail != null) {
			restaurantDeliveryTimeList = restaurantDetailData
					.getmDeliveryList();

		}
		finalTime = new GregorianCalendar();
		
		new GetAllOrderDetails().execute();


	}

	

	private class GetAllOrderDetails extends AsyncTask<String[], Void, String> {
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
			String[] param1 = { "cust_id", "store_id" };
			String[] param2 = {
					mAppPrefs.getString(ConstantsUrl.CUSTOMER_ID, ""),
					ConstantsUrl.STOREID };

			return JsonParser.Webserice_Call_URL(ConstantsUrl.ORDERHISTOREY,
					param1, param2);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			parseResponse(result);
			orderAdapter.notifyDataSetChanged();
			progressDialog.dismiss();
		}
	}

	private void parseResponse(String result) {
		orderList.clear();
		if (result != null) {
			try {
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser
							.getkeyValue_Str(mainJson, "state");
					if (state != null && state.equals("1")) {
						JSONObject dataJson = mainJson.getJSONObject("data");
						if (dataJson.has("orders")) {
							JSONArray ordersArr = dataJson
									.getJSONArray("orders");
							{

								for (int i = 0; i < ordersArr.length(); i++) {
									OrderHistoryMetadata orderData = new OrderHistoryMetadata();
									JSONObject orderJson = ordersArr
											.getJSONObject(i);
									orderData.setOrderId(JsonParser
											.getkeyValue_Str(orderJson,
													"order_id"));
									orderData.setCreateTs(JsonParser
											.getkeyValue_Str(orderJson,
													"create_ts"));
									orderData.setOrderStatusId(JsonParser
											.getkeyValue_Str(orderJson,
													"order_status_id"));
									orderData.setOrderStatus(JsonParser
											.getkeyValue_Str(orderJson,
													"order_status_name"));
									orderData.setOrderTotal(JsonParser
											.getkeyValue_Str(orderJson,
													"order_total"));

									try {
										orderData.setOrderSalesTax(orderJson
												.getDouble("order_sales_tax"));
									} catch (Exception e) {
										e.printStackTrace();
									}
									try {
										orderData
												.setOrderServiceTax(orderJson
														.getDouble("order_service_tax"));
									} catch (Exception e) {
										e.printStackTrace();
									}
									orderData.setOrderGrossAmt(JsonParser
											.getkeyValue_Str(orderJson,
													"order_gross_amt"));
									orderData.setOrderDeliveryTime(JsonParser
											.getkeyValue_Str(orderJson,
													"order_delivery_time"));
									orderData
											.setOrderInstruction(JsonParser
													.getkeyValue_Str(orderJson,
															"order_delivery_instructions"));
									orderData.setOrderAddress("order_address");
									orderData.setAddressStreet(JsonParser
											.getkeyValue_Str(orderJson,
													"address_street"));
									// orderData.setAddressStreet(JsonParser
									// .getkeyValue_Str(orderJson, ""));
									orderData.setAddressPincode(JsonParser
											.getkeyValue_Str(orderJson,
													"pincode"));
									orderData.setAddressCity(JsonParser
											.getkeyValue_Str(orderJson,
													"city_id"));
									orderData.setOrderStateId(JsonParser
											.getkeyValue_Str(orderJson,
													"state_id"));
									orderData.setOrderState(getState(orderData
											.getOrderStateId()));
									if (orderJson.has("products")) {
										JSONArray productsArr = orderJson
												.getJSONArray("products");
										ArrayList<OrderItemsMetadata> productsList = new ArrayList<OrderItemsMetadata>();

										for (int j = 0; j < productsArr
												.length(); j++) {
											OrderItemsMetadata itemsData = new OrderItemsMetadata();
											JSONObject productJson = productsArr
													.getJSONObject(j);
											itemsData.setOrderId(JsonParser
													.getkeyValue_Str(
															productJson,
															"order_id"));
											itemsData
													.setpId(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"store_product_id"));
											itemsData
													.setpQuant(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"order_product_qty"));
											itemsData
													.setpUnitPrice(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"order_product_rate"));
											itemsData
													.setpTotalPrice(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"order_product_price"));
											itemsData
													.setpDiscount(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"order_product_discount"));
											itemsData
													.setpInstruction(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"order_product_instruction"));
											itemsData
													.setpName(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"store_product_name"));
											itemsData
													.setpDesc(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"store_product_desc"));
											itemsData
													.setpSKU(JsonParser
															.getkeyValue_Str(
																	productJson,
																	"store_product_sku"));
											itemsData
													.setSalesTaxAmt(JsonParser
															.getkeyValue_Double(
																	productJson,
																	"store_product_service_tax_amount"));
											itemsData
													.setServiceTaxAmt(JsonParser
															.getkeyValue_Double(
																	productJson,
																	"store_product_sales_tax_amount"));
											JSONArray addonsArr1 = productJson
													.getJSONArray("store_product_addon");
											String attributesSkus = "";
											ArrayList<String> ids = new ArrayList<String>();
											JSONArray addonsArr = addonsArr1
													.getJSONArray(0);
											ArrayList<RestaurantAddOnMetadata> addOnsList = new ArrayList<RestaurantAddOnMetadata>();

											for (int k = 0; k < addonsArr
													.length(); k++) {
												RestaurantAddOnMetadata addonData = new RestaurantAddOnMetadata();
												JSONObject addOnObject = addonsArr
														.getJSONObject(k);
												addonData.setId(JsonParser
														.getkeyValue_Str(
																addOnObject,
																"id"));
												addonData.setTaxId(JsonParser
														.getkeyValue_Str(
																addOnObject,
																"tax_id"));
												addonData
														.setpName(JsonParser
																.getkeyValue_Str(
																		addOnObject,
																		"product_name"));
												addonData
														.setpPrice(JsonParser
																.getkeyValue_Str(
																		addOnObject,
																		"product_price"));
												addonData
														.setServiceTaxAmt(JsonParser
																.getkeyValue_Str(
																		addOnObject,
																		"service_tax_amount"));
												addonData
														.setSalesTaxAmt(JsonParser
																.getkeyValue_Str(
																		addOnObject,
																		"sales_tax_amount"));
												addonData.setInStock(itemsData
														.getpQuant());
												String addonId = addonData
														.getId();
												if (addonId != null
														&& !addonId.equals("")
														&& !addonId
																.equals("null")) {
													attributesSkus += addonId
															+ ",";
													ids.add(addonId);
												}
												addOnsList.add(addonData);
											}
											if (attributesSkus != null
													&& attributesSkus.length() > 0) {
												attributesSkus = attributesSkus
														.trim()
														.substring(
																0,
																attributesSkus
																		.length() - 1);
											}
											itemsData.setAttributesIdsList(ids);
											itemsData
													.setAttributesSKU(attributesSkus);
											itemsData.setAddonList(addOnsList);
											productsList.add(itemsData);
										}
										orderData
												.setOrderItemsList(productsList);
									}
									orderList.add(orderData);
								}
							}
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String getState(String id) {

		String state = "";
		String query = "Select state_name from tbl_state where state_code='"
				+ id + "'";

		DatabaseHelper helperObj = DatabaseHelper.newInstance(getActivity());
		helperObj.openDataBase();
		SQLiteDatabase databaseObj = helperObj.getReadableDatabase();
		Cursor cursorObj = databaseObj.rawQuery(query, null);
		if (cursorObj != null) {
			if (cursorObj.moveToFirst()) {
				do {
					state = cursorObj.getString(0);
				} while (cursorObj.moveToNext());
			}
			cursorObj.close();
		}
		helperObj.close();
		return state;
	}

	private class OrderHistoryAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		OrderHistoryAdapter() {
			mInflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderList.size();
		}

		@Override
		public OrderHistoryMetadata getItem(int arg0) {
			// TODO Auto-generated method stub
			return orderList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holderObj;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row_order_history,
						parent, false);
				holderObj = new ViewHolder();
				holderObj.orderIdTxt = (TextView) convertView
						.findViewById(R.id.orderId);
				holderObj.statusTxt = (TextView) convertView
						.findViewById(R.id.status);
				holderObj.descTxt = (TextView) convertView
						.findViewById(R.id.desc);
				holderObj.priceTxt = (TextView) convertView
						.findViewById(R.id.price);
				holderObj.new_button = (TextView) convertView
						.findViewById(R.id.button);

				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();
			}
			final OrderHistoryMetadata data = getItem(position);
			holderObj.orderIdTxt.setText("#" + data.getOrderId());
//			holderObj.new_button.setBackgroundColor(Color
//					.parseColor(ConstantsUrl.STORE_COLOR_CODE));
			if (data.getOrderStatus().equalsIgnoreCase("new")) {
				holderObj.new_button.setVisibility(View.VISIBLE);
			} else {
				holderObj.new_button.setVisibility(View.INVISIBLE);
			}
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				SimpleDateFormat dateFormat1 = new SimpleDateFormat(
						"dd-MMM-yyyy");
				Date date = dateFormat.parse(data.getCreateTs());
				holderObj.statusTxt.setText("(" + dateFormat1.format(date)
						+ ")");

			} catch (Exception e) {
				e.printStackTrace();
			}
			BigDecimal subTotal = new BigDecimal(data.getOrderGrossAmt());
			holderObj.priceTxt.setText("Price: $ "
					+ subTotal.setScale(2, BigDecimal.ROUND_UP));
			String product = "";
			ArrayList<OrderItemsMetadata> items = data.getOrderItemsList();

			for (int j = 0; j < items.size(); j++) {
				if (j != items.size() - 1)
					product += items.get(j).getpName() + ", ";
				else
					product += items.get(j).getpName();
			}
			holderObj.descTxt.setText(product);
			// if (position % 2 == 0) {
			// convertView.setBackgroundColor(getResources().getColor(
			// R.color.white));
			//
			// } else {
			// convertView.setBackgroundColor(getResources().getColor(
			// R.color.grey));
			// }
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					dialog = new Dialog(getActivity(), R.style.CustomDialog1);
//					dialog.setContentView(R.layout.dialog_order_details);
//					dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
//							LayoutParams.MATCH_PARENT);
//
//					TextView statusTx = (TextView) dialog
//							.findViewById(R.id.status);
//					TextView totalTxt = (TextView) dialog
//							.findViewById(R.id.total);
//					final TextView orderNumbertxt = (TextView) dialog
//							.findViewById(R.id.orderNumber);
//					ListView listViewObj = (ListView) dialog
//							.findViewById(R.id.productList);
//					OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(
//							data.getOrderItemsList());
//					listViewObj.setAdapter(orderDetailAdapter);
//					Button addToCart = (Button) dialog
//							.findViewById(R.id.addToCart);
//
//					addToCart.setBackgroundColor(Color
//							.parseColor(ConstantsUrl.STORE_COLOR_CODE));
//					try {
//						SimpleDateFormat dateFormat = new SimpleDateFormat(
//								"yyyy-MM-dd HH:mm:ss");
//
//						SimpleDateFormat dateFormat1 = new SimpleDateFormat(
//								"MMMMMMMMM-dd-yyyy hh:mm aaa");
//						Date date = dateFormat.parse(data.getCreateTs());
//						statusTx.setText(dateFormat1.format(date)
//								.replace("am", "AM").replace("pm", "PM"));
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					orderNumbertxt.setText(orderNumbertxt.getText()
//							+ data.getOrderId());
//
//					BigDecimal bigDecimal = new BigDecimal(data
//							.getOrderGrossAmt());
//					totalTxt.setText(totalTxt.getText() + "$ "
//							+ bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
//					addToCart.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//
//							// TODO Auto-generated method stub
//
//							try {
//
//								DatabaseHelper databaseHelper = DatabaseHelper
//										.newInstance(getActivity());
//								databaseHelper.openDataBase();
//								databaseHelper.clearCart();
//								ArrayList<OrderItemsMetadata> productsList = data
//										.getOrderItemsList();
//								BigDecimal pricePerUnit, serviceTaxUnit, salesTaxUnit;
//								if (productsList != null) {
//
//									for (int n = 0; n < productsList.size(); n++) {
//										try {
//											boolean isFind = false;
//											String cartId = null;
//											OrderItemsMetadata product = productsList
//													.get(n);
//											pricePerUnit = new BigDecimal(
//													product.getpUnitPrice());
//											salesTaxUnit = new BigDecimal(
//													product.getSalesTaxAmt());
//											serviceTaxUnit = new BigDecimal(
//													product.getServiceTaxAmt());
//											ArrayList<String> ids = product
//													.getAttributesIdsList();
//											ArrayList<String> cartIds = databaseHelper
//													.getAllCartIds(
//															product.getpId(), 1);
//
//											for (int i = 0; i < cartIds.size(); i++) {
//												cartId = cartIds.get(i);
//												isFind = false;
//												ArrayList<String> valueList = databaseHelper
//														.getAllAttributesNameByCartId(
//																cartIds.get(i),
//																1);
//
//												if (valueList != null
//														&& ids != null) {
//													if (valueList.size() == 0
//															&& ids.size() == 0) {
//														isFind = true;
//													} else {
//														outerloop: for (int j = 0; j < ids
//																.size(); j++) {
//
//															for (int k = 0; k < valueList
//																	.size(); k++) {
//
//																if (!ids.get(j)
//																		.equals(valueList
//																				.get(k))) {
//																	isFind = false;
//
//																	break outerloop;
//																}
//																isFind = true;
//															}
//														}
//													}
//												}
//
//												if (isFind)
//													break;
//											}
//
//											int quant = 0;
//											try {
//												quant = Integer
//														.parseInt(product
//																.getpQuant());
//											} catch (Exception e) {
//												e.printStackTrace();
//											}
//											ContentValues values = new ContentValues();
//
//											if (!isFind) {
//
//												values.put(
//														DatabaseHelper.CheckOut_ProductId,
//														product.getpId());
//												values.put(
//														DatabaseHelper.CheckOut_ProductName,
//														product.getpName());
//												values.put(
//														DatabaseHelper.CheckOut_ProductDescription,
//														product.getpDesc());
//												values.put(
//														DatabaseHelper.CheckOut_Instruction,
//														product.getpInstruction());
//												values.put(
//														DatabaseHelper.CheckOut_ProductQuantity,
//														product.getpQuant());
//												values.put(
//														DatabaseHelper.CheckOut_PricePerUnit,
//														product.getpUnitPrice());
//												values.put(
//														DatabaseHelper.CHECKOut_ProductSKU,
//														product.getpSKU());
//												values.put(
//														DatabaseHelper.CheckOut_TotalAmount,
//														pricePerUnit.multiply(
//																new BigDecimal(
//																		quant))
//																.toString());
//												values.put(
//														DatabaseHelper.CHECKOUT_PTYPE,
//														1);
//												values.put(
//														DatabaseHelper.CHECKOut_SALESTAXAMT,
//														salesTaxUnit.multiply(
//																new BigDecimal(
//																		quant))
//																.toString());
//												values.put(
//														DatabaseHelper.CHECKOut_SERVICETAXAMT,
//														serviceTaxUnit
//																.multiply(
//																		new BigDecimal(
//																				quant))
//																.toString());
//												values.put(
//														DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
//														product.getSalesTaxAmt());
//												values.put(
//														DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
//														product.getServiceTaxAmt());
//												long insertedId = databaseHelper
//														.insetCartItem(values);
//												if (insertedId != -1) {
//
//													databaseHelper
//															.addOnsFromOrderHistory(
//																	product.getAddonList(),
//																	insertedId);
//
////													AppUtiles
////															.getInstance()
////															.showToast(
////																	getView(),
////																	"Product successfully added in cart.");
//													Toast.makeText(getContext(),"Product successfully added in cart.", 2).show();
//
//													((Global) OrderHistoryFragment.this
//															.getActivity()
//															.getApplication())
//															.setFavroite_order_id(orderNumbertxt
//																	.getText()
//																	.toString());
//													Intent in2 = new Intent(
//															getActivity(),
//															CheckOutProductListActivity.class);
//													in2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//													startActivity(in2);
//												} else {
////													AppUtiles
////															.getInstance()
////															.showToast(
////																	getActivity(),
////																	"Product not added in cart.");
//												Toast.makeText(getContext(), "Product not added in cart.", 2).show();
//												}
//
//											} else {
//
//												int updateQuantity = databaseHelper
//														.getQuantityByCartId(cartId);
//												BigDecimal previous = null;
//												try {
//													previous = new BigDecimal(
//															product.getpUnitPrice());
//												} catch (Exception e) {
//													e.printStackTrace();
//												}
//												values.put(
//														DatabaseHelper.CheckOut_ProductQuantity,
//														(updateQuantity + quant));
//												values.put(
//														DatabaseHelper.CheckOut_TotalAmount,
//														previous.multiply(
//																new BigDecimal(
//																		(updateQuantity + quant)))
//																.toString());
//												values.put(
//														DatabaseHelper.CHECKOut_SALESTAXAMT,
//														product.getSalesTaxAmt()
//																* (updateQuantity + quant));
//												values.put(
//														DatabaseHelper.CHECKOut_SERVICETAXAMT,
//														product.getServiceTaxAmt()
//																* (updateQuantity + quant));
//
//												databaseHelper
//														.updateCartAttributeItem(
//																values,
//																String.valueOf(cartId));
//
//												databaseHelper
//														.updateAttributeQuantity(
//																(updateQuantity + quant),
//																cartId);
////												AppUtiles
////														.getInstance()
////														.showToast(
////																getActivity(),
////																"Product successfully added in cart.");
//												Toast.makeText(getContext(), "Product successfully added in cart.", 2).show();
//												
//												((Global) OrderHistoryFragment.this
//														.getActivity()
//														.getApplication())
//														.setFavroite_order_id(orderNumbertxt
//																.getText()
//																.toString());
//
//												Intent in1 = new Intent(
//														getActivity(),
//														CheckOutProductListActivity.class);
//												in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//												startActivity(in1);
//											}
//										} catch (Exception e) {
//											e.printStackTrace();
//										}
//
//									}
//								}
//								databaseHelper.close();
//								if (mCartCountTxt != null) {
//									AppUtiles.getInstance().setCartItems(
//											mCartCountTxt, getActivity());
//								}
//								dialog.dismiss();
//							} catch (Exception e) {
//								e.printStackTrace();
//							}

					
				//	}
				//	});
					// expressCheckout.setOnClickListener(new OnClickListener()
					// {
					//
					// @Override
					// public void onClick(View v) {
					// // TODO Auto-generated method stub
					//
					// String storeDeliveryStatus = null;// = mAppPrefs
					// // .getString(
					// // ConstantsUrl.RESTAURANT_DELIVERYSTATUS,
					// // "0");
					// String minimumDelivery = null;// =
					// // mAppPrefs.getString(
					// // ConstantsUrl.RESTAURANT_MINDELIVERYVALUE,
					// // "0");
					// if (restaurantDetailData != null) {
					// storeDeliveryStatus = restaurantDetailData
					// .getmStoreDeliveryStatus();
					// minimumDelivery = restaurantDetailData
					// .getmStoreMinDeliveryValue();
					// }
					//
					// String totalAmt = data.getOrderTotal();
					// BigDecimal minimumDeliveryDec, totalAmtDec;
					// try {
					// minimumDeliveryDec = new BigDecimal(
					// minimumDelivery);
					// } catch (Exception e) {
					// minimumDeliveryDec = new BigDecimal(0);
					// }
					// try {
					// totalAmtDec = new BigDecimal(totalAmt);
					// } catch (Exception e) {
					// // e.printStackTrace();
					// totalAmtDec = new BigDecimal(0);
					// }
					// minimumDeliveryDec = minimumDeliveryDec.setScale(2,
					// BigDecimal.ROUND_HALF_UP);
					// totalAmtDec = totalAmtDec.setScale(2,
					// BigDecimal.ROUND_HALF_UP);
					//
					// if (storeDeliveryStatus == null
					// || storeDeliveryStatus.equals("")
					// || storeDeliveryStatus.equals("0")) {
					// AppUtiles.getInstance()
					// .showToast(getActivity(),
					// "Currently Restaurant has not accept orders.Sorry!");
					// } else if ((totalAmtDec
					// .compareTo(new BigDecimal(0)) == 0 && minimumDeliveryDec
					// .compareTo(new BigDecimal(0)) == 0)
					// || (minimumDeliveryDec
					// .compareTo(totalAmtDec) == 1)) {
					// AppUtiles.getInstance()
					// .showToast(
					// getActivity(),
					// "Minimum Order: $ "
					// + minimumDeliveryDec
					// .setScale(
					// 2,
					// BigDecimal.ROUND_HALF_UP));
					//
					// } else {
					// sendDataOnServer(data);
					// }
					//
					// }
					// });
				//	dialog.show();
					try {

						DatabaseHelper databaseHelper = DatabaseHelper
								.newInstance(getActivity());
						databaseHelper.openDataBase();
						databaseHelper.clearCart();
						ArrayList<OrderItemsMetadata> productsList = data
								.getOrderItemsList();
						BigDecimal pricePerUnit, serviceTaxUnit, salesTaxUnit;
						if (productsList != null) {

							for (int n = 0; n < productsList.size(); n++) {
								try {
									boolean isFind = false;
									String cartId = null;
									OrderItemsMetadata product = productsList
											.get(n);
									pricePerUnit = new BigDecimal(
											product.getpUnitPrice());
									salesTaxUnit = new BigDecimal(
											product.getSalesTaxAmt());
									serviceTaxUnit = new BigDecimal(
											product.getServiceTaxAmt());
									ArrayList<String> ids = product
											.getAttributesIdsList();
									ArrayList<String> cartIds = databaseHelper
											.getAllCartIds(
													product.getpId(), 1);

									for (int i = 0; i < cartIds.size(); i++) {
										cartId = cartIds.get(i);
										isFind = false;
										ArrayList<String> valueList = databaseHelper
												.getAllAttributesNameByCartId(
														cartIds.get(i),
														1);

										if (valueList != null
												&& ids != null) {
											if (valueList.size() == 0
													&& ids.size() == 0) {
												isFind = true;
											} else {
												outerloop: for (int j = 0; j < ids
														.size(); j++) {

													for (int k = 0; k < valueList
															.size(); k++) {

														if (!ids.get(j)
																.equals(valueList
																		.get(k))) {
															isFind = false;

															break outerloop;
														}
														isFind = true;
													}
												}
											}
										}

										if (isFind)
											break;
									}

									int quant = 0;
									try {
										quant = Integer
												.parseInt(product
														.getpQuant());
									} catch (Exception e) {
										e.printStackTrace();
									}
									ContentValues values = new ContentValues();

									if (!isFind) {

										values.put(
												DatabaseHelper.CheckOut_ProductId,
												product.getpId());
										values.put(
												DatabaseHelper.CheckOut_ProductName,
												product.getpName());
										values.put(
												DatabaseHelper.CheckOut_ProductDescription,
												product.getpDesc());
										values.put(
												DatabaseHelper.CheckOut_Instruction,
												product.getpInstruction());
										values.put(
												DatabaseHelper.CheckOut_ProductQuantity,
												product.getpQuant());
										values.put(
												DatabaseHelper.CheckOut_PricePerUnit,
												product.getpUnitPrice());
										values.put(
												DatabaseHelper.CHECKOut_ProductSKU,
												product.getpSKU());
										values.put(
												DatabaseHelper.CheckOut_TotalAmount,
												pricePerUnit.multiply(
														new BigDecimal(
																quant))
														.toString());
										values.put(
												DatabaseHelper.CHECKOUT_PTYPE,
												1);
										values.put(
												DatabaseHelper.CHECKOut_SALESTAXAMT,
												salesTaxUnit.multiply(
														new BigDecimal(
																quant))
														.toString());
										values.put(
												DatabaseHelper.CHECKOut_SERVICETAXAMT,
												serviceTaxUnit
														.multiply(
																new BigDecimal(
																		quant))
														.toString());
										values.put(
												DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
												product.getSalesTaxAmt());
										values.put(
												DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
												product.getServiceTaxAmt());
										long insertedId = databaseHelper
												.insetCartItem(values);
										if (insertedId != -1) {

											databaseHelper
													.addOnsFromOrderHistory(
															product.getAddonList(),
															insertedId);

//											AppUtiles
//													.getInstance()
//													.showToast(
//															getView(),
//															"Product successfully added in cart.");
											Toast.makeText(getContext(),"Product successfully added in cart.", 2).show();

											((Global) OrderHistoryFragment.this
													.getActivity()
													.getApplication())
													.setFavroite_order_id(data.getOrderId());
											Intent in2 = new Intent(
													getActivity(),
													CheckOutProductListActivity.class);
											in2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

											startActivity(in2);
										} else {
//											AppUtiles
//													.getInstance()
//													.showToast(
//															getActivity(),
//															"Product not added in cart.");
										Toast.makeText(getContext(), "Product not added in cart.", 2).show();
										}

									} else {

										int updateQuantity = databaseHelper
												.getQuantityByCartId(cartId);
										BigDecimal previous = null;
										try {
											previous = new BigDecimal(
													product.getpUnitPrice());
										} catch (Exception e) {
											e.printStackTrace();
										}
										values.put(
												DatabaseHelper.CheckOut_ProductQuantity,
												(updateQuantity + quant));
										values.put(
												DatabaseHelper.CheckOut_TotalAmount,
												previous.multiply(
														new BigDecimal(
																(updateQuantity + quant)))
														.toString());
										values.put(
												DatabaseHelper.CHECKOut_SALESTAXAMT,
												product.getSalesTaxAmt()
														* (updateQuantity + quant));
										values.put(
												DatabaseHelper.CHECKOut_SERVICETAXAMT,
												product.getServiceTaxAmt()
														* (updateQuantity + quant));

										databaseHelper
												.updateCartAttributeItem(
														values,
														String.valueOf(cartId));

										databaseHelper
												.updateAttributeQuantity(
														(updateQuantity + quant),
														cartId);
//										AppUtiles
//												.getInstance()
//												.showToast(
//														getActivity(),
//														"Product successfully added in cart.");
										Toast.makeText(getContext(), "Product successfully added in cart.", 2).show();
										
										((Global) OrderHistoryFragment.this
												.getActivity()
												.getApplication())
												.setFavroite_order_id(data.getOrderId());

										Intent in1 = new Intent(
												getActivity(),
												CheckOutProductListActivity.class);
										in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

										startActivity(in1);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						}
						databaseHelper.close();
						if (mCartCountTxt != null) {
							AppUtiles.getInstance().setCartItems(
									mCartCountTxt, getActivity());
						}
						dialog.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			return convertView;
		}
	}

	private class ViewHolder {
		private TextView orderIdTxt, statusTxt, descTxt, priceTxt;
		private TextView new_button;
	}





	private class OrderDetailAdapter extends BaseAdapter {
		private ArrayList<OrderItemsMetadata> orderItemsList = new ArrayList<OrderItemsMetadata>();
		private LayoutInflater mInflater;

		public OrderDetailAdapter(ArrayList<OrderItemsMetadata> data) {
			// TODO Auto-generated constructor stub
			orderItemsList = data;
			mInflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return orderItemsList.size();
		}

		@Override
		public OrderItemsMetadata getItem(int position) {
			// TODO Auto-generated method stub
			return orderItemsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holderObj;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.row_order_history_detail, parent, false);
				holderObj = new ViewHolder();
				holderObj.orderIdTxt = (TextView) convertView
						.findViewById(R.id.productName);

				holderObj.descTxt = (TextView) convertView
						.findViewById(R.id.productDesc);
				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();
			}
			OrderItemsMetadata data = getItem(position);
			holderObj.orderIdTxt.setText(data.getpName());
			String product = "";
			ArrayList<RestaurantAddOnMetadata> items = data.getAddonList();

			for (int j = 0; j < items.size(); j++) {
				if (j != items.size() - 1)
					product += items.get(j).getpName() + ", ";
				else
					product += items.get(j).getpName();
			}
			holderObj.descTxt.setText(product);

			return convertView;
		}
	}

	private class CalculateDiscount extends AsyncTask<String[], Void, String> {

		private ImageView mImg;
		private ProgressBar mProgress;
		private TextView mTextview;

		public CalculateDiscount(ImageView imgView, ProgressBar bar,
				TextView mText) {
			// TODO Auto-generated constructor stub
			mImg = imgView;
			mProgress = bar;
			mTextview = mText;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mImg.setVisibility(View.GONE);
			mProgress.setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(String[]... params) {
			// TODO Auto-generated method stub

			return JsonParser.Webserice_Call_URL(ConstantsUrl.APPLYCOUPONCODE,
					params[0], params[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			mImg.setVisibility(View.VISIBLE);
			mProgress.setVisibility(View.GONE);
			parseDiscountResponse(mTextview, result);
		}
	}

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

	
	
}
