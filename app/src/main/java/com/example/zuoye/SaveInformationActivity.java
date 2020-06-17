package com.example.zuoye;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SaveInformationActivity extends AppCompatActivity {
    int choice ;
    Button saveBtn;
    EditText name;
    EditText student_number;
    EditText mail;
    EditText phone;
    TextView sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saveinformation);

        Button saveBtn = findViewById(R.id.tv_forward);
         name = findViewById(R.id.title_tv);
         student_number = findViewById(R.id.student_id);
         mail = findViewById(R.id.mail_id);
         phone = findViewById(R.id.phone_id);

        SharedPreferences sp = getSharedPreferences("save", Context.MODE_PRIVATE);
        String et_name = sp.getString("name",null);
        name.setText(et_name);
        String et_student_number = sp.getString("student_number",null);
        student_number.setText(et_student_number);
        String et_mail = sp.getString("mail",null);
        mail.setText(et_mail);
        String et_phone = sp.getString("phone",null);
        phone.setText(et_phone);
        sex = (TextView) findViewById(R.id.edit_sex);
        String et_sex = sp.getString("sex","男");
        sex.setText(et_sex);
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog();
            }
        });
        saveBtn.setOnClickListener(new SaveClickListener());
    }
    protected void onCreateDialog() {

                Dialog dialog = null;
                final CharSequence[] items =new String[] {"男","女"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SaveInformationActivity.this);
                builder.setTitle("选择性别");
                int s=0;
                if(sex.getText().toString().equals("女")){
                    s=1;
                }
                builder.setSingleChoiceItems(items, s, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice=which;
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choice!=-1) {
                            TextView text = (TextView) findViewById(R.id.edit_sex);
                            text.setText(items[choice]);
                            Toast.makeText(SaveInformationActivity.this,
                                    "你选择了" + items[choice],
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (choice==-1)
                        {
                            Toast.makeText(SaveInformationActivity.this,
                                    "错误",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     Toast.makeText(SaveInformationActivity.this,
                             "取消",
                             Toast.LENGTH_SHORT).show();
                     }
                });
                dialog = builder.create();
                dialog.show();
    }
    private class SaveClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            SharedPreferences sharedPreferences = getSharedPreferences("save", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor =  sharedPreferences.edit();
            String  student_name  =  name.getText().toString();
            String   stu_number = student_number.getText().toString();
            String  ed_mail = mail.getText().toString();
            String  ed_phone = phone.getText().toString();
            if(student_name.equals("")||stu_number.equals("")
            ||ed_mail.equals("")
            ||ed_phone.equals("")){
                Toast.makeText(SaveInformationActivity.this,
                        "请输入信息",Toast.LENGTH_SHORT).show();
                return;
            }
            editor.putString("name",student_name);
            editor.putString("student_number",stu_number);
            editor.putString("mail",ed_mail);
            editor.putString("phone",ed_phone);
            editor.putString("sex",sex.getText().toString());
            editor.commit();
            Toast.makeText(SaveInformationActivity.this,
                    "保存成功",Toast.LENGTH_SHORT).show();
        }
    }
}
