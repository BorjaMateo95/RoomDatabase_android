package com.example.bmateo.roomdatabase_example.data;

import android.os.AsyncTask;

import com.example.bmateo.roomdatabase_example.data.NoteDao;
import com.example.bmateo.roomdatabase_example.models.Note;

import java.util.List;

public class InsertNoteAsyncTask extends AsyncTask<Void, Void, Void> {

    private NoteDao noteDao;
    private List<Note> notes;

    public InsertNoteAsyncTask(NoteDao noteDao, List<Note> notes) {
        this.noteDao = noteDao;
        this.notes = notes;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        noteDao.insertAll(notes);
        return null;
    }
}
