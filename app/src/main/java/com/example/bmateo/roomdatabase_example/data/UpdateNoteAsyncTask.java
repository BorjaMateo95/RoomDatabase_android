package com.example.bmateo.roomdatabase_example.data;

import android.os.AsyncTask;

import com.example.bmateo.roomdatabase_example.data.NoteDao;
import com.example.bmateo.roomdatabase_example.models.Note;

public class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao noteDao;

    public UpdateNoteAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.update(notes[0]);
        return null;
    }
}
