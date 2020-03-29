package com.example.recipeapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.recipeapp.R;
import com.example.recipeapp.Utility.Utility;

import java.util.Objects;

public class Home extends Fragment implements View.OnClickListener {
    private RelativeLayout rlViewRecipe, rlCreateRecipe;
    private Utility utility;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        utility = new Utility();

        findViewByIdsAndSetListeners(rootView);
        return rootView;

    }


    private void findViewByIdsAndSetListeners(View rootView){
        rlViewRecipe = rootView.findViewById(R.id.rlViewRecipe);
        rlCreateRecipe = rootView.findViewById(R.id.rlCreateRecipe);
        rlViewRecipe.setOnClickListener(this);
        rlCreateRecipe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rlViewRecipe:
                ViewRecipe viewRecipe = new ViewRecipe();
                Utility.replaceFragment((AppCompatActivity) Objects.requireNonNull(getActivity()), R.id.container, viewRecipe, ViewRecipe.class.getSimpleName());
                break;
            case R.id.rlCreateRecipe:
                CreateOwnRecipe createOwnRecipe = new CreateOwnRecipe(false,null);
                Utility.replaceFragment((AppCompatActivity) Objects.requireNonNull(getActivity()), R.id.container, createOwnRecipe, CreateOwnRecipe.class.getSimpleName());
                break;
        }
    }
}
