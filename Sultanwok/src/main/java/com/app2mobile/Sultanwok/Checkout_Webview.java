package com.app2mobile.Sultanwok;

import java.io.UnsupportedEncodingException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Checkout_Webview extends Activity {
	private WebView webview;
	String base64;

	@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
	@SuppressWarnings({ "deprecation", "static-access" })
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebook_webview);
		Intent uri = this.getIntent();
		String data = uri.getStringExtra("data");
		webview = (WebView) findViewById(R.id.fb_webview);
		// fb.setWebViewClient(new WebClient());
		// WebSettings set = fb.getSettings()
		//
		// set.setJavaScriptEnabled(true);
		//
		// set.setBuiltIn0ZoomControls(false);

		webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		webview.getSettings().getCacheMode();
		webview.getSettings().setCacheMode(MODE_MULTI_PROCESS);
		CookieManager.getInstance().setAcceptCookie(true);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.clearCache(true);
		webview.clearHistory();
		webview.setWebChromeClient(new WebChromeClient());
		webview.getSettings().setLoadWithOverviewMode(true);
		CookieManager.getInstance().setAcceptThirdPartyCookies(webview, true);
		webview.getSettings().getLoadWithOverviewMode();	
		webview.setWebViewClient(new MyWebViewClient());
		webview.setWebContentsDebuggingEnabled(true);

		// fb.loadUrl("https://stg.app2food.com/test/ajax_pay_1.html");
		if (((Global) Checkout_Webview.this.getApplicationContext())
				.getCard_token().equals("")) {

			try {
				base64 = android.util.Base64.encodeToString(
						data.getBytes("UTF-8"), android.util.Base64.DEFAULT);
				webview.loadData(base64, "text/html; charset=UTF-8", "base64");

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			try {
				// fb.evaluateJavascript(data, new ValueCallback<String>() {
				//
				// @Override
				// public void onReceiveValue(String value) {
				// // TODO Auto-generated method stub
				// System.out.print("result" +value);
				// }
				// });
				base64 = android.util.Base64.encodeToString(
						data.getBytes("UTF-8"), android.util.Base64.DEFAULT);
				webview.loadData(base64, "text/html; charset=UTF-8", "base64");
				// fb.loadUrl(data);
				// fb.loadDataWithBaseURL(null, TextUtils.htmlEncode(data),
				// "text/html", "utf-8", null);
				Log.e("dataaaa", "" + data);
				// fb.postUrl(ConstantsUrl.CHECKOUT, data.getBytes());

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// fb.postUrl(ConstantsUrl.CHECKOUT,EncodingUtils.getBytes(data,
			// "UTF-8") );

		}

	}

	private class MyWebViewClient extends WebViewClient {

		@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			Log.e("urlllll", url);
			if (url.startsWith(getResources().getString(R.string.scheme1)
					+ "://")) {
			//	Toast.makeText(getApplicationContext(), "iff loop", 2).show();

				try {
					// Uri uri = Uri.parse(url);
					// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					// startActivity(intent);
					finish();
					view.getContext().startActivity(
							new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

				} catch (Exception e) {
					Log.d("JSLogs", "Webview Error:" + e.getMessage());
					
				}
				return true;
			} else {
				//Toast.makeText(getApplicationContext(), "else loop", 2).show();
				Log.e("urllllllll script", url);
				
				view.loadUrl(url);
				
				return true;
			}
			//
			// Intent intent = new
			// Intent(Facebook_Login.this,Second_Webview.class);
			// intent.putExtra("data", url);
			// startActivity(intent);

		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			// You can add some custom functionality here
		//	Toast.makeText(getApplicationContext(), "onpage start", 2).show();
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			// You can add some custom functionality here
		//	Toast.makeText(getApplicationContext(), "onpage finish", 2).show();
			try{
			Log.e("onpagefinish", url);
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onLoadResource(view, url);
		}
		@Override
		public void onFormResubmission(WebView view, Message dontResend,
				Message resend) {
			// TODO Auto-generated method stub
			super.onFormResubmission(view, dontResend, resend);
			Log.e("dontresend", ""+dontResend);
			Log.e("resend", ""+resend);
		}
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			// You can add some custom functionality here
			Log.e("errooooor", failingUrl);
			Log.e("errooooor", "" + errorCode);
			Log.e("eroor", description);

		}
	}
}
