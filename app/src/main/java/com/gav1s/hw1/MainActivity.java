package com.gav1s.hw1;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;

import com.gav1s.hw1.data.FragmentChangeListener;
import com.gav1s.hw1.data.NoteSource;
import com.gav1s.hw1.data.NoteSourceImpl;
import com.gav1s.hw1.ui.NoteContentFragment;
import com.gav1s.hw1.ui.Settings;
import com.gav1s.hw1.ui.SettingsFragment;
import com.gav1s.hw1.ui.NotesListFragment;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener {

    private static final String KEY_INDEX = "index";
    private int index;
    private boolean isLandscape;
    public static NoteSource noteSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readSettings();
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        if (savedInstanceState == null) {
            addFragments();
        }
        try {
            noteSource = new NoteSourceImpl().init();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initView();
    }

    private void readSettings() {
        SharedPreferences sharedPref = getSharedPreferences(Settings.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        Settings.isDarkTheme = sharedPref.getBoolean(Settings.DARK_THEME, false);
    }
    private void addFragments() {
        NotesListFragment notesListFragment = NotesListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer, notesListFragment)
                .commit();
        if (isLandscape) {
            NoteContentFragment noteContentFragment = NoteContentFragment.newInstance(index);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentContainer, noteContentFragment)
                    .commit();
        }
    }
    private void initView() {
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigationDrawerOpen,
                R.string.navigationDrawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (navigateFragment(id)){
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
            return false;
        });
    }
    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (navigateFragment(id)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("NonConstantResourceId")
    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.actionSettings:
                replaceFragment(new SettingsFragment());
                return true;
            case R.id.actionFavorite:
                Toast.makeText(MainActivity.this, "Favorite menu selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.actionSearch);
        SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        index = savedInstanceState.getInt(KEY_INDEX);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainContainer, NotesListFragment.newInstance())
                .commit();
        if (isLandscape) {
            NoteContentFragment noteContentFragment = NoteContentFragment.newInstance(index);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, noteContentFragment)
                    .commit();
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_INDEX, index);
    }
    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        if (isLandscape) {
            fragmentTransaction.replace(R.id.contentContainer, fragment);
        } else {
            fragmentTransaction.replace(R.id.mainContainer, fragment)
                    .addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
    @Override
    public void closeFragmentAndBackTo(int indexPopFragment) {
        if (isLandscape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentContainer, NoteContentFragment.newInstance(indexPopFragment))
                    .commit();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
    @Override
    public void changeIndex(int index) {
        this.index = index;
    }
}