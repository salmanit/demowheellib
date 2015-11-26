package com.sage.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.sage.libwheelview.widget.SelectBirthdayPopupWindow;
import com.sage.libwheelview.widget.SelectHeightPopupWindow;
import com.sage.libwheelview.widget.SelectSimpletPopupWindow;
import com.sage.libwheelview.widget.SelectWeight9PopupWindow;
import com.sage.libwheelview.widget.SelectWeightPopupWindow;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

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
}
