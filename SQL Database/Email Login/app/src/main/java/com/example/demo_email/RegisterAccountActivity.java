package com.example.demo_email;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAccountActivity extends AppCompatActivity {

EditText txtfullname,txtemail,txtpass,txtmobile;
Button btnreg;

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_account);


        ActionBar ab = getSupportActionBar();
        ab.hide();

        openHelper = new datahelper(this);

        //Referencing EditText widgets and Button placed inside in xml layout file
        txtfullname = (EditText) findViewById(R.id.txtname_reg);
        txtemail = (EditText) findViewById(R.id.txtemail_reg);
        txtpass = (EditText) findViewById(R.id.txtpass_reg);
        txtmobile = (EditText) findViewById(R.id.txtmobile_reg);
        btnreg = (Button) findViewById(R.id.btn_reg);

        //Register Button Click Event
         btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                db = openHelper.getWritableDatabase();

                String fullname = txtfullname.getText().toString();
                String email = txtemail.getText().toString();
                String pass = txtpass.getText().toString();
                String mobile =txtmobile.getText().toString();

                if (!fullname.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !mobile.isEmpty())
                {
                    txtfullname.setError(null);
                    txtemail.setError(null);
                    txtpass.setError(null);
                    txtmobile.setError(null);
                    if (!isValidEmail(email)){
                        txtemail.setError("Invelid Email");
                    }

                    //Calling InsertData Method - Defined below
                    InsertData(fullname, email, pass, mobile);

                //Alert dialog after clicking the Register Account
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAccountActivity.this);
                builder.setTitle("Information");
                builder.setMessage("Your Account is Successfully Created.");
                builder.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Finishing the dialog and removing Activity from stack.
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
                else {
                    if (fullname.isEmpty()){
                        txtfullname.setError("Enter your FullName");
                    }
                    else if (email.isEmpty()){
                        txtemail.setError("Enter your Email");
                    }
                    else if (pass.isEmpty()){
                        txtpass.setError("Enter your Password");
                    }
                    else if (mobile.isEmpty()){
                        txtmobile.setError("Enter your Mobile");
                    }

                }
            }
        });

    }

    //Inserting Data into database - Like INSERT INTO QUERY.
    public void InsertData(String fullName, String email, String password, String mobile ) {

        ContentValues values = new ContentValues();
        values.put(datahelper.NAME,fullName);
        values.put(datahelper.COL_EMAIL,email);
        values.put(datahelper.COL_PASSWORD,password);
        values.put(datahelper.COL_MOBILE,mobile);
        long id = db.insert(datahelper.TABLE_NAME,null,values);

    }
    private boolean isValidEmail(String email)
    {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}