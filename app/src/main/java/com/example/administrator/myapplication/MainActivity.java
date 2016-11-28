package com.example.administrator.myapplication;


import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button sendnumber,send;
    EditText phone,pwd,number;
    String temp="";
    String phonenum="";
    CheckBox agree;
    TimeCount timeco;
    TextView timecount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendnumber=(Button) findViewById(R.id.sendnumber);
        send=(Button) findViewById(R.id.send);
        phone=(EditText) findViewById(R.id.phone);
        pwd=(EditText) findViewById(R.id.paw);
        number=(EditText) findViewById(R.id.number);//激活码
        agree=(CheckBox) findViewById(R.id.agree);
        timecount=(TextView) findViewById(R.id.counttime);
        timeco = new TimeCount(120000, 1000);//时间为120秒
        sendnumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                for(int i=0;i<5;i++){//产生一个五位数的激活码
                    int k=(int) (Math.random()*10);
                    temp+=k;
                }

                phonenum=phone.getText().toString().trim();
                SmsManager smsmanger=SmsManager.getDefault();
                if(isPhoneNumberValid(phonenum)){
                    PendingIntent mPI=PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(), 0);
                    smsmanger.sendTextMessage(phonenum, null, "你的激活码是："+temp, mPI, null);
                    Toast.makeText(MainActivity.this, "激活码发送成功!", Toast.LENGTH_LONG).show();
                    timeco.start();
                }
                else{
                    Toast.makeText(MainActivity.this, "电话格式不正确,请检查！", Toast.LENGTH_LONG).show();
                }

            }
        });
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                phonenum=phone.getText().toString().trim();
                if(!isPhoneNumberValid(phonenum)){
                    Toast.makeText(MainActivity.this, "电话格式不正确,请检查！", Toast.LENGTH_LONG).show();
                }
                else if(phonenum.length()==0){
                    Toast.makeText(MainActivity.this, "请输入电话号码！", Toast.LENGTH_LONG).show();
                }
                else if(number.getText().toString().trim().length()==0){
                    Toast.makeText(MainActivity.this, "请输入激活码！", Toast.LENGTH_LONG).show();
                }
                else if(!number.getText().toString().equals(temp)){
                    Toast.makeText(MainActivity.this, "激活码不正确！", Toast.LENGTH_LONG).show();
                }
                else if(pwd.getText().toString().trim().length()<6||pwd.getText().toString().trim().length()>12){
                    Toast.makeText(MainActivity.this, "密码不能少于6为多余12位！！", Toast.LENGTH_LONG).show();
                }
                else if(!agree.isChecked()){
                    Toast.makeText(MainActivity.this, "请先阅读百度协议！！", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                    timeco.cancel();
                }
            }
        });
    }
    /*检查字符串是否为电话号码的方法,并回传true or false的判断值*/
    public static boolean isPhoneNumberValid(String mobiles){
        Matcher m = null;
        if(mobiles.trim().length()>0){
            Pattern p = Pattern.compile("^((13[0-9])|(15[0-3])|(15[7-9])|(18[0,5-9]))\\d{8}$");
            m= p.matcher(mobiles);
        }
        else{

            return false;
        }
        return m.matches();
    }
    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            temp="";
            Toast.makeText(MainActivity.this, "超时，需要重新发送激活码！", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示

            timecount.setText("倒计时："+millisUntilFinished /1000+"秒");
        }
    }
}
