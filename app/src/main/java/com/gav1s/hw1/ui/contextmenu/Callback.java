package com.gav1s.hw1.ui.contextmenu;

public interface Callback<T> {

    void onSuccess(T result);

    void onError(Throwable error);
}
