package com.example.zuoye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class SelectActivity extends Activity {
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        context=this;
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent it=new Intent(context, MusicActivity.class);

                    it.putExtra("name",name);
                    it.putExtra("author","无名");
                    context.startActivity(it);
            }
        });
        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(context, PlayActivity.class);
                context.startActivity(it);
            }
        });
        getProvider();
        startActivity(new Intent(this,LoginActivity.class));

    }
    private String name;
    public void getProvider() {
        Uri uri = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/" + DbHelp.TABLE_NAME);
        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "name"}, null, null, null);
            if (cursor != null) {

                while (cursor.moveToNext()) {
                    Log.e("xxxx", "ID:" + cursor.getInt(cursor.getColumnIndex("_id"))
                            + "  name:" + cursor.getString(cursor.getColumnIndex("name")));
                    name=cursor.getString(cursor.getColumnIndex("name"));
                }
                cursor.close();
            }
        }
    }
}
