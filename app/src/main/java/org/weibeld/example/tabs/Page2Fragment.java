package org.weibeld.example.tabs;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


/* Fragment used as page 2 */
public class Page2Fragment extends Fragment implements View.OnClickListener {
    GridView galleryGridView;
    ArrayList<Integer> f = new ArrayList<Integer>();
    ArrayList<Integer> activeTagIds = new ArrayList<Integer>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Toast.makeText(getActivity(), "refreshed", Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        getData(); //set f after f.clear();

        GridViewAdapter adapter = new GridViewAdapter(this.getActivity(), R.layout.album_row, f);
        gridView.setAdapter(adapter);

        view.findViewById(R.id.textView0).setOnClickListener(this);
        view.findViewById(R.id.textView1).setOnClickListener(this);
        view.findViewById(R.id.textView2).setOnClickListener(this);
        view.findViewById(R.id.textView3).setOnClickListener(this);
        view.findViewById(R.id.textView4).setOnClickListener(this);
        view.findViewById(R.id.textView5).setOnClickListener(this);
        return view;
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
        Integer ele = Integer.valueOf(v.getId()) - 2131165310; // textview 의 id 가 2131165310, 2131165311, ...
        // v.getId()를 activeTagIds 넣거나 빼기
        if ( activeTagIds.contains(ele) ) { // activate 된 태그면
            activeTagIds.remove(Integer.valueOf(ele)); // 그냥 써버리면 index꺼가 사라짐 ㅜㅜ
            v.setBackgroundResource(R.drawable.rounded_corner_deact);
        }
        else {
            activeTagIds.add(ele);
            v.setBackgroundResource(R.drawable.rounded_corner);
        }

        //Toast.makeText(getActivity(), String.valueOf(activeTagIds), Toast.LENGTH_SHORT).show();

        refresh();
    }
    public boolean containActiveTags(JSONObject tags) {
        try {
            for (int i = 0; i < activeTagIds.size(); i++) {
                if (tags.getBoolean(String.valueOf(activeTagIds.get(i)))) return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void getData() {
        // f 비우기
        f.clear();
        // TODO activeTagIds 에 따라서 data 넣기
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAssets()); // 전체 파일
            JSONArray jsonArray = jsonObject.getJSONArray("TagsImg"); // 목록들
            int count = 0;
            Integer img;
            JSONObject tags;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                img = object.getInt("Img");
                tags = object.getJSONObject("tags");
                Log.e("json의 tags", String.valueOf(tags));
                if (containActiveTags(tags)) f.add(img);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getActivity(), String.valueOf(f), Toast.LENGTH_SHORT).show();

    }

    public String loadJSONFromAssets() {
        String json = null;
        AssetManager assetManager = getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open("document2.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}
