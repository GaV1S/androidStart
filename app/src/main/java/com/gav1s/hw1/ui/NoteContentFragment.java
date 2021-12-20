package com.gav1s.hw1.ui;

import android.content.Context;
import android.app.Activity;
import android.view.inputmethod.InputMethodManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.ui.dialogalert.AlertDialogFragment;
import com.gav1s.hw1.ui.dialogalert.BottomDialogFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gav1s.hw1.MainActivity;
import com.gav1s.hw1.R;

public class NoteContentFragment extends Fragment {
    private static final String ARG_NOTE = "ARG_NOTE";

    public static NoteContentFragment newInstance(NoteData noteData) {
        NoteContentFragment fragment = new NoteContentFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_NOTE, noteData);
        fragment.setArguments(arguments);
        return fragment;
    }
    public NoteContentFragment() {
        super(R.layout.fragment_note_content);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NoteData noteData = requireArguments().getParcelable(ARG_NOTE);

        BottomAppBar bar = view.findViewById(R.id.bar);
        Activity activity = requireActivity();
        if (activity instanceof ToolbarSetter) {
            ((ToolbarSetter) activity).setToolbar(bar);
        }
        bar.replaceMenu(R.menu.note_actions);
        bar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.actionSend:
                    Toast.makeText(requireContext(), getString(R.string.menuSend), Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.actionEdit:
                    showBottomDialog(getString(R.string.menuEdit));
                    return true;

                case R.id.actionDelete:
                    showAlertFragmentDialog(getString(R.string.menuDelete));
                    return true;
            }
            return false;
        });
        TextView noteTitle = view.findViewById(R.id.noteTitle);
        TextView noteContent = view.findViewById(R.id.noteContent);
        ImageView close = view.findViewById(R.id.close_icon);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        noteTitle.setText(noteData.getNoteTitle());
        noteContent.setText(noteData.getNoteContent());

        fab.setOnClickListener(v -> Toast.makeText(requireContext(), getString(R.string.fab_edit_message), Toast.LENGTH_SHORT).show());
        close.setOnClickListener(v -> {
            hideKeyboardFrom(requireContext(), noteTitle);
            hideKeyboardFrom(requireContext(), noteContent);

            ((MainActivity) requireActivity()).setSelectedNoteToNull();
            requireActivity().onBackPressed();
        });
    }

    private void showAlertFragmentDialog(String message) {
        setDialogListener(AlertDialogFragment.KEY_RESULT, AlertDialogFragment.ARG_BUTTON, getString(R.string.note_deleted_message));

        if (getParentFragmentManager().findFragmentByTag(AlertDialogFragment.getTAG()) == null) {
            AlertDialogFragment.newInstance(message)
                    .show(getParentFragmentManager(), AlertDialogFragment.getTAG());
        }
    }

    private void showBottomDialog(String message) {
        setDialogListener(BottomDialogFragment.KEY_RESULT, BottomDialogFragment.ARG_BUTTON, getString(R.string.attach_message));

        if (getParentFragmentManager().findFragmentByTag(BottomDialogFragment.getTAG()) == null) {
            BottomDialogFragment.newInstance(message)
                    .show(getParentFragmentManager(), BottomDialogFragment.getTAG());
        }
    }

    private void setDialogListener(String keyResult, String argument, String message) {
        getParentFragmentManager()
                .setFragmentResultListener(keyResult, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        if (result.getInt(argument) == R.id.btn_dialog_ok) {
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}