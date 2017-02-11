package com.shiddhant.files.ehouseholdservice;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TabActivity extends AppCompatActivity {
    //Web api url
    public static final String DATA_URL = "http://e-household-service.000webhostapp.com/getdata.php";


    //Tag values to read from json
    public static final String TAG_NAME = "services";



    //ArrayList for Storing image urls and titles

    private ArrayList<String> services_title;

    private Toolbar toolbar;
    GridView var_gridview_service;


    int[] service_image = {
            R.drawable.ic_home_black_48dp,
            R.drawable.ic_event_black_48dp,
            R.drawable.ic_image_black_48dp,
            R.drawable.ic_settings_black_48dp,
            R.drawable.ic_insert_link_black_48dp,
            R.drawable.ic_insert_link_black_48dp,
            R.drawable.ic_insert_link_black_48dp,
            R.drawable.ic_my_location_black_48dp,


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        /**
         * attaching toolbar in action bar
         */
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);


        services_title = new ArrayList<>();

        /**
         * attacing gridview layout in java file
         */
        var_gridview_service = (GridView) findViewById(R.id.gridview_service);

        var_gridview_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
            }
        });
        //Calling the getData method
        getData();






    }

    private void getData(){
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...","Fetching data...",false,false);

        //Creating a json array request to get the json from our api
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        loading.dismiss();

                        //Displaying our grid
                        showGrid(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void showGrid(JSONArray jsonArray){
        //Looping through all the elements of json array
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                services_title.add(obj.getString(TAG_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        /**
         * To attach image and title in gridview
         * But we have to create own CustomGrid java class
         */
        CustomeGrid adapter = new CustomeGrid(TabActivity.this, services_title, service_image);

        /**
         * setting adapter in girdview
         */

        var_gridview_service.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

