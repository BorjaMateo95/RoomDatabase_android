package com.example.bmateo.roomdatabase_example.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bmateo.roomdatabase_example.R;
import com.example.bmateo.roomdatabase_example.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private LoadMore loadMore;
    int visibleItems = 30;
    int lastVisible, totalItem;
    private final int VIEW_TYPE_ITEM=0, VIEW_TYPE_LOADING = 1;

    public NoteAdapter(RecyclerView recyclerView) {

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItem = linearLayoutManager.getItemCount();
                lastVisible = linearLayoutManager.findLastVisibleItemPosition();

                if (totalItem <= (lastVisible+visibleItems)) {
                    if (loadMore != null) {
                        loadMore.onLoadMore();
                    }
                }
            }
        });
    }


    @Override
    public int getItemViewType(int position) {
        return notes.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_ITEM;
    }

    public void setLoadMore(LoadMore loadMore) {
        this.loadMore = loadMore;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder noteHolder, int i) {
        Note nota = notes.get(i);
        noteHolder.txPriority.setText(String.valueOf(nota.getPriority()));
        noteHolder.txDescription.setText(nota.getDescription());
        noteHolder.txTitle.setText(nota.getTitle());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void addNotes(List<Note> subList) {
        this.notes.addAll(subList);
        notifyDataSetChanged();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        private TextView txTitle;
        private TextView txDescription;
        private TextView txPriority;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            txTitle = itemView.findViewById(R.id.text_view_title);
            txDescription = itemView.findViewById(R.id.text_view_description);
            txPriority = itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(notes.get(getAdapterPosition()));
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


}
