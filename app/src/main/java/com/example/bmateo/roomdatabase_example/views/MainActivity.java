package com.example.bmateo.roomdatabase_example.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bmateo.roomdatabase_example.adapters.LoadMore;
import com.example.bmateo.roomdatabase_example.models.Note;
import com.example.bmateo.roomdatabase_example.adapters.NoteAdapter;
import com.example.bmateo.roomdatabase_example.viewmodels.NoteViewModel;
import com.example.bmateo.roomdatabase_example.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private static final int ADD_NOTE_REQUEST = 1;
    private List<Note> listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.button_add_note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter(recyclerView);
        recyclerView.setAdapter(adapter);


        noteViewModel = ViewModelProviders.of(this).
                get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable final List<Note> notes) {
                listNotes = notes;
                adapter.setNotes(listNotes.subList(0, 100));
            }
        });

        adapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadMore() {
                adapter.addNotes(listNotes.subList(101, 1000));
            }

        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                    @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Nota eliminada", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                i.putExtra("note", note);
                startActivityForResult(i, ADD_NOTE_REQUEST);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_insert_all:
                List<Note> lista = new ArrayList<>();
                for (int i = 0; i < 50000; i++) {
                    lista.add(new Note("titulo " + i, "descri " + i, 5));
                }

                noteViewModel.insertAll(lista);
                return true;
            case R.id.menu_delete_all_notes:
                noteViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && requestCode == RESULT_OK) {
            String titulo = data.getStringExtra("title");
            String descripcion = data.getStringExtra("description");
            String priority = data.getStringExtra("priority");
            String id = data.getStringExtra("id");
            int idN = Integer.parseInt(id);
            Note n = new Note(titulo, descripcion, Integer.parseInt(priority));

            if (idN != 0) {
                n.setId(idN);
                noteViewModel.update(n);
                Toast.makeText(this, "Nota actualizada", Toast.LENGTH_LONG).show();
            }else{
                noteViewModel.insert(n);
                Toast.makeText(this, "Nota guardada", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, "Error al guardar la Nota", Toast.LENGTH_LONG).show();
        }
    }
}
