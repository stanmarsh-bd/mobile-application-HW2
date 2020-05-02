package com.example.homework2.Model;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.homework2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class FoodList extends Fragment {

    private TextView textView;
    private String detailText ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_list, container, false);
        textView = view.findViewById(R.id.lst_food);
        new JSOUP().execute();
        return view;
    }

    public class JSOUP extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params){
            try{
                detailText = "";
                Document doc =
                        Jsoup.connect("http://web.archive.org/web/20190406185041/https://aybu.edu.tr/sks/").get();
                final Elements element = doc.select("table tbody tr td  table tbody tr");
                for(int i = 0; i<element.size();i++){
                    detailText += "\n" + element.get(i).text();
                }
            }

            catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            progressDialog.dismiss();
            textView.setText(detailText);
            super.onPostExecute(result);
        }
    }
}
