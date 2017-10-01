package com.example.aarong.startechdemo;
import com.example.aarong.startechdemo.Product;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {

    private ArrayList<Product> data = new ArrayList<Product>();
    private CustomListAdapter listAdapter = new CustomListAdapter(this, data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        ListView listview = (ListView) findViewById(R.id.list_view);
        listview.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onClickFab());
    }

    @NonNull
    private View.OnClickListener onClickFab() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast("Fetching data...");
                toast("Just kidding. This is not implemented yet...");
                //TODO - get data and then call setData
            }
        };
    }

    private void toast(String message) {
        Toast.makeText(ScrollingActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void setData(ArrayList<Product> products) {
        this.data.addAll(products);
        toast("Data loaded!");
        listAdapter.notifyDataSetChanged();
    }

}
