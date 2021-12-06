package com.gav1s.hw1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class NotesListFragment extends Fragment {

    public NotesListFragment() {
    }

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_list, container, false);
        try {
            createNotesList();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initList(view);
        return view;
    }

    private void createNotesList() throws ParseException {
        AppData.notes = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        formatter.setLenient(false);
        AppData.notes.add(new Note("Title 1", "Content 1",
                formatter.parse("12:45 05.01.2021"), 1));
        AppData.notes.add(new Note("Title 2", "Content 2",
                formatter.parse("08:14 15.01.2021"), 1));
        AppData.notes.add(new Note("Title 3", "Content 3",
                formatter.parse("22:05 03.02.2021"), 1));
        AppData.notes.add(new Note("Title 4", "Content 4",
                formatter.parse("11:30 19.02.2021"), 1));
        AppData.notes.add(new Note("Title 5", "Content 5",
                formatter.parse("16:17 28.02.2021"), 1));
        AppData.notes.add(new Note("Title 6", "Content 6",
                formatter.parse("16:49 08.03.2021"), 1));
        AppData.notes.add(new Note("Title 7", "Content 7",
                formatter.parse("18:24 16.03.2021"), 1));
    }

    @SuppressLint("ResourceAsColor")
    private void initList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        int mainLayoutPadding = getResources().getInteger(R.integer.mainLayoutPadding);
        view.setPadding(mainLayoutPadding, 0, mainLayoutPadding, 0);
        for (int i = 0; i < AppData.notes.size(); i++) {
            LinearLayout noteLineLayout = new LinearLayout(getContext());
            noteLineLayout.setOrientation(LinearLayout.VERTICAL);
            noteLineLayout.setBackgroundColor(R.color.design_default_color_on_primary);
            int noteLinePadding = getResources().getInteger(R.integer.noteLinePadding);
            noteLineLayout.setPadding(
                    noteLinePadding * 2,
                    noteLinePadding,
                    noteLinePadding,
                    noteLinePadding);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            int noteLineMargin = getResources().getInteger(R.integer.noteLineMargin);
            layoutParams.setMargins(
                    noteLineMargin,
                    noteLineMargin,
                    noteLineMargin,
                    0);
            TextView titleTextView = new TextView(getContext());
            titleTextView.setText(AppData.notes.get(i).getTitle());
            titleTextView.setTextSize(getResources().getInteger(R.integer.lineTitleTextSize));
            noteLineLayout.addView(titleTextView);
            TextView timeTextView = new TextView(getContext());
            timeTextView.setGravity(Gravity.END);
            timeTextView.setText(AppData.notes.get(i).getStringCreateTime());
            timeTextView.setTextSize(getResources().getInteger(R.integer.lineTimeTextSize));
            noteLineLayout.addView(timeTextView);
            final int fi = i;
            noteLineLayout.setOnClickListener(v -> changeFragment(fi));
            linearLayout.addView(noteLineLayout, layoutParams);
            linearLayout.requestLayout();
        }
    }

    private void changeFragment(int index) {
        FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
        assert fragmentChangeListener != null;
        fragmentChangeListener.changeIndex(index);
        fragmentChangeListener.replaceFragment(NoteContentFragment.newInstance(index));
    }

}
