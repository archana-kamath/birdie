package com.cmpe277.birdie;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder implements View.OnClickListener{

    TextView textView0, textView1, textView2,textView3, textView4, textView5,textView6,
            textView7, textView8,textView9, textView10, textView11,textView12, textView13, textView14;

    ImageView imageView;
    ItemClickListener itemClickListener;

    public MyViewHolder(View view){

        imageView = (ImageView) view.findViewById(R.id.img);
        textView0 = (TextView) view.findViewById(R.id.textId0);
        textView1 = (TextView) view.findViewById(R.id.textId1);
        textView2 = (TextView) view.findViewById(R.id.textId2);
        textView3 = (TextView) view.findViewById(R.id.textId3);
        textView4 = (TextView) view.findViewById(R.id.textId4);
        textView5 = (TextView) view.findViewById(R.id.textId5);
        textView6 = (TextView) view.findViewById(R.id.textId6);
        textView7 = (TextView) view.findViewById(R.id.textId7);
        textView8 = (TextView) view.findViewById(R.id.textId8);
        textView9 = (TextView) view.findViewById(R.id.textId9);
        textView10 = (TextView) view.findViewById(R.id.textId10);
        textView11 = (TextView) view.findViewById(R.id.textId11);
        textView12 = (TextView) view.findViewById(R.id.textId12);
        textView13 = (TextView) view.findViewById(R.id.textId13);
        textView14 = (TextView) view.findViewById(R.id.textId14);

        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(view);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
