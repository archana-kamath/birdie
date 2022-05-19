package com.cmpe277.birdie;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cmpe277.birdie.data.BirdsTaxonomy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DownloadData extends AsyncTask {

    private ProgressDialog qDialog;
    private Context context;
    private String dialogString;
    private ArrayList<BirdsTaxonomy> birdsTaxonomies;
    private InformComplete myCallback;

    public DownloadData(String dialogMessage, Context con,InformComplete callback)
    {
        this.qDialog =  new ProgressDialog(con);
        this.dialogString = dialogMessage;
        this.context = con;
        this.myCallback=callback;
        birdsTaxonomies = new ArrayList<BirdsTaxonomy>();
    }
    @Override
    protected ArrayList<BirdsTaxonomy> doInBackground(Object[] objects) {

        File dir = new File("/sdcard/Download/eBirdTaxonomy.csv", "");

        BufferedReader br = null;
        try{
            String sCurrentLine;
            br = new BufferedReader(new FileReader(dir));
            while ((sCurrentLine = br.readLine()) != null) {

                String[] str = sCurrentLine.split(",", 15);
                Log.i("",sCurrentLine);
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
                birdsTaxonomies.add(birdsTaxonomy);

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

        Log.i("DownloadData", birdsTaxonomies.size()+"");
        return birdsTaxonomies;
    }

    public void onPostExecute(ArrayList<BirdsTaxonomy> birdsTaxonomies)
    {
        qDialog.dismiss();
        myCallback.PostData(birdsTaxonomies);
    }
    public interface InformComplete
    {
        public void PostData(ArrayList<BirdsTaxonomy> result);
    }
}
