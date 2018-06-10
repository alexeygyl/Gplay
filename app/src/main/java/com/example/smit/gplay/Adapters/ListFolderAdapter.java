package com.example.smit.gplay.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smit.gplay.Communicator;
import com.example.smit.gplay.MainActivity;
import com.example.smit.gplay.MusicFragment;
import com.example.smit.gplay.R;

import java.io.File;
import java.util.List;

public class ListFolderAdapter extends ArrayAdapter<String> {
    private final Context context;
    private List<String> listOfFolders;
    private Communicator communicator;

    public ListFolderAdapter(Context context, List<String> listOfFolders) {
        super(context, R.layout.list_folders,listOfFolders);
        this.context = context;
        communicator  = (Communicator)context;
        this.listOfFolders =  listOfFolders;

    }

    public  void updateListOfFolders( List<String> listOfFolders){
        this.listOfFolders =  listOfFolders;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_folders, parent, false);
        }
        TextView folderNPath = convertView.findViewById(R.id.NewFolderPath);
        TextView itemCount = convertView.findViewById(R.id.NewFolderItemCount);
        ImageView folderIcon = convertView.findViewById(R.id.NewFolderLogo);
        CheckBox addFolder = convertView.findViewById(R.id.checkFolder);


        if(listOfFolders.get(position).equalsIgnoreCase("..")){
            Log.e(MainActivity.LOGTAG,"equalsIgnoreCase "+ position+ " :" + listOfFolders.get(position));
            addFolder.setVisibility(View.INVISIBLE);
        }

//        File file = new File(MusicFragment.currentDir+"/"+listOfFolders.get(position));
//        if(file.isFile()){
//            if(MainActivity.ifMusicExists(file.getName())){
//                addFolder.setChecked(true);
//            }
//        }
//        else if(file.isDirectory()){
//            if(MainActivity.folderUnits.contains(file.getPath())){
//                addFolder.setChecked(true);
//            }
//            else if(MainActivity.isInFolder(file.getPath())){
//                addFolder.setChecked(true);
//            }
//        }



        addFolder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) communicator.onFolderAdd(position);
                else  communicator.onFolderDel(position);
            }
        });


        folderNPath.setText(listOfFolders.get(position));
        folderNPath.setTextColor(Color.BLACK);
        folderNPath.setMaxWidth(320);


        File f = new File(MusicFragment.currentDir+"/"+listOfFolders.get(position));
        if(f.isFile()){
            //Log.e(MainActivity.LOGTAG,"isFile " + MusicFragment.currentDir+"/"+listOfFolders.get(position));
            if(getFileFormat(position).equals("mp3"))folderIcon.setImageResource(R.drawable.mp3_7317);
            else if (getFileFormat(position).equals("wav"))folderIcon.setImageResource(R.drawable.wav_9679);
        }else{
            //Log.e(MainActivity.LOGTAG,"isNOTFile " + MusicFragment.currentDir+"/"+listOfFolders.get(position));
            try{
                folderIcon.setImageResource(R.drawable.folder_blue_8483);
                Integer count = f.list().length;
                if(position != 0 && !MusicFragment.currentDir.equals(MusicFragment.root))
                    itemCount.setText("Items: "+ Long.toString(count) );
            }catch (NullPointerException e){
                if(position != 0 && !MusicFragment.currentDir.equals(MusicFragment.root))
                    itemCount.setText("Items: 0");
             }
            itemCount.setTextColor(Color.rgb(150, 150, 150));
        }


        return convertView;
    }

    public  String getFileFormat(int position){
        return  listOfFolders.get(position).substring(
                                            listOfFolders.get(position).lastIndexOf(".")+1,
                                            listOfFolders.get(position).length());
    }
}

