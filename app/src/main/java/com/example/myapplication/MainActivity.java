package com.example.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

public class MainActivity extends AppCompatActivity {
            ArrayList<Song> arrList ;
            ArrayAdapter<String> adapter ;
            ListView list ;
            private final static int MY_PERMISSION_REQUAEST = 1 ;

                @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]
                       {Manifest.permission.READ_EXTERNAL_STORAGE },
                       MY_PERMISSION_REQUAEST);
  //************************************************************************************************************************
            }else{               ActivityCompat.requestPermissions(MainActivity.this,new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE },
                    MY_PERMISSION_REQUAEST);

            }

        }else {new doTheStuff().execute();


        }
                }

                public void doTheThing(final ArrayList<Song> arr){
                   list = findViewById(R.id.listView);
                    final Context context = getApplicationContext();
                     final SongAdapter songAdapter = new SongAdapter(arr,context);
                    list.setAdapter(songAdapter);
                    AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    };
                        list.setOnItemClickListener(songAdapter.lol);
                }

         public ArrayList<Song> getMusicInfo() {
             ContentResolver contentResolver = getContentResolver();
             Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
             Cursor mCursor = contentResolver.query(songUri, null, null, null, null);


             if (mCursor != null && mCursor.moveToFirst()) {

                 int songTitle = mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                 int artistTitle = mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                 arrList = new ArrayList<Song>();
                 do {

                     String songName = mCursor.getString(songTitle);
                     String artistName = mCursor.getString(artistTitle);
                     int duration = mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                     Uri sUri = MediaStore.Audio.Media.getContentUri(songName);
                     int intpath = mCursor.getColumnIndex(MediaStore.Audio.Media
                     .DATA);
                     String path = mCursor.getString(intpath);
                     Song song = new Song(songName, artistName, path, sUri);

                     arrList.add(song);
                 }
                 while (mCursor.moveToNext());
             }


         return arrList;}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUAEST : if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                      Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                        new doTheStuff().execute();
                    }

            }else {Toast.makeText(this,"Permission Not Granted",Toast.LENGTH_SHORT).show();
                    finish();
            }return;
        }

    }

    class doTheStuff extends AsyncTask<String,String[],ArrayList<Song>> {

        @Override
        protected ArrayList<Song> doInBackground(String... strings) {
            return getMusicInfo();
        }

        @Override
        protected void onPostExecute(ArrayList<Song> strings) {
        if(strings != null){
        doTheThing(strings);
            if (strings.size() >190){
                Log.d("MyActivity","yes it's");
            }

        }else Toast.makeText(MainActivity.this,"Strings is empty",Toast.LENGTH_LONG).show();        }
    }


}