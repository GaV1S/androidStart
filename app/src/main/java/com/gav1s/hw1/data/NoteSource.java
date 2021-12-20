package com.gav1s.hw1.data;

import android.content.Context;

import java.util.List;

public interface NoteSource {

    List<NoteData> getNotes(Context context);
}
