package org.weibeld.example.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import org.weibeld.example.R;
import android.widget.ArrayAdapter;
import android.app.Activity;

import java.util.ArrayList;

class GridViewAdapter extends BaseAdapter   {
    private Context context;
    private int layoutResourceId;
    private ArrayList<TagsImg> data = new ArrayList<TagsImg>();
    int layout;
    LayoutInflater inf;

    public GridViewAdapter(Context context, int layout, ArrayList<TagsImg> data) {
        this.context = context;
        this.data = data;
        this.layout = layout;
        this.inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() { // 총 데이터의 개수를 리턴
        return data.size();
    }
    @Override
    public Object getItem(int position) { // 해당번째의 데이터 값
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = inf.inflate(layout, null);

        ImageView iv = (ImageView)convertView.findViewById(R.id.image);
        Object m = data.get(position).getImg();
        iv.setImageResource((Integer) m);
        return convertView;

    }

}

