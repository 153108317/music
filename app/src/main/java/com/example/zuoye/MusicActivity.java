package com.example.zuoye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MusicActivity extends AppCompatActivity {
    ImageButton play;
    ImageButton stop;
    TextView title;
    TextView author;
    private ActivityReceiver filter;
    private int next_status = 1;
    private Intent actionIntent;
    private MusicActivity context;
    private String sauthor;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        title = this.findViewById(R.id.title);
        stop = this.findViewById(R.id.stop);
        title = this.findViewById(R.id.title);
        author = this.findViewById(R.id.another);
        play = this.findViewById(R.id.play);
        stop = this.findViewById(R.id.stop);
        sauthor = getIntent().getStringExtra("author");
        name = getIntent().getStringExtra("name");
        if (name == null) {
            name = "好听的歌";
            title.setText("" + name);
            author.setText("" );
        }else {
            title.setText("" + name);
            author.setText("" );
        }

        if (name.equals("Feder,Alex Aiono - Lordly")) {
            MusicService.musics = new String[]{"Feder,Alex Aiono - Lordly.mp3"};
        }
//        else {
//            MusicService.musics = new String[]{"bucai1.mp3"};
//        }
        //创建一个BroardCast Receiver
        Intent actionIntent = new Intent(this, MusicService.class);
        actionIntent.setAction("android.intent.action.RESPOND_VIA_MESSAGE");
        MusicActivity.this.startService(actionIntent);
        filter = new ActivityReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicBoxConstant.ACTION_UPDATE);
        this.registerReceiver(filter, intentFilter);
        MusicListener musicListener = new MusicListener();
        play.setOnClickListener(musicListener);
        stop.setOnClickListener(musicListener);
     TextView tv=   findViewById(R.id.tv_forward);
//     tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(context, SaveInformationActivity.class);
//                context.startActivity(it);
//            }
//        });
        tv.setVisibility(View.GONE);
        tv.setText("播放视频");
     tv.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent it = new Intent(context, PlayActivity.class);
             context.startActivity(it);
         }
     });
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(MusicBoxConstant.ACTION_UPDATE);
                sendIntent.putExtra("update", -2);
                sendIntent.putExtra("current", -3);
                context.sendBroadcast(sendIntent);
            }
        });
        findViewById(R.id.tv_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(context, PlayActivity.class);
                context.startActivity(it);
            }
        });
//        getProvider();
    }


    public class ActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Intent stopIntent = new Intent(MusicActivity.this, MusicService.class);
            int update = intent.getIntExtra("update", -1);
            int current = intent.getIntExtra("current", -1);
            Log.e("xxxxx", "update" + update + "current" + current);
            if (current >= 0) {
                title.setText("" + name);
                author.setText("" + sauthor);
            }
            switch (update) {
                case MusicBoxConstant.PAUSING:
                    play.setImageResource(R.drawable.start1);
                    next_status = MusicBoxConstant.START;
                    break;
                case MusicBoxConstant.IDLE:
                    play.setImageResource(R.drawable.stop);
                    next_status = MusicBoxConstant.PAUSING;
                    break;
                case MusicBoxConstant.START:
                    play.setImageResource(R.drawable.stop);
                    next_status = MusicBoxConstant.PAUSING;
                    break;
                case MusicBoxConstant.STOP:
                    play.setImageResource(R.drawable.start1);
                    next_status = MusicBoxConstant.IDLE;
                    break;
            }
        }
    }

    // 播放，暂停 按钮点击事件

    class MusicListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MusicBoxConstant.ACTION_CTL);
            switch (v.getId()) {
                case R.id.play:
                    intent.putExtra("control", 1);
                    intent.putExtra("status", next_status);
                    break;
                case R.id.stop:
                    intent.putExtra("control", 2);
                    intent.putExtra("status", next_status);
                    break;
            }
            //把点击状态传送到Service 类里面的广播去处理我们的播放事件
            MusicActivity.this.sendBroadcast(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(MusicBoxConstant.ACTION_CTL);
        intent.putExtra("control", 2);
        intent.putExtra("status", next_status);
        MusicActivity.this.sendBroadcast(intent);
    }
}
