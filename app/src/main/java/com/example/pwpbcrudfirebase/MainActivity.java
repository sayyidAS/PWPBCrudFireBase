package com.example.pwpbcrudfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    List<Notes> nlist;

    DatabaseReference db;

    @Override
    protected void onStart() {
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nlist.clear();
                for(DataSnapshot notess: dataSnapshot.getChildren()){
                    Notes note = notess.getValue(Notes.class);
                    nlist.add(note);
                }

                ArrayAdapter adapter = new NoteList(MainActivity.this,nlist);
                lv = findViewById(R.id.list);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(MainActivity.this,ManageData.class);
                pindah.putExtra("action","Tambah");
                pindah.putExtra("id","");
                startActivity(pindah);
            }
        });

        db = FirebaseDatabase.getInstance().getReference("Notes");

        lv = findViewById(R.id.list);
        nlist = new ArrayList<Notes>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notes bear = nlist.get(position);
                showUpdateDialog(bear.getId());
            }
        });

    }

    private void showUpdateDialog(final String id){
        AlertDialog.Builder dialogb = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogv = inflater.inflate(R.layout.activity_notes,null);

        dialogb.setView(dialogv);

        final Button btn_edit = dialogv.findViewById(R.id.Edit);
        final Button btn_delete = dialogv.findViewById(R.id.Delete);
        final AlertDialog dialog = dialogb.create();
        dialog.show();

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(MainActivity.this,ManageData.class);
                pindah.putExtra("action","Ubah");
                pindah.putExtra("id",id);
                startActivity(pindah);
                dialog.dismiss();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(id);
                dialog.dismiss();
            }
        });

    }

    private void Delete(String id){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Notes").child(id);
        db.removeValue();
        Toast.makeText(this,"Data berhasil dihapus",Toast.LENGTH_LONG).show();
    }
}
