package com.app2mobile.Sultanwok.fragment;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.app2mobile.acplibrary.ACProgressCustom;
import com.app2mobile.Sultanwok.R;
import com.app2mobile.metadata.FavoriteProductMetadata;
import com.app2mobile.utiles.AppUtiles;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.DatabaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Product_favroite_fragment extends Fragment {
	private static Product_favroite_fragment instance = null;

	private ListView favListView;
	private ArrayList<FavoriteProductMetadata> favoriteList = new ArrayList<FavoriteProductMetadata>();
	private FavouriteAdapter favAdapter;
	private boolean isEditMode = false, isLeft = false;
	private RelativeLayout rel;
	private TextView mCartCountTxt;
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.favirote_product, container, false);
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		rel=(RelativeLayout)view.findViewById(R.id.rel);
		favListView=(ListView)view.findViewById(R.id.favList);
		mCartCountTxt=(TextView)view.findViewById(R.id.cartItems);
//		LayoutParams params = (LayoutParams) mCartCountTxt.getLayoutParams();
//		mCartCountTxt.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
//		int maximumWidth = Math.max(mCartCountTxt.getMeasuredWidth(),
//				mCartCountTxt.getMeasuredHeight());
//		params.width = maximumWidth;
//		params.height = maximumWidth;
//		mCartCountTxt.setLayoutParams(params);
//		File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
//		
//		Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
//		Drawable dr = new BitmapDrawable(bmp);
//		rel.setBackgroundDrawable(dr);
		favAdapter = new FavouriteAdapter();
		favListView.setAdapter(favAdapter);
		new getAllFavAsync().execute();
		
	}
	
	
	private class getAllFavAsync extends AsyncTask<Void, Void, Void> {
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
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getAllFavourites();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			favAdapter.notifyDataSetChanged();
			progressDialog.dismiss();
		}
	}

	private void getAllFavourites() {
		favoriteList.clear();
		try {
			DatabaseHelper databaseHelper = DatabaseHelper
					.newInstance(getActivity());
			databaseHelper.openDataBase();
			Cursor cursor = databaseHelper.getAllFavourites();

			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						FavoriteProductMetadata data = new FavoriteProductMetadata();
						data.setFavId(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_Id)));
						data.setProductId(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_ProductId)));
						data.setProductName(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_ProductName)));

						data.setProductDesc(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_Instruction)));
						data.setQuantity(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_ProductQuantity)));
						data.setPricePerUnit(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_PricePerUnit)));
						data.setCreatedOn(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_CreatedOn)));
						data.setSpecialInstruction(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_Instruction)));
						data.setTotalAmount(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_TotalAmount)));
						data.setProductSKU(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_ProductSKU)));
						data.setParentId(cursor.getInt(cursor
								.getColumnIndex(DatabaseHelper.FAV_ISPARENT)));
						data.setpType(cursor.getInt(cursor
								.getColumnIndex(DatabaseHelper.FAV_PTYPE)));
						data.setTotalSalesTax(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.FAV_SALESTAXAMT)));
						data.setTotalServiceTax(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.FAV_SERVICETAXAMT)));
						data.setSaleTaxUnit(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.FAV_SALESTAXAMTUNIT)));
						data.setServiceTaxUnit(cursor.getDouble(cursor
								.getColumnIndex(DatabaseHelper.FAV_SERVICETAXAMTUNIT)));
						data.setmGroupId(cursor.getString(cursor
								.getColumnIndex(DatabaseHelper.FAV_GroupId)));
						data.setAddTotalAmt(data.getTotalAmount());
						data.setProductSubTitle("");
						data.setAddPricePerUnit(data.getPricePerUnit());
						ArrayList<String> ids = new ArrayList<String>();
						data.setAttributesIdsList(ids);
						
						if (data.getpType() == 1 || data.getpType() == 2) {
							BigDecimal productPrice = new BigDecimal(
									data.getTotalAmount());
							productPrice = productPrice.setScale(2,
									BigDecimal.ROUND_HALF_UP);
							BigDecimal unitPrice = new BigDecimal(
									data.getPricePerUnit());
							BigDecimal productItemPrice;
							String projectDesc = "";
							ArrayList<FavoriteProductMetadata> dataList = new ArrayList<FavoriteProductMetadata>();

							Cursor cursor1 = databaseHelper.getCartAttributes(
									data.getFavId(), 2);
							if (cursor1 != null) {
							
								if (cursor1.moveToFirst()) {
									do {
										FavoriteProductMetadata data1 = new FavoriteProductMetadata();
										data1.setFavId(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_Id)));
										data1.setProductId(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_ProductId)));
										data1.setProductName(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_ProductName)));

										data1.setProductDesc(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_Instruction)));
										data1.setQuantity(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_ProductQuantity)));
										data1.setPricePerUnit(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_PricePerUnit)));
										data1.setCreatedOn(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_CreatedOn)));
										data1.setSpecialInstruction(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_Instruction)));
										data1.setTotalAmount(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_TotalAmount)));
										data1.setProductSKU(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_ProductSKU)));
										data1.setParentId(cursor1.getInt(cursor1
												.getColumnIndex(DatabaseHelper.FAV_ISPARENT)));
										data1.setpType(cursor1.getInt(cursor1
												.getColumnIndex(DatabaseHelper.FAV_PTYPE)));
										data1.setTotalSalesTax(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.FAV_SALESTAXAMT)));
										data1.setTotalServiceTax(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.FAV_SERVICETAXAMT)));
										data1.setSaleTaxUnit(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.FAV_SALESTAXAMTUNIT)));
										data1.setServiceTaxUnit(cursor1.getDouble(cursor1
												.getColumnIndex(DatabaseHelper.FAV_SERVICETAXAMTUNIT)));
										data1.setmGroupId(cursor1.getString(cursor1
												.getColumnIndex(DatabaseHelper.FAV_GroupId)));
										dataList.add(data1);
										String desc = data1.getProductName();
										System.out.println("product desc "
												+ desc);
										if (desc != null && !desc.equals("")) {
											projectDesc += desc + ", ";

										}
										BigDecimal quant = new BigDecimal(data1.getQuantity());
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
										ids.add(data1.getProductId());
										System.out.println("datat.... type  " + data1.getpType());
									} while (cursor1.moveToNext());
								}
							}
							cursor1.close();
							if (projectDesc != null && projectDesc.length() > 0) {
								projectDesc = projectDesc.trim().substring(0,
										projectDesc.length() - 2);
							}
							data.setAddTotalAmt(productPrice.toString());
							data.setProductSubTitle(projectDesc);
							data.setAttributesIdsList(ids);
							data.setAddPricePerUnit(unitPrice.toString());
							data.setFavAttributesList(dataList);
						}
						favoriteList.add(data);
						// }
					} while (cursor.moveToNext());

				}
				cursor.close();
			}
			databaseHelper.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		favAdapter.notifyDataSetChanged();
	}

	private class FavouriteAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public FavouriteAdapter() {
			// TODO Auto-generated constructor stub
			mInflater = LayoutInflater.from(getActivity());
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return favoriteList.size();
		}

		@Override
		public FavoriteProductMetadata getItem(int arg0) {
			// TODO Auto-generated method stub
			return favoriteList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			final ViewHolder holderObj;
			if (convertView == null) {
				holderObj = new ViewHolder();
				convertView = mInflater.inflate(R.layout.row_favourites, null);
				holderObj.productCart = (ImageView) convertView
						.findViewById(R.id.cart);
				holderObj.productName = (TextView) convertView
						.findViewById(R.id.productName);
				holderObj.productPrice = (TextView) convertView
						.findViewById(R.id.price);
				holderObj.deleteIcon = (ImageView) convertView
						.findViewById(R.id.deleteIcon);
				holderObj.deleteFav = (LinearLayout) convertView
						.findViewById(R.id.delete);
				holderObj.slideLayout = (LinearLayout) convertView
						.findViewById(R.id.slide);
				holderObj.productDesc = (TextView) convertView
						.findViewById(R.id.productDesc);
				holderObj.price_tag=(LinearLayout)convertView.findViewById(R.id.price_tag);
				holderObj.price_tag.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
				convertView.setTag(holderObj);
			} else {
				holderObj = (ViewHolder) convertView.getTag();
			}
			if (isEditMode) {
				holderObj.deleteIcon.setVisibility(View.VISIBLE);

			} else {
				holderObj.deleteIcon.setVisibility(View.GONE);

			}
			final FavoriteProductMetadata product = getItem(position);
			if (product.isAddedInCart()) {
				holderObj.productCart.setImageResource(R.drawable.ic_cart);
			} else {
				holderObj.productCart.setImageResource(R.drawable.ic_cart_in);
			}
			holderObj.deleteFav.measure(MeasureSpec.UNSPECIFIED,
					MeasureSpec.UNSPECIFIED);
			slideRight(holderObj.slideLayout, 0, holderObj.deleteFav);

			BigDecimal bigDecimal = new BigDecimal(product.getAddTotalAmt());
			holderObj.productName.setText(product.getProductName());
			holderObj.productPrice.setText("$ "
					+ bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP));
			holderObj.productDesc.setText(product.getProductSubTitle());
			holderObj.productCart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {

						DatabaseHelper databaseHelper = DatabaseHelper
								.newInstance(getActivity());
						databaseHelper.openDataBase();

						if (isLeft && isEditMode) {

							/**
							 * As developer its wrong section
							 */

							// try {
							if (holderObj.deleteFav.getVisibility() == View.VISIBLE) {
								int count = databaseHelper.deleteFav(product
										.getFavId());
								if (count != 0) {
									favoriteList.remove(position);
								}

							}
							isLeft = !isLeft;
							/**
	 *
	 */
						} else {

							boolean status = product.isAddedInCart();

							boolean isFind = false;
							String cartId = null;

							ArrayList<String> ids = product
									.getAttributesIdsList();
							ArrayList<String> cartIds = databaseHelper
									.getAllCartIds(product.getProductId(), 1);
							if (cartIds != null) {
								for (int i = 0; i < cartIds.size(); i++) {
									cartId = cartIds.get(i);
									isFind = false;
									ArrayList<String> valueList = databaseHelper
											.getAllAttributesNameByCartId(
													cartIds.get(i), 1);
									System.out.println("ids " + ids);
									System.out
											.println("valueList " + valueList);
									if (valueList != null && ids != null) {
										if (valueList.size() == 0
												&& ids.size() == 0) {
											isFind = true;
										} else if (valueList.size() != ids
												.size()) {
											System.out.println("sizes  "
													+ (valueList.size() != ids
															.size()));
											isFind = false;
										} else {
											for (int j = 0; j < ids.size(); j++) {

												for (int k = 0; k < valueList
														.size(); k++) {

													if (ids.get(j).equals(
															valueList.get(k))) {
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

								int updateQuantity = 1;
								ContentValues values = new ContentValues();
								if (!status) {
									if (!isFind) {

										values.put(
												DatabaseHelper.CheckOut_ProductId,
												product.getProductId());
										values.put(
												DatabaseHelper.CheckOut_ProductName,
												product.getProductName());
										values.put(
												DatabaseHelper.CheckOut_ProductDescription,
												product.getProductDesc());
										values.put(
												DatabaseHelper.CheckOut_Instruction,
												product.getSpecialInstruction());
										values.put(
												DatabaseHelper.CheckOut_ProductQuantity,
												updateQuantity);
										values.put(
												DatabaseHelper.CheckOut_PricePerUnit,
												product.getPricePerUnit());
										values.put(
												DatabaseHelper.CHECKOut_ProductSKU,
												product.getProductSKU());
										values.put(
												DatabaseHelper.CheckOut_TotalAmount,
												product.getTotalAmount());
										values.put(
												DatabaseHelper.CHECKOUT_PTYPE,
												product.getpType());
										values.put(
												DatabaseHelper.CHECKOut_SALESTAXAMT,
												product.getTotalSalesTax());
										values.put(
												DatabaseHelper.CHECKOut_SERVICETAXAMT,
												product.getTotalServiceTax());
										values.put(
												DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
												product.getSaleTaxUnit());
										values.put(
												DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
												product.getServiceTaxUnit());
										values.put(
												DatabaseHelper.CHECKOut_GroupId,
												product.getmGroupId());
										long insertedId = databaseHelper
												.insetCartItem(values);
										if (insertedId != -1) {

											databaseHelper.addOnsFromCart(
													product.getFavAttributesList(),
													insertedId);
//											AppUtiles.getInstance()
//													.showToast(getView(),
//															"Product successfully added in cart.");
											Toast.makeText(getActivity(), "Product successfully added in cart.", 2).show();
										} else {
//											AppUtiles.getInstance()
//													.showToast(getActivity(),
//															"Product not added in cart.");
											Toast.makeText(getActivity(), "Product not added in cart.", 2).show();

										}

									} else {

										int quant = databaseHelper
												.getQuantityByCartId(cartId);
										BigDecimal previous = null;
										try {
											previous = new BigDecimal(product
													.getPricePerUnit());
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
												product.getSaleTaxUnit()
														* (updateQuantity + quant));
										values.put(
												DatabaseHelper.CHECKOut_SERVICETAXAMT,
												product.getServiceTaxUnit()
														* (updateQuantity + quant));

										databaseHelper.updateCartAttributeItem(
												values, String.valueOf(cartId));

										databaseHelper.updateAttributeQuantity(
												(updateQuantity + quant),
												cartId);

									}
								} else {

									databaseHelper.deleteCartItem(cartId);
								}
								product.setAddedInCart(!product.isAddedInCart());

							}
						}
						databaseHelper.close();
						if (mCartCountTxt != null) {
							AppUtiles.getInstance().setCartItems(mCartCountTxt, getActivity());
						}
						notifyDataSetChanged();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			holderObj.deleteIcon.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (isEditMode) {
						if (!isLeft) {
							slideLeft(holderObj.slideLayout,
									holderObj.deleteFav.getMeasuredWidth(),
									holderObj.deleteFav);
						} else {
							notifyDataSetChanged();
						}
						isLeft = !isLeft;

					}
				}
			});

			holderObj.deleteFav.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isEditMode) {
						try {

							DatabaseHelper databaseHelper = DatabaseHelper
									.newInstance(getActivity());
							databaseHelper.openDataBase();
							int count = databaseHelper.deleteFav(product
									.getFavId());
							databaseHelper.close();
							if (count != 0) {
								favoriteList.remove(position);
							}
							isLeft = !isLeft;

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					notifyDataSetChanged();
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

		private class ViewHolder {
			private TextView productName, productPrice, productDesc;
			private ImageView productCart, deleteIcon;
			private LinearLayout slideLayout, deleteFav,price_tag;
		}
	}

	private void slideLeft(View startView, final int slide, View v) {

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

	private void slideRight(final View startView, final int slide, View v) {

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

	
	
	
}
