package com.gav1s.hw1.data;

import com.gav1s.hw1.data.NoteData;

public interface NoteSource {

    NoteData getNoteData(int position);

    int size();

    void updateNoteData(int position, NoteData noteData);

    void deleteNoteData(int position);
}
