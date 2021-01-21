package com.example.uts_ppb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();;
    private ArrayList<Mahasiswa> listmhs;

    TextInputEditText inputNama;
    TextInputEditText inputNIM;
    Button btnAdd, btnEdit, btnDelete;
    ListView listview;

    public int index;
    public String item, id, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                customAdapter adapter = new customAdapter(MainActivity.this, R.layout.row_mahasiswa, listmhs);
                listview.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                item = listmhs.get(position).getKey().toString() + "terpilih";
                index = position;
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
                key = listmhs.get(position).getKey().toString();
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
                    String key = database.push().getKey();
                    submit(new Mahasiswa(key, namaVal, nimVal));
                    inputNama.setText("");
                    inputNIM.setText("");
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
                    edit(new Mahasiswa(key, namaVal, nimVal), key);
                    inputNama.setText("");
                    inputNIM.setText("");
                    key = "";
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
                    delete(new Mahasiswa(key, namaVal, nimVal));
                    inputNama.setText("");
                    inputNIM.setText("");
                    key = "";
                }
            }
        });
    }

    private void submit(Mahasiswa mhs){
        database.child("Mahasiswa").child(mhs.getKey()).setValue(mhs);
    }
    private void edit(Mahasiswa mhs, String key) {
        database.child("Mahasiswa").child(key).setValue(mhs);
    }
    private void delete(Mahasiswa mhs) {
        database.child("Mahasiswa").child(mhs.getKey()).removeValue();
    }
}