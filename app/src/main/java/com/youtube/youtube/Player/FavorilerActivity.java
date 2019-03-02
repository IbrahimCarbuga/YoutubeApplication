package com.youtube.youtube.Player;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class FavorilerActivity extends AppCompatActivity {
//This page shows favourite videos ID
    private ListView listView;
private ArrayList arrayList;

    public void Listele(){
        Veritabani vt = new Veritabani(FavorilerActivity.this);
        List<String> list = vt.VeriListele();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FavorilerActivity.this, android.R.layout.simple_list_item_1,android.R.id.text1,list);
        listView.setAdapter(adapter);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favori);
        Intent i=getIntent();
        listView=(ListView)findViewById(R.id.videos_favori);
        Listele();
        }


}