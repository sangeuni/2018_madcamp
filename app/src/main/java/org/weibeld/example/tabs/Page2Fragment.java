package org.weibeld.example.tabs;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.weibeld.example.R;

import java.util.ArrayList;
import java.util.HashMap;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter;

import com.bumptech.glide.Glide;

import java.io.File;
import static android.os.Environment.DIRECTORY_PICTURES;

/* Fragment used as page 2 */
public class Page2Fragment extends Fragment {
    GridView galleryGridView;
    ArrayList<TagsImg> f = new ArrayList<TagsImg>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page2, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridView);

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
        GridViewAdapter adapter = new GridViewAdapter(this.getActivity(), R.layout.album_row, f);
        gridView.setAdapter(adapter);

        return view;
    }
}
