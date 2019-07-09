package com.example.bmateo.roomdatabase_example.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.example.bmateo.roomdatabase_example.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insertAll(List<Note> notes) {
        new InsertNoteAsyncTask(noteDao, notes).execute();
    }

    public void insert(Note note) {
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        new InsertNoteAsyncTask(noteDao, notes).execute();
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
