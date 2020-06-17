package com.example.zuoye;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity{

    private MyDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button = findViewById(R.id.register);
        Button login = findViewById(R.id.login_activity);
        dbHelper = new MyDBHelper(this,"UserStore.db",null,1);
        button.setOnClickListener(new MyListener());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Toast.makeText(RegisterActivity.this,"you have clicked Button1",Toast.LENGTH_SHORT).show();
            EditText editText3=(EditText)findViewById(R.id.editText3);
            EditText editText4=(EditText)findViewById(R.id.editText4);
            String newname =editText3.getText().toString();
            String password=editText4.getText().toString();
            if (CheckIsDataAlreadyInDBorNot(newname)) {
                Toast.makeText(RegisterActivity.this,"该用户名已被注册，注册失败",Toast.LENGTH_SHORT).show();
            }else{
                if(register(newname,password)){
                    Toast.makeText(RegisterActivity.this, "插入数据表成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    public boolean CheckIsDataAlreadyInDBorNot(String name){

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql = "Select * from userData where name=?";
        Cursor cursor = db.rawQuery(sql,new String[]{name});
        if(cursor.getCount()>0){
            cursor.close();
            return true;
        }
        cursor.close();
        Toast.makeText(this, "没有账号，需要注册", Toast.LENGTH_SHORT).show();
       // Cursor cursor = db.query("userData", null, null, null, null, null, null);
        return false;
    }
    public boolean register(String username,String password){
        SQLiteDatabase db= dbHelper.getWritableDatabase();

        db.execSQL("insert into userData (name,password) values (?,?)",new String[]{username,password});
        Toast.makeText(this, "没有账号，需要注册", Toast.LENGTH_SHORT).show();
        return true;
    }

}
