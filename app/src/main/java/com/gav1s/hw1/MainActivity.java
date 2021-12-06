package com.gav1s.hw1;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener {

    private static final String KEY_INDEX = "index";
    private int index;
    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        NotesListFragment notesListFragment = NotesListFragment.newInstance();
        if (savedInstanceState == null) {
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