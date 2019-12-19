package com.example.test3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SelectArea extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_area_layout);

        ImageView s1 = findViewById(R.id.image1);
        s1.setX(520);
        s1.setY(800);
        ImageView s2 = findViewById(R.id.image2);
        s2.setX(340);
        s2.setY(870);
        ImageView s3 = findViewById(R.id.image3);
        s3.setX(650);
        s3.setY(500);
        ImageView s4 = findViewById(R.id.image4);
        s4.setX(380);
        s4.setY(520);
        ImageView s5 = findViewById(R.id.image5);
        s5.setX(450);
        s5.setY(835);
        ImageView s6 = findViewById(R.id.image6);
        s6.setX(170);
        s6.setY(1000);
        ImageView s7 = findViewById(R.id.image7);
        s7.setX(630);
        s7.setY(780);
        ImageView s8 = findViewById(R.id.image8);
        s8.setX(380);
        s8.setY(750);

        ImageView g = findViewById(R.id.gif);
        Glide.with(this).load(R.drawable.turngif).into(g);
    }

    public void selectArea (View view) {
        Intent intent = new Intent(getApplicationContext(), RankPage.class);
        startActivity(intent);
        finish();
    }

    public void onClick1(View view) {
        Toast T= Toast.makeText(getApplicationContext(), "다른 지역시즌을 기다려주세요", Toast.LENGTH_LONG);
        T.show();
    }
}
