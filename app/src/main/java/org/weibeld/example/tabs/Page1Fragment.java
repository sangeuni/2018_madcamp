package org.weibeld.example.tabs;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.weibeld.example.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* Fragment used as page 1 */
public class Page1Fragment extends Fragment {

    private ListView listView;
    private NameListAdapter adapter;
    private List<NameListItem> nameList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page1, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview);
        nameList = new ArrayList<NameListItem>();
        adapter = new NameListAdapter(getContext().getApplicationContext(), nameList);
        listView.setAdapter(adapter);
        String[] items = new String[]{"item1", "item2", "item3"};

        try {
            nameList.clear();
            JSONObject jsonObject = new JSONObject(loadJSONFromAssets()); // 전체 파일
            JSONArray jsonArray = jsonObject.getJSONArray("users"); // 목록들
            int count = 0;
            String name;
            while (count < jsonArray.length()) {

                JSONObject object = jsonArray.getJSONObject(count);
                name = object.getString("name");
                NameListItem item = new NameListItem(name);
                nameList.add(item);
                count++;
            }
            if (count == 0) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(Page1Fragment.this.getActivity());
                dialog = builder.setMessage("등록된 전화번호가 없습니다.")
                        .setPositiveButton("확인", null)
                        .create();
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rootView;
    }


    public String loadJSONFromAssets() {
        String json = null;
        AssetManager assetManager = getResources().getAssets();
        try {
            InputStream inputStream = assetManager.open("document.json");
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
