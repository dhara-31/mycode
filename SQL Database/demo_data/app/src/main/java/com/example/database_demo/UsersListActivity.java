package com.example.database_demo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private TextView textViewname,textView;
    private RecyclerView recyclerView;
    private List<user> listUsers;
    private Adapter adapter;
    private Databasehelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        textView=(TextView)findViewById(R.id.textviewemail);

        getSupportActionBar().setTitle("");
        db = new Databasehelper(this);

        initViews();
        initObjects();
    }

    private void initViews() {
        textViewname = (TextView) findViewById(R.id.textviewname);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);



    }

    private void initObjects() {
        listUsers = new ArrayList<>();
        listUsers=db.getAllUser();
        adapter = new Adapter(listUsers,UsersListActivity.this);

        String string=getIntent().getStringExtra("EMAIL");
        textView.setText(string);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        getDataFromSQLite();

    }

    private void getDataFromSQLite() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(db.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }

        }.execute();
    }


}