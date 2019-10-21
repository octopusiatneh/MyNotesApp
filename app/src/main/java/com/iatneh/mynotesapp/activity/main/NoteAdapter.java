package com.iatneh.mynotesapp.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.iatneh.mynotesapp.R;
import com.iatneh.mynotesapp.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    Context context;
    ItemClickListener itemClickListener;
    List<Note> mData;
    public NoteAdapter(Context context, List<Note> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note_item = mData.get(position);
        holder.tv_title.setText(note_item.getTitle());
        holder.tv_note.setText(note_item.getNote());
        holder.tv_date.setText(note_item.getDate());
        holder.card_item.setCardBackgroundColor(note_item.getColor());

        holder.card_item.setOnClickListener(v -> itemClickListener.onItemClick(note_item, position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_note, tv_date;
        CardView card_item;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.title);
            tv_note = itemView.findViewById(R.id.note);
            tv_date = itemView.findViewById(R.id.date);

            card_item = itemView.findViewById(R.id.card_item);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Note note, int position);
    }

    public List<Note> getData() {
        return mData;
    }


}
