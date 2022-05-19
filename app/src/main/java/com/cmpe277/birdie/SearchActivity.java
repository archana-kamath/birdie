package com.cmpe277.birdie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.google.android.gms.location.LocationRequest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.cmpe277.birdie.data.BirdsObserved;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    EditText location,speciesCode;
    private LocationRequest locationRequest;
    double latitude, longitude;
    private BarChart barChart;
    private PieChart pieChart;
    private Switch aSwitchBorP;
    Button searchBirds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBirds = (Button) findViewById(R.id.searchBirds);
        searchBirds.setOnClickListener(this);

        speciesCode = (EditText) findViewById(R.id.speciesCode);

        location = (EditText) findViewById(R.id.location);
        barChart = (BarChart) findViewById(R.id.barChart);
        pieChart = (PieChart) findViewById(R.id.pieChart);

        aSwitchBorP = (Switch) findViewById(R.id.switchBorP);

        aSwitchBorP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b==true){
                    barChart.setVisibility(View.GONE);
                    pieChart.setVisibility(View.VISIBLE);

                }else{
                    barChart.setVisibility(View.VISIBLE);
                    pieChart.setVisibility(View.GONE);
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Birdie");
        // actionBar.setSubtitle("Bird watching has never been better!");
        // actionBar.setIcon(R.drawable.bird);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.searchBirds:
                geoLocation();
                break;
        }
    }

    private void geoLocation() {

        String inputLocation = location.getText().toString();
        Log.i("Location",inputLocation);

        String inputSpeciesCd = speciesCode.getText().toString();
        Log.i("Species Code",inputSpeciesCd);

        if(inputLocation.equals("")){
            Toast.makeText(SearchActivity.this, "Please enter location and/or species code", Toast.LENGTH_LONG).show();
            return;
        }

        if(!inputSpeciesCd.equals("") && inputLocation.equals("")){
            Toast.makeText(SearchActivity.this, "Please enter location along with species code", Toast.LENGTH_LONG).show();
            return;
        }

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {

            List addressList = geocoder.getFromLocationName(inputLocation,1);
            if(addressList!=null && addressList.size()!=0){
                Address address = (Address) addressList.get(0);
                Log.i("Geocoder",address.getLatitude()+" "+address.getLongitude());

                latitude = address.getLatitude();
                longitude = address.getLongitude();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url ="";

                if(!inputSpeciesCd.equals("") && !inputLocation.equals("")){
                    url = "https://ebird.org/ws2.0/data/obs/geo/recent/"+inputSpeciesCd+"?lat="+latitude+"&lng="+longitude;
                }

                if(inputSpeciesCd.equals("") && !inputLocation.equals("")){
                    url = "https://ebird.org/ws2.0/data/obs/geo/recent?lat="+latitude+"&lng="+longitude;
                }

                Log.i("url",url);

                JsonArrayRequest
                        jsonArrayRequest
                        = new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response)
                            {
                                Log.i("eBird Response",response.toString());
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<BirdsObserved>>(){}.getType();
                                List<BirdsObserved> birdsList = gson.fromJson(response.toString(), type);
                                Log.i("eBird Response List",birdsList.size()+"");

                                if(birdsList.size()>0){
                                    loadGraph(birdsList,inputLocation);
                                }else{
                                    Toast.makeText(SearchActivity.this, "No birds found in this region", Toast.LENGTH_LONG).show();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.i("eBird Response",error.toString());
                            }
                        }) {

                    @Override
                    public Map getHeaders() throws AuthFailureError
                    {
                        HashMap headers = new HashMap();
                        headers.put("x-ebirdapitoken", "");
                        return headers;
                    }

                };
                requestQueue.add(jsonArrayRequest);



            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadGraph(List<BirdsObserved> birdsList, String inputLocation) {

        int visibility = barChart.getVisibility();
        if(visibility==0){
            pieChart.setVisibility(View.GONE);
        }

        if(pieChart.getVisibility()==View.GONE){
            barChart.setVisibility(View.VISIBLE);
        }


        int i=0;
        List<BarEntry> barEntries = new ArrayList<>();
        List<PieEntry> pieEntries = new ArrayList<>();

        birdsList.sort(new Comparator<BirdsObserved>() {
            @Override
            public int compare(BirdsObserved birdsObserved, BirdsObserved t1) {

                if(birdsObserved.getHowMany()>t1.getHowMany()){
                    return -1;
                }
                if(birdsObserved.getHowMany()<t1.getHowMany()){
                    return 1;
                }
                return 0;
            }
        });

        IndexAxisValueFormatter axisValueFormatter = new IndexAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value) {
                return birdsList.get((int) value).getComName();
            }
        };

        for(BirdsObserved bo : birdsList){
            Log.i("Bird Details", bo.getComName() + " - " +bo.getHowMany());
            barEntries.add(new BarEntry(i,bo.getHowMany(),bo.getComName()));
            if(i<25){
                pieEntries.add(new PieEntry(i,bo.getHowMany()+" "+bo.getSpeciesCode()));
            }
            i++;
        }

        BarDataSet barDataSet = new BarDataSet(barEntries,"Birds Observed");
        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Birds Observed");

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);


        BarData barData = new BarData(barDataSet);
        PieData pieData = new PieData(pieDataSet);

        barData.setBarWidth(0.5f);
        barChart.animateY(5000);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.setDrawValueAboveBar(true);
        barChart.setVisibleXRangeMaximum(20);
        barChart.setTouchEnabled(true);

        pieChart.animateXY(5000,5000);
        pieChart.setData(pieData);

        Description description = new Description();
        description.setText("Birds observed up to 14 days at a radius of 25kms -"+ inputLocation);

        barChart.setDescription(description);
        pieChart.setDescription(description);

        pieChart.invalidate();

    }
}