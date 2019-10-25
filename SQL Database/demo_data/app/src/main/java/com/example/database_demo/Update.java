package com.example.database_demo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Update extends AppCompatActivity {

    private user user;

    EditText name, email, password;
    Button button;
    Databasehelper db;
    private List<user> listUser;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        final Databasehelper db = new Databasehelper(this);


        name = (EditText) findViewById(R.id.edittext);
        email = (EditText) findViewById(R.id.edittext2);
        password = (EditText) findViewById(R.id.edittext3);
        button = (Button) findViewById(R.id.button);


        final int id=getIntent().getIntExtra("id",0);

        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));
        password.setText(getIntent().getStringExtra("password"));
        Log.e("TAG", "onCreate:++++++++ " + name);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String string = name.getText().toString().trim();
                String s = email.getText().toString().trim();
                String s1 = password.getText().toString().trim();

                Log.e("TAG>>>", "onClick: " + string);

                user user=new user();
                user.setId(id);
                user.setPassword(password.getText().toString());
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                db.update(user);

                Toast.makeText(Update.this, "Data is Update", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Update.this,login.class);
                startActivity(intent);

            }
        });


    }

   /* public Update(List<user> listUser, Activity usersListActivity) {
        this.listUser = listUser;
        this.activity = usersListActivity;

    }*/
}