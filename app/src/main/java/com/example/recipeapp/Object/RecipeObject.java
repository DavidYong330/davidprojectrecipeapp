package com.example.recipeapp.Object;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.Serializable;

public class RecipeObject implements Serializable {

    private String recipeName;
    private String recipeDescription;
    private String level;
    private String timeComplete;
    private Drawable recipeImage;
    private String recipeTypes;
    private String recipeIngredients;
    private String recipeDirections;
    private String selfCreatedIndicator;
    private byte[] Image;




    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipeDirections() {
        return recipeDirections;
    }

    public void setRecipeDirections(String recipeDirections) {
        this.recipeDirections = recipeDirections;
    }


    public String getRecipeTypes() {
        return recipeTypes;
    }

    public void setRecipeTypes(String recipeTypes) {
        this.recipeTypes = recipeTypes;
    }

    public Drawable getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(Drawable recipeImage) {
        this.recipeImage = recipeImage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTimeComplete() {
        return timeComplete;
    }

    public void setTimeComplete(String timeComplete) {
        this.timeComplete = timeComplete;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getSelfCreatedIndicator() {
        return selfCreatedIndicator;
    }

    public void setSelfCreatedIndicator(String selfCreatedIndicator) {
        this.selfCreatedIndicator = selfCreatedIndicator;
    }




}
