package com.app2mobile.Sultanwok;

import java.util.List;

import com.app2mobile.metadata.Credit_Card_Metadata;
import com.app2mobile.utiles.ConstantsUrl;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class CreditCard_Adapter extends
		RecyclerView.Adapter<CreditCard_Adapter.View_Holder> implements RecyclerView.OnItemTouchListener {
	int[] color_arr = { Color.parseColor("#c9a3a3"),
			Color.parseColor("#720707"), Color.parseColor("#1634e8"),
			Color.parseColor("#283747"), Color.parseColor("#153d10"),
			Color.parseColor("#194338"), Color.parseColor("#b84743"),
			Color.parseColor("#00b159"), Color.parseColor("#00171d"),
			Color.parseColor("#f5c952"), Color.parseColor("#c9a3a3"),
			Color.parseColor("#720707"), Color.parseColor("#1634e8"),
			Color.parseColor("#283747"), Color.parseColor("#153d10"),
			Color.parseColor("#194338"), Color.parseColor("#b84743"),
			Color.parseColor("#00b159"), Color.parseColor("#00171d"),
			Color.parseColor("#f5c952"), Color.parseColor("#c9a3a3"),
			Color.parseColor("#720707"), Color.parseColor("#1634e8"),
			Color.parseColor("#283747"), Color.parseColor("#153d10"),
			Color.parseColor("#194338"), Color.parseColor("#b84743"),
			Color.parseColor("#00b159"), Color.parseColor("#00171d"),
			Color.parseColor("#f5c952"), Color.parseColor("#c9a3a3"),
			Color.parseColor("#720707"), Color.parseColor("#1634e8"),
			Color.parseColor("#283747"), Color.parseColor("#153d10"),
			Color.parseColor("#194338"), Color.parseColor("#b84743"),
			Color.parseColor("#00b159"), Color.parseColor("#00171d"),
			Color.parseColor("#f5c952") };
	int i = 0;
	 private OnItemClickListener mListener;
	public CreditCard_Adapter(Context context, List<Credit_Card_Metadata> list) {
		this.mContext = context;
		this.list = list;
	}

	private Context mContext;
	private List<Credit_Card_Metadata> list;
	GestureDetector mGestureDetector;
	public class View_Holder extends RecyclerView.ViewHolder  {
		TextView card_holder_name, card_number, exp_month,exp_year;
		ImageView card_image;
		CardView cardview;
		CheckBox check;

		@SuppressLint("NewApi") public View_Holder(View view) {
			super(view);
			// TODO Auto-generated constructor stub
			cardview = (CardView) view.findViewById(R.id.card_view);
			card_holder_name = (TextView) view
					.findViewById(R.id.card_holder_name);
			card_number = (TextView) view.findViewById(R.id.card_number);
			card_image = (ImageView) view.findViewById(R.id.card_image);
			exp_month = (TextView) view.findViewById(R.id.exp_month);
			exp_year=(TextView)view.findViewById(R.id.exp_year);
			check=(CheckBox)view.findViewById(R.id.selected);
			int api=android.os.Build.VERSION.SDK_INT;
			if(api>= android.os.Build.VERSION_CODES.LOLLIPOP){
			check.setButtonTintList(ColorStateList.valueOf(Color.parseColor(ConstantsUrl.STORE_COLOR_CODE)));
			}
		}

		
	

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
	   public CreditCard_Adapter(Context context, OnItemClickListener listener) {
	        mListener = listener;
	        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
	            @Override
	            public boolean onSingleTapUp(MotionEvent e) {
	                return true;
	            }
	        });
	    }
	
	public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
	
	 @Override
	    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
	        View childView = view.findChildViewUnder(e.getX(), e.getY());
	        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
	            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));

	        }
	        return false;
	    }

	    @Override
	    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
	    }

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	private static CheckBox lastChecked = null;
	private static int lastCheckedPos = 0;
	@Override
	public void onBindViewHolder(View_Holder holder, int arg1) {
		// TODO Auto-generated method stub
		final Credit_Card_Metadata credit = list.get(arg1);
		final int pos= arg1;
		holder.cardview.setBackgroundColor(color_arr[arg1]);
		holder.card_holder_name.setText(credit.getCard_holder_name());
		holder.card_number.setText(credit.getCard_number());
		holder.exp_month.setText(credit.getExp_month());
		holder.exp_year.setText(credit.getExp_year());
		holder.check.setOnCheckedChangeListener(null);
		holder.check.setChecked(credit.isSelected());
		holder.check.setTag(new Integer(arg1));
	
		Picasso.with(mContext).load(credit.getCard_image())
				.into(holder.card_image);
		if(arg1 == 0 && list.get(0).isSelected() && holder.check.isChecked())
	    {
	       lastChecked = holder.check;
	       lastCheckedPos = 0;
	    }

		 holder.check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generatxed method stub
				 CheckBox cb = (CheckBox)v;
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
		           ((Global)CreditCard_Adapter.this.mContext.getApplicationContext()).setCard_number( list.get(clickedPos).getCard_number());
		           ((Global)CreditCard_Adapter.this.mContext.getApplicationContext()).setMonth( list.get(clickedPos).getExp_month());
		           ((Global)CreditCard_Adapter.this.mContext.getApplicationContext()).setYear( list.get(clickedPos).getExp_year());
		           ((Global)CreditCard_Adapter.this.mContext.getApplicationContext()).setCvv("");
		           ((Global)CreditCard_Adapter.this.mContext.getApplicationContext()).setCard_token(list.get(clickedPos).getToken());
		        //   AppUtiles.getInstance().showToast(mContext, ((Global)CreditCard_Adapter.this.mContext.getApplicationContext()).getCard_number());
		           Log.e("card nooooo",((Global)CreditCard_Adapter.this.mContext.getApplicationContext()).getCard_token());

			}
		});
		 
		holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			credit.setSelected(isChecked);	
			}
		});
		
	}

	@Override
	public View_Holder onCreateViewHolder(ViewGroup holder, int arg1) {
		// TODO Auto-generated method stub
		View itemview = LayoutInflater.from(holder.getContext()).inflate(
				R.layout.single_card_view, holder, false);

		return new View_Holder(itemview);
	}

	@Override
	public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
