package com.youtube.youtube.Player;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper{
    public static final String DATABASE_NAME= "veritabani";
    private static final int DATABASE_VERSION=1;
    public static final String FAVORILER_TABLE="favoriler";
    public static final String ROW_ID="id";
    public static final String ROW_VIDEOID="video_id";
  //  public static final String ROW_TITLE="title";
 //   public static final String THUMBNAILURL ="thumbnailURL";


    public Veritabani(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE favoriler(id INTEGER PRIMARY KEY AUTOINCREMENT, video_id TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+ FAVORILER_TABLE);
        onCreate(db);
    }

    public void VeriEkle(String videosId){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ROW_VIDEOID,videosId);


        db.insert(FAVORILER_TABLE,null,cv);
        db.close();
    }





    public List<String> VeriListele(){
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {ROW_VIDEOID};
            Cursor cursor = db.query(FAVORILER_TABLE, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                veriler.add(cursor.getString(0));

            }
        }catch (Exception e){
        }
        db.close();
        return veriler;
    }


}
