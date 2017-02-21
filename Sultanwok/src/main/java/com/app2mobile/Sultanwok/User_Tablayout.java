package com.app2mobile.Sultanwok;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class User_Tablayout extends TabLayout {
	  public User_Tablayout(Context context) {
	        super(context);
	        
	    }
	 
	    public User_Tablayout(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	 
	    public User_Tablayout(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }

	    
	    public void createTabs() {
	        addTab( R.string.profile_detail);
	        addTab(R.string.profile_addres);
	        addTab(R.string.profile_card);
	    }
	 
	    private void addTab( @StringRes int contentDescriptionId) {
	        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_icon, null);
	        TextView txt= (TextView)tabView.findViewById(R.id.tab_text);
//	        Typeface tf= Typeface.createFromAsset(null, "Sintony-Regular.ttf");
//	        txt.setTypeface(tf);
	       
	        txt.setText(contentDescriptionId);
	        Tab tab = newTab();
	        tab.setCustomView(tabView).setContentDescription(contentDescriptionId);
	        addTab(tab);
	    }
	    
}