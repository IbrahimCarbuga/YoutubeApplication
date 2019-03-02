package com.youtube.youtube.Player;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class YoutubePlayerActivity1 extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView playerView;
    Button btngeri,btnekle,btngit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        playerView=(YouTubePlayerView) findViewById(R.id.player_view);
        btngeri=(Button)findViewById(R.id.btngeri);
        btnekle=(Button)findViewById(R.id.btnekle);
        btngit=(Button)findViewById(R.id.btngit);
        final  ListView veriler=(ListView)findViewById(R.id.videos_favori) ;

        playerView.initialize(YoutubeConnector.KEY,this);
        btngeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
//---------------------------------------------------------------------------------------------------
        btngit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent= new Intent(YoutubePlayerActivity1.this,FavorilerActivity.class);
                startActivity(intent);
            }
        });
        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=getIntent();
                String videoid=intent.getExtras().getString("VIDEO_ID");
           //     String title=intent.getExtras().getString("TITLE");
             //   String thumbnailURL=intent.getExtras().getString("VIDEO_IMAGE");

                Veritabani veritabani = new Veritabani(YoutubePlayerActivity1.this);
               veritabani.VeriEkle(videoid);
                Toast.makeText(getApplicationContext(),"Favorilere eklendi.",Toast.LENGTH_SHORT).show();

            }
        });

    }
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
            if(!restored){
                player.cueVideo(getIntent().getStringExtra("VIDEO_ID"));
       //         player.cueVideo(getIntent().getStringExtra("TITLE"));
            //    player.cueVideo(getIntent().getStringExtra("THUMBNAILURL"));
            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {

        }



    }

