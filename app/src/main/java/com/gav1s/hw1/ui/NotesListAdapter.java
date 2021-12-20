package com.gav1s.hw1.ui;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.gav1s.hw1.R;
import com.gav1s.hw1.data.NoteData;
import com.gav1s.hw1.data.NoteSource;

import java.util.ArrayList;
import java.util.Collection;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private ArrayList<NoteData> data = new ArrayList<NoteData>();

    private OnClick onClick;

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    interface OnClick {
        void onClick(NoteData note);
    }


    public void setData(Collection<NoteData> notes) {
        data.clear();
        data.addAll(notes);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        NoteData note = data.get(position);

        holder.getNoteTitle().setText(note.getNoteTitle());
        holder.getNoteCreateDate().setText(note.getNoteCreateDate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView noteTitle;

        private final TextView noteCreateDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.note_title);

            noteCreateDate = itemView.findViewById(R.id.note_create_date);

            itemView.findViewById(R.id.card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NoteData note = data.get(getAdapterPosition());

                    if (getOnClick() != null) {
                        getOnClick().onClick(note);
                    }
                }
            });
        }

        public TextView getNoteTitle() {
            return noteTitle;
        }

        public TextView getNoteCreateDate() {
            return noteCreateDate;
        }
    }
}