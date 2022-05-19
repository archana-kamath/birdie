package com.cmpe277.birdie;

import android.widget.Filter;

import com.cmpe277.birdie.data.BirdsTaxonomy;

import java.util.ArrayList;

public class SearchFilters extends Filter {

    ArrayList<BirdsTaxonomy> filteredList;
    MyViewAdapter myViewAdapter;

    public SearchFilters(ArrayList<BirdsTaxonomy> filteredList, MyViewAdapter myViewAdapter) {
        this.filteredList = filteredList;
        this.myViewAdapter = myViewAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults filterResults = new FilterResults();

        if(charSequence!=null && charSequence.length()>0){

            charSequence = charSequence.toString().toUpperCase();

            ArrayList<BirdsTaxonomy> filteredData = new ArrayList<>();
            for(int i=0;i<filteredList.size();i++){
                if(filteredList.get(i).getCommonName().toUpperCase().contains(charSequence)){
                    filteredData.add(filteredList.get(i));
                }
            }

            filterResults.count = filteredData.size();
            filterResults.values = filteredData;

        }else{
            filterResults.count = filteredList.size();
            filterResults.values = filteredList;
        }


        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        myViewAdapter.birdsTaxonomies = (ArrayList<BirdsTaxonomy>) filterResults.values;
        myViewAdapter.notifyDataSetChanged();

    }
}
