package com.levking.ivan.traveller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.levking.ivan.traveller.fragments.AboutFragment;
import com.levking.ivan.traveller.fragments.AccountFragment;
import com.levking.ivan.traveller.fragments.ConfirmFragment;
import com.levking.ivan.traveller.fragments.CountryFragment;
import com.levking.ivan.traveller.fragments.DialogListItemFragment;
import com.levking.ivan.traveller.fragments.HistoryFragment;
import com.levking.ivan.traveller.fragments.SettingFragment;
import com.levking.ivan.traveller.fragments.dummy.DummyContent;
import com.mukesh.countrypicker.Country;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , CountryFragment.OnFragmentInteractionListener
, HistoryFragment.OnListFragmentInteractionListener, ConfirmFragment.OnFragmentInteractionListener, DialogListItemFragment.OnFragmentInteractionListener,
        SettingFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener{


    private static final String APP_PREFERENCES = "preferences";
    SharedPreferences preferences;

    public FragmentManager fragmentManager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showCountryFragment();

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(preferences.contains("item count")) {
           // DummyContent.COUNT = preferences.getInt("item count", 0);
            for (int i = 0; i < preferences.getInt("item count",0); i++)
                DummyContent.addItem(new DummyContent.DummyItem(
                        Country.getCountryByName(preferences.getString("item" + i + "depCountryName", "")),
                        Country.getCountryByName(preferences.getString("item" + i + "destCountryName", "")),
                        preferences.getString("item" + i + "details", ""),
                        preferences.getLong("item" + i + "depDate", 1),
                        preferences.getLong("item" + i + "destDate", 0),
                        preferences.getBoolean("item"+i+"state",false)));
        }
        preferences.edit().clear().apply();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
        editor.putInt("item count",DummyContent.ITEMS.size());
        for(int i =0; i<DummyContent.ITEMS.size(); i++){
            editor.putString("item"+i+"depCountryName",DummyContent.ITEMS.get(i).depCountry.getName())
            .putString("item"+i+"destCountryName",DummyContent.ITEMS.get(i).destCountry.getName())
            .putString("item"+i+"details",DummyContent.ITEMS.get(i).details)
            .putLong("item"+i+"depDate",DummyContent.ITEMS.get(i).depDate)
            .putLong("item"+i+"destDate",DummyContent.ITEMS.get(i).destDate)
            .putBoolean("item"+i+"state",DummyContent.ITEMS.get(i).state);
        }
        DummyContent.ITEMS.clear();
        DummyContent.ITEM_MAP.clear();
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            fragmentManager.beginTransaction().replace(R.id.content_main,CountryFragment.newInstance(null)).commit();
            //super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_acc) {
            fragmentManager.beginTransaction().replace(R.id.content_main, AccountFragment.newInstance()).commit();
        } else if (id == R.id.nav_history) {
            fragmentManager.beginTransaction().replace(R.id.content_main, HistoryFragment.newInstance(1)).commit();
        } else if (id == R.id.nav_settings) {
            fragmentManager.beginTransaction().replace(R.id.content_main, SettingFragment.newInstance()).commit();
        } else if (id == R.id.nav_about) {
            fragmentManager.beginTransaction().replace(R.id.content_main, AboutFragment.newInstance()).commit();
        } else if (id == R.id.nav_main) {
            showCountryFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showCountryFragment(){
        fragmentManager.beginTransaction().replace(R.id.content_main,CountryFragment.newInstance(null)).commit();

    }


    public void onFragmentInteraction(Uri uri){

    }

    public void onListFragmentInteraction(DummyContent.DummyItem item){

    }
}
