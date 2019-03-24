package com.example.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    MediaPlayer player;
    MediaPlayer plater1;
    TextView songTxt;
    TextView artistTxt;
    ArrayList<Song> arrayList;
    Context context;

    public SongAdapter(ArrayList<Song> arr, Context context) {
        this.arrayList = arr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Song getItem(int c) {
        return arrayList.get(c);
    }

    @Override
    public long getItemId(int c) {
        return c;
    }

    @Override
    public View getView(int c, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        Song currentSong = getItem(c);
        songTxt = view.findViewById(R.id.songName);
        artistTxt = view.findViewById(R.id.artistName);
        songTxt.setText(currentSong.getName());
        artistTxt.setText(currentSong.getSongArtist());
        player = new MediaPlayer();
        plater1 = new MediaPlayer();
        return view;
    }

    AdapterView.OnItemClickListener lol = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Song current = getItem(position);
            if (current.getSongUri() != null) {

                if (!player.isPlaying()&&(plater1.isPlaying()||!plater1.isPlaying())) { try {
                    plater1.stop();
                    player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(current.getPath());
                    player.prepare();
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer player) {

                        }
                    }
                    );} catch (IOException e) {
                    e.printStackTrace();
                }

                        player.start();
                    } else if (!plater1.isPlaying()&&player.isPlaying()) {
                        player.stop();
                        plater1 = new MediaPlayer();
                        plater1.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        try {
                            plater1.setDataSource(current.getPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            plater1.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        plater1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {

                            }
                        });
                        plater1.start(); }

            }

            }

    };}
