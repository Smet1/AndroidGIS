package com.park.smet_k.bauman_gis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private final static String KEY_IS_FIRST = "is_first";
    private final static String KEY_OAUTH = "oauth";
    private final static String STORAGE_NAME = "storage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        final ImageButton startNewActivityBtn = findViewById(R.id.imageButton);

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account: {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AccountFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

                drawerLayout.closeDrawers();
                break;
            }
            case R.id.navigation: {
                final Intent navIntent = new Intent(MainActivity.this, NavigatorActivity.class);

                startActivity(navIntent);
                // TODO(): есть ли смысл закрывать шторку, т.к. анимация
                drawerLayout.closeDrawers();
                break;
            }
            case R.id.settings: {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, SettingsFragment.newInstance())
                        .addToBackStack(null)
                        .commit();

                drawerLayout.closeDrawers();
                break;
            }
        }
        return true;
    }

}

