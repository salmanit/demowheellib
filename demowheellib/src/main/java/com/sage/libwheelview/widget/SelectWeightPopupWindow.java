package com.sage.libwheelview.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.sage.libwheelview.widget.wheel.WheelView;
import com.sage.libwheelview.widget.wheel.adapter.ArrayWheelAdapter;
import com.sage.libwheelview.widget.wheel.adapter.NumericWheelAdapter;


public class SelectWeightPopupWindow extends PopupWindow implements
		OnClickListener {

	private View mMenuView;
	private ViewFlipper viewfipper;
	private Button btn_submit, btn_cancel;
	private int weight_number;
	private int float_number;
	private DateNumericAdapter weightAdapter;
	private WheelView select_weight, select_float;
	private Handler mHandler;
	private final String values[] = new String[] { ".0", ".5", };
	public static final int WHAT=7;
	private int minWeight;
	private int maxWeight;
	public SelectWeightPopupWindow(Context context, float weight,
								   Handler handler) {
		super(context);
		initData(context,weight,30,200,handler);
	}
	public SelectWeightPopupWindow(Context context, float weight,int minWeight,int maxWeight,
								   Handler handler) {
		super(context);
		initData(context,weight,minWeight,maxWeight,handler);
	}


	private void initData(Context context, float weight,int minWeight,int maxWeight,
						  Handler handler){
		setAnimationStyle(R.style.PopupAnimation);
		mHandler = handler;
		this.minWeight=minWeight;
		this.maxWeight=maxWeight;
		if(weight<=0||minWeight<=0||maxWeight<=0){
			throw new IllegalArgumentException("weight must be positive number");
		}
		if(minWeight>=maxWeight){
			throw  new IllegalArgumentException("minWeigh is big than maxHeight");
		}
		if(weight<minWeight){
			weight=minWeight;
		}else if(weight>maxWeight+0.5f){
			weight=maxWeight;
		}
		weight_number= (int) weight;
		float_number= (int) ((weight-weight_number)*10);
		if(float_number<5){
			float_number=0;
		}else{
			float_number=5;
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popupwindow_weight, null);
		viewfipper = new ViewFlipper(context);
		viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		select_weight = (WheelView) mMenuView
				.findViewById(R.id.weight_select_weight);
		select_float = (WheelView) mMenuView
				.findViewById(R.id.float_select_weight);
		btn_submit = (Button) mMenuView
				.findViewById(R.id.btn_submit_select_weight);
		btn_cancel = (Button) mMenuView
				.findViewById(R.id.btn_cancel_select_weight);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		weightAdapter = new DateNumericAdapter(context, minWeight, maxWeight, weight_number);
		select_weight.setViewAdapter(weightAdapter);
		select_weight.setCurrentItem(weight_number - minWeight);

		select_float = (WheelView) mMenuView
				.findViewById(R.id.float_select_weight);
		ArrayWheelAdapter<String> floatAdapter = new ArrayWheelAdapter<>(
				context, values);
		floatAdapter.setItemResource(R.layout.float_item);
		floatAdapter.setItemTextResource(R.id.tv_float_item);
		floatAdapter.setEmptyItemResource(R.layout.float_item);
		select_float.setViewAdapter(floatAdapter);
		if (float_number == 0) {
			select_float.setCurrentItem(0);
		} else {
			select_float.setCurrentItem(1);
		}
		viewfipper.addView(mMenuView);
		viewfipper.setFlipInterval(6000000);
		this.setContentView(viewfipper);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00000000);
		this.setBackgroundDrawable(dw);
		this.update();
	}


	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		viewfipper.startFlipping();
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(18);
		}

		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		public CharSequence getItemText(int index) {
			currentItem = index;
			return super.getItemText(index);
		}

	}

	public void onClick(View v) {
		if(v.getId()==R.id.btn_submit_select_weight){
			Message message = Message.obtain();
			message.what = WHAT;
			weight_number = select_weight.getCurrentItem() + minWeight;
			float_number = select_float.getCurrentItem();
			if (float_number == 0) {
				message.obj=weight_number;
			} else {
				message.obj=weight_number+0.5f;
			}
			mHandler.sendMessage(message);
		}else if(v.getId()==R.id.btn_cancel_select_weight){

		}
		dismiss();
	}

}
