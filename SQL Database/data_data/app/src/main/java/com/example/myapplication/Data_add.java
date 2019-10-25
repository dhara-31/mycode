package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Data_add extends AppCompatActivity implements View.OnClickListener {

    Button addTodoBtn;
    EditText subjectEditText,descEditText;
    DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_add);

        addTodoBtn = (Button) findViewById(R.id.button);
        subjectEditText = (EditText) findViewById(R.id.editText);
        descEditText = (EditText) findViewById(R.id.editText2);

        dbManager = new DBManager(this);
        dbManager.open();
        addTodoBtn.setOnClickListener(this);
    }

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.button:

                    final String name = subjectEditText.getText().toString();
                    final String desc = descEditText.getText().toString();

                    dbManager.insert(name,desc);

                    Intent main = new Intent(Data_add.this, MainActivity.class);
                    startActivity(main);
                    Toast.makeText(this, "Next page", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

}