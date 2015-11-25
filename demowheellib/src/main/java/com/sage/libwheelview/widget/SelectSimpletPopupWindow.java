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


public class SelectSimpletPopupWindow extends PopupWindow implements
		OnClickListener {

	public static final String CHECKED_CONTENT="content";
	public static final String CHECKED_INDEX="index";
	private Activity mContext;
	private View mMenuView;
	private ViewFlipper viewfipper;
	private Button btn_submit, btn_cancel;
	private SimpleAdapter adapter;
	private WheelView select_simple;
	private Handler mHandler;
	private String[] arr;

	public SelectSimpletPopupWindow(Activity context, String[] arrays,int select,
			Handler handler) {
		super(context);
		setAnimationStyle(R.style.PopupAnimation);
		mContext = context;
		mHandler = handler;
		arr=arrays;
		if(arr==null){
			dismiss();
			return;
		}
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popupwindow_simple_text, null);
		viewfipper = new ViewFlipper(context);
		viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		select_simple = (WheelView) mMenuView.findViewById(R.id.wheel_id_simple_text);

		btn_submit = (Button) mMenuView
				.findViewById(R.id.btn_submit_select_simple);
		btn_cancel = (Button) mMenuView
				.findViewById(R.id.btn_cancel_select_simple);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);

		adapter=new SimpleAdapter(mContext, arr,select);
		select_simple.setViewAdapter(adapter);
		select_simple.setCurrentItem(select);

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
	private class SimpleAdapter extends ArrayWheelAdapter<String> {
		public int currentItem;
		// Index of item to be highlighted
		public int currentValue;
		public SimpleAdapter(Context context, String[] items,int current) {
			super(context, items);
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
		if(v.getId()==R.id.btn_submit_select_simple){
			Message message = Message.obtain();
			message.what = 10;
			Bundle bundle = new Bundle();
			bundle.putString(CHECKED_CONTENT, arr[select_simple.getCurrentItem()]);
			bundle.putInt(CHECKED_INDEX, select_simple.getCurrentItem());
			message.setData(bundle);
			mHandler.sendMessage(message);
		}else if(v.getId()==R.id.btn_cancel_select_simple){

		}
		dismiss();

	}

}
