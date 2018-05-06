package com.example.phungg.sqlite;




import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<HocSinh> hocSinhs;
    HocSinhAdapter adapterHocSinh;
    ListView listviewhochinh;
   public static Database database ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(MainActivity.this,"Quanly.sqlite",null,1);
        listviewhochinh = (ListView) findViewById(R.id.lvhocsinh);
        hocSinhs = new ArrayList<>();

        adapterHocSinh = new HocSinhAdapter(MainActivity.this,R.layout.listview_dong_hocsinh,hocSinhs);
        listviewhochinh.setAdapter(adapterHocSinh);


         //tao bang
      //  String CreateTable= "CREATE TABLE IF NOT EXISTS HocSinh (Id INTEGER PRIMARY KEY AUTOINCREMENT , Ten VARCHAR(200) ,Namsinh INTEGER ,Diadiem VARCHAR(200) )";
       // database.QueryData(CreateTable);
        //them du lieu
    //    String InsertHocSinh = "INSERT INTO HocSinh VALUES(null,'Nguyen Van A',1980,'TP Ho Chi Minh')";
//
//        database.QueryData(InsertHocSinh);


        Cursor cursor = database.GetData("SELECT * FROM HocSinh");
        while(cursor.getCount()!=0&& cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            int namsinh = cursor.getInt(2);
            String diadiem= cursor.getString(3);
            HocSinh hocSinh = new HocSinh(id,ten,namsinh,diadiem);
            hocSinhs.add(hocSinh);

            Log.d("BBB","ten hoc vien : "+ hocSinh.getHoTen() + " Nam Sinh "+ hocSinh.getNamSinh() + " Dia Diem : " + hocSinh.getDiaChi());
        }
       listviewhochinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(MainActivity.this,Sua.class);
               intent.putExtra("hocsinh",hocSinhs.get(position));
               startActivity(intent);
           }
       });
        listviewhochinh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String delete ="DELETE FROM HocSinh WHERE Id= "+hocSinhs.get(position).getId()+"";
                database.QueryData(delete);
                hocSinhs.clear();
                Cursor cursor = database.GetData("SELECT * FROM HocSinh");
                while(cursor.getCount()!=0&& cursor.moveToNext())
                {
                    int idd = cursor.getInt(0);
                    String ten = cursor.getString(1);
                    int namsinh = cursor.getInt(2);
                    String diadiem= cursor.getString(3);
                    HocSinh hocSinh = new HocSinh(idd,ten,namsinh,diadiem);
                    hocSinhs.add(hocSinh);
                    adapterHocSinh.notifyDataSetChanged();
                    Log.d("BBB","ten hoc vien : "+ hocSinh.getHoTen() + " Nam Sinh "+ hocSinh.getNamSinh() + " Dia Diem : " + hocSinh.getDiaChi());
                }
             return true;
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_them,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuinsert:{
                Intent intent = new Intent(MainActivity.this,Them.class);
                startActivity(intent);
                break;
            }
        }



        return super.onOptionsItemSelected(item);
    }
}
