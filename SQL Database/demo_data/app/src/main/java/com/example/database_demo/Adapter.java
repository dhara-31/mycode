package com.example.database_demo;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.UserViewHolder>
{

    Databasehelper db;
        private List<user> listUser;
        Activity activity;

        public Adapter(List<user> listUser, Activity usersListActivity){
            this.listUser=listUser;
            this.activity=usersListActivity;


        }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user,viewGroup,false);



        return new UserViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, final int pos) {




        userViewHolder.textviewname.setText(listUser.get(pos).getName());
        userViewHolder.textviewemail.setText(listUser.get(pos).getEmail());
        userViewHolder.textviewpassword.setText(listUser.get(pos).getPassword());


        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                builder.setTitle("Infromation");
                builder.setMessage("Do you want to Delete or Update..??");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Log.e("digan--=-=>", "onClick: "+ getItemCount());
                        Log.e("digan--=-=>", "onClick: "+ pos);
                        Log.e("digan--=-=>", "onClick: "+ listUser.size());
                        Log.e("Digan-->>", "onClick: "+listUser.get(pos).getId());
                        db.delete(listUser.get(pos));
                        listUser.remove(listUser.get(pos));
                        notifyDataSetChanged();



                    }
                });
                builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent= new Intent(activity,Update.class);


                        intent.putExtra("id",listUser.get( pos ).getId());
                        intent.putExtra("name",listUser.get( pos ).getName());
                        intent.putExtra("email", listUser.get(pos).getEmail());
                        intent.putExtra("password", listUser.get(pos).getPassword());
                        Log.e("TAG", "onClick: >>>>>>>>"+listUser );


                        activity.startActivity(intent);

                        Log.e("TAG", "onClick:------>>> "+intent );

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show(); }
    });
    }

    @Override
    public int getItemCount() {
        Log.v(Adapter.class.getSimpleName(),"TAG===>>"+listUser.size());
        return listUser.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

            public TextView textviewname;
            public TextView textviewemail;
            public TextView textviewpassword;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);



            textviewname=(TextView)itemView.findViewById(R.id.textviewname);
            textviewemail=(TextView)itemView.findViewById(R.id.textviewemail);
            textviewpassword=(TextView)itemView.findViewById(R.id.textviewpassword);
            db=new Databasehelper(activity);

        }
    }
}
