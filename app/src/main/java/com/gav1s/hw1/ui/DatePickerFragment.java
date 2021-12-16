package com.gav1s.hw1.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.gav1s.hw1.data.FragmentChangeListener;
import com.gav1s.hw1.MainActivity;
import com.gav1s.hw1.R;

import java.util.Calendar;

public class DatePickerFragment extends Fragment {

    private static final String ARG_INDEX = "index";
    private int index;

    private DatePicker datePicker;

    public DatePickerFragment() {
    }

    public static DatePickerFragment newInstance(int index) {
        DatePickerFragment fragment = new DatePickerFragment();
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
        View view = inflater.inflate(R.layout.fragment_date_picker, container, false);
        datePicker = view.findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(MainActivity.noteSource.getNoteData(index).getCreateTime());
        datePicker.updateDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        view.findViewById(R.id.saveDateButton).setOnClickListener(v -> {
            calendar.set(datePicker.getYear(),
                    datePicker.getMonth(),
                    datePicker.getDayOfMonth());
            MainActivity.noteSource.getNoteData(index).setCreateTime(calendar.getTime());
            FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
            if (fragmentChangeListener != null) {
                fragmentChangeListener.closeFragmentAndBackTo(index);
            }
        });
        return view;
    }
}
