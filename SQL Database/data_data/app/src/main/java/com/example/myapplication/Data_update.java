package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Data_update extends AppCompatActivity implements View.OnClickListener {

    EditText editText,editText2;
    Button update,delete;

    long _id;
    DBManager dbManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_update);

        editText=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);

        dbManager=new DBManager(this);
        dbManager.open();

        Intent intent=getIntent();
        String id  =intent.getStringExtra("id");
        String name=intent.getStringExtra("title");
        String desc=intent.getStringExtra("desc");

        _id=Long.parseLong(id);

        editText.setText(name);
        editText2.setText(desc);

        update.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.update:
                String title = editText.getText().toString();
                String desc = editText2.getText().toString();

                dbManager.update(_id, title, desc);
                this.returnHome();
                break;

            case R.id.delete:
                dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}