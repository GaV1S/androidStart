package com.gav1s.hw1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.MyViewHolder> {

    private final static String TAG = "NoteListAdapter";

    private final NoteSource noteSource;
    private OnItemClickListener itemClickListener;

    public NotesListAdapter(NoteSource noteSource) {
        this.noteSource = noteSource;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(noteSource.getNoteData(position));
        Log.d(TAG, "onBindViewHolder");

    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final private TextView title;
        final private TextView createTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            createTime = itemView.findViewById(R.id.createTime);
            LinearLayout linearLayout = itemView.findViewById(R.id.noteLine);
            linearLayout.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
        }

        public void bind(NoteData noteData){
            title.setText(noteData.getTitle());
            createTime.setText(noteData.getStringCreateTime());
        }
    }
}