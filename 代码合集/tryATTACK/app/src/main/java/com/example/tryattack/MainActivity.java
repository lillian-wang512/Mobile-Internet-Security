package com.example.tryattack;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
//        Intent[] intents = new Intent[2];
        Intent innocent,attack;
        attack=new Intent(this,Attack.class);
        attack.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//将attack活动放置在一个新task中
        attack.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//取消过度动画,增加恶意软件迷惑性
        innocent=new Intent(this,Innocent.class);
        innocent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivitiy(attack)
//        startActivities(new Intent[]{attack});

        startActivities(new Intent[]{attack,innocent});//先后启动attack活动与innocent活动
        finish();
    }


}




//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;

//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//}




//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setContentView(R.layout.activity_main);
//        Intent innocent,attack;
//        attack=new Intent(this,Attack.class);
//        attack.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//将attack活动放置在一个新task中
//        attack.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//取消过度动画,增加恶意软件迷惑性
//        innocent=new Intent(this,Innocent.class);
//        innocent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivities(new Intent[]{attack,innocent});//先后启动attack活动与innocent活动
//        finish();
//    }
//}