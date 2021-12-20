package com.gav1s.hw1.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gav1s.hw1.ui.dialogalert.AlertDialogFragment;
import com.gav1s.hw1.ui.list.NotesListPresenter;
import com.gav1s.hw1.ui.list.NotesListView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.gav1s.hw1.R;
import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.data.NoteSourceImpl;

import java.util.List;

public class NotesListFragment extends Fragment implements NotesListView {
    public static String ARG_NOTE = "ARG_NOTE";
    public static String RESULT_KEY = "NotesListFragment_RESULT";

    private RecyclerView notesContainer;
    private NotesListPresenter presenter;
    private NotesListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, new NoteSourceImpl());
        adapter = new NotesListAdapter();
        adapter.setOnClick(new NotesListAdapter.OnClick() {
            @Override
            public void onClick(NoteData note) {
                Bundle data = new Bundle();
                data.putParcelable(ARG_NOTE, note);

                getParentFragmentManager()
                        .setFragmentResult(RESULT_KEY, data);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager()
                .setFragmentResultListener(AlertDialogFragment.KEY_RESULT, this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        if (result.getInt(AlertDialogFragment.ARG_BUTTON) == R.id.btn_dialog_ok) {
                            requireActivity().finish();
                            Toast.makeText(requireContext(), getString(R.string.exit_message), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        notesContainer = view.findViewById(R.id.notes_container);
        notesContainer.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        notesContainer.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireContext(), getString(R.string.fab_new_note_message), Toast.LENGTH_SHORT).show();
            }
        });

        BottomAppBar bar = view.findViewById(R.id.bar);
        bar.replaceMenu(R.menu.main);

        Activity activity = requireActivity();
        if (activity instanceof ToolbarSetter) {
            ((ToolbarSetter) activity).setToolbar(bar);
        }

        bar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.actionSort:
                    Snackbar snackbar = Snackbar.make(view, R.string.snack_sync_notes, Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(requireContext().getColor(R.color.note_title));
                    snackbar.setTextColor(requireContext().getColor(R.color.background_dark));
                    snackbar.show();
                    return true;

                case R.id.actionSearch:
                    Toast.makeText(requireContext(), getString(R.string.search_notes_message), Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.actionExit:
                    showAlertFragmentDialog(getString(R.string.alert_dialog_exit_message));
                    return true;
            }
            return false;
        });

        presenter.updateNotesList(requireContext());
    }

    @Override
    public void showNotes(List<NoteData> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();
    }

    private void showAlertFragmentDialog(String message) {
        AlertDialogFragment.newInstance(message)
                .show(getParentFragmentManager(), AlertDialogFragment.getTAG());
    }
}