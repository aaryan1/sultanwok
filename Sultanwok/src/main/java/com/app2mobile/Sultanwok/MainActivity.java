package com.app2mobile.Sultanwok;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app2mobile.utiles.ConnectionDetector;
import com.app2mobile.utiles.ConstantsUrl;
import com.app2mobile.utiles.JsonParser;

public class MainActivity extends Activity {
	private SharedPreferences appPrefs;
	private SharedPreferences.Editor prefs_editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash);
		//new GetRestaurantDetails().execute(new String[]{"store_id"},new String[]{ConstantsUrl.STOREID});
		
		new Location().execute();
		
	}
	
	class Location extends AsyncTask<String, Void, String> {
		String result;
		Dialog progressDialog;
		
@Override
protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
//	progressDialog = new Dialog(MainActivity.this);
//	progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	progressDialog.setCancelable(false);
//	progressDialog.setCanceledOnTouchOutside(false);
//	progressDialog.setContentView(R.layout.custom_progress_layout);
//	progressDialog.getWindow().setBackgroundDrawable(
//			new ColorDrawable(android.graphics.Color.TRANSPARENT));
//	progressDialog.show();
}
		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			   try {
				   return JsonParser
							.Webserice_Call_URL(
									ConstantsUrl.Store_location,
									new String[] { "store_bundle_id" },
									new String[] { ConstantsUrl.STORE_BUNDLE_ID});
		         } catch (Exception e) {
		             // save exception and re-thrown it then. 
		             return e.toString();
		         }
			

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.print("aaaaa" + result);
			//Toast.makeText(getApplicationContext(), result, 3).show();
			//progressDialog.dismiss();
			super.onPostExecute(result);
			ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
			Boolean isInternetPresent = cd.isConnectingToInternet();
			if (!isInternetPresent) {
				Toast.makeText(getApplicationContext(), "Internet is not Connected, Try Again", 3).show();
				return;
			}else{
			try {
				
				if(result!=null){
				JSONObject mainJson = new JSONObject(result);
				if (mainJson.has("state")) {
					String state = JsonParser
							.getkeyValue_Str(mainJson, "state");
					System.out.print("aaaaa" + state);

					if (state != null && state.equals("1")) {
						if (mainJson.has("data")) {
							JSONObject dataJson = mainJson
									.getJSONObject("data");
//							 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//						        StrictMode.setThreadPolicy(policy);
							String background_image = dataJson.getString("background_image");

							downloadFile(background_image);
							String location = dataJson.getString("locations");
							System.out.println("aa"+dataJson.getString("locations"));
							JSONArray loca = new JSONArray(location);
							String color_code= JsonParser.getkeyValue_Str(dataJson, "colorbase");
							ConstantsUrl.STORE_COLOR_CODE="#"+color_code;
							Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(background_image).getContent());
							ConstantsUrl.STORE_ID=ConstantsUrl.STORE_BUNDLE_ID;
							String logo_image = dataJson.getString("store_logo");
							
							//Bitmap bitmap1 = BitmapFactory.decodeStream((InputStream)new URL(logo_image).getContent());
							  // logo.setImageBitmap(bitmap1);
						}
							  
						}
				}
				}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			}
	}
			
						public void downloadFile(String uRl) {
							
						    File direct = new File("/mnt/sdcard/App2food");

						    if (!direct.exists()) {
						        direct.mkdirs();
						    }
						    File f = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
							if(f.exists()){
								Log.e("image exits", "image exitss");
								Intent in = new Intent(MainActivity.this,
				        				Manual_Location.class);
				        		//intent.putExtra("restaurant", data);
				        		startActivity(in);
				        		finish();
							}else{
								Log.e("image exits", "image not exitss");
//						    
								
								  MyDownloadManager downloadManager = new MyDownloadManager();
							        downloadManager.Download(getApplicationContext(), uRl);
							}
//							 File f1 = new File("/mnt/sdcard/App2food/"+ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");
//								
//								Bitmap bmp = BitmapFactory.decodeFile(f1.getAbsolutePath());
//								Drawable dr = new BitmapDrawable(bmp);
								
						}
						
						
						public class MyDownloadManager{
							private Context mContext;
							private String url;

							public void Download(Context context, String url){
							    mContext = context;
							    this.url = url;
							    String serviceString = Context.DOWNLOAD_SERVICE;
							    DownloadManager mgr = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

							    Uri downloadUri = Uri.parse(url);
							    DownloadManager.Request request = new DownloadManager.Request(
							            downloadUri);

							    request.setAllowedNetworkTypes(
							            DownloadManager.Request.NETWORK_WIFI
							                    | DownloadManager.Request.NETWORK_MOBILE)
							            .setAllowedOverRoaming(false).setTitle("bgimg")
							           
							            .setDestinationInExternalPublicDir("/App2food", ConstantsUrl.STORE_BUNDLE_ID+"backround_img.jpg");

							    mgr.enqueue(request);
							    RegisterDownloadManagerReciever(context);
							}
							public void RegisterDownloadManagerReciever(Context context) {
							    BroadcastReceiver receiver = new BroadcastReceiver() {
							        @Override
							        public void onReceive(Context context, Intent intent) {
							            String action = intent.getAction();
							            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
							                // Do something on download complete
							            	Intent in = new Intent(MainActivity.this,
							        				Manual_Location.class);
							        		//intent.putExtra("restaurant", data);
							        		startActivity(in);
							        		finish();
							            }
							        }
							    };
							    context.registerReceiver(receiver, new IntentFilter(
							            DownloadManager.ACTION_DOWNLOAD_COMPLETE));
							    }
						}
}

