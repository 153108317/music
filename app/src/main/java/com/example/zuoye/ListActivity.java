package com.example.zuoye;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListActivity extends Activity {
    private ListView lv;
    private MyAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        lv = findViewById(R.id.lv);
        listAdapter = new MyAdapter(this, list);
        lv.setAdapter(listAdapter);
        MyContentProvider.AUTHORITY = getString(R.string.myprovider);
        getProvider();

    }

    private ArrayList<String> list = new ArrayList();

    public void getProvider() {
        Uri uri = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/" + DbHelp.TABLE_NAME);
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "name"}, null, null, null);
            if (cursor != null) {
                list.clear();
                while (cursor.moveToNext()) {
                    Log.e("xxxx", "ID:" + cursor.getInt(cursor.getColumnIndex("_id"))
                            + "  name:" + cursor.getString(cursor.getColumnIndex("name")));
                    list.add(cursor.getString(cursor.getColumnIndex("name")));
                }

                cursor.close();
                handler.sendEmptyMessage(2);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(2);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    listAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
