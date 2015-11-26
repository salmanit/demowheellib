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

import com.sage.libwheelview.widget.wheel.OnWheelChangedListener;
import com.sage.libwheelview.widget.wheel.WheelView;
import com.sage.libwheelview.widget.wheel.adapter.NumericWheelAdapter;

import java.util.Calendar;


public class SelectBirthdayPopupWindow extends PopupWindow implements OnClickListener {

	public static final int WHAT=5;
	private int beforeYear=120;
	private Activity mContext;
	private View mMenuView;
	private ViewFlipper viewfipper;
	private Button btn_submit, btn_cancel;
	private String age;
	private DateNumericAdapter monthAdapter, dayAdapter, yearAdapter;
	private WheelView year, month, day;
	private int mCurYear = 80, mCurMonth = 5, mCurDay = 14;
	private String[] dateType;
	private Handler mHandler;
	public SelectBirthdayPopupWindow(Activity context, String birthday, Handler handler) {
		super(context);
		setAnimationStyle(R.style.PopupAnimation);
		mContext = context;
		mHandler = handler;
		// "2012-9-25"
		this.age =birthday;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popupwindow_birthday, null);
		viewfipper = new ViewFlipper(context);
		viewfipper.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		year = (WheelView) mMenuView.findViewById(R.id.year_birthday);
		month = (WheelView) mMenuView.findViewById(R.id.month_birthday);
		day = (WheelView) mMenuView.findViewById(R.id.day_birthday);
		btn_submit = (Button) mMenuView.findViewById(R.id.btn_submit_select_birthday);
		btn_cancel = (Button) mMenuView.findViewById(R.id.btn_cancel_select_birthday);
		btn_submit.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		Calendar calendar = Calendar.getInstance();
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);

			}
		};
		int curYear = calendar.get(Calendar.YEAR);
		if (age != null && age.contains("-")) {
			String str[] = age.split("-");
			mCurYear = beforeYear - (curYear - Integer.parseInt(str[0]));
			mCurYear = Integer.parseInt(str[0])-(curYear-beforeYear);
			mCurMonth = Integer.parseInt(str[1]) - 1;
			mCurDay = Integer.parseInt(str[2]) - 1;
		}
		dateType = mContext.getResources().getStringArray(R.array.lib_date);
		monthAdapter = new DateNumericAdapter(context, 1, 12, 5);
		monthAdapter.setTextType(dateType[1]);
		month.setViewAdapter(monthAdapter);
		month.setCurrentItem(mCurMonth);
		month.addChangingListener(listener);
		// year

		yearAdapter = new DateNumericAdapter(context, curYear - beforeYear, curYear,
				beforeYear-20 );
		yearAdapter.setTextType(dateType[0]);
		year.setViewAdapter(yearAdapter);
		year.setCurrentItem(mCurYear);
		year.addChangingListener(listener);
		// day

		updateDays(year, month, day);
		day.setCurrentItem(mCurDay);
		updateDays(year, month, day);
		day.addChangingListener(listener);

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


	private void updateDays(WheelView year, WheelView month, WheelView day) {
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,
				calendar.get(Calendar.YEAR) + year.getCurrentItem()-beforeYear);
		calendar.set(Calendar.MONTH, month.getCurrentItem());
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		dayAdapter = new DateNumericAdapter(mContext, 1, maxDays,
				calendar.get(Calendar.DAY_OF_MONTH) - 1);
		dayAdapter.setTextType(dateType[2]);
		day.setViewAdapter(dayAdapter);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		int years = calendar.get(Calendar.YEAR);
		 int m=month.getCurrentItem()+1;
		 int d=day.getCurrentItem()+1;
		 age=years+"-"+(m<10?"0":"")+m+"-"+(d<10?"0":"")+d;
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
		if(v.getId()==R.id.btn_submit_select_birthday){
			Message message = Message.obtain();
			message.what=WHAT;
			message.obj=age;
			mHandler.sendMessage(message);
		}else if(v.getId()==R.id.btn_cancel_select_birthday){

		}
		dismiss();
	}

	
	
	
	
}
