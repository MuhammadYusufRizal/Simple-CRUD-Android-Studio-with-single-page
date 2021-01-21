package com.example.uts_ppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private DatabaseReference database;
    private ArrayList<Mahasiswa> listmhs;

    TextInputEditText inputNama;
    TextInputEditText inputNIM;
    Button btnAdd, btnEdit, btnDelete;
    ListView listview;

    int index;
    String item, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();

        listview = findViewById(R.id.listview);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        inputNama = (TextInputEditText) findViewById(R.id.input_nama);
        inputNIM = (TextInputEditText) findViewById(R.id.input_nim);

        database.child("Mahasiswa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listmhs = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Mahasiswa mahasiswa = dataSnapshot.getValue(Mahasiswa.class);
                    listmhs.add(mahasiswa);
                }
                customAdapter adapter = new customAdapter(MainActivity.this, R.layout.row_negara, listmhs);
                listview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString() + "terpilih";
                item = adapterView.getItemAtPosition(position).toString() + "terpilih";
                index = position;
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                inputNama.setText(listmhs.get(position).getNama().toString());
                inputNIM.setText(listmhs.get(position).getNim().toString());
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String namaVal = inputNama.getText().toString();
                String nimVal = inputNIM.getText().toString();

                if(TextUtils.isEmpty(namaVal) || TextUtils.isEmpty(nimVal)){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Field tidak boleh kosong!")
                            .setNegativeButton("Ok", null)
                            .show();
                }
                else{
                    if(!namaVal.matches("\\w+") || !nimVal.matches("\\w+")){
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Input hanya berupa karakter dan angka!")
                                .setNegativeButton("Ok", null)
                                .show();
                    }
                    else{
                        submit(new Mahasiswa(namaVal, nimVal));
                        inputNama.setText("");
                        inputNIM.setText("");
                    }

                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String namaVal = inputNama.getText().toString();
                String nimVal = inputNIM.getText().toString();

                if(TextUtils.isEmpty(namaVal) || TextUtils.isEmpty(nimVal)){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Field tidak boleh kosong!")
                            .setNegativeButton("Ok", null)
                            .show();
                }
                else{
                    if(!namaVal.matches("\\w+") || !nimVal.matches("\\w+")){
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Input hanya berupa karakter dan angka!")
                                .setNegativeButton("Ok", null)
                                .show();
                    }
                    else {
                        edit(new Mahasiswa(namaVal, nimVal), nimVal);
                        inputNama.setText("");
                        inputNIM.setText("");
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String namaVal = inputNama.getText().toString();
                String nimVal = inputNIM.getText().toString();

                if(TextUtils.isEmpty(namaVal) || TextUtils.isEmpty(nimVal)){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Field tidak boleh kosong!")
                            .setNegativeButton("Ok", null)
                            .show();
                }
                else{
                    delete(new Mahasiswa(namaVal, nimVal));
                    inputNama.setText("");
                    inputNIM.setText("");
                }
            }
        });
    }

    private void submit(Mahasiswa mhs){
        database.child("Mahasiswa").child(mhs.getNim()).setValue(mhs);
    }
    private void edit(Mahasiswa mhs, String id) {
        database.child("Mahasiswa").child(id).setValue(mhs);
    }
    private void delete(Mahasiswa mhs) {
        database.child("Mahasiswa").child(mhs.getNim()).removeValue();
    }
}