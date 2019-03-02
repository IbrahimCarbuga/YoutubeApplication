package com.youtube.youtube.Player;

import android.content.Intent;
import android.net.MailTo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button btnarama,btnfavori;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnarama=(Button)findViewById(R.id.btnarama);
        btnfavori=(Button)findViewById(R.id.btnfavori);
        Intent i = getIntent();

        btnarama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        btnfavori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FavorilerActivity.class);

                startActivity(intent);
            }
        });
    }
}
