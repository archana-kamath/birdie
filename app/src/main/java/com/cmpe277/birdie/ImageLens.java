package com.cmpe277.birdie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageLens extends AppCompatActivity {

    private ImageView capture;
    private Button click, find;
    private RecyclerView search_results;
    private SearchAdapter searchAdapter;
    private ArrayList<SearchModal> searchModalArrayList;
    int REQUEST_CODE = 1;
    private Bitmap imageBitmap;
    String title,link,displayedLink,snippet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_lens);

        capture = (ImageView)findViewById(R.id.capture);
        click = (Button)findViewById(R.id.click);
        find = (Button)findViewById(R.id.find);
        search_results = findViewById(R.id.searchResults);
        searchModalArrayList = new ArrayList<>();
        searchAdapter = new SearchAdapter(this, searchModalArrayList);
        search_results.setLayoutManager(new LinearLayoutManager(ImageLens.this, LinearLayoutManager.HORIZONTAL, false));
        search_results.setAdapter(searchAdapter);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchModalArrayList.clear();
                searchAdapter.notifyDataSetChanged();
                clickSomethingIntent();
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchModalArrayList.clear();
                searchAdapter.notifyDataSetChanged();
                getResults();
            }
        });
    }

    private void getResults(){
        InputImage img = InputImage.fromBitmap(imageBitmap,0);
        ImageLabelerOptions options = new ImageLabelerOptions.Builder().setConfidenceThreshold(0.7f).build();
        ImageLabeler label = ImageLabeling.getClient(options);

        label.process(img).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
            @Override
            public void onSuccess(List<ImageLabel> firebaseVisionImageLabels) {
                String searchQuery = firebaseVisionImageLabels.get(0).getText();
                getSearchResults(searchQuery);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ImageLens.this, "No image detected..", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void getSearchResults(String searchQuery) {
        searchQuery = "rare bird species";
        String apiKey= "";
        String url = "https://serpapi.com/search?q="+ searchQuery +"&location=United States&hl=en&gl=us&google_domain=google.com&api_key="+apiKey;
        RequestQueue queue = Volley.newRequestQueue(ImageLens.this);
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("organic_results");
                    //JSONObject res = new JSONObject(result.toString());
                    //JSONObject results = res.getJSONObject("sitelinks");
                    //JSONObject resObject = results.getJSONObject(String.valueOf(0));
                    for (int i=0; i<results.length(); i++){
                        JSONObject resObject = results.getJSONObject(i);
                        if (resObject.has("title")) {
                            title = resObject.getString("title");
                        }
                        if (resObject.has("link")) {
                            link = resObject.getString("link");
                        }
                        if (resObject.has("sitelinks")) {
                            displayedLink = resObject.getString("sitelinks");
                        }
                        if (resObject.has("snippet")) {
                            snippet = resObject.getString("snippet");
                        }
                        searchModalArrayList.add(new SearchModal(title, link, displayedLink, snippet));
                    }
                    searchAdapter.notifyDataSetChanged();

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ImageLens.this, "No Results Found..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObject);
    }

    private void clickSomethingIntent() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            capture.setImageBitmap(imageBitmap);
        }
    }
}