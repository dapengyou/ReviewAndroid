package com.test.reviewandroid.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.test.reviewandroid.R;
import com.test.reviewandroid.bean.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlarmActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private List<Music> musicList = new ArrayList<>(); // 创建一个集合存储读取的歌曲信息


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
//        getMusicInfo();
        requestPermission();
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
//            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    mMediaPlayer.start();
//
//                }
//            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        randomPlay(musicList);

    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/10
     * @author lady_zhou
     * @Description 动态申请权限
     */
    private void requestPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //表示未授权时
            //进行授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getMusicInfo();
        }
    }

    //prepare方法是将资源同步缓存到内存中,一般加载本地较小的资源可以用这个,如果是较大的资源或者网络资源建议使用prepareAsync方法,异步加载
    private void randomPlay(List<Music> musicList) {
        int key;
        Random random = new Random();
        if (musicList.size() != 0) {
            key = random.nextInt(musicList.size() - 1);
            //从sd卡中加载音乐
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(musicList.get(key).getPath());
                //需使用异步缓
//            mMediaPlayer.prepareAsync();
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }

    }

    private List<Music> getMusicInfo() {

        //读取数据库中歌曲信息，获取手机内存上的音乐资源 INTERNAL_CONTENT_URI，，外置存储卡中的音乐信息EXTERNAL_CONTENT_URI
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (cursor.getCount() != 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                Music music = new Music();
                cursor.moveToNext();
                // id
                music.setId(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                // 歌曲
                music.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                // 歌手
                music.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                // 时长
                music.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                // 文件大小
                music.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
                // 路径
                music.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));

                // 将资源为音乐的媒体文件存储到集合中
                if (cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC)) != 0) {
                    musicList.add(music);
                }
            }

        } else {
            Toast.makeText(this, "暂无歌曲", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        Log.d("Music", "musicList: " + musicList.size());
        return musicList;

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.stop();
        mMediaPlayer.release();
        mMediaPlayer = null;

    }
}
