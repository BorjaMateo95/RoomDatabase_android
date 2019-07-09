package com.example.bmateo.roomdatabase_example.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.bmateo.roomdatabase_example.models.Note;
import com.example.bmateo.roomdatabase_example.R;

public class AddNoteActivity extends AppCompatActivity {

    private EditText edTitle, edDescription;
    private NumberPicker numberPicker;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        edDescription = findViewById(R.id.ed_description);
        edTitle = findViewById(R.id.ed_title);
        numberPicker = findViewById(R.id.number_picker_priority);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        Intent intent = getIntent();
        Note note = (Note) intent.getSerializableExtra("note");

        if (note != null) {
            setTitle("Update Note");
            edTitle.setText(note.getTitle());
            edDescription.setText(note.getDescription());
            numberPicker.setValue(note.getPriority());
            id = note.getId();
        }else{
            setTitle("Add Note");
        }

    }

    private void saveNote() {
        if (edTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "El titulo debe estar completo", Toast.LENGTH_LONG).show();
            return;
        }else{
            Intent data = new Intent();
            data.putExtra("title", edTitle.getText().toString());
            data.putExtra("description", edDescription.getText().toString());
            data.putExtra("priority", String.valueOf(numberPicker.getValue()));
            data.putExtra("id", String.valueOf(id));

            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
