package com.gav1s.hw1.data;

import android.os.Handler;
import android.os.Looper;

import com.gav1s.hw1.ui.contextmenu.Callback;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteSourceImpl implements NoteSource {

    public static final NoteSource INSTANCE = new NoteSourceImpl ();

    private final ArrayList<NoteData> notes = new ArrayList<>();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final Handler handler = new Handler(Looper.getMainLooper());

    private NoteSourceImpl() {

        Calendar calendar = Calendar.getInstance();

        for (int i = 1; i < 51; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, -(int) (1 + Math.random() * 365));
            calendar.add(Calendar.MINUTE, -(int) (1 + Math.random() * 60));
            String text = "Note_" + i;
            notes.add(new NoteData(UUID.randomUUID().toString(), text, contentMaker(text), calendar.getTime()));
        }
    }

    private String contentMaker(String content) {
        String newContent = content;
        for (int i = 0; i < 200; i++) {
            newContent = newContent + ", " + content;
        }
        return newContent;
    }

    @Override
    public void getAll(Callback<List<NoteData>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(notes);
                    }
                });
            }
        });
    }

    @Override
    public void save(String title, String message, Callback<NoteData> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        NoteData noteData = new NoteData(UUID.randomUUID().toString(), title, message, new Date());

                        notes.add(noteData);

                        callback.onSuccess(noteData);
                    }
                });
            }
        });
    }

    @Override
    public void update(String noteId, String title, String message, Callback<NoteData> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        int index = 0;

                        for (int i = 0; i < notes.size(); i++) {
                            if (notes.get(i).getId().equals(noteId)) {
                                index = i;
                                break;
                            }
                        }

                        NoteData editableNote = notes.get(index);

                        editableNote.setTitle(title);
                        editableNote.setContent(message);

                        callback.onSuccess(editableNote);
                    }
                });
            }
        });
    }

    @Override
    public void delete(NoteData noteData, Callback<Void> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        notes.remove(noteData);

                        callback.onSuccess(null);
                    }
                });
            }
        });
    }
}