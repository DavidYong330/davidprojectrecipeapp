package com.example.recipeapp.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeapp.Adapter.RecipeSelectionAdapter;
import com.example.recipeapp.Object.RecipeObject;
import com.example.recipeapp.R;
import com.example.recipeapp.SQLiteDB.DBHelper;
import com.example.recipeapp.Utility.AlertTool;
import com.example.recipeapp.Utility.Constant;
import com.example.recipeapp.Utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ViewRecipe extends Fragment implements View.OnClickListener, RecipeSelectionAdapter.RecipeSelectionAdapterListener, AlertTool.alertListener {
    private RecyclerView rvRecipes;
    private RecipeSelectionAdapter recipeSelectionAdapter;
    private Spinner spFilterRecipe;
    private DBHelper dbHelper;
    private AlertTool alertTool;
    private String selectedRecipe;
    private RecipeObject selectedRecipeObj;


    public ViewRecipe() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_recipe, container, false);
        findViewByIdsAndSetListeners(rootView);
        dbHelper = new DBHelper(getContext());
        alertTool = new AlertTool(this);
        setRecipesAdapter(setRecipe());
        setSpinner();
        return rootView;

    }

    private void findViewByIdsAndSetListeners(View rootView){
        rvRecipes = rootView.findViewById(R.id.rvRecipes);
        spFilterRecipe = rootView.findViewById(R.id.spFilterRecipe);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

//
    private void setRecipesAdapter(ArrayList<RecipeObject> recipeList){
        RecyclerView.LayoutManager mLayoutManagerSupport = new LinearLayoutManager(getActivity());
        rvRecipes.setLayoutManager(mLayoutManagerSupport);
        rvRecipes.setNestedScrollingEnabled(false);
        recipeSelectionAdapter = new RecipeSelectionAdapter(getContext(),recipeList,this);

        rvRecipes.setAdapter(recipeSelectionAdapter);
    }


    private void setSpinner(){
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.selection_recipe_array)) {

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
        spFilterRecipe.setAdapter(spinnerArrayAdapter);
        spFilterRecipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                switch (position) {
                    case 0:
                        filterRecipeType("all");
                        break;
                    case 1:
                        filterRecipeType("Chicken");
                        break;
                    case 2:
                        filterRecipeType("Fish");
                        break;
                    case 3:
                        filterRecipeType("Others");
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    /**             Filter the recipelist based on spinner selection           **/
    public void filterRecipeType(String filterRecipe){
        ArrayList<RecipeObject> filteredRecipe = new ArrayList<>();
        switch(filterRecipe){
            case "all":
                filteredRecipe = setRecipe();
                setRecipesAdapter(filteredRecipe);
                break;


            case "Chicken":
                for(int i = 0; i<setRecipe().size(); i++){
                    if(setRecipe().get(i).getRecipeTypes().equals("Chicken")){
                        filteredRecipe.add(setRecipe().get(i));
                    }
                }
                setRecipesAdapter(filteredRecipe);
                break;

            case "Fish":
                for(int i = 0; i<setRecipe().size(); i++){
                    if(setRecipe().get(i).getRecipeTypes().equals("Fish")){
                        filteredRecipe.add(setRecipe().get(i));
                    }
                }
                setRecipesAdapter(filteredRecipe);
                break;
            case "Others":
                for(int i = 0; i<setRecipe().size(); i++){
                    if(setRecipe().get(i).getRecipeTypes().equals("Others")){
                        filteredRecipe.add(setRecipe().get(i));
                    }
                }
                setRecipesAdapter(filteredRecipe);
                break;
        }

    }




    /**             This is the List of recipe object              **/
    private ArrayList<RecipeObject> setRecipe(){
        ArrayList<RecipeObject> recipeList = new ArrayList<>();
        RecipeObject recipeObject = new RecipeObject();
        recipeObject.setRecipeName("Fried Chicken");
        recipeObject.setLevel("Easy");
        recipeObject.setSelfCreatedIndicator("no");
        recipeObject.setRecipeIngredients(getResources().getString(R.string.ingredients_fried_chicken));
        recipeObject.setRecipeImage(getResources().getDrawable(R.drawable.chicken_fried_chicken));
        recipeObject.setRecipeDirections(getResources().getString(R.string.steps_fried_chicken));
        recipeObject.setTimeComplete("60");
        recipeObject.setRecipeTypes("Chicken");
        RecipeObject recipeObject2 = new RecipeObject();
        recipeObject2.setRecipeName("Chicken Sausage With Peppers");
        recipeObject2.setLevel("Medium");
        recipeObject2.setSelfCreatedIndicator("no");
        recipeObject2.setRecipeImage(getResources().getDrawable(R.drawable.chicken_chicken_sausage));
        recipeObject2.setRecipeIngredients(getResources().getString(R.string.ingredients_chicken_sausage));
        recipeObject2.setRecipeDirections(getResources().getString(R.string.steps_chicken_sausage));
        recipeObject2.setTimeComplete("35");
        recipeObject2.setRecipeTypes("Chicken");
        RecipeObject recipeObject3 = new RecipeObject();
        recipeObject3.setRecipeName("Curry Salmon With Mango Chutney");
        recipeObject3.setLevel("Hard");
        recipeObject3.setSelfCreatedIndicator("no");
        recipeObject3.setRecipeImage(getResources().getDrawable(R.drawable.fish_curry_salmon));
        recipeObject3.setRecipeIngredients(getResources().getString(R.string.ingredients_curry_salmon));
        recipeObject3.setRecipeDirections(getResources().getString(R.string.steps_curry_salmon));
        recipeObject3.setTimeComplete("40");
        recipeObject3.setRecipeTypes("Fish");

        recipeList.add(recipeObject);
        recipeList.add(recipeObject2);
        recipeList.add(recipeObject3);


        ArrayList<RecipeObject> mergeRecipes = new ArrayList<>();
        mergeRecipes.addAll(recipeList);
        mergeRecipes.addAll(dbHelper.getAllCreatedRecipes());

        return mergeRecipes;
    }

    @Override
    public void onDetailBtnClick(RecipeObject recipeList, int position) {
        RecipeDetail recipeDetail = new RecipeDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.RECIPE_LIST, recipeList);
        recipeDetail.setArguments(bundle);
        Utility.replaceFragment(((AppCompatActivity) Objects.requireNonNull(getActivity())), R.id.container, recipeDetail, RecipeDetail.class.getSimpleName());

    }

    @Override
    public void onDeleteBtnClick(RecipeObject recipeObject) {
        selectedRecipe = recipeObject.getRecipeName();
        alertTool.alertDuoButton(getContext(),Constant.CONFIRM_DELETE,R.string.confirm_delete,R.string.yes,R.string.no);
    }

    @Override
    public void onEditeBtnClick(RecipeObject recipeObject) {
        Utility.printInfo("TAG", "image "+ recipeObject.getRecipeImage());
        byte [] imageByte = Utility.drawableToByte(recipeObject.getRecipeImage());
        selectedRecipeObj = recipeObject;
        selectedRecipeObj.setImage(imageByte);
        alertTool.alertDuoButton(getContext(),Constant.CONFIRM_EDIT,R.string.confirm_edit,R.string.yes,R.string.no);

    }

    @Override
    public void onPositiveDialogClick(String dialogTag) {
        switch (dialogTag){
            case Constant.CONFIRM_DELETE:
                dbHelper.deleteRow(selectedRecipe);
                setRecipesAdapter(setRecipe());
                break;
            case Constant.CONFIRM_EDIT:
                CreateOwnRecipe createOwnRecipe = new CreateOwnRecipe(true,selectedRecipeObj);
                Utility.replaceFragment((AppCompatActivity) getActivity(), R.id.container, createOwnRecipe, CreateOwnRecipe.class.getSimpleName());

                break;
        }
    }

    @Override
    public void onNegativeDialogClick(String dialogTag) {

    }
}
