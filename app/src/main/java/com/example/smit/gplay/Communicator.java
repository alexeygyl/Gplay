package com.example.smit.gplay;

public interface Communicator {
    void onMusicListUpdate(String msg);
    void onFolderAdd(int pos);
    void onFolderDel(int pos);
}
