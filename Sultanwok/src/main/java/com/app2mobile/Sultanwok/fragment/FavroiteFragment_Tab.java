package com.app2mobile.Sultanwok.fragment;

import com.app2mobile.Sultanwok.R;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class FavroiteFragment_Tab extends TabLayout {
	  public FavroiteFragment_Tab(Context context) {
	        super(context);
	    }
	 
	    public FavroiteFragment_Tab(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	 
	    public FavroiteFragment_Tab(Context context, AttributeSet attrs, int defStyleAttr) {
	        super(context, attrs, defStyleAttr);
	    }

	    
	    public void createTabs() {
	        addTab( R.string.fav_product);
	        addTab(R.string.fav_order);
	    }
	 
	    private void addTab( @StringRes int contentDescriptionId) {
	        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_icon, null);
	        TextView txt= (TextView)tabView.findViewById(R.id.tab_text);
	      
	        txt.setText(contentDescriptionId);
	        Tab tab = newTab();
	        tab.setCustomView(tabView).setContentDescription(contentDescriptionId);
	        addTab(tab);
	    }
}
