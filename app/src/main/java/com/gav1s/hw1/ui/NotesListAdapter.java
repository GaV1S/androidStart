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

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.MyViewHolder> {

    private final static String TAG = "NoteListAdapter";

    private final NoteSource noteSource;
    private final Fragment fragment;
    private OnItemClickListener itemClickListener;
    private int menuPosition;

    public NotesListAdapter(NoteSource noteSource, Fragment fragment) {
        this.noteSource = noteSource;
        this.fragment = fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    public int getMenuPosition() {
        return menuPosition;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        final private TextView title;
        final private TextView createTime;

        @RequiresApi(api = Build.VERSION_CODES.N)
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            createTime = itemView.findViewById(R.id.createTime);
            registerContextMenu(itemView);
            LinearLayout linearLayout = itemView.findViewById(R.id.noteLine);
            linearLayout.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            linearLayout.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                itemView.showContextMenu(10, 10);
                return true;
            });

        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null){
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public void bind(NoteData noteData){
            title.setText(noteData.getTitle());
            createTime.setText(noteData.getStringCreateTime());
        }
    }
}