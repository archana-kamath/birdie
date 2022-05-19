package com.cmpe277.birdie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.cmpe277.birdie.data.BirdsTaxonomy;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button birdex;
    String root = Environment.getExternalStorageDirectory().toString();
    ListView listView;
    private static final int PERMISSION_REQUEST_CODE = 100;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        birdex = (Button) findViewById(R.id.birdexId);
        birdex.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.listView);
        searchView = (SearchView) findViewById(R.id.search);

//        MyViewAdapter myViewAdapter = new MyViewAdapter(this.getData());
  //      listView.setAdapter(myViewAdapter);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Birdie");
        // actionBar.setSubtitle("Bird watching has never been better!");
        // actionBar.setIcon(R.drawable.bird);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.upload:
                Toast.makeText(HomeActivity.this, "Upload image to cloud", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this, CloudUpload.class));
                break;
            case R.id.lens:
                Toast.makeText(HomeActivity.this, "Identify the bird!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this, ImageLens.class));
                break;
            case R.id.search:
                Toast.makeText(HomeActivity.this, "Search Birds!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                break;
//            case R.id.maps:
//                Toast.makeText(HomeActivity.this, "Search Birds using Map", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(HomeActivity.this, MapActivity.class));
//                break;
            case R.id.logout:
                Toast.makeText(HomeActivity.this, "Logging out!", Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getBirdsTaxonomy() {

        Log.i("HomeActivity","Entering birdex");
        getBirdData();

        if(checkPermission()){
            getBirdData();
        }else{
            requestPermission();
        }


/*        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                getBirdData();
            } else {
                allowPermissionForFile();
            }
        } else {
            getBirdData();
        }
*/
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void getReadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(HomeActivity.this, "Read External Storage permission allows us to read files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkReadPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }

    }

    // request permission for WRITE Access
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(HomeActivity.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    private void getBirdData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://ebird.org/ws2.0/ref/taxonomy/ebird";


        Log.i("HomeActivity","In getBirdData");
        StringRequest
                stringRequest
                = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.i("HomeActivity",response.length()+"");

                        //sdcard/Download/eBirdTaxonomy.csv
                        //root+"/Download/eBirdTaxonomy.csv"
                        try (PrintWriter writer = new PrintWriter("sdcard/Download/eBirdTaxonomy.csv")) {

                            StringBuilder sb = new StringBuilder();
                            writer.write(response.toString());

                            Log.i("HomeActivity","csv file written to Downloads folder");

                            MyViewAdapter myViewAdapter = new MyViewAdapter(getApplicationContext(), getData(response.toString()));
                            listView.setAdapter(myViewAdapter);

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String s) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String s) {
                                    myViewAdapter.getFilter().filter(s);
                                    return false;
                                }
                            });


                        } catch (FileNotFoundException e) {
                            System.out.println(e.getMessage());
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
        requestQueue.add(stringRequest);


    }

    private ArrayList<BirdsTaxonomy> getData(String data) {

//            DownloadData downloadData = (DownloadData) new DownloadData("Please wait",this,letMeKnow);
//            downloadData.execute();
/*
        if(checkReadPermission()){
           // NameOfRunnable.run();
            readFromCSV();

        }else{
            getReadPermission();
        }*/

        ArrayList<BirdsTaxonomy> birdsTaxonomies = new ArrayList<>();
        return readFromString(data,birdsTaxonomies);

}

    private ArrayList<BirdsTaxonomy> readFromString(String data, ArrayList<BirdsTaxonomy> birdsTaxonomies) {

        String[] row = data.split(System.lineSeparator());
        // i<row.length
        for(int i=0; i< 1000;i++){
            String[] str = row[i].split(",");

            if(i==0 && !str[3].toString().equals("species")){
                continue;
            }

            String scientificName= str[0].toString();
            String commonName= str[1].toString();
            String speciesCode= str[2].toString();
            String category= str[3].toString();
            String taxonOrder= str[4].toString();
            String comNameCodes= str[5].toString();
            String scientificNameCodes= str[6].toString();
            String bandingCodes= str[7].toString();
            String order= str[8].toString();
            String familyComName= str[9].toString();
            String familySciName= str[10].toString();
            String reportAs= str[11].toString();
            String extinct= str[12].toString();
            String extinctYear= str[13].toString();
            String familyCode= str[14].toString();

            BirdsTaxonomy birdsTaxonomy = new BirdsTaxonomy(scientificName,commonName,speciesCode,category,
                    taxonOrder,comNameCodes,scientificNameCodes,bandingCodes,order,familyComName,familySciName,
                    reportAs,extinct,extinctYear,familyCode);
            Log.i("",birdsTaxonomy.toString());
//https://assets.whatbird.com/api/image/birds_na_147/image/53683?x=322
//            birdsTaxonomy.setImageUrl("https://static.scientificamerican.com/sciam/cache/file/7A715AD8-449D-4B5A-ABA2C5D92D9B5A21_source.png");
            birdsTaxonomy.setImageUrl("https://assets.whatbird.com/api/image/birds_na_147/image/53683?x=322");
            birdsTaxonomies.add(birdsTaxonomy);

        }

        return birdsTaxonomies;
    }

    public Runnable NameOfRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            while (true)
            {
                // TODO add code to refresh in background
                try
                {
                    Thread.sleep(1000);// sleeps 1 second
                    readFromCSV();
                } catch (InterruptedException e){
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    };


    private void readFromCSV() {
        Log.i("HomeActivity = read from csv ","entered -"+ "sdcard/Download/eBirdTaxonomy.csv");


        File dir = new File("sdcard/Download/eBirdTaxonomy.csv", "");

        BufferedReader br = null;
        try{
            String sCurrentLine;
            br = new BufferedReader(new FileReader(dir));
            int i=0;

            while ((sCurrentLine = br.readLine()) != null) {

                if(i==0){
                    continue;
                }
                i++;

                if(i>100){
                    break;
                }

                String[] str = sCurrentLine.split(",", 15);
                String scientificName= str[0].toString();
                String commonName= str[1].toString();
                String speciesCode= str[2].toString();
                String category= str[3].toString();
                String taxonOrder= str[4].toString();
                String comNameCodes= str[5].toString();
                String scientificNameCodes= str[6].toString();
                String bandingCodes= str[7].toString();
                String order= str[8].toString();
                String familyComName= str[9].toString();
                String familySciName= str[10].toString();
                String reportAs= str[11].toString();
                String extinct= str[12].toString();
                String extinctYear= str[13].toString();
                String familyCode= str[14].toString();

                BirdsTaxonomy birdsTaxonomy = new BirdsTaxonomy(scientificName,commonName,speciesCode,category,
                        taxonOrder,comNameCodes,scientificNameCodes,bandingCodes,order,familyComName,familySciName,
                        reportAs,extinct,extinctYear,familyCode);
                Log.i("",birdsTaxonomy.toString());
               // birdsTaxonomies.add(birdsTaxonomy);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                Log.e("IOException", ex.getMessage().toString());
            }
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.birdexId:
                Log.i("HomeActivity","Before entering ");
                getBirdsTaxonomy();
                break;

        }
    }

    private DownloadData.InformComplete letMeKnow=new DownloadData.InformComplete()
    {
        @Override
        public void PostData(ArrayList<BirdsTaxonomy> result) {

        }
    };

}

