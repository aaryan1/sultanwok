package com.app2mobile.Sultanwok;

import java.util.ArrayList;
import java.util.Iterator;

import com.app2mobile.metadata.ManualLocation_Metadata;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;



public class Autocomplete extends ArrayAdapter<ManualLocation_Metadata>  {
Activity act;
TextView city, deliver;
int res;
Context ctx;
LayoutInflater inflate;
public Filter filter;
ArrayList<ManualLocation_Metadata> call;
ArrayList<ManualLocation_Metadata> loc;
ArrayList<ManualLocation_Metadata> originalitems= new ArrayList<ManualLocation_Metadata>();
private final Object mLock = new Object();

public Autocomplete(Context context, int resource,
		ArrayList<ManualLocation_Metadata> poj) {
	super(context, resource, poj);
	// TODO Auto-generated constructor stub
	ctx = context;
	res = resource;
	loc = poj;
call= new ArrayList<ManualLocation_Metadata>();
	inflate = (LayoutInflater) ctx
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	this.call.addAll(loc);
	cloneItems(poj);
}



@Override
public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	
	ViewHolder holder = null;
	if (convertView==null){
		convertView=inflate.inflate(res, null);
		holder= new ViewHolder();
		holder.city= (TextView)convertView.findViewById(R.id.city);
		holder.deliver=(TextView)convertView.findViewById(R.id.pickup);
		holder.timing=(TextView)convertView.findViewById(R.id.timing);
		holder.store_id=(TextView)convertView.findViewById(R.id.storeid);
		holder.open=(TextView)convertView.findViewById(R.id.imageView1);
		holder.open_close=(TextView)convertView.findViewById(R.id.open);
		holder.time=(TextView)convertView.findViewById(R.id.time);
		holder.address=(TextView)convertView.findViewById(R.id.address);

		//holder.open.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		Typeface tf= Typeface.createFromAsset(getContext().getAssets(),  "OpenSans-Bold.ttf");
		Typeface tf1= Typeface.createFromAsset(getContext().getAssets(),  "Sintony-Bold.ttf");
		Typeface tf2= Typeface.createFromAsset(getContext().getAssets(),  "Quattrocento-Bold.ttf");
		holder.city.setTypeface(tf);
		holder.deliver.setTypeface(tf1);
		holder.timing.setTypeface(tf2);
		holder.time.setTypeface(tf2);
		holder.open.setTypeface(tf1);
		holder.city.setSelected(true);
		convertView.setTag(holder);
					
	}
	else{
		holder=(ViewHolder)convertView.getTag();
	}
	//ManualLocation_Metadata metadata= call.get(position);
	

	Log.e("city.......", loc.get(position).getCity_name());
	holder.deliver.setText(loc.get(position).getDelivery_type());
	holder.timing.setText(loc.get(position).getTiming());
	holder.store_id.setText(loc.get(position).getStore_id());
	holder.city.setText(loc.get(position).getCity_name());
	holder.open_close.setText(loc.get(position).getStore_delivery_status());
	holder.address.setText(loc.get(position).getStore_first_address()+" "+loc.get(position).getCity_name()+" "+loc.get(position).getState_code()+" "+loc.get(position).getCountry_code()+" "+loc.get(position).getStore_pincode());
	if(loc.get(position).getStore_delivery_status().equals("1")){
		holder.open.setText("OPEN");
	//	holder.open.setBackgroundColor(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE));
		
	}else{
		holder.open.setText("CLOSED");
				
	}
	return convertView;
}
@Override
public void add(ManualLocation_Metadata object) {
	// TODO Auto-generated method stub
	loc.add(object);
}

@Override
public Filter getFilter() {
	// TODO Auto-generated method stub
if(filter==null)
	filter= new Pkclass();
	return filter;

	
}
protected void cloneItems(ArrayList<ManualLocation_Metadata> items) {
    for (Iterator iterator = items.iterator(); iterator.hasNext();) {
    	ManualLocation_Metadata gi = (ManualLocation_Metadata) iterator.next();
        originalitems.add(gi);
    }
}
class ViewHolder{
public TextView city;
public TextView deliver;
public TextView timing,store_id;
public TextView open;
public TextView open_close;
public TextView time;
public TextView address;
}
private class Pkclass extends Filter{


@SuppressLint("DefaultLocale") @SuppressWarnings("null")
@Override
protected FilterResults performFiltering(CharSequence constraint) {
// TODO Auto-generated method stub
FilterResults result= new FilterResults();

System.out.println("perform Filtering");
String prefix = constraint.toString().toLowerCase();


if (prefix != null || prefix.toString().length() < 0)
{
	 synchronized (mLock) {
	System.out.print("ifffff");
   // ArrayList<ManualLocation_Metadata> list = new ArrayList<ManualLocation_Metadata>(loc);
	
    final ArrayList<ManualLocation_Metadata> list = new ArrayList<ManualLocation_Metadata>();
    
    final ArrayList<ManualLocation_Metadata> nlist = new ArrayList<ManualLocation_Metadata>();
    
    nlist.addAll(originalitems);
final int count=nlist.size();
    for (int i=0; i<count; i++)
    {
        final ManualLocation_Metadata pkmn = nlist.get(	i);
        final String value = pkmn.getCity_name().toLowerCase();

        if (value.startsWith(prefix))
        {
            list.add(pkmn);
        }

    }
    result.values = list;
    result.count = list.size();
    
	 }
    
}
else
{
	System.out.print("elseeee");
	synchronized (mLock) {
		   result.values = originalitems;
	       result.count = originalitems.size();
	} 
}
Log.e("Result", ""+result.count);
return result;

}

@SuppressWarnings("unchecked")
@Override
protected void publishResults(CharSequence constraint, FilterResults results) {
// TODO Auto-generated method stub
	
	 
	  synchronized (mLock) {
final ArrayList<ManualLocation_Metadata> cal = (ArrayList<ManualLocation_Metadata>)results.values;
  notifyDataSetChanged();
  clear();
  
  int count = cal.size();
  for (Iterator iterator = cal.iterator(); iterator
          .hasNext();)
  {
	  ManualLocation_Metadata meta=(ManualLocation_Metadata)iterator.next();
	  
      add(meta);	
      notifyDataSetInvalidated();
      
  }
	  }
}
}
}

