package com.example.bmateo.roomdatabase_example.data;

import android.os.AsyncTask;

import com.example.bmateo.roomdatabase_example.data.NoteDao;
import com.example.bmateo.roomdatabase_example.models.Note;

public class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

    private NoteDao noteDao;

    public DeleteNoteAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDao.delete(notes[0]);
        return null;
    }
}
