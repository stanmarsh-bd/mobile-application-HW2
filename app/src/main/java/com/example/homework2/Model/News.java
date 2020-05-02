package com.example.homework2.Model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.homework2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class News extends Fragment {

    private String detailText ="";
    private ListView lst_news;

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private Elements elements;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news, container, false);
        lst_news = view.findViewById(R.id.lst_news);
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
                arrayList = new ArrayList<>();
                Document doc = Jsoup.connect("https://aybu.edu.tr/muhendislik/bilgisayar/").get();
                Elements elements = doc.select(".cnContent .cncItem");
                for(int i = 0; i<elements.size();i++){
                    detailText = elements.get(i).text();
                    arrayList.add(detailText);

                }
                News.this.elements = doc.select(".cnContent .cncItem a");
            }

            catch (Exception e){
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            progressDialog.dismiss();
            arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList.toArray(new String[arrayList.size()]));
            lst_news.setAdapter(arrayAdapter);



            lst_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.aybu.edu.tr/muhendislik/bilgisayar/"+getElementLink(position)));
                    startActivity(browserIntent);
                }
            });


            super.onPostExecute(result);
        }

        private String getElementLink(int ind){
            return elements.get(ind).attr("href");
        }



    }










}
