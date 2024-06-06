package com.example.menudemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private ListView listView;
    private TextView textView, textView2, textView3;

    private ArrayAdapter<CharSequence> categoryAdapter, mainDishAdapter, sideDishAdapter, drinkAdapter;

    private String selectedMainDish = "", selectedSideDish = "", selectedDrink = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = findViewById(R.id.item);
        listView = findViewById(R.id.menu);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        categoryAdapter = ArrayAdapter.createFromResource(this, R.array.food_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryAdapter);

        mainDishAdapter = ArrayAdapter.createFromResource(this, R.array.menu1, android.R.layout.simple_list_item_1);
        sideDishAdapter = ArrayAdapter.createFromResource(this, R.array.menu2, android.R.layout.simple_list_item_1);
        drinkAdapter = ArrayAdapter.createFromResource(this, R.array.drinks, android.R.layout.simple_list_item_1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        listView.setAdapter(mainDishAdapter);
                        break;
                    case 1:
                        listView.setAdapter(sideDishAdapter);
                        break;
                    case 2:
                        listView.setAdapter(drinkAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        selectedMainDish = selectedItem;
                        textView.setText("主餐: " + selectedMainDish);
                        break;
                    case 1:
                        selectedSideDish = selectedItem;
                        textView2.setText("附餐: " + selectedSideDish);
                        break;
                    case 2:
                        selectedDrink = selectedItem;
                        textView3.setText("飲料: " + selectedDrink);
                        break;
                }
            }
        });
    }
}