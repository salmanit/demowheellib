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

如图所示，复写对应的资源文件即可替换库里默认的。
![Image of 示例](https://raw.githubusercontent.com/salmanit/demowheellib/master/res.png)


使用
complile 'com.sage.libwheelview.widget:demowheellib:1.0.1'

代码中简单的一行调用


    private String[] arr=new String[]{"张三","li","王五","赵六"};

    public void btnClick(View view){
        switch (view.getId()){
            case R.id.btn_height:
                new SelectHeightPopupWindow(this,170,mHandler).showAtLocation(view, Gravity.BOTTOM,0,0);
                //new SelectHeightPopupWindow(this,170,100,200,mHandler).showAtLocation(view, Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_weight:
                new SelectWeightPopupWindow(this,70,mHandler).showAtLocation(view,Gravity.BOTTOM,0,0);
                //new SelectWeightPopupWindow(this,70,30,250,mHandler).showAtLocation(view,Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_weight9:
                new SelectWeight9PopupWindow(this,70.9f,mHandler).showAtLocation(view,Gravity.BOTTOM,0,0);
                //new SelectWeight9PopupWindow(this,70.9f,40,100,mHandler).showAtLocation(view,Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_birthday:
                new SelectBirthdayPopupWindow(this,"2000-01-02",mHandler).showAtLocation(view,Gravity.BOTTOM,0,0);
                break;
            case R.id.btn_custom:
                new SelectSimpletPopupWindow(this,arr,3,mHandler).showAtLocation(view,Gravity.BOTTOM,0,0);
                break;
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SelectHeightPopupWindow.WHAT:
                    showToast("身高为："+msg.arg1);
                    break;
                case SelectWeightPopupWindow.WHAT:
                case SelectWeight9PopupWindow.WHAT:
                    float weight= (float) msg.obj;
                    showToast("体重为:"+weight);
                    break;
                case SelectBirthdayPopupWindow.WHAT:
                    String birthday= (String) msg.obj;
                    showToast("生日："+birthday);
                    break;
                case  SelectSimpletPopupWindow.WHAT:
                    int index=msg.arg1;
                    showToast(arr[index]);
                    break;
            }
        }
    };

    private void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
