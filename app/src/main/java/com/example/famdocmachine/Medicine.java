package com.example.famdocmachine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class Medicine extends Activity {
    List<Pt> heroList;
    ListView listView;
    TextView rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        listView = findViewById(R.id.listViewHeroes);
        rate = findViewById(R.id.rate);
        heroList = new ArrayList<>();
        String no=Diseases.prc+"";
        rate.setText(no);
        treatment();
    }

    private void treatment(){
        String[] mds=Diseases.med.split(",");
        String[] qt=Diseases.qt.split(",");

        heroList.clear();
        for (int i = 0; i < mds.length; i++) {
            heroList.add(new Pt(mds[i],qt[i]));
        }
        HeroAdapter adapter = new HeroAdapter(heroList);
        listView.setAdapter(adapter);
    }
    class HeroAdapter extends ArrayAdapter<Pt> {
        List<Pt> heroList;
        HeroAdapter(List<Pt> heroList) {
            super(Medicine.this, R.layout.layout_hero_list, heroList);
            this.heroList = heroList;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint({"ViewHolder", "InflateParams"}) View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);
            TextView textViewName = listViewItem.findViewById(R.id.meds);
            TextView textViewDelete = listViewItem.findViewById(R.id.qntt);
            final Pt hero = heroList.get(position);
            textViewName.setText(hero.getMd());
            textViewDelete.setText(hero.getQtt());
            return listViewItem;
        }
    }
}