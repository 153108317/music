package com.example.zuoye;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class MusicService extends Service {
        private MyReceiver serviceReceiver ;
        private MediaPlayer mediaPlayer;
        private int status = MusicBoxConstant.IDLE;
        private AssetManager assetManager ;
        private int current = 0;


    public static  String[] musics = new String[]{"Feder,Alex Aiono - Lordly.mp3"};
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        assetManager = this.getAssets();
        serviceReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicBoxConstant.ACTION_CTL);
        registerReceiver(serviceReceiver,filter);
        mediaPlayer = new MediaPlayer();

        super.onCreate();


    }

    public class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int control = intent.getIntExtra("control",-1);
            int status = intent.getIntExtra("status",-1);
            switch (control){
                case 1:
                    if(status == MusicBoxConstant.IDLE){
                        //播放音乐,发送ui
                        prepareAndPlay(musics[current]);
                        status = MusicBoxConstant.IDLE;
                    }else if (status == MusicBoxConstant.PAUSING){
                        pausePlaying();
                        status = MusicBoxConstant.PAUSING;
                    }else {
                        startPlaying();
                        status = MusicBoxConstant.START;
                    }
                    break;
                case 2:
                    if (status == MusicBoxConstant.IDLE || status == MusicBoxConstant.PAUSING || MusicBoxConstant.START == status){
                        stopPlaying();
                        status = MusicBoxConstant.STOP;
                    }
                    break;
            }
            Intent sendIntent = new Intent(MusicBoxConstant.ACTION_UPDATE);
            sendIntent.putExtra(MusicBoxConstant.TOKEN_UPDATE,status);
            sendIntent.putExtra(MusicBoxConstant.TOKEN_CURRENT,current);
            MusicService.this.sendBroadcast(sendIntent);
        }
    }
    private void prepareAndPlay(String music){
        try{
            AssetFileDescriptor   afd = assetManager.openFd(music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());


            mediaPlayer.prepare();
            mediaPlayer.start();


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void pausePlaying(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }
    private void stopPlaying(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
    private void startPlaying(){
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }
    public void onDestroy() {
        super.onDestroy();
    }
}
