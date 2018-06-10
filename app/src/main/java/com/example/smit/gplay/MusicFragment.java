package com.example.smit.gplay;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.smit.gplay.Adapters.ListFolderAdapter;
import com.example.smit.gplay.Adapters.ListMusicAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MusicFragment extends Fragment implements View.OnClickListener, Animation.AnimationListener, AdapterView.OnItemClickListener {

    private List<MusicUnits> musicUnits;
    private List<String> folderUnits;
    private List<String> folderList;
    private Communicator communicator;
    private ListMusicAdapter adapterMusic;
    private ListFolderAdapter adapterFolder;
    private ListView listViewMusic;
    private ListView listViewFolder;
    private FloatingActionButton folders;
    private RelativeLayout  foldersLayout;
    private Animation folderAnimationUp;
    private Animation folderAnimationDown;
    int FolderViewVisible = View.INVISIBLE;
    public static String root = "/storage";
    public static String currentDir = root;
    Context context;

    public MusicFragment(Context context) {
        this.context = context;
        communicator = (Communicator)context;
        musicUnits = new ArrayList<>();
        folderUnits = new ArrayList<>();
        folderList = new ArrayList<>();
        updateListFolder(currentDir);
        for (int i =0; i <20;i++){
            MusicUnits units = new MusicUnits();
            units.MAuthor = "asd";
            units.Mname = "asd";
            units.Mtime = "00:12";
            units.MDuration = 12000;
            musicUnits.add(units);
        }
        adapterMusic = new ListMusicAdapter(context, musicUnits);
        adapterFolder = new ListFolderAdapter(context, folderList);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        findAllViews(view);
        listViewMusic.setAdapter(adapterMusic);
        listViewFolder.setAdapter(adapterFolder);
        listViewMusic.setOnItemClickListener(this);
        listViewFolder.setOnItemClickListener(this);
        folders.setOnClickListener(this);
        foldersLayout.setOnClickListener(this);
        folderAnimationUp.setAnimationListener(this);
        folderAnimationDown.setAnimationListener(this);
        return view;
    }

    void findAllViews( View view){
        listViewMusic = view.findViewById(R.id.listViewMusic);
        listViewFolder = view.findViewById(R.id.listViewFolder);
        folders = view.findViewById(R.id.FoldersFAB);
        foldersLayout = view.findViewById(R.id.FoldersLayout);
        foldersLayout.setVisibility(FolderViewVisible);
        folderAnimationUp = AnimationUtils.loadAnimation(getActivity(), R.anim.folder_layout_up);
        folderAnimationDown = AnimationUtils.loadAnimation(getActivity(), R.anim.folder_layout_down);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.FoldersFAB:
                if(FolderViewVisible == View.VISIBLE)foldersLayout.startAnimation(folderAnimationDown);
                else  {
                    foldersLayout.startAnimation(folderAnimationUp);
                }
                break;
            case R.id.FoldersLayout:
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if(FolderViewVisible == View.VISIBLE) {
            foldersLayout.setVisibility(View.INVISIBLE);
        }
        else {
            foldersLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(FolderViewVisible == View.VISIBLE) {
            foldersLayout.setVisibility(View.INVISIBLE);
            FolderViewVisible = View.INVISIBLE;
        }
        else {
            foldersLayout.setVisibility(View.VISIBLE);
            FolderViewVisible = View.VISIBLE;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.listViewMusic:
                Log.e(MainActivity.LOGTAG,"listViewMusic " + musicUnits.get(i).Mname);
                break;
            case R.id.listViewFolder:
                folderClickItem(view,i);
                break;
        }
    }


    private void updateListFolder(String path){
        List<String> listFolders = new ArrayList<String>();
        folderList.clear();
        Log.e(MainActivity.LOGTAG,path);
        try {
            File list = new File(path);
            if(!path.equalsIgnoreCase(root))listFolders.add("..");
            for(String elem : list.list()){

                File f = new File(path+"/"+elem);
                if(f.isDirectory() && !isFolderEmpty(f)){

                    listFolders.add(elem);
                }
                else if(f.isFile()){

                    if(getFileFormat(elem).equals("mp3"))listFolders.add(elem);
                    else if (getFileFormat(elem).equals("wav"))listFolders.add(elem);
                }
            }
        }catch (Exception e){e.printStackTrace();}
        for (String unit: listFolders) {
            folderList.add(unit);
        }
    }

    private boolean isFolderEmpty(File f){
        if(f.list().length == 0)return true;
        for (String elem: f.list()) {
            File f2 = new File(f.getAbsolutePath()+"/"+elem);
            if(f2.isDirectory())return false;
            else if(f2.isFile()){
                if(getFileFormat(elem).equals("mp3"))return false;
            }
        }
        return true;
    }

    private static  String getFileFormat(String file){
        return  file.substring(file.lastIndexOf(".")+1, file.length());
    }

    private void folderClickItem(View view,int pos){
        File f = new File(currentDir + "/" + folderList.get(pos));
        if(pos == 0 && !currentDir.equals(root)){
            currentDir = currentDir.substring(0,currentDir.lastIndexOf("/"));
            updateListFolder(currentDir);
            adapterFolder.notifyDataSetChanged();
        }
        else if (f.isDirectory()) {
            currentDir += "/" + folderList.get(pos);
            updateListFolder(currentDir);
            adapterFolder.notifyDataSetChanged();
        }
        else if(f.isFile()){
            CheckBox cb = view.findViewById(R.id.checkFolder);
            //cb.setChecked(!cb.isChecked());
            //if(cb.isChecked()) addFolder(position);
            //else delFolder(position);
        }
    }


    public void onFolderAdd(int pos) {
        folderUnits.add(currentDir + "/" + folderList.get(pos));
        Log.e(MainActivity.LOGTAG, " " + folderUnits.size() );
        //File file = new File(currentDir + "/" + folderUnits.get(pos));
//        if(file.isFile()){
//            MainActivity.updateMusicList(currentDir + "/" + folderUnits.get(pos));
//        }
//        else if (!MainActivity.folderUnits.contains(currentDir + "/" + folderUnits.get(pos))) {
//            MainActivity.folderUnits.add(currentDir + "/" + folderUnits.get(pos));
//            MainActivity.insertNewFolder(currentDir + "/" + folderUnits.get(pos));
//            MainActivity.updateMusicList(currentDir + "/" + folderUnits.get(pos));
//
//        }
    }

    public void onFolderDel(int pos) {
        folderUnits.remove(currentDir + "/" + folderList.get(pos));
        Log.e(MainActivity.LOGTAG, " " + folderUnits.size() );
//        File file = new File(currentDir + "/" + folderUnits.get(pos));
//        if(file.isFile()){
//            MainActivity.removeMusic(folderUnits.get(pos));
//            MainActivity.removeMusicFromList(folderUnits.get(pos));
//        }else{
//            MainActivity.removeMusicByFolderFromBD(currentDir + "/"+folderUnits.get(pos));
//            MainActivity.removeMusicByFolderFromList(currentDir + "/"+folderUnits.get(pos));
//            MainActivity.removeFolderFromBD(currentDir + "/"+folderUnits.get(pos));
//            MainActivity.removeFolder(currentDir + "/"+folderUnits.get(pos));
//        }
    }
}
