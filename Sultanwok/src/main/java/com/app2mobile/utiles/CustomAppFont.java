package com.app2mobile.utiles;

import com.app2mobile.Sultanwok.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomAppFont extends TextView {

	// public CustomAppFont(Context context, AttributeSet attrs, int
	// defStyleAttr,
	// int defStyleRes) {
	// super(context, attrs, defStyleAttr, defStyleRes);
	// // TODO Auto-generated constructor stub
	// }

	public CustomAppFont(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(attrs);
	}

	public CustomAppFont(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(attrs);
	}

	public CustomAppFont(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(null);
	}

	private void init(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs,
					R.styleable.CustomTextView);
			String fontName = a.getString(R.styleable.CustomTextView_fontname);
			if (fontName != null) {
				Typeface myTypeface = Typeface.createFromAsset(getContext()
						.getAssets(), "fonts/" + fontName);
				setTypeface(myTypeface);
			}
//			String textStyle=a.getString(R.styleable.CustomTextView_textStyle);
			
			a.recycle();
		}
	}

}
