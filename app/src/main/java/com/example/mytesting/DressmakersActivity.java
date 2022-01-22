package com.example.mytesting;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DressmakersActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dressmaker);

        this.setTitle("Dressmakers");

        ArrayList<Item> details = getListData();
        final ListView lv1 = (ListView) findViewById(R.id.custom_list);
        lv1.setAdapter(new CustomListAdapter(this, details));
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                Item item = (Item) o;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getTelephone()));
                startActivity(intent);
            }
        });

        FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item krawiec = new Item();
                krawiec.setName("Adam Krzykala");
                krawiec.setAddress("Kosciuszki 77/13 Wroclaw");
                krawiec.setTelephone("734 450 471");
                details.add(krawiec);
                lv1.setAdapter(new CustomListAdapter(getBaseContext(), details));
            }
        });
    }

    private ArrayList<Item> getListData() {
        ArrayList<Item> results = new ArrayList<Item>();
        Item krawiec = new Item();
        krawiec.setName("Adam Trydewski");
        krawiec.setAddress("Kosciuszki 123/87 Wroclaw");
        krawiec.setTelephone("733 567 091");
        results.add(krawiec);

        // Add some more dummy data for testing
        return results;
    }
}