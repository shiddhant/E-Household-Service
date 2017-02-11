package com.shiddhant.files.ehouseholdservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Custome Created class.
 * But we have to implement the 4 override method of BaseAdapter class
 */

public class CustomeGrid extends BaseAdapter {
    private Context mContext;
    ArrayList<String> service_title;

    private final int[] var_service_image;

    /**
     * Constructor to initialize the value which is passed from TabActivity java class */
    public CustomeGrid(Context c, ArrayList<String> service_title, int[] service_image) {
        mContext = c;
        this.service_title = service_title;
        var_service_image = service_image;
    }

    @Override
    public int getCount() {
        return service_title.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View var_view_grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            var_view_grid = new View(mContext);
            var_view_grid = inflater.inflate(R.layout.single_layout_grid, null);
            ImageView iv = (ImageView) var_view_grid.findViewById(R.id.iv_single_image_id);
            TextView tv = (TextView) var_view_grid.findViewById(R.id.tv_single_title);
            tv.setText(service_title.get(position));
            iv.setImageResource(var_service_image[position]);
        }else{
            var_view_grid = (View) convertView;
        }
        return var_view_grid;
    }
}
