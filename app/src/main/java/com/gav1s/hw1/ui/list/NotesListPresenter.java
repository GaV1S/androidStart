package com.gav1s.hw1.ui.list;

import com.gav1s.hw1.ui.contextmenu.Callback;
import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.data.NoteSource;
import com.gav1s.hw1.ui.contextmenu.AdapterItem;
import com.gav1s.hw1.ui.contextmenu.NoteAdapterItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesListPresenter {

    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private NotesListView view;

    private NoteSource repository;

    public NotesListPresenter(NotesListView view, NoteSource repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        view.showProgress();

        repository.getAll(new Callback<List<NoteData>>() {
            @Override
            public void onSuccess(List<NoteData> result) {
                ArrayList<AdapterItem> adapterItems = new ArrayList<>();

                for (NoteData noteData : result) {
                    adapterItems.add(new NoteAdapterItem(noteData, noteData.getTitle(), dateFormat.format(noteData.getCreatedAt()) + " " + timeFormat.format(noteData.getCreatedAt())));
                }

                view.showNotes(adapterItems);

                if (adapterItems.isEmpty()) {
                    view.showEmpty();
                } else {
                    view.hideEmpty();
                }

                view.hideProgress();
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });

    }

    public void onNoteAdded(NoteData noteData) {
        NoteAdapterItem adapterItem = new NoteAdapterItem(noteData, noteData.getTitle(), dateFormat.format(noteData.getCreatedAt()) + " " + timeFormat.format(noteData.getCreatedAt()));

        view.onNoteAdded(adapterItem);

        view.hideEmpty();


    }

    public void removeItem(NoteData selectedNote) {
        view.showProgress();

        repository.delete(selectedNote, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.hideProgress();
                view.onNoteRemoved(selectedNote);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });
    }
}
