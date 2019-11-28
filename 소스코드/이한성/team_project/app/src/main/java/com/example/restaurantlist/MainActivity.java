package com.example.restaurantlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String test = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<restaurantItem> list = setXMLlist();
        CustomAdapter customAdapter = new CustomAdapter(this,0,list);
        ListView listView = (ListView)findViewById(R.id.ListView);
        final Geocoder geocoder = new Geocoder(this);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Listview, 클릭한view, 위치, id(위치id)
                    List<Address> addresses = null;
                    restaurantItem item = (restaurantItem)parent.getItemAtPosition(position);
                    try {
                        addresses = geocoder.getFromLocationName(item.get_location(), 1);
                    }catch(IOException E){ ; }
                    String rocation = "geo:";
                    rocation += addresses.get(0).getLatitude()+",";
                    rocation += addresses.get(0).getLongitude()+"?q=";
                    rocation += item.get_name();
                    Uri uri = Uri.parse(rocation);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");
                    if (intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }
                }
        });
    }
    //arraylist에서 해당 이름이 있는지 파악
    boolean isexist(ArrayList<restaurantItem> list,String target) {
        restaurantItem[] arr = (restaurantItem[]) list.toArray();
        for (int i=0;i < arr.length;i++){
            if (arr[i].get_name().equals(target))
                return true;
        }
        return false;
    }
    public ArrayList<restaurantItem> setXMLlist() {
        ArrayList<restaurantItem> items = new ArrayList<restaurantItem>();
        try {
            XmlPullParserFactory XmlFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = getResources().getXml(R.xml.restaurantlist);
            int event_tag = parser.getEventType();
            String state = "";
            restaurantItem item = new restaurantItem();
            while(event_tag != XmlPullParser.END_DOCUMENT){
                switch(event_tag){
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
        }
        catch(XmlPullParserException e){;}
        catch(Exception e){;}
        return items;
    }

    //리스트 클래스
    private class restaurantItem{
        public int num, price, count;
        public String name, location, kind;

        public restaurantItem(){
            num = 0;
            price = 0;
            count = 1;
            location = "위치";
            name = "이름";
            kind = "종류";
        }
        public int get_num(){ return num;}
        public int get_price(){ return price;}
        public int get_count(){ return count;}
        public String get_location(){ return location;}
        public String get_name(){ return name;}
        public String get_kind(){ return kind;}

        public void set_num(int arg){ num = arg;}
        public void set_price(int arg){ price = arg;;}
        public void set_count(int arg){ count = arg;;}
        public void set_location(String arg){ location = arg;}
        public void set_name(String arg){ name = arg;}
        public void set_kind(String arg){ kind = arg;}

        public void set_item(String target, String arg){
            if (target.equals("num")){
                arg = arg.replaceAll(" ","");
                set_num(Integer.parseInt(arg));
            }else if (target.equals("price")){
                arg = arg.replaceAll(" ","");
                set_price(Integer.parseInt(arg));
            }else if (target.equals("count")){
                arg = arg.replaceAll(" ","");
                set_count(Integer.parseInt(arg));
            }else if (target.equals("location")){
                set_location(arg);
            }else if (target.equals("name")){
                set_name(arg);
            }else if (target.equals("kind")){
                set_kind(arg);
            }
        }
    }
    //커스텀 리스트 리스너
    private class CustomAdapter extends ArrayAdapter<restaurantItem> {
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
            name.setText(String.format("이름 : %s",item.get_name()));
            TextView location = (TextView) convertView.findViewById(R.id.location);
            location.setText(String.format("위치 : %s",item.get_location()));
            TextView price = (TextView) convertView.findViewById(R.id.price);
            price.setText(String.format("가격 : %d",item.get_price()));
            TextView kind = (TextView) convertView.findViewById(R.id.kind);
            kind.setText(String.format("종류 : %s",item.get_kind()));

            return convertView;
        }
    }
}
