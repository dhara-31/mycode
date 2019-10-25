package com.example.data_demo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_Successfull extends AppCompatActivity {

    EditText edittext,edittext2;
    Button button,button2;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__successfull);


        edittext=findViewById(R.id.edittext);
        edittext2=findViewById(R.id.edittext2);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);

        db=new DatabaseHelper(this);


        final String string=getIntent().getStringExtra("EMAIL");
        final String password=getIntent().getStringExtra("PASSWORD");


        edittext.setText(string);
        edittext2.setText(password);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String string=edittext.getText().toString();
                String s1=edittext2.getText().toString();

               boolean update= db.updateData(string,s1);

                Log.e("TAG>>>", "onClick:::: "+ string);
                Log.e("TAG---", "onClick:>>> "+s1 );
               if (update==true){


                   Intent intent=new Intent(login_Successfull.this,MainActivity.class);
                   startActivity(intent);

                   Toast.makeText(login_Successfull.this, "Data is Update", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(login_Successfull.this, "Not update", Toast.LENGTH_SHORT).show();
               }


            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer delete=db.deleteData(edittext.getText().toString());
                Log.e("TAG", "onClick:----> "+delete );
                if (delete > 0)
                {
                    edittext.setText("");
                    edittext2.setText("");

                    Toast.makeText(login_Successfull.this, "Data is Delete", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(login_Successfull.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(login_Successfull.this, "Data not Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}