package com.example.pwpbcrudfirebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class NoteList extends ArrayAdapter {
    private Activity con;
    private List<Notes> notelist;

    public NoteList(Activity con, List<Notes> list){
        super(con, R.layout.notes_layout,list);
        this.con = con;
        this.notelist = list;
    }

    @NonNull
    @Override
    public View getView(int position, View cview, ViewGroup parent){
        LayoutInflater inflater = con.getLayoutInflater();

        View ListView = inflater.inflate(R.layout.notes_layout,null,true);

        TextView jdl = ListView.findViewById(R.id.judul);
        TextView detail = ListView.findViewById(R.id.detail);
        TextView tanggal = ListView.findViewById(R.id.txt_tanggal);

        Notes note = notelist.get(position);

        jdl.setText(note.getJudul());
        detail.setText(note.getDeskripsi());
        tanggal.setText(note.getTanggal());

        return ListView;
    }
}
