package com.gav1s.hw1.ui.contextmenu;

import android.os.Bundle;

import com.gav1s.hw1.R;
import com.gav1s.hw1.ui.contextmenu.Callback;
import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.data.NoteSource;
import com.gav1s.hw1.ui.list.NotePresenter;

public class AddNotePresenter implements NotePresenter {
    public static String ARG_NOTE = "ARG_NOTE";
    public static final String KEY = "NoteContentFragment_ADD";


    private AddNoteView view;
    private NoteSource noteSource;

    public AddNotePresenter(AddNoteView view, NoteSource noteSource) {
        this.view = view;
        this.noteSource = noteSource;

        view.setFabIcon(R.drawable.ic_baseline_save_24);
    }

    @Override
    public void onActionPressed(String title, String message) {
        view.showProgress();
        noteSource.save(title, message, new Callback<NoteData>() {
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
