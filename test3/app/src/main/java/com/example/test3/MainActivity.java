package com.example.test3;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.test3.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.rank:
                Toast.makeText(this.getApplicationContext(),"랭킹순으로 정렬합니다.", Toast.LENGTH_SHORT).show();
                break;
                //추가로 listview 출력시킬 것 수정

            case R.id.cheap:
                Toast.makeText(this.getApplicationContext(),"낮은 가격순으로 정렬합니다.", Toast.LENGTH_SHORT).show();
                break;
                //추가로 listview 출력시킬 것 수정

            case R.id.expensive:
                Toast.makeText(this.getApplicationContext(),"높은 가격순으로 정렬합니다.", Toast.LENGTH_SHORT).show();
                break;
                //추가로 listview 출력시킬 것 수정

            case R.id.nearest:
                Toast.makeText(this.getApplicationContext(),"거리순으로 정렬합니다.", Toast.LENGTH_SHORT).show();
                break;
                //추가로 listview 출력시킬 것 수정
        }

        return super.onOptionsItemSelected(item);
    }
}