package com.gav1s.hw1.ui.list;

import android.os.Bundle;

import com.gav1s.hw1.R;
import com.gav1s.hw1.ui.contextmenu.Callback;
import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.data.NoteSource;
import com.gav1s.hw1.ui.contextmenu.AddNoteView;

public class UpdateNotePresenter implements NotePresenter {

    public static String ARG_NOTE = "ARG_NOTE";
    public static final String KEY = "NoteContentFragment_UPDATE";

    private AddNoteView view;
    private NoteSource noteSource;
    private NoteData noteData;

    public UpdateNotePresenter(AddNoteView view, NoteSource noteSource, NoteData noteData) {
        this.view = view;
        this.noteSource = noteSource;
        this.noteData = noteData;

        view.setFabIcon(R.drawable.ic_baseline_edit_24);

        view.setTitle(noteData.getTitle());
        view.setMessage(noteData.getContent());
    }

    @Override
    public void onActionPressed(String title, String message) {
        view.showProgress();

        noteSource.update(noteData.getId(), title, message, new Callback<NoteData>() {
            @Override
            public void onSuccess(NoteData result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable error) {
                view.hideProgress();
            }
        });

    }
}
