package com.example.test3;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test3.ui.main.SectionsPagerAdapter;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

public class RankPage extends AppCompatActivity {
    String test = "";
    ListView listView;
    CustomAdapter customAdapter;
    ArrayList<restaurantItem> list;

    private static Toolbar toolbar;
    private static ViewPager viewPager;
    private static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("TAG","TAB1");
                        break;
                    case 1:
                        Log.e("TAG","TAB2");
                        break;
                    case 2:
                        Log.e("TAG","TAB3");
                        break;
                    case 3:
                        Log.e("TAG","TAB1");
                        break;
                    case 4:
                        Log.e("TAG","TAB2");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        list = setXMLlist();
        customAdapter = new CustomAdapter(this, 0, list);
        listView = (ListView) findViewById(R.id.ListView);
        orderedListView(0);

        final Geocoder geocoder = new Geocoder(this);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Listview, 클릭한view, 위치, id(위치id)
                List<Address> addresses = null;
                restaurantItem item = (restaurantItem) parent.getItemAtPosition(position);
                try {
                    addresses = geocoder.getFromLocationName(item.get_location(), 1);
                } catch (IOException E) {
                    ;
                }
                String rocation = "geo:";
                rocation += addresses.get(0).getLatitude() + ",";
                rocation += addresses.get(0).getLongitude() + "?q=";
                rocation += item.get_name();
                Uri uri = Uri.parse(rocation);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    public void orderedListView(int order) {

        List selectedList = new ArrayList();
        switch(order) {
            case 0:
                Collections.sort(list, new Comparator<restaurantItem>() {
                    @Override
                    public int compare(restaurantItem r1, restaurantItem r2) {
                        if (r1.get_count() < r2.get_count()) {
                            return -1;
                        } else if (r1.get_count() > r2.get_count()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                break;

            case 1:
                Collections.sort(list, new Comparator<restaurantItem>() {
                    @Override
                    public int compare(restaurantItem r1, restaurantItem r2) {
                        if (r1.get_price() < r2.get_price()) {
                            return -1;
                        } else if (r1.get_price() > r2.get_price()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                break;

            case 2:
                Collections.sort(list, new Comparator<restaurantItem>() {
                    @Override
                    public int compare(restaurantItem r1, restaurantItem r2) {
                        if (r1.get_price() > r2.get_price()) {
                            return -1;
                        } else if (r1.get_price() < r2.get_price()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                break;

        }
        customAdapter = new CustomAdapter(this, 0, list);
        listView.setAdapter(customAdapter);
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

        switch (id) {
            case R.id.rank:
                Toast.makeText(this.getApplicationContext(), "랭킹순으로 정렬합니다.", Toast.LENGTH_SHORT).show();
                orderedListView(0);
                break;

            //추가로 listview 출력시킬 것 수정

            case R.id.cheap:
                Toast.makeText(this.getApplicationContext(), "낮은 가격순으로 정렬합니다.", Toast.LENGTH_SHORT).show();
                orderedListView(1);
                break;
            //추가로 listview 출력시킬 것 수정

            case R.id.expensive:
                Toast.makeText(this.getApplicationContext(), "높은 가격순으로 정렬합니다.", Toast.LENGTH_SHORT).show();
                orderedListView(2);
                break;
            //추가로 listview 출력시킬 것 수정
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<restaurantItem> setXMLlist() {
        ArrayList<restaurantItem> items = new ArrayList<restaurantItem>();
        try {
            XmlPullParserFactory XmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = getResources().getXml(R.xml.restaurantlist);
            int event_tag = parser.getEventType();
            String state = "";
            restaurantItem item = new restaurantItem();
            while (event_tag != XmlPullParser.END_DOCUMENT) {
                switch (event_tag) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        state = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        item.set_item(state, text);
                        break;
                    case XmlPullParser.END_TAG:
                        state = parser.getName();
                        if (state.equals("restaurant")) {
                            items.add(item);
                            item = new restaurantItem();
                        }
                        break;
                }
                event_tag = parser.next();
            }
        } catch (XmlPullParserException e) {
            ;
        } catch (Exception e) {
            ;
        }
        return items;
    }

    //리스트 클래스
    public class restaurantItem {
        public int num, price, count;
        public String name, location, kind;

        public restaurantItem() {
            num = 0;
            price = 0;
            count = 1;
            location = "위치";
            name = "이름";
            kind = "종류";
        }

        public int get_price() {
            return price;
        }

        public int get_count() {
            return count;
        }

        public String get_location() {
            return location;
        }

        public String get_name() {
            return name;
        }

        public String get_kind() {
            return kind;
        }

        public void set_num(int arg) {
            num = arg;
        }

        public void set_price(int arg) {
            price = arg;
            ;
        }

        public void set_count(int arg) {
            count = arg;
            ;
        }

        public void set_location(String arg) {
            location = arg;
        }

        public void set_name(String arg) {
            name = arg;
        }

        public void set_kind(String arg) {
            kind = arg;
        }

        public void set_item(String target, String arg) {
            if (target.equals("num")) {
                arg = arg.replaceAll(" ", "");
                set_num(Integer.parseInt(arg));
            } else if (target.equals("price")) {
                arg = arg.replaceAll(" ", "");
                set_price(Integer.parseInt(arg));
            } else if (target.equals("count")) {
                arg = arg.replaceAll(" ", "");
                set_count(Integer.parseInt(arg));
            } else if (target.equals("location")) {
                set_location(arg);
            } else if (target.equals("name")) {
                set_name(arg);
            } else if (target.equals("kind")) {
                set_kind(arg);
            }
        }
    }

    //커스텀 리스트 리스너
    public class CustomAdapter extends ArrayAdapter<restaurantItem> {
        private LayoutInflater mLayoutInflater;

        public CustomAdapter(Context context, int resource, List<restaurantItem> objects) {
            super(context, resource, objects);
            mLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // 특정 행(position)의 데이터를 구함
            restaurantItem item = (restaurantItem) getItem(position);

            // 같은 행에 표시시킨 View는 재사용되기 때문에 첫 회만 생성
            if (null == convertView) {
                convertView = mLayoutInflater.inflate(R.layout.item_layout, null);
            }

            // 데이터를 View의 각 Widget에 설정
            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(String.format("%s", item.get_name()));
            TextView location = (TextView) convertView.findViewById(R.id.location);
            location.setText(String.format("%s", item.get_location()));
            TextView price = (TextView) convertView.findViewById(R.id.price);
            price.setText(String.format("가격 : %d", item.get_price()));
            TextView kind = (TextView) convertView.findViewById(R.id.kind);
            kind.setText(String.format("종류 : %s", item.get_kind()));

            return convertView;
        }
    }
}