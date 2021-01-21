package com.example.uts_ppb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class customAdapter extends ArrayAdapter<Mahasiswa> {
    private Context context;
    private DatabaseReference database;
    int resource;
    customAdapter(Context context, int resource, ArrayList<Mahasiswa> mahasiswas){
        super(context, resource, mahasiswas);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String nama = getItem(position).getNama();
        String nim = getItem(position).getNim();

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.row_mahasiswa, parent, false);

        TextView vNama = (TextView) convertView.findViewById(R.id.textNama);
        TextView vNIM = (TextView) convertView.findViewById(R.id.textNIM);

        vNama.setText(nama);
        vNIM.setText(nim);

        return convertView;
    }
}
