package com.example.myapplication;

import android.net.Uri;

public class Song {
    String songname ;
    String songArtist ;
    String duration ;
    Uri songUri ;
    Song(String song , String artist , String path, Uri uri){
        this.songname = song ;
        this.songArtist = artist ;
        this.duration = path;
        this.songUri = uri ;
    }

        String getName(){return songname;}
        String getSongArtist(){return songArtist;}
        String getPath(){return duration;}
        Uri getSongUri(){return songUri;}


}
