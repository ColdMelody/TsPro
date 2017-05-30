package com.example.bcp.tspro;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class PlayerActivity extends Activity implements View.OnClickListener, MediaPlayer.OnErrorListener {
    MediaPlayer mediaPlayer;
    Button mBtnPlay;
    Button mBtnPause;
    Button mBtnStop;
    boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mBtnPlay = (Button) findViewById(R.id.btn_play_music);
        mBtnPause = (Button) findViewById(R.id.btn_pause_music);
        mBtnStop = (Button) findViewById(R.id.btn_stop_music);
        initListener();
        initRawMedia();
    }

    private void initListener() {
        mBtnPlay.setOnClickListener(this);
        mBtnPause.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    private void initRemoteMedia(String url) {
        mediaPlayer = new MediaPlayer();/*Idle state*/
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);/*Initialized state*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {/*Prepared state*/
                mediaPlayer.start();/*Started state*/
            }
        });
    }

    private void initRawMedia() {
        /*调用create方法，播放器直接进入Prepared状态*/
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.lucky);

    }

    private void isStarted() {
        started = mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_play_music:
                /*调用create方法，播放器直接进入Prepared状态*/
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.lucky);
                /*Started state*/
                mediaPlayer.start();
                break;
            case R.id.btn_pause_music:
                /*Paused state*/
                mediaPlayer.pause();
                break;
            case R.id.btn_stop_music:
                /*Stop state*/
                mediaPlayer.stop();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            /*End state*/
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        /*Error state,wo do nothing*/
        return false;
    }
}
