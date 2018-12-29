package org.weibeld.example.tabs;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.weibeld.example.R;
import java.util.ArrayList;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


/* Fragment used as page 2 */
public class Page2Fragment extends Fragment implements View.OnClickListener {
    GridView galleryGridView;
    ArrayList<TagsImg> f = new ArrayList<TagsImg>();
    ArrayList<Integer> activeTagIds = new ArrayList<Integer>();
    int test=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        Toast.makeText(getActivity(), "test is "+String.valueOf(activeTagIds), Toast.LENGTH_SHORT).show();


        coloringActiveTags();
        getData(); //set f after f.clear();

        GridViewAdapter adapter = new GridViewAdapter(this.getActivity(), R.layout.album_row, f);
        gridView.setAdapter(adapter);

        TextView one = (TextView) view.findViewById(R.id.textView0);
        one.setOnClickListener(this); // calling onClick() method
        TextView two = (TextView) view.findViewById(R.id.textView1);
        two.setOnClickListener(this); // calling onClick() method
        TextView three = (TextView) view.findViewById(R.id.textView2);
        three.setOnClickListener(this); // calling onClick() method
        TextView four = (TextView) view.findViewById(R.id.textView3);
        four.setOnClickListener(this); // calling onClick() method
        TextView five = (TextView) view.findViewById(R.id.textView4);
        five.setOnClickListener(this); // calling onClick() method

        return view;
    }
    public void coloringActiveTags() {

    }
    public void refresh() {
        Fragment frg = null;
        frg = this;
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
    public void onClick(View v) {
        // v.getId()를 activeTagIds 넣거나 빼기
        if ( activeTagIds.contains(v.getId()) ) {
            activeTagIds.remove(Integer.valueOf(v.getId())); // 그냥 써버리면 index꺼가 사라짐 ㅜㅜ
        }
        else activeTagIds.add(v.getId());

        refresh();
    }
    public void getData() {
        // f 비우기
        f.clear();
        // TODO activeTagIds 에 따라서 data 넣기

        // 친구, 상속자들, 여행, 특별한 순간
        f.add(new TagsImg(R.drawable.a1, new Integer[]{0, 2}));
        f.add(new TagsImg(R.drawable.a2, new Integer[]{0 }));
        f.add(new TagsImg(R.drawable.a3, new Integer[]{0 }));
        f.add(new TagsImg(R.drawable.a4, new Integer[]{0 }));
        f.add(new TagsImg(R.drawable.a5, new Integer[]{0 }));
        f.add(new TagsImg(R.drawable.a6, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a7, new Integer[]{2 }));
        f.add(new TagsImg(R.drawable.a8, new Integer[]{2 }));
        f.add(new TagsImg(R.drawable.a9, new Integer[]{2,3 }));
        f.add(new TagsImg(R.drawable.a10, new Integer[]{0 }));
        f.add(new TagsImg(R.drawable.a11, new Integer[]{1,3 }));
        /*
        f.add(new TagsImg(R.drawable.a12, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a13, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a14, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a15, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a16, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a17, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a18, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a19, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a20, new Integer[]{ }));
        f.add(new TagsImg(R.drawable.a21, new Integer[]{ }));
         */
    }

}
