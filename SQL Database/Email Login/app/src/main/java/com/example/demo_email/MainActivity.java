package com.example.demo_email;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" + "(?=.*[0-9])" + "(?=.*[a-z])" + "(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "$");

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;
    Cursor cursor;
    EditText editText, editText2;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To hide AppBar for fullscreen.
        ActionBar ab = getSupportActionBar();
        ab.hide();

        //Referencing UserEmail, Password EditText and TextView for SignUp Now
        editText = (EditText) findViewById(R.id.txtemail);
        editText2 = (EditText) findViewById(R.id.txtpass);
        button = (Button) findViewById(R.id.btnsignin);
        textView = (TextView) findViewById(R.id.btnreg);
       //Opening SQLite Pipeline
        dbhelper = new datahelper(this);
        db = dbhelper.getReadableDatabase();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editText.getText().toString();
                String pass = editText2.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (!email.isEmpty() && !pass.isEmpty()) {
                    editText.setError(null);
                    editText2.setError(null);

                    if (email.matches(emailPattern)){
                        editText.setError("Invalid Email");
                    }

                    cursor = db.rawQuery("SELECT *FROM " + datahelper.TABLE_NAME + " WHERE " + datahelper.COL_EMAIL + "=? AND " + datahelper.COL_PASSWORD + "=?", new String[]{email, pass});
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {

                            cursor.moveToFirst();
                            //Retrieving User FullName and Email after successfull login and passing to LoginSucessActivity
                            String _fname = cursor.getString(cursor.getColumnIndex(datahelper.NAME));
                            String _email = cursor.getString(cursor.getColumnIndex(datahelper.COL_EMAIL));
                            Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, LoginSuccessActivity.class);
                            intent.putExtra("fullname", _fname);
                            intent.putExtra("email", _email);
                            startActivity(intent);

                            //Removing MainActivity[Login Screen] from the stack for preventing back button press.
                            finish();
                        } else {

                            //I am showing Alert Dialog Box here for alerting user about wrong credentials
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Alert");
                            builder.setMessage("Username or Password is wrong.");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    dialogInterface.dismiss();

                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                            //-------Alert Dialog Code Snippet End Here
                        }
                    }
                }
                else {
                    if (email.isEmpty()){

                        editText.setError("Enter your Email");

                    }

                    else if (pass.isEmpty()){
                        editText2.setError("Enter your Password");
                    }
                }
            }
        });

        // Intent For Opening RegisterAccountActivity
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,RegisterAccountActivity.class);
                startActivity(intent);
            }
        });

    }

}