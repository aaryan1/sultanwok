package com.app2mobile.utiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app2mobile.metadata.FavoriteProductMetadata;
import com.app2mobile.metadata.RestaurantAddOnMetadata;
import com.app2mobile.metadata.RestaurantAddOnsMetadata;
import com.app2mobile.metadata.RestaurantBundleMetadata;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static String DB_NAME = "Restaurant.sqlite";
	private final Context myContext;
	private SQLiteDatabase mDataBase;

	private static String DB_PATH;
	public static final String TB_Fav = "FavoriteTbl";
	public static final String TB_CheckOut = "CheckOutTbl";
	public static final String TBL_LOCALITY = "tblLocality";
	public static final String TBL_DELIVERYCHARGES = "tblCharges";
	public static final String TBL_CITIY = "tbl_cities";
	public static final String TBL_MIles= "tbl_miles";

	// public static final String FAV_Id = "FavoriteId";
	// public static final String FAV_ProductID = "ProductId";
	// public static final String FAV_ProductDescription = "ProductDescription";
	// public static final String FAV_Quantity = "ProductQuantity";
	// public static final String FAV_ProductPrice = "ProductPrice";
	// public static final String FAV_ProductName = "ProductName";

	// public static final String FAV_Currency = "ProductCurrency";
	// public static final String FAV_CreatedOn = "CreatedOn";
	// public static final String FAV_TotalAmount = "TotalAmount";
	// public static final String FAV_SpecialIns = "SpecialInstruction";

	// public static final String FAV_ProductSKU = "ProductSKU";
	// public static final String FAV_ISPARENT = "isParent";
	// public static final String FAV_ISATTRIBUTE = "isAttribute";

	public static final String CheckOut_Id = "CheckOutId";
	public static final String CheckOut_ProductId = "ProductId";
	public static final String CheckOut_ProductName = "ProductName";
	public static final String CheckOut_ProductDescription = "ProductDescription";
	public static final String CheckOut_ProductQuantity = "ProductQuantity";
	public static final String CheckOut_PricePerUnit = "PricePerUnit";
	// public static final String CheckOut_Currencytype = "CurrencyType";
	public static final String CheckOut_CreatedOn = "CreatedOn";
	public static final String CheckOut_Instruction = "SpecialInstruction";
	public static final String CheckOut_TotalAmount = "TotalAmount";
	public static final String CHECKOut_ProductSKU = "ProductSKU";
	public static final String CHECKOUT_PTYPE = "pType";
	public static final String CHECKOut_SERVICETAXAMT = "serviceTaxAmt";
	public static final String CHECKOut_SALESTAXAMT = "salesTaxAmt";
	public static final String CHECKOut_SALESTAXAMTUNIT = "salesTaxAmtPerUnit";
	public static final String CHECKOut_SERVICETAXAMTUNIT = "serviceTaxAmtPerUnit";
	public static final String CHECKOut_ISPARENT = "parentId";
	public static final String CHECKOut_GroupId = "groupID";
	// public static final String CHECKOut_ISATTRIBUTE = "isAttribute";

	public static final String FAV_Id = "FavoriteId";
	public static final String FAV_ProductId = "ProductId";
	public static final String FAV_ProductName = "ProductName";
	public static final String FAV_ProductDescription = "ProductDescription";
	public static final String FAV_ProductQuantity = "ProductQuantity";
	public static final String FAV_PricePerUnit = "PricePerUnit";
	// public static final String CheckOut_Currencytype = "CurrencyType";
	public static final String FAV_CreatedOn = "CreatedOn";
	public static final String FAV_Instruction = "SpecialInstruction";
	public static final String FAV_TotalAmount = "TotalAmount";
	public static final String FAV_ProductSKU = "ProductSKU";
	public static final String FAV_PTYPE = "pType";
	public static final String FAV_SERVICETAXAMT = "serviceTaxAmt";
	public static final String FAV_SALESTAXAMT = "salesTaxAmt";
	public static final String FAV_SALESTAXAMTUNIT = "salesTaxAmtPerUnit";
	public static final String FAV_SERVICETAXAMTUNIT = "serviceTaxAmtPerUnit";
	public static final String FAV_ISPARENT = "parentId";
	public static final String FAV_GroupId = "groupID";

	public static final String DELIVERYCHARGES_ID = "id";
	public static final String DELIVERYCHARGES_MOV = "mov";
	public static final String DELIVERYCHARGES_CHARGE = "charge";
	public static final String DELIVERYCHARGES_ZIPCODE = "locationPin";
	public static final String DELIVERYCHARGES_LOCATIONID = "locationId";

	public static final String LOCALITY_LOCATIONID = "location_id";
	public static final String LOCALITY_STOREDELIVERRID = "store_deliverale_location_id";
	public static final String LOCALITY_LOCATION = "location_name";
	public static final String LOCALITY_CITYID = "city_id";
	public static final String LOCALITY_CITY = "city_name";
	public static final String LOCALITY_STATECODE = "state_code";
	public static final String LOCALITY_COUNTRYCODE = "country_code";
	public static final String LOCALITY_MOV = "mov";
	public static final String LOCALITY_DELIVERY = "delivery_charge";
	// public static final String LOCALITY_LOCATIONID = "locationId";

	public static final String CITY_ID = "city_id";
	public static final String CITY_NAME = "city_name";
	// public static final String CITY_CITYCODE="";
	public static final String CITY_STATECODE = "state_code";
	public static final String CITY_COUNTRYCODE = "country_code";
	public static final String CITY_ = "timezoneid";
	public static DatabaseHelper instance = null;

	public static DatabaseHelper newInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context);
		}
		return instance;

	}

	private DatabaseHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DB_NAME, null, 2);
		DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		myContext = context;
		try {
			createDataBase();
			close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DB_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DB_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		if (myInput != null && myOutput != null) {
			// transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
		} else {
			Log.d("error", "Not Found");
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		mDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	public SQLiteDatabase openDatabaseInReadMode() {

		return getReadableDatabase();

	}

	public SQLiteDatabase opendatabaseInWritableMode() {
		return getWritableDatabase();
	}

	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		if (mDataBase != null)
			mDataBase.close();

		super.close();
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
		if (!db.isReadOnly()) {
			// Enable foreign key constraints
			db.execSQL("PRAGMA foreign_keys=ON;");
			db.execSQL("PRAGMA case_sensitive_like=ON");

		}

	}

	// public Cursor getDeliveryCharge(String zipcode) {
	// return mDataBase.query(true, TBL_DELIVERYCHARGES, null,
	// DELIVERYCHARGES_ZIPCODE + "='" + zipcode + "'", null, null,
	// null, null, null);
	// }

	public Cursor getDeliveryCharge(String localityId, String ship_deliverable) {
		return mDataBase.query(true, TBL_LOCALITY, null, LOCALITY_LOCATIONID
				+ "='" + localityId + "' AND " + LOCALITY_STOREDELIVERRID
				+ "='" + ship_deliverable + "'", null, null, null, null, null);
	}

	public void insertDeliveryCharges(JSONArray deliveryArray) {
		try {
			mDataBase.delete(TBL_DELIVERYCHARGES, null, null);
			mDataBase.beginTransaction();
			if (deliveryArray != null) {
				ContentValues values = new ContentValues();
				for (int i = 0; i < deliveryArray.length(); i++) {
					values.clear();
					JSONObject deliveryObject = deliveryArray.getJSONObject(i);
					values.put(DELIVERYCHARGES_CHARGE,
							deliveryObject.getInt("charge"));
					values.put(DELIVERYCHARGES_MOV,
							deliveryObject.getInt("mov"));
					values.put(DELIVERYCHARGES_LOCATIONID,
							deliveryObject.getInt("location_id"));
					values.put(DELIVERYCHARGES_ZIPCODE, JsonParser
							.getkeyValue_Str(deliveryObject, "location_pin"));
					mDataBase.insert(TBL_DELIVERYCHARGES, null, values);
				}
			}
			mDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			mDataBase.endTransaction();
		}
	}

	public void insertCities(JSONArray cityArray) {
		try {
			mDataBase.delete(TBL_CITIY, null, null);
			mDataBase.beginTransaction();
			if (cityArray != null) {
				ContentValues values = new ContentValues();
				for (int i = 0; i < cityArray.length(); i++) {
					values.clear();
					JSONObject cityObject = cityArray.getJSONObject(i);
					values.put(CITY_ID,
							JsonParser.getkeyValue_Str(cityObject, CITY_ID));
					values.put(CITY_COUNTRYCODE, JsonParser.getkeyValue_Str(
							cityObject, CITY_COUNTRYCODE));
					values.put(CITY_NAME,
							JsonParser.getkeyValue_Str(cityObject, CITY_NAME));
					values.put(CITY_STATECODE, JsonParser.getkeyValue_Str(
							cityObject, CITY_STATECODE));
					mDataBase.insert(TBL_CITIY, null, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mDataBase.endTransaction();
		}
	}

	public void insertLocality(JSONArray localityArray) {
		try {
			mDataBase.delete(TBL_LOCALITY, null, null);
			mDataBase.beginTransaction();
			if (localityArray != null) {
				ContentValues values = new ContentValues();
				for (int i = 0; i < localityArray.length(); i++) {
					values.clear();
					JSONObject deliveryObject = localityArray.getJSONObject(i);
					values.put(LOCALITY_LOCATIONID, JsonParser.getkeyValue_Str(
							deliveryObject, LOCALITY_LOCATIONID));
					values.put(LOCALITY_STOREDELIVERRID, JsonParser
							.getkeyValue_Str(deliveryObject,
									LOCALITY_STOREDELIVERRID));
					values.put(LOCALITY_LOCATION, JsonParser.getkeyValue_Str(
							deliveryObject, LOCALITY_LOCATION));
					values.put(LOCALITY_CITYID, JsonParser.getkeyValue_Str(
							deliveryObject, LOCALITY_CITYID));
					values.put(LOCALITY_CITY, JsonParser.getkeyValue_Str(
							deliveryObject, LOCALITY_CITY));
					// values.put(LOCALITY_LOCATION, JsonParser.getkeyValue_Str(
					// deliveryObject, LOCALITY_LOCATION));
					values.put(LOCALITY_COUNTRYCODE, JsonParser
							.getkeyValue_Str(deliveryObject,
									LOCALITY_COUNTRYCODE));
					values.put(LOCALITY_MOV, JsonParser.getkeyValue_Str(
							deliveryObject, LOCALITY_MOV));

					values.put(LOCALITY_DELIVERY, JsonParser.getkeyValue_Str(
							deliveryObject, LOCALITY_DELIVERY));

					values.put(LOCALITY_STATECODE, JsonParser.getkeyValue_Str(
							deliveryObject, LOCALITY_STATECODE));
					mDataBase.insert(TBL_LOCALITY, null, values);
				}
			}
			mDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			mDataBase.endTransaction();
		}
	}

	public int clearDeliveryCharges() {
		return mDataBase.delete(TBL_DELIVERYCHARGES, null, null);
	}

	public long insetCartItem(ContentValues contentValues) {
		return mDataBase.insert(TB_CheckOut, null, contentValues);
	}

	public int updateCartItem(ContentValues values, String cartId) {
		return mDataBase.update(TB_CheckOut, values, CheckOut_Id + "='"
				+ cartId + "'", null);
	}

	public int getTotalCartItems() {
		int count = 0;
		Cursor cursor = mDataBase.query(TB_CheckOut, null, CHECKOUT_PTYPE
				+ "!=3 AND " + CHECKOUT_PTYPE + "!=4", null, null, null, null);
		if (cursor != null) {
			count = cursor.getCount();
			cursor.close();
		}
		return count;
	}

	public int clearCart() {
		return mDataBase.delete(TB_CheckOut, null, null);
	}

	public int getProductQuantityInCart(String productId, String inst) {
		int quantity = 0;

		Cursor cursor;
		if (inst == null) {
			cursor = mDataBase.query(true, TB_CheckOut, null,
					CheckOut_ProductId + "='" + productId + "' AND "
							+ CHECKOUT_PTYPE + "=0", null, null, null, null,
					null);
		} else {
			cursor = mDataBase.query(true, TB_CheckOut, null,
					CheckOut_ProductId + "='" + productId + "' AND "
							+ CHECKOUT_PTYPE + "=0 AND " + CheckOut_Instruction
							+ "='" + inst + "'", null, null, null, null, null);
		}
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					quantity = cursor.getInt(cursor
							.getColumnIndex(CheckOut_ProductQuantity));
				} while (cursor.moveToNext());

			}
			cursor.close();
		}

		return quantity;
	}

	public String getCartId(String productId, String inst) {
		String cartIds = "";
		// Cursor cursor = mDataBase.query(true, TB_CheckOut, null,
		// CHECKOut_ISPARENT + "=0 AND " + CHECKOut_ProductSKU + "='"
		// + productSKU + "' AND " +
		// CheckOut_Instruction + "='" + inst + "'",
		// null, null, null, CheckOut_ProductName, null);

		Cursor cursor;
		if (inst == null) {
			cursor = mDataBase.query(true, TB_CheckOut, null, CHECKOUT_PTYPE
					+ "=0 AND " + CheckOut_ProductId + "='" + productId + "'",
					null, null, null, CheckOut_ProductName, null);
		} else {
			cursor = mDataBase.query(true, TB_CheckOut, null, CHECKOUT_PTYPE
					+ "=0 AND " + CheckOut_ProductId + "='" + productId
					+ "' AND " + CheckOut_Instruction + "='" + inst + "'",
					null, null, null, CheckOut_ProductName, null);
		}
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					cartIds = cursor.getString(cursor
							.getColumnIndex(CheckOut_Id));
				} while (cursor.moveToNext());
			}
		}
		return cartIds;
	}

	/**
	 * Get all cartId's of that product
	 */
	public ArrayList<String> getAllCartIds(String productId, int caseValue) {
		ArrayList<String> cartIds = new ArrayList<String>();
		Cursor cursor = null;
		String column = "";
		if (caseValue == 1) {
			cursor = mDataBase.query(true, TB_CheckOut, null, CHECKOUT_PTYPE
					+ "!=3 AND " + CHECKOUT_PTYPE + "!=4" + "  AND "
					+ CheckOut_ProductId + "='" + productId + "'", null, null,
					null, CheckOut_ProductName, null);
			column = CheckOut_Id;
		} else if (caseValue == 2) {
			cursor = mDataBase.query(true, TB_Fav, null, FAV_PTYPE + "!=3 AND "
					+ FAV_PTYPE + "!=4" + " AND " + FAV_ProductId + "='"
					+ productId + "'", null, null, null, FAV_ProductName, null);
			column = FAV_Id;
		}
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					cartIds.add(cursor.getString(cursor.getColumnIndex(column)));
				} while (cursor.moveToNext());
			}
		}
		return cartIds;

	}

	/**
	 * get all attributes of that productCartId
	 */
	public ArrayList<String> getAllAttributesNameByCartId(String cartId,
			int state) {
		String nameStr = "";
		ArrayList<String> nameList = new ArrayList<String>();
		Cursor cursor = null;
		if (state == 1) {
			cursor = mDataBase.query(true, TB_CheckOut, null, CHECKOut_ISPARENT
					+ "=" + cartId, null, null, null, CheckOut_ProductName,
					null);
		} else if (state == 2) {
			cursor = mDataBase.query(true, TB_Fav, null, FAV_ISPARENT + "="
					+ cartId, null, null, null, FAV_ProductName, null);
		}
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					// nameStr += cursor.getString(cursor
					// .getColumnIndex(CheckOut_ProductName)) + ", ";

					nameStr = cursor.getString(cursor
							.getColumnIndex(CheckOut_ProductId));

					nameList.add(nameStr);
				} while (cursor.moveToNext());
			}
		}

		return nameList;
	}

	public int getQuantityByCartId(String cartId) {
		int quantity = 0;
		Cursor cursor = mDataBase.query(true, TB_CheckOut, null, CheckOut_Id
				+ "=" + cartId, null, null, null, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					quantity = cursor.getInt(cursor
							.getColumnIndex(CheckOut_ProductQuantity));
				} while (cursor.moveToNext());

			}
			cursor.close();
		}
		return quantity;
	}

	public int updateCartAttributeItem(ContentValues values, String id) {
		return mDataBase.update(TB_CheckOut, values, CheckOut_Id + "=" + id,
				null);
	}

	public void updateAttributeQuantity(int qunatity, String id) {
		// ContentValues values = new ContentValues();
		// values.put(CheckOut_ProductQuantity, qunatity);
		// values.put(CheckOut_TotalAmount, (CheckOut_PricePerUnit*qunatity))
		// mDataBase.update(TB_CheckOut, values, CHECKOut_ISPARENT + "=" + id,
		// null);
		String query = "update " + TB_CheckOut + " set "
				+ CheckOut_ProductQuantity + "=" + qunatity + ","
				+ CheckOut_TotalAmount + " = " + CheckOut_PricePerUnit + " * "
				+ qunatity + ", " + CHECKOut_SERVICETAXAMT + "="
				+ CHECKOut_SERVICETAXAMTUNIT + "*" + qunatity + ", "
				+ CHECKOut_SALESTAXAMT + "=" + CHECKOut_SALESTAXAMTUNIT + "*"
				+ qunatity + " where " + CHECKOut_ISPARENT + "=" + id;

		mDataBase.execSQL(query);
	}

	public void addOnsFromOrderHistory(
			ArrayList<RestaurantAddOnMetadata> addonsList, long id) {
		try {
			mDataBase.beginTransaction();

			if (addonsList != null) {
				BigDecimal pricePerUnit = null, salesTaxPerUnit, serviceTaxUnit;
				int quant = 0;
				ContentValues values = new ContentValues();
				for (int i = 0; i < addonsList.size(); i++) {
					RestaurantAddOnMetadata data = addonsList.get(i);
					if (data != null) {

						try {
							quant = Integer.parseInt(data.getInStock());
						} catch (Exception e) {

							e.printStackTrace();
						}
						try {
							pricePerUnit = new BigDecimal(data.getpPrice());
						} catch (Exception e) {
							pricePerUnit = new BigDecimal(0);
						}
						try {

							salesTaxPerUnit = new BigDecimal(
									data.getSalesTaxAmt());
						} catch (Exception e) {
							salesTaxPerUnit = new BigDecimal(0);
							e.printStackTrace();
						}
						try {

							serviceTaxUnit = new BigDecimal(
									data.getServiceTaxAmt());
						} catch (Exception e) {
							serviceTaxUnit = new BigDecimal(0);
							e.printStackTrace();
						}
						values.put(DatabaseHelper.CheckOut_ProductId,
								data.getId());
						values.put(DatabaseHelper.CheckOut_ProductName,
								data.getpName());
						values.put(DatabaseHelper.CheckOut_ProductDescription,
								"");
						values.put(DatabaseHelper.CheckOut_Instruction, "");
						values.put(DatabaseHelper.CheckOut_PricePerUnit,
								data.getpPrice());
						values.put(DatabaseHelper.CheckOut_ProductQuantity,
								data.getInStock());

						// values.put(
						// DatabaseHelper.CheckOut_Currencytype,
						// "USD");
						values.put(DatabaseHelper.CheckOut_TotalAmount,
								pricePerUnit.multiply(new BigDecimal(quant))
										.toString());
						values.put(DatabaseHelper.CHECKOut_ProductSKU, "");
						values.put(DatabaseHelper.CHECKOut_ISPARENT, id);
						values.put(DatabaseHelper.CHECKOUT_PTYPE, 3);
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMT,
								salesTaxPerUnit.multiply(new BigDecimal(quant))
										.toString());
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMT,
								serviceTaxUnit.multiply(new BigDecimal(quant))
										.toString());
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
								data.getSalesTaxAmt());
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
								data.getServiceTaxAmt());
						mDataBase.insert(TB_CheckOut, null, values);
					}
					values.clear();
				}
			}
			mDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			mDataBase.endTransaction();
		}

	}

	public void addOnsFromCart(ArrayList<FavoriteProductMetadata> addonsList,
			long id) {
		try {
			mDataBase.beginTransaction();

			if (addonsList != null) {
				ContentValues values = new ContentValues();
				for (int i = 0; i < addonsList.size(); i++) {
					FavoriteProductMetadata data = addonsList.get(i);
					if (data != null) {

						values.put(DatabaseHelper.CheckOut_ProductId,
								data.getProductId());
						values.put(DatabaseHelper.CheckOut_ProductName,
								data.getProductName());
						values.put(DatabaseHelper.CheckOut_ProductDescription,
								data.getProductDesc());
						values.put(DatabaseHelper.CheckOut_Instruction,
								data.getSpecialInstruction());
						values.put(DatabaseHelper.CheckOut_PricePerUnit,
								data.getPricePerUnit());
						values.put(DatabaseHelper.CheckOut_ProductQuantity,
								data.getQuantity());

						// values.put(
						// DatabaseHelper.CheckOut_Currencytype,
						// "USD");
						values.put(DatabaseHelper.CheckOut_TotalAmount,
								data.getTotalAmount());
						values.put(DatabaseHelper.CHECKOut_ProductSKU,
								data.getProductSKU());
						values.put(DatabaseHelper.CHECKOut_ISPARENT, id);
						values.put(DatabaseHelper.CHECKOUT_PTYPE,
								data.getpType());
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMT,
								data.getSaleTaxUnit());
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMT,
								data.getServiceTaxUnit());
						values.put(DatabaseHelper.CHECKOut_SALESTAXAMTUNIT,
								data.getSaleTaxUnit());
						values.put(DatabaseHelper.CHECKOut_SERVICETAXAMTUNIT,
								data.getServiceTaxUnit());
						values.put(DatabaseHelper.CHECKOut_GroupId,
								data.getmGroupId());
						mDataBase.insert(TB_CheckOut, null, values);
					}
					values.clear();
				}
			}
			mDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			mDataBase.endTransaction();
		}

	}

	public void addBundleAddons(ArrayList<RestaurantBundleMetadata> bundleList,
			long id, int attribute, int tableName) {
		try {
			mDataBase.beginTransaction();
			if (bundleList != null) {
				for (int l = 0; l < bundleList.size(); l++) {
					RestaurantBundleMetadata bundleMetadata = bundleList.get(l);
					if (bundleMetadata != null) {
						ArrayList<RestaurantAddOnsMetadata> addonsList = bundleMetadata
								.getBundleAddOnsList();

						if (addonsList != null) {
							ContentValues values = new ContentValues();
							for (int i = 0; i < addonsList.size(); i++) {
								RestaurantAddOnsMetadata data = addonsList
										.get(i);
								// System.out.println("groupId "+data.getmGroupId());
								// System.out.println("addOnSNaName "+data.getaName());
								// System.out.println("addOnsId "+data.getId());
								if (data != null) {
									if (data.isSelected()) {
										if (tableName == 1) {
											values.put(CheckOut_ProductId,
													data.getId());
											values.put(CheckOut_ProductName,
													data.getaName());
											values.put(
													CheckOut_ProductDescription,
													"");
											values.put(CheckOut_Instruction, "");
											values.put(CheckOut_PricePerUnit,
													data.getaPrice());
											values.put(
													CheckOut_ProductQuantity, 1);

											// values.put(
											// DatabaseHelper.CheckOut_Currencytype,
											// "USD");
											values.put(CheckOut_TotalAmount,
													data.getaPrice());
											values.put(CHECKOut_ProductSKU, "");
											values.put(CHECKOut_ISPARENT, id);
											values.put(CHECKOUT_PTYPE,
													attribute);
											values.put(CHECKOut_SALESTAXAMT,
													data.getSalesTaxAmt());
											values.put(CHECKOut_SERVICETAXAMT,
													data.getServiceTaxAmt());
											values.put(
													CHECKOut_SALESTAXAMTUNIT,
													data.getSalesTaxAmt());
											values.put(
													CHECKOut_SERVICETAXAMTUNIT,
													data.getServiceTaxAmt());
											values.put(CHECKOut_GroupId,
													data.getmGroupId());
											mDataBase.insert(TB_CheckOut, null,
													values);
										} else if (tableName == 2) {
											values.put(FAV_ProductId,
													data.getId());
											values.put(FAV_ProductName,
													data.getaName());
											values.put(FAV_ProductDescription,
													"");
											values.put(FAV_Instruction, "");
											values.put(FAV_ProductQuantity, 1);
											values.put(FAV_PricePerUnit,
													data.getaPrice());
											values.put(FAV_ProductSKU, "");
											values.put(FAV_ISPARENT, id);
											values.put(FAV_PTYPE, attribute);
											values.put(FAV_TotalAmount,
													data.getaPrice());
											// values.put(DatabaseHelper.FAV_PTYPE,
											// 1);
											values.put(FAV_SALESTAXAMT,
													data.getSalesTaxAmt());
											values.put(FAV_SERVICETAXAMT,
													data.getServiceTaxAmt());
											values.put(FAV_SALESTAXAMTUNIT,
													data.getSalesTaxAmt());
											values.put(FAV_SERVICETAXAMTUNIT,
													data.getServiceTaxAmt());
											values.put(FAV_GroupId,
													data.getmGroupId());
											mDataBase.insert(TB_Fav, null,
													values);
										}
									}
									values.clear();
								}
								// }
							}
						}

					}
				}
			}
			mDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			mDataBase.endTransaction();
		}
	}

	public void addAddons(ArrayList<RestaurantAddOnMetadata> addonsList,
			long id, int attribute, int tableName) {
		// System.out.println("attribute "+attribute);
		try {
			mDataBase.beginTransaction();

			if (addonsList != null) {
				ContentValues values = new ContentValues();
				for (int i = 0; i < addonsList.size(); i++) {
					RestaurantAddOnMetadata data = addonsList.get(i);
					if (data != null) {
						if (data.isSelected()) {
							if (tableName == 1) {
								values.put(CheckOut_ProductId, data.getId());
								values.put(CheckOut_ProductName,
										data.getpName());
								values.put(CheckOut_ProductDescription, "");
								values.put(CheckOut_Instruction, "");
								values.put(CheckOut_PricePerUnit,
										data.getpPrice());
								values.put(CheckOut_ProductQuantity, 1);

								// values.put(
								// DatabaseHelper.CheckOut_Currencytype,
								// "USD");
								values.put(CheckOut_TotalAmount,
										data.getpPrice());
								values.put(CHECKOut_ProductSKU, "");
								values.put(CHECKOut_ISPARENT, id);
								values.put(CHECKOUT_PTYPE, attribute);
								values.put(CHECKOut_SALESTAXAMT,
										data.getSalesTaxAmt());
								values.put(CHECKOut_SERVICETAXAMT,
										data.getServiceTaxAmt());
								values.put(CHECKOut_SALESTAXAMTUNIT,
										data.getSalesTaxAmt());
								values.put(CHECKOut_SERVICETAXAMTUNIT,
										data.getServiceTaxAmt());
								mDataBase.insert(TB_CheckOut, null, values);
							} else if (tableName == 2) {
								values.put(FAV_ProductId, data.getId());
								values.put(FAV_ProductName, data.getpName());
								values.put(FAV_ProductDescription, "");
								values.put(FAV_Instruction, "");
								values.put(FAV_ProductQuantity, 1);
								values.put(FAV_PricePerUnit, data.getpPrice());
								values.put(FAV_ProductSKU, "");
								values.put(FAV_ISPARENT, id);
								values.put(FAV_PTYPE, attribute);
								values.put(FAV_TotalAmount, data.getpPrice());
								// values.put(DatabaseHelper.FAV_PTYPE, 1);
								values.put(FAV_SALESTAXAMT,
										data.getSalesTaxAmt());
								values.put(FAV_SERVICETAXAMT,
										data.getServiceTaxAmt());
								values.put(FAV_SALESTAXAMTUNIT,
										data.getSalesTaxAmt());
								values.put(FAV_SERVICETAXAMTUNIT,
										data.getServiceTaxAmt());
								mDataBase.insert(TB_Fav, null, values);
							}
						}
						values.clear();
					}
					// }
				}
			}
			mDataBase.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			mDataBase.endTransaction();
		}
	}

	public Cursor getAllCartItems() {
		return mDataBase.query(true, TB_CheckOut, null, CHECKOUT_PTYPE
				+ "!=3 AND " + CHECKOUT_PTYPE + "!=4", null, null, null, null,
				null);
	}

	public Cursor getCartAttributes(String id, int caseValue) {
		if (caseValue == 1) {
			return mDataBase.query(true, DatabaseHelper.TB_CheckOut, null,
					DatabaseHelper.CHECKOut_ISPARENT + "=" + id, null, null,
					null, null, null);
		} else if (caseValue == 2) {
			return mDataBase.query(true, DatabaseHelper.TB_Fav, null,
					FAV_ISPARENT + "=" + id, null, null, null, null, null);
		}
		return null;
	}

	public void updateCartQuantity(String checkoutId, String quantity) {
		// mDataBase.update(TB_CheckOut, values,
		// CheckOut_Id + "=" + checkoutId
		// + " AND " + CHECKOut_ISPARENT+ "="
		// + checkoutId, null);

		String query = "update " + TB_CheckOut + " set "
				+ CheckOut_ProductQuantity + "=" + quantity + ","
				+ CheckOut_TotalAmount + " = " + CheckOut_PricePerUnit + " * "
				+ quantity + " , " + CHECKOut_SALESTAXAMT + "="
				+ CHECKOut_SALESTAXAMTUNIT + " * " + quantity + " , "
				+ CHECKOut_SERVICETAXAMT + "=" + CHECKOut_SERVICETAXAMTUNIT
				+ " * " + quantity + " where " + CHECKOut_ISPARENT + "="
				+ checkoutId + " OR " + CheckOut_Id + "=" + checkoutId;
		// System.out.println("query "+query);
		mDataBase.execSQL(query);
	}

	public int deleteCartItem(String cartId) {
		return mDataBase.delete(TB_CheckOut, CheckOut_Id + "=" + cartId
				+ " OR " + CHECKOut_ISPARENT + "=" + cartId, null);
	}

	public int getFavCopy(String productId, String inst) {
		int count = 0;
		// Cursor cursor = mDataBase.query(TB_Fav, null, FAV_ProductSKU + "='"
		// + productSKU + "' AND " + FAV_SpecialIns + "='" + inst + "'",
		// null, null, null, null);
		Cursor cursor;
		if (inst == null) {
			cursor = mDataBase.query(TB_Fav, null, FAV_ProductId + "='"
					+ productId + "'", null, null, null, null);
		} else {
			cursor = mDataBase.query(TB_Fav, null, FAV_ProductId + "='"
					+ productId + "' AND " + FAV_Instruction + "='" + inst
					+ "'", null, null, null, null);
		}
		if (cursor != null) {
			count = cursor.getCount();
			cursor.close();
		}
		return count;
	}

	public long insertFav(ContentValues values) {
		return mDataBase.insert(TB_Fav, null, values);
	}
	
	public Cursor getAllFavourites() {
		// return mDataBase.query(TB_Fav, null, null, null, null, null,
		// Fav_product_Name);

		return mDataBase.query(true, TB_Fav, null, FAV_PTYPE + "!=3 AND "
				+ FAV_PTYPE + "!=4", null, null, null, null, null);
	}

	public int deleteFav(String id) {
		return mDataBase.delete(TB_Fav, FAV_Id + "=" + id + " OR "
				+ FAV_ISPARENT + "=" + id, null);
	}
	public int deleteFav1(int id) {
		return mDataBase.delete(TB_Fav, FAV_ProductId + "=" + FAV_ProductId , null);
	}
	public Cursor getDeliveryUsingCity(String cityId) {

		return mDataBase.query(true, TBL_LOCALITY, null, LOCALITY_CITYID + "='"
				+ cityId + "'", null, null, null, null, null);
	}

	public Cursor getDeliverableCities(String stateId) {
		return mDataBase.query(true, TBL_LOCALITY, null, LOCALITY_STATECODE
				+ "='" + stateId + "'", null, null, null, null, null);
	}
	// public int getFavAttributCopy(String productId, String description) {
	// int count = 0;
	// Cursor cursor = mDataBase.query(TB_Fav, null, FAV_ProductID + "=? AND "
	// + FAV_ProductDescription + "=?", new String[] { productId,
	// description }, null, null, null);
	// if (cursor != null) {
	// count = cursor.getCount();
	// cursor.close();
	// }
	// return count;
	// }

	// public int getFavAttributId(String productId, String description) {
	// int count = 0;
	// Cursor cursor = mDataBase.query(TB_Fav, null, FAV_ProductID + "=? AND "
	// + FAV_ProductDescription + "=?", new String[] { productId,
	// description }, null, null, null);
	// if (cursor != null) {
	// if (cursor.moveToFirst()) {
	// do {
	// count = cursor.getInt(cursor.getColumnIndex(FAV_Id));
	// } while (cursor.moveToNext());
	// }
	// cursor.close();
	// }
	// return count;
	// }

	// public int deletCartItemByProductId(String productId) {
	// return mDataBase.delete(TB_CheckOut, CheckOut_ProductId + "="
	// + productId, null);
	// }

	// public Cursor getProductDetails(String productId) {
	// return mDataBase
	// .query(true, TB_CheckOut, null, CheckOut_ProductId + "="
	// + productId, null, null, null, CheckOut_ProductName,
	// null);
	// }

	// public int getProductQuantityAttributeInCart(String productId,
	// String description) {
	// int quantity = 0;
	// Cursor cursor = mDataBase.query(true, TB_CheckOut, null,
	// CheckOut_ProductId + "=?  AND " + CheckOut_ProductDescription
	// + "=?", new String[] { String.valueOf(productId),
	// description }, null, null, CheckOut_ProductName, null);
	//
	// if (cursor != null) {
	// if (cursor.moveToFirst()) {
	// do {
	// quantity = cursor.getInt(cursor
	// .getColumnIndex(CheckOut_ProductQuantity));
	// } while (cursor.moveToNext());
	//
	// }
	// cursor.close();
	// }
	// return quantity;
	// }

	// public int getProductIDAttributeInCart(String productId, String
	// description) {
	// int quantity = 0;
	// Cursor cursor = mDataBase.query(true, TB_CheckOut, null,
	// CheckOut_ProductId + "=?  AND " + CheckOut_ProductDescription
	// + "=?", new String[] { String.valueOf(productId),
	// description }, null, null, CheckOut_ProductName, null);
	//
	// if (cursor != null) {
	// if (cursor.moveToFirst()) {
	// do {
	// quantity = cursor
	// .getInt(cursor.getColumnIndex(CheckOut_Id));
	// } while (cursor.moveToNext());
	//
	// }
	// cursor.close();
	// }
	// return quantity;
	// }

	// public String getProductPriceFromCart(String productId) {
	// String price = null;
	// Cursor cursor = mDataBase.query(true, TB_CheckOut, null,
	// CheckOut_ProductId + "=" + productId, null, null, null,
	// CheckOut_ProductName, null);
	// if (cursor != null) {
	// if (cursor.moveToFirst()) {
	// do {
	// price = cursor.getString(cursor
	// .getColumnIndex(CheckOut_TotalAmount));
	// } while (cursor.moveToNext());
	//
	// }
	// cursor.close();
	// }
	// return price;
	// }

	// public void updateCartItems(ArrayList<CheckOutMetadata> list) {
	// try {
	// mDataBase.beginTransaction();
	// // ContentValues values = new ContentValues();
	// for (int i = 0; i < list.size(); i++) {
	// //
	// // values.put(CheckOut_ProductQuantity,
	// // list.get(i).getQuantity());
	// // values.put(CheckOut_TotalAmount,
	// // list.get(i).getTotalAmount());
	// // mDataBase.update(TB_CheckOut, values,
	// // CheckOut_Id + "=" + list.get(i).getCheckOutId()
	// // + " AND " + CheckOut_ProductId + "="
	// // + list.get(i).getProductId(), null);
	// // values.clear();
	// String query = "update " + TB_CheckOut + " set "
	// + CheckOut_ProductQuantity + "="
	// + list.get(i).getQuantity() + ","
	// + CheckOut_TotalAmount + " = " + CheckOut_PricePerUnit
	// + " * " + list.get(i).getQuantity() + " where "
	// + CHECKOut_ISPARENT + "=" + list.get(i).getCheckOutId()
	// + " OR " + CheckOut_Id + "="
	// + list.get(i).getCheckOutId();
	// mDataBase.execSQL(query);
	//
	// }
	// mDataBase.setTransactionSuccessful();
	// } catch (Exception e) {
	//
	// } finally {
	// mDataBase.endTransaction();
	// }
	// }

	// public void addOptions2(ArrayList<ProductAttributesMetadata> optionsList,
	// long id, int attribute, int tableName, int quantity) {
	// try {
	// mDataBase.beginTransaction();
	//
	// if (optionsList != null) {
	// ContentValues values = new ContentValues();
	// for (int i = 0; i < optionsList.size(); i++) {
	// ArrayList<ProductOptionsMetadata> options = optionsList
	// .get(i).getpOptionsList();
	//
	// if (options != null) {
	// for (int j = 0; j < options.size(); j++) {
	// ProductOptionsMetadata productDetailData = options
	// .get(j);
	//
	// if (tableName == 1) {
	// values.put(DatabaseHelper.CheckOut_ProductId,
	// "");
	// values.put(DatabaseHelper.CheckOut_ProductName,
	// productDetailData.getpLabel());
	// values.put(
	// DatabaseHelper.CheckOut_ProductDescription,
	// "");
	// values.put(
	// DatabaseHelper.CheckOut_PricePerUnit,
	// productDetailData.getpPrice());
	//
	// values.put(
	// DatabaseHelper.CheckOut_ProductQuantity,
	// quantity);
	// values.put(DatabaseHelper.CheckOut_Instruction,
	// "");
	// values.put(
	// DatabaseHelper.CheckOut_Currencytype,
	// "USD");
	// values.put(DatabaseHelper.CheckOut_TotalAmount,
	// productDetailData.getpPrice());
	// values.put(DatabaseHelper.CHECKOut_ProductSKU,
	// productDetailData.getpSKU());
	// values.put(DatabaseHelper.CHECKOut_ISPARENT, id);
	// values.put(DatabaseHelper.CHECKOut_ISATTRIBUTE,
	// attribute);
	// mDataBase.insert(TB_CheckOut, null, values);
	// } else if (tableName == 2) {
	// values.put(DatabaseHelper.FAV_ProductID, "");
	//
	// values.put(DatabaseHelper.FAV_ProductName,
	// productDetailData.getpLabel());
	// values.put(
	// DatabaseHelper.FAV_ProductDescription,
	// "");
	// values.put(DatabaseHelper.FAV_ProductPrice,
	// productDetailData.getpPrice());
	// values.put(DatabaseHelper.FAV_Quantity,
	// String.valueOf(1));
	// values.put(DatabaseHelper.FAV_SpecialIns, "");
	//
	// values.put(DatabaseHelper.FAV_Currency, "USD");
	//
	// values.put(DatabaseHelper.FAV_TotalAmount,
	// productDetailData.getpPrice());
	// values.put(DatabaseHelper.FAV_ProductSKU,
	// productDetailData.getpSKU());
	// values.put(DatabaseHelper.CHECKOut_ISPARENT, id);
	// values.put(DatabaseHelper.FAV_ISATTRIBUTE,
	// attribute);
	// mDataBase.insert(TB_Fav, null, values);
	//
	// }
	// values.clear();
	// }
	// }
	// }
	// }
	// mDataBase.setTransactionSuccessful();
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// } finally {
	// mDataBase.endTransaction();
	// }
	// }

	// public void addCrosselAndUpsellItemsInCart(
	// ArrayList<CrosssellAndUpsellMetadata> list, long id, int attribute,
	// int tableName) {
	//
	// try {
	// mDataBase.beginTransaction();
	// ContentValues values = new ContentValues();
	// for (int i = 0; i < list.size(); i++) {
	// CrosssellAndUpsellMetadata productDetailData = list.get(i);
	// if (productDetailData.isSelected()) {
	// if (tableName == 1) {
	// values.put(DatabaseHelper.CheckOut_ProductId, "");
	// values.put(DatabaseHelper.CheckOut_ProductName,
	// productDetailData.getName());
	// values.put(DatabaseHelper.CheckOut_ProductDescription,
	// productDetailData.getProductDesc());
	// values.put(DatabaseHelper.CheckOut_PricePerUnit,
	// productDetailData.getProductPrice());
	// values.put(DatabaseHelper.CheckOut_ProductQuantity, 1);
	// values.put(DatabaseHelper.CheckOut_Instruction, "");
	// values.put(DatabaseHelper.CheckOut_Currencytype,
	// productDetailData.getCurrency());
	// values.put(DatabaseHelper.CheckOut_TotalAmount,
	// productDetailData.getProductPrice());
	// values.put(DatabaseHelper.CHECKOut_ProductSKU,
	// productDetailData.getProductSKU());
	// values.put(DatabaseHelper.CHECKOut_ISPARENT, id);
	// values.put(DatabaseHelper.CHECKOut_ISATTRIBUTE,
	// attribute);
	// mDataBase.insert(TB_CheckOut, null, values);
	// } else if (tableName == 2) {
	// values.put(DatabaseHelper.FAV_ProductID, "");
	//
	// values.put(DatabaseHelper.FAV_ProductName,
	// productDetailData.getName());
	// values.put(DatabaseHelper.FAV_ProductDescription,
	// productDetailData.getProductDesc());
	// values.put(DatabaseHelper.FAV_ProductPrice,
	// productDetailData.getProductPrice());
	// values.put(DatabaseHelper.FAV_Quantity,
	// String.valueOf(1));
	// values.put(DatabaseHelper.FAV_SpecialIns, "");
	//
	// values.put(DatabaseHelper.FAV_Currency,
	// productDetailData.getCurrency());
	//
	// values.put(DatabaseHelper.FAV_TotalAmount,
	// productDetailData.getProductPrice());
	// values.put(DatabaseHelper.FAV_ProductSKU,
	// productDetailData.getProductSKU());
	// values.put(DatabaseHelper.CHECKOut_ISPARENT, id);
	// values.put(DatabaseHelper.FAV_ISATTRIBUTE, attribute);
	// mDataBase.insert(TB_Fav, null, values);
	// }
	// }
	// values.clear();
	// }
	// mDataBase.setTransactionSuccessful();
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// } finally {
	// mDataBase.endTransaction();
	// }
	// }

	// public void addAttributes(ArrayList<AttributesMeatadata> attributesList,
	// long id, int tableName) {
	//
	// if (attributesList != null) {
	// try {
	// mDataBase.beginTransaction();
	// ContentValues values = new ContentValues();
	//
	// for (int i = 0; i < attributesList.size(); i++) {
	// ArrayList<AttributeOptions> optionsList = attributesList
	// .get(i).getAttributeDetails().getOptionsList();
	// if (optionsList != null) {
	// for (int j = 0; j < optionsList.size(); j++) {
	// AttributeOptions options = optionsList.get(j);
	// if (optionsList.get(j).isSelected()) {
	// if (tableName == 1) {
	// values.put(
	// DatabaseHelper.CheckOut_ProductId,
	// options.getValue());
	// values.put(
	// DatabaseHelper.CheckOut_ProductName,
	// options.getLabel());
	// values.put(
	// DatabaseHelper.CheckOut_ProductDescription,
	// "");
	// values.put(
	// DatabaseHelper.CheckOut_PricePerUnit,
	// 0.0);
	// values.put(
	// DatabaseHelper.CheckOut_ProductQuantity,
	// 1);
	// values.put(
	// DatabaseHelper.CheckOut_Instruction,
	// "");
	// values.put(
	// DatabaseHelper.CheckOut_Currencytype,
	// "USD");
	// values.put(
	// DatabaseHelper.CheckOut_TotalAmount,
	// 0.0);
	// values.put(
	// DatabaseHelper.CHECKOut_ProductSKU,
	// "");
	// values.put(
	// DatabaseHelper.CHECKOut_ISPARENT,
	// id);
	// values.put(
	// DatabaseHelper.CHECKOut_ISATTRIBUTE,
	// 2);
	// mDataBase.insert(TB_CheckOut, null, values);
	//
	// } else if (tableName == 2) {
	// values.put(DatabaseHelper.FAV_ProductID,
	// options.getValue());
	//
	// values.put(DatabaseHelper.FAV_ProductName,
	// options.getLabel());
	// values.put(
	// DatabaseHelper.FAV_ProductDescription,
	// "");
	// values.put(DatabaseHelper.FAV_ProductPrice,
	// 0.0);
	// values.put(DatabaseHelper.FAV_Quantity,
	// String.valueOf(1));
	// values.put(DatabaseHelper.FAV_SpecialIns,
	// "");
	//
	// values.put(DatabaseHelper.FAV_Currency,
	// "USD");
	//
	// values.put(DatabaseHelper.FAV_TotalAmount,
	// 0.0);
	// values.put(DatabaseHelper.FAV_ProductSKU,
	// "");
	// values.put(
	// DatabaseHelper.CHECKOut_ISPARENT,
	// id);
	// values.put(DatabaseHelper.FAV_ISATTRIBUTE,
	// 2);
	// mDataBase.insert(TB_Fav, null, values);
	// }
	// }
	// values.clear();
	// }
	// }
	// }
	//
	// mDataBase.setTransactionSuccessful();
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// } finally {
	// mDataBase.endTransaction();
	// }
	//
	// }
	// }

	/**
 * 
 */

	/**
	 * 
	 */
	/**
	 * get all attributes of that productCartId
	 */
	// public String getAllAttributesByCartId(String cartId) {
	// String nameStr = "";
	// // ArrayList<String> nameList = new ArrayList<String>();
	// Cursor cursor = mDataBase.query(true, TB_CheckOut, null,
	// CHECKOut_ISPARENT + "=" + cartId, null, null, null,
	// CheckOut_ProductName, null);
	// if (cursor != null) {
	// if (cursor.moveToFirst()) {
	// do {
	// nameStr += cursor.getString(cursor
	// .getColumnIndex(CheckOut_ProductName)) + ", ";
	//
	// } while (cursor.moveToNext());
	// }
	// }
	// if (nameStr != null && !nameStr.equals("") && nameStr.length() > 0) {
	// nameStr = nameStr.trim().substring(0, nameStr.length() - 2);
	// }
	// return nameStr;
	// }

	/**
	 * Get all favId's of that product
	 */
	// public ArrayList<String> getAllFavIds(String productSKU) {
	// ArrayList<String> cartIds = new ArrayList<String>();
	// Cursor cursor = mDataBase.query(true, TB_Fav, null, CHECKOut_ISPARENT
	// + "=0 AND " + CHECKOut_ProductSKU + "='" + productSKU
	// + "' AND " + CHECKOut_ISATTRIBUTE + "=1", null, null, null,
	// null, null);
	// if (cursor != null) {
	// if (cursor.moveToFirst()) {
	// do {
	// cartIds.add(cursor.getString(cursor.getColumnIndex(FAV_Id)));
	// } while (cursor.moveToNext());
	// }
	// }
	// return cartIds;
	//
	// }

	/**
 * 
 * 
 */
	// public ArrayList<String> getAllAttributesNameByFavId(String favId) {
	// String nameStr = "";
	// ArrayList<String> nameList = new ArrayList<String>();
	// Cursor cursor = mDataBase.query(true, TB_Fav, null, CHECKOut_ISPARENT
	// + "=" + favId, null, null, null, CheckOut_ProductName, null);
	// if (cursor != null) {
	// if (cursor.moveToFirst()) {
	// do {
	// // nameStr += cursor.getString(cursor
	// // .getColumnIndex(FAV_ProductName)) + ", ";
	// nameStr = cursor.getString(cursor
	// .getColumnIndex(FAV_ProductSKU));
	// nameList.add(nameStr);
	// } while (cursor.moveToNext());
	// }
	// }
	// // if (nameStr != null && !nameStr.equals("") && nameStr.length() > 0) {
	// // nameStr = nameStr.trim().substring(0, nameStr.length() - 2);
	// // }
	// return nameList;
	// }

	// public Cursor getAllFavAttributes(String favId) {
	// return mDataBase.query(true, DatabaseHelper.TB_Fav, null,
	// DatabaseHelper.FAV_ISPARENT + "=" + favId, null, null, null,
	// null, null);
	// }

	// public void insertInCartFromFav(ArrayList<FavoriteProductMetadata> list)
	// {
	// try {
	// mDataBase.beginTransaction();
	// ContentValues values = new ContentValues();
	// long insertedId = 0;
	// for (int i = 0; i < list.size(); i++) {
	//
	// FavoriteProductMetadata productDetailData = list.get(i);
	//
	// values.put(CheckOut_ProductId, productDetailData.getProductId());
	// values.put(CheckOut_ProductName,
	// productDetailData.getProductName());
	// values.put(CheckOut_ProductDescription,
	// productDetailData.getProductDesc());
	// values.put(CheckOut_PricePerUnit,
	// productDetailData.getProductPrice());
	// values.put(CheckOut_ProductQuantity,
	// productDetailData.getQuantity());
	// values.put(CheckOut_Instruction,
	// productDetailData.getSpecialInstruction());
	// values.put(CheckOut_Currencytype,
	// productDetailData.getCurrency());
	//
	// values.put(CheckOut_TotalAmount,
	// productDetailData.getProductPrice());
	// values.put(CHECKOut_ProductSKU,
	// productDetailData.getProductSKU());
	// values.put(CHECKOut_ISATTRIBUTE,
	// productDetailData.getIsAttribute());
	// if (i != 0) {
	//
	// values.put(CHECKOut_ISPARENT, insertedId);
	// mDataBase.insert(TB_CheckOut, null, values);
	// } else {
	//
	// values.put(CHECKOut_ISPARENT, 0);
	// insertedId = mDataBase.insert(TB_CheckOut, null, values);
	// }
	//
	// values.clear();
	// }
	// mDataBase.setTransactionSuccessful();
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// mDataBase.endTransaction();
	// }
	// }

}
