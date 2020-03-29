package com.example.recipeapp.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.recipeapp.Fragments.Home;
import com.example.recipeapp.R;
import com.example.recipeapp.Utility.Utility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Home homeFragment = new Home();
        Utility.replaceFragment(MainActivity.this, R.id.container, homeFragment, Home.class.getSimpleName());
        Utility.hideMobileStatusBar(MainActivity.this);

    }
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(currentFragment instanceof Home){
            finish();
        }
        super.onBackPressed();
    }

}
