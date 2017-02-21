package com.app2mobile.Sultanwok;

import java.util.List;

import com.app2mobile.metadata.Single_Address_Metadata;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Address_Adapter extends RecyclerView.Adapter<Address_Adapter.View_Holder>{
	private Context mContext;
	private List<Single_Address_Metadata> list;
	
	public Address_Adapter(Context ctx,List<Single_Address_Metadata> list) {
		// TODO Auto-generated constructor stub
		this.mContext=ctx;
		this.list=list;
	}
	
 public class View_Holder extends RecyclerView.ViewHolder{
TextView address1,city,pincode,state;
RadioButton select_address;
	public View_Holder(View v) {
		super(v);
		// TODO Auto-generated constructor stub
		address1=(TextView)v.findViewById(R.id.street);
		city=(TextView)v.findViewById(R.id.city);
		state=(TextView)v.findViewById(R.id.state);
		pincode=(TextView)v.findViewById(R.id.pin);
		select_address=(RadioButton)v.findViewById(R.id.select_address);
		Typeface tf= Typeface.createFromAsset(v.getContext().getAssets(), "OpenSans-Light.ttf");
		address1.setTypeface(tf);
		city.setTypeface(tf);
		state.setTypeface(tf);
		pincode.setTypeface(tf);
	}
	 
 }

@Override
public int getItemCount() {
	// TODO Auto-generated method stub
	return list.size();
}

public void clearApplications() {
    int size = list.size();
    if (size > 0) {
        for (int i = 0; i < size; i++) {
        	list.remove(0);
        }

       notifyItemRangeRemoved(0, size);
    }
}
private static RadioButton lastChecked = null;
private static int lastCheckedPos = 0;
@Override
public void onBindViewHolder(View_Holder v, int arg1) {
	// TODO Auto-generated method stub
	final Single_Address_Metadata single= list.get(arg1);
	v.address1.setText(single.getAddress1());
	v.city.setText(single.getCity());
	v.state.setText(single.getState());
	v.pincode.setText(single.getPincode());
	v.select_address.setTag(new Integer(arg1));
	v.select_address.setChecked(single.isSelected());
	
	if(arg1 == 0 && list.get(0).isSelected() && v.select_address.isChecked())
    {
       lastChecked = v.select_address;
       lastCheckedPos = 0;
    }
	
	 v.select_address.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generatxed method stub
				 RadioButton cb = (RadioButton)v;
				 int clickedPos = ((Integer)cb.getTag()).intValue(); 

		           if(cb.isChecked())
		           {
		              if(lastChecked != null)
		              {
		                  lastChecked.setChecked(false);
		                  list.get(lastCheckedPos).setSelected(false);
		              }                       

		              lastChecked = cb;
		              lastCheckedPos = clickedPos;
		          }else
		          {
		        	  lastChecked = null;

		          }
		           list.get(clickedPos).setSelected(cb.isChecked());
		           ((Global)Address_Adapter.this.mContext.getApplicationContext()).setAddress1( list.get(clickedPos).getAddress1());
		           ((Global)Address_Adapter.this.mContext.getApplicationContext()).setCity( list.get(clickedPos).getCity());
		           ((Global)Address_Adapter.this.mContext.getApplicationContext()).setState(list.get(clickedPos).getState());
		           ((Global)Address_Adapter.this.mContext.getApplicationContext()).setPincode(list.get(clickedPos).getPincode());
		           ((Global)Address_Adapter.this.mContext.getApplicationContext()).setSelect_city_id(list.get(clickedPos).getCityCode());
		           ((Global)Address_Adapter.this.mContext.getApplicationContext()).setSelect_state_id(list.get(clickedPos).getStateCode());;




			}
		});
	 
	 v.select_address.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			single.setSelected(isChecked);	
			}
		});
}

@Override
public View_Holder onCreateViewHolder(ViewGroup v, int arg1) {
	// TODO Auto-generated method stub
	View view=LayoutInflater.from(v.getContext()).inflate(R.layout.single_address, v,false);
	
	return new View_Holder(view);
}
}
