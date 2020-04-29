package com.example.homework2.Model;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.widget.ListView;

import android.os.AsyncTask;
import android.app.ProgressDialog;

import com.example.homework2.R;

import java.util.ArrayList;


public class Announcements extends Fragment {

    private String detailText = "";
    private ListView lst_announcement;


    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private Elements links;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.announcements, container, false);

        lst_announcement = view.findViewById(R.id.lst_announcement);
        new JSOUP().execute();
        return view;
    }

    public class JSOUP extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("loading...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Document doc = Jsoup.connect("https://aybu.edu.tr/muhendislik/bilgisayar/").get();
                Elements elements = doc.select(".caContent > .cncItem");
                links = doc.select(".caContent .cncItem a");
                arrayList = new ArrayList<>();
                for (int i = 0; i < elements.size(); i++) {
                    detailText = elements.get(i).text();
                    arrayList.add(detailText);
                }
            } catch (Exception e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayList.toArray(new String[arrayList.size()]));
            lst_announcement.setAdapter(arrayAdapter);
            lst_announcement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.aybu.edu.tr/muhendislik/bilgisayar/" + getElementLink(position)));
                    startActivity(browserIntent);
                }
            });


            super.onPostExecute(result);
        }

        private String getElementLink(int ind) {
            return links.get(ind).attr("href");
        }
    }


}



