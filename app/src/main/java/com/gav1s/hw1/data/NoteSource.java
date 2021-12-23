package com.gav1s.hw1.data;

import com.gav1s.hw1.ui.contextmenu.Callback;

import java.util.List;

public interface NoteSource {

    void getAll(Callback<List<NoteData>> callback);

    void save (String title, String message, Callback<NoteData> callback);

    void update (String noteId, String title, String message, Callback<NoteData> callback);

    void delete (NoteData noteData, Callback<Void> callback);
}
