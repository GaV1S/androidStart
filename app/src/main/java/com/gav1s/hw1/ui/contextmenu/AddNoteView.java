package com.gav1s.hw1.ui.contextmenu;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.DrawableRes;

import com.gav1s.hw1.data.NoteData;

public interface AddNoteView {

    void showProgress();

    void hideProgress();

    void setFabIcon(int icon);

    void setTitle(String title);

    void setMessage(String message);

    void actionCompleted(String key, Bundle bundle);
}
