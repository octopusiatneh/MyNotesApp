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

import static com.iatneh.mynotesapp.activity.main.MainActivity.noteList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    Context mContext;
    ItemClickListener itemClickListener;

    private List<Note> mNoteList;

    public NoteAdapter(Context context, List<Note> mNoteList) {
        mContext = context;
        this.mNoteList = mNoteList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note_item = mNoteList.get(position);
        holder.tv_title.setText(note_item.getTitle());
        holder.tv_note.setText(note_item.getContent());
        holder.tv_date.setText(note_item.getDate());
        holder.card_item.setCardBackgroundColor(note_item.getColor());

        holder.card_item.setOnClickListener(v -> itemClickListener.onItemClick(note_item, position));
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
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

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase();
        List<Note> mFilteredList;
        if(charText.isEmpty()) {
            mFilteredList = noteList;
        } else {
            List<Note> filteredList = new ArrayList<>();
            for(Note row : noteList) {
                if(row.getTitle().toLowerCase().contains(charText)) {
                    filteredList.add(row);
                }
            }

            mFilteredList = filteredList;
        }

        mNoteList = mFilteredList;

        notifyDataSetChanged();
    }
}
