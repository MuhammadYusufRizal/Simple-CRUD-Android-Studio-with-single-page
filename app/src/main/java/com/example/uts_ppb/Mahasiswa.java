package com.example.uts_ppb;

public class Mahasiswa{
    private String key;
    private String nim;
    private String nama;

    public Mahasiswa(){}

    public Mahasiswa(String key, String nama, String nim) {
        this.key = key;
        this.nim = nim;
        this.nama = nama;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
