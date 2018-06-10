package com.example.smit.gplay.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smit.gplay.MusicUnits;
import com.example.smit.gplay.R;

import java.util.List;

public class ListMusicAdapter extends ArrayAdapter<MusicUnits>  {
    private final Context context;
    private List<MusicUnits> musicUnitses;

    public ListMusicAdapter(Context context, List<MusicUnits> musicUnitses) {
        super(context, R.layout.list_music,musicUnitses);
        this.context = context;
        this.musicUnitses =  musicUnitses;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_music, parent, false);

        }
        TextView textName =convertView.findViewById(R.id.MusicName);
        TextView textAuthor =  convertView.findViewById(R.id.MusicAuthor);
        TextView textTime =  convertView.findViewById(R.id.MusicTime);
        ImageView imageView = convertView.findViewById(R.id.logo);

        textName.setText(musicUnitses.get(position).Mname);
        textName.setTextColor(Color.BLACK);
        textName.setMaxWidth(320);
        textAuthor.setText(musicUnitses.get(position).MAuthor);
        textAuthor.setTextColor(Color.rgb(150,150,150));
        textAuthor.setMaxWidth(320);
        textTime.setText(musicUnitses.get(position).Mtime);
        String s = musicUnitses.get(position).MAuthor;
        return convertView;
    }

}
