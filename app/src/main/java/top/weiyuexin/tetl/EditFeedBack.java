package top.weiyuexin.tetl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

public class EditFeedBack extends AppCompatActivity {
    private Toolbar toolbar_EditFeedBack;
    private RelativeLayout textviewLayout;
    private EditText telenumber,my_suggess_submit;
    private Button submit;
    private TextView currentNumberTV;
    private TextWatcher watcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit_feed_back);
        initView();
        ImmersionBar.with(this)
                .statusBarColor(R.color.gray)     //状态栏颜色，不写默认透明色
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
    }

    private void initView() {
        toolbar_EditFeedBack=findViewById(R.id.toolbar_EditFeedBack);
        toolbar_EditFeedBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textviewLayout=(RelativeLayout) findViewById(R.id.textviewLayout);
        telenumber=(EditText) findViewById(R.id.telenumber);
        submit=(Button) findViewById(R.id.submit);
        currentNumberTV=(TextView) findViewById(R.id.currentNumberTV);
        my_suggess_submit=(EditText) findViewById(R.id.my_suggess_submit);
        //监控输入字数
        watcher = new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                int len = my_suggess_submit.getText().length();
                if (len >= 200){
                    currentNumberTV.setText(String.valueOf(len));
                    currentNumberTV.setTextColor(getResources().getColor(R.color.pink));
                }else {
                    currentNumberTV.setTextColor(getResources().getColor(R.color.black));
                    currentNumberTV.setText(String.valueOf(len));
                }
            }
        };
        my_suggess_submit.addTextChangedListener(watcher);
        submit.setOnClickListener(new ShowListener());
    }

    private class ShowListener implements View.OnClickListener{
        public void onClick(View v){
            String info=my_suggess_submit.getText().toString();
            if(info.length()<10){
                Toast toast=Toast.makeText(EditFeedBack.this,"请填写补充描述多于10个字",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
            if(info.length()>200){
                Toast toast=Toast.makeText(EditFeedBack.this,"补充描述内容多于200字，请控制字数",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
            }
            if(info.length()>=10&&info.length()<=200){
                Intent intent=new Intent();
                intent.setClass(EditFeedBack.this, successuggestActivity.class);
                EditFeedBack.this.startActivity(intent);
            }
        }
    }


    /**
     重写dispatchTouchEvent
     * 点击软键盘外面的区域关闭软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                //根据判断关闭软键盘
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    /**
     * 判断用户点击的区域是否是输入框
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    @Override
    /*重写finish方法，改变返回时的动画*/
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.slide_out_right);
    }

}