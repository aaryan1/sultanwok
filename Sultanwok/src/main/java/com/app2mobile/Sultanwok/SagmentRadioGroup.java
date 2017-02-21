package com.app2mobile.Sultanwok;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

public class SagmentRadioGroup extends RadioGroup {
	public SagmentRadioGroup(Context context) {
		super(context);
		}

		public SagmentRadioGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		}

		@Override
		protected void onFinishInflate() {
		super.onFinishInflate();
		changeButtonsImages();
		}

		private void changeButtonsImages(){
		int count = super.getChildCount();

		if(count > 1)
		{
		super.getChildAt(0).setBackgroundResource
		(R.drawable.square);

		super.getChildAt(1).setBackgroundResource
		(R.drawable.square);
		super.getChildAt(2).setBackgroundResource
		(R.drawable.square);
		}
		}
}
