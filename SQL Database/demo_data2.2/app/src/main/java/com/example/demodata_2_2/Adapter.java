package com.example.demodata_2_2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<Note> noteList;



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView note;
        private TextView dot;
        private TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            note=itemView.findViewById(R.id.note);
            dot=itemView.findViewById(R.id.dot);
            timestamp=itemView.findViewById(R.id.timestamp);

        }
    }

    public Adapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row,viewGroup,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Note note=noteList.get(i);
        Log.e(TAG, "onBindViewHolder:-----> "+note );
        viewHolder.note.setText(note.getNote());
        Log.e(TAG, "onBindViewHolder: ------<<<"+viewHolder.note );
        viewHolder.dot.setText(Html.fromHtml("&#8226;"));
        viewHolder.timestamp.setText(formatDate(note.getTimestamp()));
        Log.e("TAG", "onBindViewHolder: ------>>>>>"+viewHolder.timestamp );

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    private String formatDate(String timestamp)
    {
        try {
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-HH:mm:ss");
            Date date=fmt.parse(timestamp);
            SimpleDateFormat format=new SimpleDateFormat("MMM d");
            return format.format(date);
        }catch (ParseException e){

        }
        return "";
    }



}