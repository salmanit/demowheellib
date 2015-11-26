# demowheellib

简介，就是用网上的wheelview，写了 生日，身高，体重的选择器，方便项目调用。

下面是values下边的参数，可以通过复覆盖掉库里的，方便修改颜色背景等。
  <color name="lib_item_text_color">@android:color/black</color>
    <color name="lib_color_default_bg">#f9f9f9</color>
    <color name="lib_color_cancel">#000000</color>
    <color name="lib_color_ok">#000000</color>
    <color name="lib_color_text_show">#fff</color>
    <drawable name="lib_ok_bg">@null</drawable>
    <drawable name="lib_cancel_bg">@null</drawable>
    <drawable name="lib_title_bg">@color/lib_color_default_bg</drawable>

    <string name="wheel_date_of_birth">出生年月</string>
    <string name="height">身高</string>
    <string name="weight">体重(公斤)</string>
    <string name="wheel_cancel">取消</string>
    <string name="wheel_complete">完成</string>
    <style name="PopupAnimation" parent="android:Animation">
        <item name="android:windowEnterAnimation">@anim/anim_in</item>
        <item name="android:windowExitAnimation">@anim/anim_out</item>
    </style>
    
    重写 drawable目录下wheel_bg.xml 可以修wheelview的背景颜色，重写wheel_val.xml可以修改那个选中的横条颜色

使用
complile 'com.sage.libwheelview.widget:demowheellib:1.0.1'

代码中简单的一行调用
  需要传一个默认的选中的日期，格式2000-01-02
	private void chooseBirthday() {
		 new SelectBirthdayPopupWindow(this,
				tv_birthday.getText().toString(), mHandler).showAtLocation(findViewById(R.id.layout_root), Gravity.BOTTOM, 0, 0);
	}
  需要传一个身高的字符串，比如170，必须是整数型的字符串
	private void chooseHeight() {
		 new SelectHeightPopupWindow(this, height, mHandler).showAtLocation(findViewById(R.id.layout_root), Gravity.BOTTOM, 0, 0);
	}
  需要传一个体重的字符串，比如20.5，小数位只有0或者5传个整数也可以比如40
	private void chooseWeight() {
		 new SelectWeightPopupWindow(this, weight, mHandler).showAtLocation(findViewById(R.id.layout_root), Gravity.BOTTOM, 0, 0);
	}
	
	如果只是简单的一个选项，传入数组，以及默认的选中位置索引
	private void choosePart(String[] arrays, int select) {
		 new SelectSimpletPopupWindow(this, arrays, select,
				mHandler).showAtLocation(findViewById(R.id.layout_root), Gravity.BOTTOM, 0, 0);
	}
	
	
	选择完数据的获取
		private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			btn_save.setEnabled(true);
			switch (msg.what) {
			case 8:
				birth = msg.getData().getString("birthday");
				tv_birthday.setText(birth);
				break;
			case 6:
				height = msg.getData().getString("height");
				tv_height.setText(getString(R.string.height_unit, height));
				break;
			case 7:
				weight = msg.getData().getString("weight");
				tv_weight.setText(getString(R.string.weight_unit, weight));
				break;
			case 10:
					index = msg.getData().getInt(SelectSimpletPopupWindow.CHECKED_INDEX);
					Name = msg.getData().getString(SelectSimpletPopupWindow.CHECKED_CONTENT);
				break;
			}
		}
	};
