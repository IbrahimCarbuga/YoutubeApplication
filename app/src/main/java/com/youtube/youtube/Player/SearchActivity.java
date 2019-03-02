package com.youtube.youtube.Player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    private EditText searchInput;
    private ListView videosFound;
    private List<VideoItem> searchResults;
    private Handler handler;
    SearchView editsearch;
    Intent i=getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editsearch=(SearchView) findViewById(R.id.search);
        editsearch.setIconified(false);
        editsearch.setOnQueryTextListener(SearchActivity.this);
        editsearch.setOnCloseListener(SearchActivity.this);
        videosFound=(ListView) findViewById(R.id.videos_found);
        handler=new Handler();

    }
    private void searchOnYoutube(final String keywords){
        new Thread(new Runnable() {
            @Override
            public void run() {
                YoutubeConnector yc=new YoutubeConnector(SearchActivity.this);
                searchResults=yc.search(keywords);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }).start();

    }
    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter=new ArrayAdapter<VideoItem>(getApplicationContext(),R.layout.view_video_item,searchResults){
            @Override
            public View getView(final int position, View convertView, ViewGroup parent){
                if(convertView==null){
                    convertView=getLayoutInflater().inflate(R.layout.view_video_item,parent,false);
                }
                ImageView thumbnail=(ImageView) convertView.findViewById(R.id.video_thumbnail);
                TextView title=(TextView) convertView.findViewById(R.id.video_title);
                VideoItem searchResult=searchResults.get(position);
                final String videoId=searchResults.get(position).getId();
                final String videoImageUrl=searchResults.get(position).getThumbnailURL();
                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("VIDEO_ID", videoId);
                        intent.putExtra("VIDEO_IMAGE", videoImageUrl);

                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getApplicationContext(),YoutubePlayerActivity1.class);
                        intent.putExtra("VIDEO_ID",searchResults.get(position).getId());
                        startActivity(intent);
                    }
                });
            return convertView;
            }
        };


        if(searchResults!=null && !searchResults.isEmpty()){
            videosFound.setAdapter(adapter);

        } else if (searchResults != null){
            videosFound.setAdapter(adapter);
        }
    }
       public  void hideKeyboard(View view){

      InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }





    @Override
    public boolean onQueryTextChange(final String newText) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    String getSearchText = editsearch.getQuery().toString();
                    if (getSearchText.length() > 0) {
                        searchOnYoutube(getSearchText);
                    } else {
                        if (searchResults != null) {
                            searchResults.clear();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateVideosFound();
                            }
                        });
                    }
                }

            }

        });
        thread.start();
        return false;

    }
    private void closeKeyboard(){
            View currentFocus=this.getCurrentFocus();
            if(currentFocus!=null){
                InputMethodManager imm=(InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(),0);
            }
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    @Override
    public boolean onClose() {
        Log.d("onCloseKeyboard","onCloseKeyboard");
        closeKeyboard();
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        closeKeyboard();
        return false;
    }
    public void onEvent(String eventName, JSONObject eventData){

    }

}
