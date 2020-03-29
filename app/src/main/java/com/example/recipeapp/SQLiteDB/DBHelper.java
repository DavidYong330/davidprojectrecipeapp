package com.example.recipeapp.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.recipeapp.Object.RecipeObject;
import com.example.recipeapp.Utility.Utility;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void initDatabase (SQLiteDatabase db) {

        /**
         * Create user information table
         */
        db.execSQL("CREATE TABLE IF NOT EXISTS app_user(" +
                "name TEXT," +
                "type TEXT ," +
                "timecomplete TEXT, " +
                "level TEXT, " +
                "ingredients TEXT, " +
                "directions TEXT, " +
                "image BLOB, " +
                "selfcreated TEXT" +
                ");");

    }

    /**
     *Insert user information into app_user table
     */
    public void insertRecipeInfo(String[] userInfo, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", userInfo[0]);
        values.put("type", userInfo[1]);
        values.put("timecomplete", userInfo[2]);
        values.put("level", userInfo[3]);
        values.put("ingredients", userInfo[4]);
        values.put("directions", userInfo[5]);
        values.put("image", image);
        values.put("selfcreated", userInfo[6]);

        db.insert("app_user", null, values);
        db.close();
    }

    public ArrayList<RecipeObject> getAllCreatedRecipes(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<RecipeObject> allCreatedRecipes= new ArrayList<>();

        Cursor  cursor = db.rawQuery("select * from app_user",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                RecipeObject recipeObject = new RecipeObject();
                recipeObject.setRecipeName(cursor.getString(0));
                recipeObject.setRecipeTypes(cursor.getString(1));
                recipeObject.setTimeComplete(cursor.getString(2));
                recipeObject.setLevel(cursor.getString(3));
                recipeObject.setRecipeIngredients(cursor.getString(4));
                recipeObject.setRecipeDirections(cursor.getString(5));
                Bitmap bmp= BitmapFactory.decodeByteArray(cursor.getBlob(6),0,cursor.getBlob(6).length);
                recipeObject.setRecipeImage(Utility.bitmap2Drawable(bmp));
                recipeObject.setSelfCreatedIndicator(cursor.getString(7));
                allCreatedRecipes.add(recipeObject);
                cursor.moveToNext();

            }
        } return  allCreatedRecipes;
    }

    /**
     * Query all user information into app_user table
     */
    public RecipeObject fetchRecipeInfo(){
        SQLiteDatabase db = getWritableDatabase();
        RecipeObject recipe = null;

        Cursor c = db.rawQuery("SELECT * FROM app_user " , null);
        if (c.moveToNext()) {
            recipe = new RecipeObject();
            recipe.setRecipeName(c.getString(0));
            recipe.setRecipeTypes(c.getString(1));
            recipe.setTimeComplete(c.getString(2));
            recipe.setLevel(c.getString(3));
            recipe.setRecipeIngredients(c.getString(4));
            recipe.setRecipeDirections(c.getString(5));
            Bitmap bmp= BitmapFactory.decodeByteArray(c.getBlob(6),0,c.getBlob(6).length);
            recipe.setRecipeImage(Utility.bitmap2Drawable(bmp));

        }
        c.close();
        db.close();
        return recipe;
    }

    public void updateDatabase(RecipeObject recipeObject, String selectedRecipeName){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",recipeObject.getRecipeName());
        cv.put("type",recipeObject.getRecipeTypes());
        cv.put("timecomplete",recipeObject.getTimeComplete());
        cv.put("level",recipeObject.getLevel());
        cv.put("ingredients",recipeObject.getRecipeIngredients());
        cv.put("directions",recipeObject.getRecipeDirections());
        cv.put("image",recipeObject.getImage());
        cv.put("selfcreated",recipeObject.getSelfCreatedIndicator());
        db.update("app_user", cv, "name = ?", new String[]{selectedRecipeName});
        db.close();

    }

    public void deleteRow(String id) {
        String where = "name" + " = ?";
        String[] whereArgs = { String.valueOf(id) };

        SQLiteDatabase db = getWritableDatabase();
        db.delete("app_user", where, whereArgs);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        initDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        initDatabase(db);
    }
}

