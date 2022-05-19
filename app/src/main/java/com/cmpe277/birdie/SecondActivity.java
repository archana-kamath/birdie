package com.cmpe277.birdie;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SecondActivity extends AppCompatActivity {

    TextView t0, t1, t2,t3, t4, t5,t6, t7, t8,t9, t10, t11,t12, t13, t14,t15;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView = (ImageView) findViewById(R.id.image);
        t0 = (TextView) findViewById(R.id.txtId0);
        t1 = (TextView) findViewById(R.id.txtId1);
        t2 = (TextView) findViewById(R.id.txtId2);
        t3 = (TextView) findViewById(R.id.txtId3);
        t4 = (TextView) findViewById(R.id.txtId4);
        t5 = (TextView) findViewById(R.id.txtId5);
        t6 = (TextView) findViewById(R.id.txtId6);
        t7 = (TextView) findViewById(R.id.txtId7);
        t8 = (TextView) findViewById(R.id.txtId8);
        t9 = (TextView) findViewById(R.id.txtId9);
        t10 = (TextView) findViewById(R.id.txtId10);
        t11 = (TextView) findViewById(R.id.txtId11);
        t12 = (TextView) findViewById(R.id.txtId12);
        t13 = (TextView) findViewById(R.id.txtId13);
        t14 = (TextView) findViewById(R.id.txtId14);
        t15 = (TextView) findViewById(R.id.txtId14);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        String scientificName = getIntent().getStringExtra("scientificName");
        String commonName = getIntent().getStringExtra("commonName");
        String speciesCode = getIntent().getStringExtra("speciesCode");
        String category = getIntent().getStringExtra("category");
        String taxonOrder = getIntent().getStringExtra("taxonOrder");
        String comNameCodes = getIntent().getStringExtra("comNameCodes");
        String scientificNameCodes = getIntent().getStringExtra("scientificNameCodes");
        String bandingCodes = getIntent().getStringExtra("bandingCodes");
        String order = getIntent().getStringExtra("order");
        String familyComName = getIntent().getStringExtra("familyComName");
        String familySciName = getIntent().getStringExtra("familySciName");
        String reportAs = getIntent().getStringExtra("reportAs");
        String extinct = getIntent().getStringExtra("extinct");
        String extinctYear = getIntent().getStringExtra("extinctYear");
        String familyCode = getIntent().getStringExtra("familyCode");

        Glide.with(getApplicationContext()).load(imageUrl).into(imageView);

        t0.setText("Scientific Name :"+scientificName);
        t1.setText("Common Name :"+commonName);
        t2.setText("Species Code :"+ speciesCode);
        t3.setText("Category :"+ category);
        t4.setText("Taxon Order :"+taxonOrder);
        t5.setText("Common Name Codes " +comNameCodes);
        t6.setText("Scientific Name Codes" +scientificNameCodes);
        t7.setText("Banding Codes :"+bandingCodes);
        t8.setText("Order :"+order);
        t9.setText("Family Common Name :"+familyComName);
        t10.setText("Family Scientific Name :"+familySciName);
        t11.setText("Report As :"+reportAs);
        t12.setText("Extinct :"+ extinct);
        t13.setText("Extinction Year :"+ extinctYear);
        t14.setText("Family Code :" +familyCode);

    }
}