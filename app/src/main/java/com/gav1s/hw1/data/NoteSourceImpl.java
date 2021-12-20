package com.gav1s.hw1.data;

import android.content.Context;

import com.gav1s.hw1.R;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {

    @Override
    public List<NoteData> getNotes(Context context) {
        ArrayList<NoteData> updatedNotesList = new ArrayList<>();

        for (int i = 1; i < 101; i++) {
            updatedNotesList.add(new NoteData(context.getString(R.string.note_title) + "_" + i, getNoteContent(R.string.note_content, context, i), R.string.note_create_date));

        }
        return updatedNotesList;
    }

    private String getNoteContent(int resourceId, Context context, int i) {
        String noteContent = "";
        String tempContent = context.getString(resourceId);
        for (int j = 0; j < 100; j++) {
            noteContent = noteContent + tempContent + "_" + i + ", ";
            if (j < 99) {
            } else {
                noteContent = noteContent + tempContent + "_" + i;
            }
        }
        return noteContent;
    }
}