package com.bassanidevelopment.santiago.hci_movil.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bassanidevelopment.santiago.hci_movil.R;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<APIObject> objects;

    public GridAdapter(Context c, ArrayList<APIObject> o){
        this.context = c;
        this.objects = o;

    }
    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        if (view == null) {

            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            grid = layoutInflater.inflate(R.layout.squares_view, viewGroup, false);

            final ImageView imageView = (ImageView) grid.findViewById(R.id.room_icon);
            final TextView roomName = (TextView) grid.findViewById(R.id.room_name);

            roomName.setText(objects.get(i).getName());
            imageView.setImageResource(R.drawable.ic_room);
        }

        else {
            grid =  view;
        }

        return grid;
    }


}
