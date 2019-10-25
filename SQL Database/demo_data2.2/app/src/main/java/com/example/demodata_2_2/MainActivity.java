package com.example.demodata_2_2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Adapter adapter;
    private List<Note> noteList=new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView textView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db=new DatabaseHelper(this);

        recyclerView=findViewById(R.id.recycler_view);
        textView=findViewById(R.id.textView);

        noteList.addAll(db.getAllNotes());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });

        adapter = new Adapter(this, noteList);
        Log.e("TAG", "onCreate:.......>>> "+adapter );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(adapter);
        Log.e("TAG", "onCreate: >>>>>>"+recyclerView );

        toggleEmptyNotes();

        recyclerView.addOnItemTouchListener(new TouchListener(this, recyclerView, new TouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);

            }
        }));
    }

    private void createNote(String note){

        long id=db.insertNote(note);
        Note n=db.getNote(id);
        if (n!=null){
            noteList.add(0,n);
            adapter.notifyDataSetChanged();
            toggleEmptyNotes();
        }
    }
    private void updateNote(String note,int position){
        Note n=noteList.get(position);
        n.setNote(note);
        db.updateNote(n);
        noteList.set(position,n);
        adapter.notifyItemChanged(position);
        toggleEmptyNotes();

    }
    private  void deleteNote(int position){
        db.deleteNote(noteList.get(position));
        noteList.remove(position);
        adapter.notifyItemRemoved(position);
        toggleEmptyNotes();
    }

    private void showActionsDialog(final int position)
    {
        CharSequence colors[]=new CharSequence[]{"Edit","Delete"};

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors,new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    showNoteDialog(true,noteList.get(position),position);
                }
                else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }


    private void showNoteDialog(final boolean shouldUpdate, final Note note, final int position)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(getApplicationContext());
        View view=layoutInflater.inflate(R.layout.note_dialog,null);

        AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
        alert.setView(view);

        final EditText editText=view.findViewById(R.id.editText);
        TextView textView=view.findViewById(R.id.textView);
        textView.setText(!shouldUpdate ? ("New Note") : ("Edit Note"));

        if (shouldUpdate && note !=null){
            editText.setText(note.getNote());
        }
        alert.setCancelable(false).setPositiveButton(shouldUpdate ? "Update" : "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }

        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        final AlertDialog alertDialog=alert.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(MainActivity.this, "Enter Note", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    alertDialog.dismiss();
                }
                if (shouldUpdate && note !=null)

                {

                    updateNote(editText.getText().toString(),position);
                }
                else {
                    createNote(editText.getText().toString());
                }
            }
        });
    }



    private void toggleEmptyNotes()
    {
            if (db.getNotesCount()>0){
                textView.setVisibility(View.GONE);
            }
            else
            {
                textView.setVisibility(View.VISIBLE);
            }
    }

}
