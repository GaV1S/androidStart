package com.gav1s.hw1;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.ui.AboutAppFragment;
import com.gav1s.hw1.ui.NoteContentFragment;
import com.gav1s.hw1.ui.navdrawer.SettingsFragment;
import com.gav1s.hw1.ui.NotesListFragment;

public class MainActivity extends AppCompatActivity implements com.gav1s.hw1.ui.ToolbarSetter {
    private static String ARG_NOTE = "ARG_NOTE";
    private NoteData selectedNote;
    private DrawerLayout navDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new NotesListFragment())
                    .commit();
        }

        if (savedInstanceState != null && savedInstanceState.containsKey(ARG_NOTE)) {
            selectedNote = savedInstanceState.getParcelable(ARG_NOTE);

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && selectedNote != null) {
                showNoteContent();
            }
        }

        getSupportFragmentManager()
                .setFragmentResultListener(NotesListFragment.RESULT_KEY, this, (requestKey, result) -> {
                    selectedNote = result.getParcelable(NotesListFragment.ARG_NOTE);

                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        showNoteContent();
                    } else {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .replace(R.id.fragment_container, NoteContentFragment.newInstance(selectedNote))
                                .addToBackStack("")
                                .commit();
                    }
                });

        navDrawer = findViewById(R.id.nav_drawer);

        NavigationView navigationView = findViewById(R.id.main_drawer);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            navigationView.setNavigationItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.actionAbout:
                        menuAction(new AboutAppFragment(), "aboutAppFragment");
                        return true;
                    case R.id.actionSettings:
                        menuAction(new SettingsFragment(), "settingsFragment");
                        return true;
                }
                return false;
            });
        }

    }

    private void menuAction(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_container, fragment, tag)
                .addToBackStack("")
                .commit();
        navDrawer.closeDrawer(GravityCompat.START);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (selectedNote != null) {
            outState.putParcelable(ARG_NOTE, selectedNote);
        }
    }

    private void showNoteContent() {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.fragment_content_container_land, NoteContentFragment.newInstance(selectedNote))
                .addToBackStack("")
                .commit();
    }

    public void setSelectedNoteToNull() {
        selectedNote = null;
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this,
                    navDrawer,
                    toolbar,
                    R.string.appbar_scrolling_view_behavior,
                    R.string.appbar_scrolling_view_behavior
            );
            navDrawer.addDrawerListener(toggle);
            toggle.syncState();
        }
    }
}