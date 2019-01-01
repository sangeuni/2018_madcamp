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

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        getData(); //set f after f.clear();

        GridViewAdapter adapter = new GridViewAdapter(this.getActivity(), R.layout.album_row, f);
        gridView.setAdapter(adapter);

        view.findViewById(R.id.textView0).setOnClickListener(this); //모든 사진 태그는 일단 보류
        view.findViewById(R.id.textView1).setOnClickListener(this);
        view.findViewById(R.id.textView2).setOnClickListener(this);
        view.findViewById(R.id.textView3).setOnClickListener(this);
        view.findViewById(R.id.textView4).setOnClickListener(this);
        view.findViewById(R.id.textView5).setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        Integer ele = Integer.valueOf(v.getId()) - Integer.valueOf(R.id.textView1); // textview 의 id 가 2131165310, 2131165311, ...
        // v.getId()를 activeTagIds 넣거나 빼기
        if ( activeTagIds.contains(ele) ) { // activate 된 태그면
            activeTagIds.remove(Integer.valueOf(ele)); // 그냥 써버리면 index꺼가 사라짐 ㅜㅜ
            v.setBackgroundResource(R.drawable.rounded_corner_deact);
        }
        else {
            activeTagIds.add(ele);
            v.setBackgroundResource(R.drawable.rounded_corner);
        }

        if (ele == -1) { // 모든 태그 버튼
            for (int i=0 ; i<5 ; i++)
                activeTagIds.remove(Integer.valueOf(i));
            getView().findViewById(R.id.textView1).setBackgroundResource(R.drawable.rounded_corner_deact);
            getView().findViewById(R.id.textView2).setBackgroundResource(R.drawable.rounded_corner_deact);
            getView().findViewById(R.id.textView3).setBackgroundResource(R.drawable.rounded_corner_deact);
            getView().findViewById(R.id.textView4).setBackgroundResource(R.drawable.rounded_corner_deact);
            getView().findViewById(R.id.textView5).setBackgroundResource(R.drawable.rounded_corner_deact);
        }

        // grid view 에 데이터 넣는 부분
        getData();
        GridView gridView = (GridView) getActivity().findViewById(R.id.gridView);
        GridViewAdapter adapter = new GridViewAdapter(this.getActivity(), R.layout.album_row, f);
        gridView.setAdapter(adapter);
    }
    public boolean containActiveTags(JSONObject tags) {
        // if 모든 버튼 clicked 이면, 걍 다 true
        if (activeTagIds.contains(-1)) return true;

        // tags 가 각각의 activeTag 에 대해 모두 true 면 true 반환
        try {
            for (int i = 0; i < activeTagIds.size(); i++) {
                if (!tags.getBoolean(String.valueOf(activeTagIds.get(i)))) return false;
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void getData() {
        // f 비우기
        f.clear();
        try {
            JSONObject jsonObject = new JSONObject(loadJSONFromAssets()); // 전체 파일
            JSONArray jsonArray = jsonObject.getJSONArray("TagsImg"); // 목록들
            int count = 0;
            int img;
            JSONObject tags;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                img = object.getInt("img");
                tags = object.getJSONObject("tags");
                if (containActiveTags(tags)) f.add(img);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getActivity(), String.valueOf(activeTagIds) + String.valueOf(f.size()), Toast.LENGTH_SHORT).show();
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
