package com.gav1s.hw1.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gav1s.hw1.MainActivity;
import com.gav1s.hw1.R;
import com.gav1s.hw1.data.FragmentChangeListener;

public class NoteContentFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private int index;

    public NoteContentFragment() {
    }

    public static NoteContentFragment newInstance(int index) {
        NoteContentFragment fragment = new NoteContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_content, container, false);
        setHasOptionsMenu(true);
        initView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.note_actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actionEdit){
            changeToFragment(NoteEditFragment.newInstance(index));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        String preCreateTimeString = getResources().getString(R.string.preCreateTimeString);
        LinearLayout noteContentLayout = (LinearLayout) view;
        TextView title = view.findViewById(R.id.noteTitle);
        title.setText(MainActivity.noteSource.getNoteData(index).getTitle());
        TextView createTime = view.findViewById(R.id.noteCreateTime);
        createTime.setText(preCreateTimeString + " "
                + MainActivity.noteSource.getNoteData(index).getStringCreateTime());
        TextView content = view.findViewById(R.id.noteContent);
        content.setText(MainActivity.noteSource.getNoteData(index).getContent());
        Button buttonDateEdit = new Button(getContext());
        buttonDateEdit.setText("Edit note");
        buttonDateEdit.setTextSize(getResources().getInteger(R.integer.lineTimeTextSize));
        buttonDateEdit.setOnClickListener(v -> {
            changeToFragment(NoteEditFragment.newInstance(index));
        });
        noteContentLayout.addView(buttonDateEdit);

    }

    void changeToFragment(Fragment fragment) {
        FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
        if (fragmentChangeListener != null) {
            fragmentChangeListener.replaceFragment(fragment);
        }
    }
}