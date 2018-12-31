package org.weibeld.example.tabs;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.weibeld.example.R;

import java.util.List;

public class NameListAdapter extends BaseAdapter{

    private Context context;
    private List<NameListItem> contactList;

    public NameListAdapter(Context context, List<NameListItem> contactList ){
        this.context =context;
        this.contactList = contactList;
    }
    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public String getItem(int i) {
        return contactList.get(i).getName();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.nameitem, null) ;
        TextView itemText= (TextView)v.findViewById(R.id.list_item);
        itemText.setText(contactList.get(i).getName());

        int weight = contactList.get(i).getWeight();
        if (weight>3) {
            itemText.setTextSize(20);
            itemText.setTypeface(null, Typeface.BOLD);
        }

        return v;
    }

}
