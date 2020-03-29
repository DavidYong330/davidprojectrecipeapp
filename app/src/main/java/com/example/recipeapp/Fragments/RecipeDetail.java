package com.example.recipeapp.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.recipeapp.Object.RecipeObject;
import com.example.recipeapp.R;
import com.example.recipeapp.Utility.Constant;


public class RecipeDetail extends Fragment {
    private LinearLayout llRecipeImage;
    private TextView tvRecipeName, tvTimeComplete, tvIngredients, tvSteps;
    private RecipeObject recipeObject;



    public RecipeDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        if(getArguments() != null ){
            if(getArguments().containsKey(Constant.RECIPE_LIST)){
                recipeObject = (RecipeObject) getArguments().getSerializable(Constant.RECIPE_LIST);
            }
        }
        findViewByIdsAndSetListeners(rootView);
        setUpDetails(recipeObject);
        return rootView;

    }

    private void findViewByIdsAndSetListeners(View rootView){
        llRecipeImage = rootView.findViewById(R.id.llRecipeImage);
        tvRecipeName = rootView.findViewById(R.id.tvRecipeName);
        tvTimeComplete = rootView.findViewById(R.id.tvTimeComplete);
        tvIngredients = rootView.findViewById(R.id.tvIngredients);
        tvSteps = rootView.findViewById(R.id.tvSteps);

    }

    private void setUpDetails(RecipeObject recipeObject){
        llRecipeImage.setBackground(recipeObject.getRecipeImage());
        tvRecipeName.setText(recipeObject.getRecipeName());
        tvTimeComplete.setText(recipeObject.getTimeComplete());
        tvIngredients.setText(recipeObject.getRecipeIngredients());
        tvSteps.setText(recipeObject.getRecipeDirections());
    }






}
