package com.example.zuoye;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private Context c;
    ArrayList<String> list;


    public MyAdapter(Context c, ArrayList<String> list) {
        this.c = c;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(0);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(c).inflate(R.layout.item_list, null);
            viewHolder.tv = convertView.findViewById(R.id.tv_name);
            viewHolder.tv_author = convertView.findViewById(R.id.tv_autor);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv
                .setText(list.get(i));
        if(list.get(i).equals("少年")){
            viewHolder.tv_author.setText("宋小睿");
        }else {
            viewHolder.tv_author.setText("東青");
        }
        final ViewHolder finalViewHolder = viewHolder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(c, MusicActivity.class);
                it.putExtra("name", finalViewHolder.tv.getText().toString());
                it.putExtra("author", finalViewHolder.tv_author.getText().toString());

                c.startActivity(it);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv;
        TextView tv_author;
    }
}
