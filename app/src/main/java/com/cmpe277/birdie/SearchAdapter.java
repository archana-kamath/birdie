package com.cmpe277.birdie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SearchModal> searchModals;

    public SearchAdapter(Context context, ArrayList<SearchModal> searchModal) {
        this.context = context;
        this.searchModals = searchModal;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(context).inflate(R.layout.search_results_list,parent,false);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        SearchModal searchModal = searchModals.get(position);
        holder.title.setText(searchModal.getTitle());
        holder.description.setText(searchModal.getSnippet());
        holder.snippet.setText(searchModal.getLink());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(searchModal.getLink()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchModals.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, snippet, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            snippet = itemView.findViewById(R.id.Snippet);
            description = itemView.findViewById(R.id.Description);
        }
    }
}
