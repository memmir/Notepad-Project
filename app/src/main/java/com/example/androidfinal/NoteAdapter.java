/*
package com.example.androidfinal;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes)
    {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNotes(int position)
    {
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder
    {
        TextView textViewTitle, textViewDescription;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);

            itemView.setOnClickListener((View v) -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION)
                {
                    listener.onItemClick(notes.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}*/


package com.example.androidfinal;


import android.content.Context;
import android.view.View;

import com.example.androidfinal.Note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes = new ArrayList<>();
    private OnItemClickListener listener;

    private Context context; // Context değişkeni ekleyin



    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());

        //holder.itemView.setBackgroundColor(currentNote.getColor());

        /*// Kategoriye göre renk ayarları
        switch (currentNote.getCategory()) {
            case "İş":
                holder.itemView.setBackgroundColor(Color.RED);
                break;
            case "Okul":
                holder.itemView.setBackgroundColor(Color.GREEN);
                break;
            case "Spor":
                holder.itemView.setBackgroundColor(Color.BLUE);
                break;
            default:
                // Diğer durumlar için bir renk ayarı yapabilirsiniz
                holder.itemView.setBackgroundColor(Color.WHITE);
                break;
        }*/

        /*String category = currentNote.getCategory();
        int color;
        switch (category) {
            case "iş":
                color = ContextCompat.getColor(context, R.color.colorCategoryWork);
                break;
            case "okul":
                color = ContextCompat.getColor(context, R.color.colorCategorySchool);
                break;
            case "spor":
                color = ContextCompat.getColor(context, R.color.colorCategorySport);
                break;
            default:
                color = ContextCompat.getColor(context, R.color.black); // Varsayılan renk
        }

        holder.itemView.setBackgroundColor(color);*/
        String category = currentNote.getCategory();
        if (category != null) {
            switch (category) {
                case "İş":
                    holder.textViewTitle.setTextColor(Color.RED);
                    break;
                case "Okul":
                    holder.textViewTitle.setTextColor(Color.GREEN);
                    break;
                case "Spor":
                    holder.textViewTitle.setTextColor(Color.BLUE);
                    break;
                default:
                    // Diğer durumlar için bir renk ayarı yapabilirsiniz
                    holder.textViewTitle.setTextColor(Color.BLACK);
                    break;
            }
        } else {
            // Kategori null ise, bir varsayılan renk ataması yapabilirsiniz
            holder.textViewTitle.setTextColor(Color.BLACK);
        }

        // Delete ikonuna tıklama işlemi
        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(notes.get(adapterPosition));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNotes(int position) {
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;
        ImageButton imageButtonDelete;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageButtonDelete = itemView.findViewById(R.id.imageButtonDelete);

            // Nota tıklama işlemi
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(notes.get(position));
                    }
                }
            });
            // Delete ImageButton tıklama işlemi
            imageButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);

        void onDeleteClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

