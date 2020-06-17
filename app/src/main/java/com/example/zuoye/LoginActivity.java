package com.example.zuoye;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private MyDBHelper dbHelper;
    private EditText username;
    private EditText userpassword;
    private LoginActivity loginActivity;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button regiter = findViewById(R.id.regis);
        Button login = findViewById(R.id.login);
        loginActivity = this;

        dbHelper = new MyDBHelper(this, "UserStore.db", null, 1);
        regiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = (EditText) findViewById(R.id.account_input);
                userpassword = (EditText) findViewById(R.id.password_input);
                String userName = username.getText().toString();
                String passWord = userpassword.getText().toString();
                if (login(userName, passWord)) {
                    Toast.makeText(LoginActivity.this, "登录成功（" + userName + "，" + passWord + "）", Toast.LENGTH_SHORT).show();
//                  Intent it=new Intent(loginActivity, MusicActivity.class);
//
//                    it.putExtra("name",name);
//                    it.putExtra("author","无名");
//                    loginActivity.startActivity(it);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getProvider();
    }
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



    public boolean login(String username, String password) {
        if(true){
            return true;
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from userData where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }


}
