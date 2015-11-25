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
import com.sage.libwheelview.widget.wheel.adapter.NumericWheelAdapter;


public class SelectHeightPopupWindow extends PopupWindow implements
		OnClickListener {

	private Activity mContext;
	private View mMenuView;
	private ViewFlipper viewfipper;
	private Button btn_submit, btn_cancel;
	private int height_number;
	private DateNumericAdapter heightAdapter;
	private WheelView select_height;
	private Handler mHandler;
	// 使用自定义的Log机制

	public SelectHeightPopupWindow(Activity context, String height,
			Handler handler) {
		super(context);
		setAnimationStyle(R.style.PopupAnimation);
		mContext = context;
		mHandler = handler;
		// "身高数值"
		try {
			height_number = Integer.valueOf(height);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			height_number=170;
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popupwindow_heigth, null);
		viewfipper = new ViewFlipper(context);
		viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		select_height = (WheelView) mMenuView.findViewById(R.id.height_select_height);

		btn_submit = (Button) mMenuView
				.findViewById(R.id.btn_submit_select_height);
		btn_cancel = (Button) mMenuView
				.findViewById(R.id.btn_cancel_select_height);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		heightAdapter = new DateNumericAdapter(context, 120, 250,
				height_number);
		select_height.setViewAdapter(heightAdapter);
		select_height.setCurrentItem(height_number-120);

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
		if(v.getId()==R.id.btn_submit_select_height){
			Message message = Message.obtain();
			message.what = 6;
			height_number = select_height.getCurrentItem()+120;
			Bundle bundle = new Bundle();
			bundle.putString("height", height_number+"");
			message.setData(bundle);
			mHandler.sendMessage(message);
		}else if(v.getId()==R.id.btn_cancel_select_height){

		}
		dismiss();
	}

}
