package com.cmpe277.birdie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.cmpe277.birdie.data.BirdsTaxonomy;

import java.util.ArrayList;

public class MyViewAdapter extends BaseAdapter  implements Filterable {

    Context context;
    ArrayList<BirdsTaxonomy> birdsTaxonomies;
    LayoutInflater inflater;
    SearchFilters searchFilters;

    ArrayList<BirdsTaxonomy> filteredList;

    public MyViewAdapter(Context context, ArrayList<BirdsTaxonomy> birdsTaxonomies) {
        this.context = context;
        this.birdsTaxonomies = birdsTaxonomies;
        this.filteredList = birdsTaxonomies;
    }

    @Override
    public int getCount() {
        return birdsTaxonomies.size();
    }

    @Override
    public Object getItem(int i) {
        return birdsTaxonomies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view==null){
            view = inflater.inflate(R.layout.layout_model,null);
        }

        MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.textView0.setText("Scientific Name :"+birdsTaxonomies.get(i).getScientificName());
        myViewHolder.textView1.setText("Common Name :"+birdsTaxonomies.get(i).getCommonName());
        myViewHolder.textView2.setText("Species Code :"+birdsTaxonomies.get(i).getSpeciesCode());
        myViewHolder.textView3.setText("Category :"+birdsTaxonomies.get(i).getCategory());
        myViewHolder.textView4.setText("Taxon Order :"+birdsTaxonomies.get(i).getTaxonOrder());
        myViewHolder.textView5.setText("Common Name Codes :"+birdsTaxonomies.get(i).getComNameCodes());
        myViewHolder.textView6.setText("Scientific Name Codes :"+birdsTaxonomies.get(i).getScientificNameCodes());
        myViewHolder.textView7.setText("Banding Codes :"+birdsTaxonomies.get(i).getBandingCodes());
        myViewHolder.textView8.setText("Order :"+birdsTaxonomies.get(i).getOrder());
        myViewHolder.textView9.setText("Family Common Name :"+birdsTaxonomies.get(i).getFamilyComName());
        myViewHolder.textView10.setText("Family Scientific Name :"+birdsTaxonomies.get(i).getFamilySciName());
        myViewHolder.textView11.setText("Report As :"+birdsTaxonomies.get(i).getReportAs());
        myViewHolder.textView12.setText("Extinct :"+birdsTaxonomies.get(i).getExtinct());
        myViewHolder.textView13.setText("Extinction Year :"+birdsTaxonomies.get(i).getExtinctYear());
        myViewHolder.textView14.setText("Family Code :"+birdsTaxonomies.get(i).getFamilyCode());

        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Toast.makeText(context.getApplicationContext(), "Item Clicked",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("imageUrl",birdsTaxonomies.get(i).getImageUrl());
                intent.putExtra("scientificName",birdsTaxonomies.get(i).getScientificName());
                intent.putExtra("commonName",birdsTaxonomies.get(i).getCommonName());
                intent.putExtra("speciesCode",birdsTaxonomies.get(i).getSpeciesCode());
                intent.putExtra("category",birdsTaxonomies.get(i).getCategory());
                intent.putExtra("taxonOrder",birdsTaxonomies.get(i).getTaxonOrder());
                intent.putExtra("comNameCodes",birdsTaxonomies.get(i).getComNameCodes());
                intent.putExtra("scientificNameCodes",birdsTaxonomies.get(i).getScientificNameCodes());
                intent.putExtra("bandingCodes",birdsTaxonomies.get(i).getBandingCodes());
                intent.putExtra("order",birdsTaxonomies.get(i).getOrder());
                intent.putExtra("familyComName",birdsTaxonomies.get(i).getFamilyComName());
                intent.putExtra("familySciName",birdsTaxonomies.get(i).getFamilySciName());
                intent.putExtra("reportAs",birdsTaxonomies.get(i).getReportAs());
                intent.putExtra("extinct",birdsTaxonomies.get(i).getExtinct());
                intent.putExtra("extinctYear",birdsTaxonomies.get(i).getExtinctYear());
                intent.putExtra("familyCode",birdsTaxonomies.get(i).getFamilyCode());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public Filter getFilter() {

        if(searchFilters ==null){
            searchFilters = new SearchFilters(filteredList,this);

        }
        return searchFilters;
    }
}
