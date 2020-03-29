package com.example.recipeapp.Utility;


import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.recipeapp.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerTool{

    public SpinnerTool () {

    }

    public void setSpinner(Context context,String[] spinnerArray, Spinner spinner){
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context,android.R.layout.simple_spinner_dropdown_item,spinnerArray) {

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                ((TextView) tv).setGravity(Gravity.CENTER);

                tv.setTextColor(Color.BLACK);


                return view;
            }

        };
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }


}
