package com.example.recipeapp.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.example.recipeapp.Object.RecipeObject;
import com.example.recipeapp.R;
import com.example.recipeapp.SQLiteDB.DBHelper;
import com.example.recipeapp.Utility.AlertTool;
import com.example.recipeapp.Utility.Constant;
import com.example.recipeapp.Utility.SpinnerTool;
import com.example.recipeapp.Utility.Utility;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;


public class CreateOwnRecipe extends Fragment implements View.OnClickListener, AlertTool.alertListener  {
    private LinearLayout llCaptureImage;
    private TextView tvTitle;
    private EditText etCompletionTime, etIngredients, etSteps, etRecipeName;
    private Spinner spDifficulty, spRecipeType;
    private Button btnCreateRecipe;
    private byte[] Image;
    private DBHelper dbHelper;
    private AlertTool alertTool;
    private SpinnerTool spinnerTool;
    private boolean hasCapturedImage = false;
    private boolean editRecipe = false;
    private RecipeObject recipeObject;

    public CreateOwnRecipe(boolean editRecipe, RecipeObject recipeObject) {
        this.editRecipe = editRecipe;
        this.recipeObject = recipeObject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_create_own_recipe, container, false);
        dbHelper = new DBHelper(getContext());
        alertTool = new AlertTool(this);
        spinnerTool = new SpinnerTool();
        findViewByIdsAndSetListeners(rootView);
        setUpEditRecipeScreen(recipeObject);
        spinnerTool.setSpinner(getContext(),getResources().getStringArray(R.array.selection_difficulty_array),spDifficulty);
        spinnerTool.setSpinner(getContext(),getResources().getStringArray(R.array.selection_recipe_types_array),spRecipeType);


        return rootView;

    }

    private void findViewByIdsAndSetListeners(View rootView) {
        tvTitle = rootView.findViewById(R.id.tvTitle);
        llCaptureImage = rootView.findViewById(R.id.ivCaptureImage);
        etCompletionTime = rootView.findViewById(R.id.etCompletionTime);
        spDifficulty = rootView.findViewById(R.id.spDifficulty);
        spRecipeType = rootView.findViewById(R.id.spRecipeType);
        etIngredients = rootView.findViewById(R.id.etIngredients);
        etSteps = rootView.findViewById(R.id.etSteps);
        btnCreateRecipe = rootView.findViewById(R.id.btnCreateRecipe);
        etRecipeName = rootView.findViewById(R.id.etRecipeName);

        llCaptureImage.setOnClickListener(this);
        btnCreateRecipe.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivCaptureImage:
                dispatchTakePictureIntent();
                break;

            case R.id.btnCreateRecipe:
                if(editRecipe){
                    alertTool.alertDuoButton(getContext(),Constant.EDIT_RECIPE_DIALOG,R.string.update_recipe,R.string.yes,R.string.no);
                }else {
                    validation();
                }
                break;
        }
    }

    private void setUpEditRecipeScreen(RecipeObject recipeObject){
        if(editRecipe){
            tvTitle.setText("Edit Recipe");
            llCaptureImage.setBackground(recipeObject.getRecipeImage());
            adjustLayoutSize(llCaptureImage);
            etRecipeName.setText(recipeObject.getRecipeName());
            etCompletionTime.setText(recipeObject.getTimeComplete());
            setSpinnerValueWhenEdit();
            etIngredients.setText(recipeObject.getRecipeIngredients());
            etSteps.setText(recipeObject.getRecipeDirections());
            btnCreateRecipe.setText("Update Recipe");
            hasCapturedImage = true;
        }

    }


    private void setSpinnerValueWhenEdit(){
        String level = recipeObject.getLevel();
        switch (level){
            case "Easy":
                spDifficulty.setSelection(0);
                break;
            case "Medium":
                spDifficulty.setSelection(1);
                break;
            case "Hard":
                spDifficulty.setSelection(2);
                break;

        }
    }

    private void adjustLayoutSize(LinearLayout linearLayout){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(800, 600, Gravity.CENTER);
        linearLayout.setLayoutParams(layoutParams);
    }

    // Open Camera Function
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Constant.TAKE_PHOTO);
        }
    }

    // Validation For Recipe Creation

    private void validation(){
        if(etRecipeName.getText().toString().trim().isEmpty()){
            alertTool.alertSingleButton(getContext(),Constant.VALIDATE_NAME,R.string.validate_recipe_name,R.string.ok);
        }else if(!hasCapturedImage){
            alertTool.alertSingleButton(getContext(),Constant.VALIDATE_PHOTO,R.string.validate_image,R.string.ok);
        }else if(etCompletionTime.getText().toString().trim().isEmpty()){
            alertTool.alertSingleButton(getContext(),Constant.VALIDATE_COMPLETION_TIME,R.string.validate_completion_time,R.string.ok);
        }else if(spDifficulty.getSelectedItem().toString().trim().isEmpty()){
            alertTool.alertSingleButton(getContext(),Constant.VALIDATE_DIFFICULTY,R.string.validate_difficulty,R.string.ok);
        }else if(etIngredients.getText().toString().trim().isEmpty()){
            alertTool.alertSingleButton(getContext(),Constant.VALIDATE_INGREDIENTS,R.string.validate_ingredients,R.string.ok);
        }else if(etSteps.getText().toString().trim().isEmpty()){
            alertTool.alertSingleButton(getContext(),Constant.VALIDATE_STEPS,R.string.validate_steps,R.string.ok);
        }else{
            alertTool.alertDuoButton(getContext(),Constant.CREATE_RECIPE_DIALOG,R.string.create_new_recipe,R.string.yes,R.string.no);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //Convert to Drawable First
            Utility.bitmap2Drawable(imageBitmap);
            llCaptureImage.setBackground(Utility.bitmap2Drawable(imageBitmap));
            adjustLayoutSize(llCaptureImage);
            hasCapturedImage = true;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Image = byteArray;
        }
    }


    /** Actions when Click Positive Button On Dialog **/
    @Override
    public void onPositiveDialogClick(String dialogTag) {
        switch (dialogTag){
            case Constant.CREATE_RECIPE_DIALOG:
                String completionTime = etCompletionTime.getText().toString().trim();
                String difficulty = spDifficulty.getSelectedItem().toString().trim();
                String ingredients = etIngredients.getText().toString().trim();
                String steps = etSteps.getText().toString().trim();
                String recipeName = etRecipeName.getText().toString().trim();
                String type =spRecipeType.getSelectedItem().toString();
                String selfCreatedindicator = "yes";

                String[] insertArr = {
                        recipeName,
                        type,
                        completionTime,
                        difficulty,
                        ingredients,
                        steps,
                        selfCreatedindicator

                };
                dbHelper.insertRecipeInfo(insertArr,Image);
                getActivity().getSupportFragmentManager().popBackStack(Home.class.getSimpleName(), 0);
                Toast.makeText(getContext(), "Recipe Created Successfully! Yum Yum", Toast.LENGTH_SHORT)
                        .show();
                break;
            case Constant.VALIDATE_PHOTO:
                break;
            case Constant.VALIDATE_NAME:
                etRecipeName.requestFocus();
                break;
            case Constant.VALIDATE_COMPLETION_TIME:
                etCompletionTime.requestFocus();
                break;
            case Constant.VALIDATE_DIFFICULTY:
                spDifficulty.requestFocus();
                break;
            case Constant.VALIDATE_INGREDIENTS:
                break;
            case Constant.VALIDATE_STEPS:
                etSteps.requestFocus();
                break;
            case Constant.EDIT_RECIPE_DIALOG:
                Utility.printInfo("TAG","TEST" + recipeObject.getImage());
                String selectedRecipeName = recipeObject.getRecipeName();
                dbHelper.updateDatabase(getEditedRecipe(), selectedRecipeName);
                getActivity().getSupportFragmentManager().popBackStack(ViewRecipe.class.getSimpleName(), 0);
                break;
        }
    }



    private RecipeObject getEditedRecipe(){
        RecipeObject recipeObject = new RecipeObject();
        byte[] Imagebyte = Utility.drawableToByte(llCaptureImage.getBackground());
        recipeObject.setImage(Imagebyte);
        String recipeName = etRecipeName.getText().toString().trim();
        String completionTime = etCompletionTime.getText().toString().trim();
        String difficulty = spDifficulty.getSelectedItem().toString().trim();
        String ingredients = etIngredients.getText().toString().trim();
        String steps = etSteps.getText().toString().trim();
        String type = spRecipeType.getSelectedItem().toString();
        String selfCreatedindicator = "yes";
        recipeObject.setRecipeName(recipeName);
        recipeObject.setRecipeTypes(type);
        recipeObject.setSelfCreatedIndicator(selfCreatedindicator);
        recipeObject.setTimeComplete(completionTime);
        recipeObject.setLevel(difficulty);
        recipeObject.setRecipeIngredients(ingredients);
        recipeObject.setRecipeDirections(steps);

        return recipeObject;


    }
    @Override
    public void onNegativeDialogClick(String dialogTag) {
    }
}
