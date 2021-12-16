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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Date;

import com.gav1s.hw1.MainActivity;
import com.gav1s.hw1.R;
import com.gav1s.hw1.data.FragmentChangeListener;
import com.gav1s.hw1.data.NoteData;

public class NoteEditFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private int index;
    private NoteData noteData;

    public NoteEditFragment() {
    }

    public static NoteEditFragment newInstance(int index) {
        NoteEditFragment fragment = new NoteEditFragment();
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
        View view = inflater.inflate(R.layout.fragment_note_edit, container, false);
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
        if (item.getItemId() == R.id.actionEdit) {
            Toast.makeText(getContext(), "Chosen edit note", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void initView(View view) {
        noteData = MainActivity.noteSource.getNoteData(index);
        String preCreateTimeString = getResources().getString(R.string.preCreateTimeString);
        LinearLayout noteContentLayout = (LinearLayout) view;
        EditText title = view.findViewById(R.id.noteTitle);
        title.setText(noteData.getTitle());
        TextView createTime = view.findViewById(R.id.noteCreateTime);
        createTime.setText(preCreateTimeString + " " + noteData.getStringCreateTime());
        EditText content = view.findViewById(R.id.noteContent);
        content.setText(noteData.getContent());
        Button buttonDateEdit = new Button(getContext());
        buttonDateEdit.setText("Edit date");
        buttonDateEdit.setTextSize(getResources().getInteger(R.integer.lineTimeTextSize));
        buttonDateEdit.setOnClickListener(v -> {
            changeToFragment(DatePickerFragment.newInstance(index));
        });
        Button buttonSave = new Button(getContext());
        buttonSave.setText("Save");
        buttonSave.setTextSize(getResources().getInteger(R.integer.lineTimeTextSize));
        buttonSave.setOnClickListener(v -> {
            noteData.setTitle(title.getText().toString())
                    .setContent(content.getText().toString())
                    .setCreateTime(new Date());

            FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
            if (fragmentChangeListener != null) {
                fragmentChangeListener.closeFragmentAndBackTo(index);
            }});
        noteContentLayout.addView(buttonDateEdit);
        noteContentLayout.addView(buttonSave);
    }

    void changeToFragment(Fragment fragment) {
        FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
        if (fragmentChangeListener != null) {
            fragmentChangeListener.replaceFragment(fragment);
        }
    }
}