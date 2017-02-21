package com.app2mobile.utiles;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {
	/**
	 * 
	 * @param url
	 *            - url that will be parse.
	 * @param name
	 *            - array of parameters that will append to url.
	 * @param value
	 *            - array of values that will to assigns to paramaters of url.
	 * @return
	 */
	public static String Webserice_Call_URL(String url, String name[],
			String value[]) {

		// initialize
		InputStream is = null;
		String result = null;
		// String url1 = "";
		try {

			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			// InetAddress ia = InetAddress.getByName(url);
			// add reuqest header
			con.setRequestProperty("X-Api-Key", ConstantsUrl.API_KEY);
			con.setRequestProperty("X-Store-Id", ConstantsUrl.STOREID);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			// con.setRequestProperty("User-Agent", USER_AGENT);
			// con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		//	con.setConnectTimeout(70000);
			con.setReadTimeout(50000);
			con.setInstanceFollowRedirects(true);
			String urlParameters = "";// =
									// "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
			if (name != null && value != null) {
				for (int i = 0; i < name.length; i++) {

					if (i != name.length - 1) {
						urlParameters += name[i] + "=" + value[i] + "&";
					} else {
						urlParameters += name[i] + "=" + value[i];
					}
				}
			}
			
			System.out.println("paramertes " + urlParameters);
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST)
				is = con.getErrorStream();
			else
				is = con.getInputStream();

			// if(responseCode==HttpURLConnection.HTTP_OK)
			// {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			// }
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			e.printStackTrace();
		
		}

		return result;
	}
	
	
	public static String Webserice_Call_URL2(String url){

		// initialize
		InputStream is = null;
		String result = null;
		// String url1 = "";
		try {

			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestProperty("X-Api-Key", "4278kjs979dczmxx8v0x");
			con.setRequestProperty("X-Store-Id", "19");
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// con.setRequestProperty("User-Agent", USER_AGENT);
			// con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setConnectTimeout(50000);
			con.setReadTimeout(50000);
			con.setInstanceFollowRedirects(true);
			String urlParameters = "";// =
									// "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
			
			
			System.out.println("paramertes " + urlParameters);
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(url);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST)
				is = con.getErrorStream();
			else
				is = con.getInputStream();

			// if(responseCode==HttpURLConnection.HTTP_OK)
			// {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			// }
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			e.printStackTrace();
		}

		return result;
	}
	
	
	

	public static String Raw_Post(String url){
		HttpClient client= new DefaultHttpClient();
		HttpPost post =new HttpPost(url);
		
		return url;
		
	}
	
	public static String Webserice_Call_URL1(String url, String name[],
			String value[]) {

		// initialize
		InputStream is = null;
		String result = null;
		// String url1 = "";
		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// add reuqest header
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			con.setConnectTimeout(30000);
			con.setReadTimeout(30000);
			con.setInstanceFollowRedirects(true);
			// con.setRequestProperty("User-Agent", USER_AGENT);
			// con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			int responseCode = con.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST)
				is = con.getErrorStream();
			else
				is = con.getInputStream();
			// if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			// }
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * send values in jsonFormat
	 */
	// public static String sendDataInJSONFormat(String url, String json,
	// String name[], String value[]) {
	// // initialize
	// InputStream is = null;
	// String result = null;
	// String url1 = "";
	// try {
	//
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpPost httppost = new HttpPost(url);
	// httppost.setHeader("Content-Type",
	// "charset=UTF-8;text/plain;application/json");
	// httppost.setHeader("Cookie",
	// "PHPSESSID=8718acf2f63bd779d7b2d9415d195394");
	// HttpParams httpParameters = new BasicHttpParams();
	// int timeoutSocket = 25000;
	// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	// if (name != null && value != null) {
	// ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	// for (int i = 0; i < name.length; i++) {
	// nameValuePairs
	// .add(new BasicNameValuePair(name[i], value[i]));
	// url1 = url1.trim() + "&" + name[i] + "=" + value[i];
	// }
	// httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
	// "utf-8"));
	// }
	// StringEntity se = new StringEntity(json.toString(), "UTF-8");
	// httppost.setEntity(se);
	// HttpResponse response = httpclient.execute(httppost);
	// HttpEntity entity = response.getEntity();
	// is = entity.getContent();
	// } catch (Exception e) {
	// Log.e("log_tag", "Error in http connection " + e.toString());
	// e.printStackTrace();
	// }
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// is, "UTF-8"), 8);
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// is.close();
	// result = sb.toString();
	// } catch (Exception e) {
	// Log.e("log_tag", "Error converting result " + e.toString());
	// e.printStackTrace();
	// }
	//
	// return result;
	// }

	// public static String sendDataInJSONFormat(String url, JSONObject json) {
	// // initialize
	// InputStream is = null;
	// String result = null;
	// String url1 = "";
	// try {
	//
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpPost httppost = new HttpPost(url);
	// httppost.setHeader("Content-Type",
	// "charset=UTF-8;text/plain;application/json");
	// httppost.setHeader("Cookie",
	// "PHPSESSID=8718acf2f63bd779d7b2d9415d195394");
	// HttpParams httpParameters = new BasicHttpParams();
	// int timeoutSocket = 25000;
	// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
	// StringEntity se = new StringEntity(json.toString(), "UTF-8");
	// httppost.setEntity(se);
	// HttpResponse response = httpclient.execute(httppost);
	// HttpEntity entity = response.getEntity();
	// is = entity.getContent();
	// } catch (Exception e) {
	// Log.e("log_tag", "Error in http connection " + e.toString());
	// e.printStackTrace();
	// }
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(
	// is, "UTF-8"), 8);
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// is.close();
	// result = sb.toString();
	// } catch (Exception e) {
	// Log.e("log_tag", "Error converting result " + e.toString());
	// e.printStackTrace();
	// }
	//
	// return result;
	// }

	/**
	 * 
	 * @param jo
	 *            - JsonObject that contains key
	 * @param tag
	 *            - name that value want to parse.
	 * @return - String
	 */
	public static String getkeyValue_Str(JSONObject jo, String tag) {
		String key_value = null;
		if (jo.has(tag)) {
			try {
				key_value = jo.getString(tag);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		return key_value;
	}

	public static Double getkeyValue_Double(JSONObject jo, String tag) {
		Double key_value = 0d;
		if (jo.has(tag)) {
			try {
				key_value = jo.getDouble(tag);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

		return key_value;
	}
}
