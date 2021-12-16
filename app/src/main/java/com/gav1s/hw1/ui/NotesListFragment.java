package com.gav1s.hw1.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gav1s.hw1.MainActivity;
import com.gav1s.hw1.R;
import com.gav1s.hw1.data.FragmentChangeListener;
import com.gav1s.hw1.data.NoteSource;


public class NotesListFragment extends Fragment {

    private NotesListAdapter adapter;
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
        RecyclerView recyclerView = view.findViewById(R.id.recycleView);
        initRecyclerView(recyclerView, MainActivity.noteSource);
        return view;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initRecyclerView(RecyclerView recyclerView, NoteSource noteSource) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NotesListAdapter(noteSource, this);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),  LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener((view, position) ->
                changeFragment(position));
    }

    private void changeFragment(int index) {
        FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
        assert fragmentChangeListener != null;
        fragmentChangeListener.changeIndex(index);
        fragmentChangeListener.replaceFragment(NoteContentFragment.newInstance(index));
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.note_actions, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();

        switch(item.getItemId()) {
            case R.id.actionEdit:
                FragmentChangeListener fragmentChangeListener = (FragmentChangeListener) getActivity();
                if (fragmentChangeListener != null) {
                    fragmentChangeListener.replaceFragment(NoteEditFragment.newInstance(position));
                }
                adapter.notifyItemChanged(position);
                return true;
            case R.id.actionSend:
                return true;
            case R.id.actionDelete:
                MainActivity.noteSource.deleteNoteData(position);
                adapter.notifyItemRemoved(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
