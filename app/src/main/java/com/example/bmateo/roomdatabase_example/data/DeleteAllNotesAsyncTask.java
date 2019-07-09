package com.example.bmateo.roomdatabase_example.data;

import android.os.AsyncTask;

import com.example.bmateo.roomdatabase_example.data.NoteDao;

public class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

    private NoteDao noteDao;

    public DeleteAllNotesAsyncTask(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        noteDao.deleteAllNotes();
        return null;
    }
}
