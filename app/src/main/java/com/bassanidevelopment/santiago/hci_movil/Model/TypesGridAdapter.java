package com.bassanidevelopment.santiago.hci_movil.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bassanidevelopment.santiago.hci_movil.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypesGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DeviceType> objects;

    public TypesGridAdapter(Context c, ArrayList<DeviceType> o){
        this.context = c;
        this.objects = o;
        icons.put("eu0v2xgprrhhg41g", R.drawable.blind);
        icons.put("go46xmbqeomjrsjr", R.drawable.lamp);
        icons.put("im77xxyulpegfmv8", R.drawable.oven);
        icons.put("li6cbv5sdlatti0j", R.drawable.ac);
        icons.put("lsf78ly0eqrjbz91", R.drawable.door);
        icons.put("mxztsyjzsrq7iaqc", R.drawable.alarm);
        icons.put("ofglvd9gqX8yfl3l", R.drawable.timer);
        icons.put("rnizejqr2di0okho", R.drawable.refri);

        names.put("eu0v2xgprrhhg41g", "Blinds");
        names.put("go46xmbqeomjrsjr", "Lamps");
        names.put("im77xxyulpegfmv8", "Ovens");
        names.put("li6cbv5sdlatti0j", "AC");
        names.put("lsf78ly0eqrjbz91", "Doors");
        names.put("mxztsyjzsrq7iaqc", "Alarm");
        names.put("ofglvd9gqX8yfl3l", "Timers");
        names.put("rnizejqr2di0okho", "Refrigerator");

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

            final ImageView imageView = (ImageView) grid.findViewById(R.id.square_icon);
            final TextView roomName = (TextView) grid.findViewById(R.id.square_text);

            roomName.setText(names.get(objects.get(i).getId()));
            imageView.setImageResource(icons.get(objects.get(i).getId()));
        }

        else {
            grid =  view;
        }

        return grid;
    }


    private HashMap<String, Integer> icons = new HashMap<>();
    private HashMap<String, String> names = new HashMap<>();

}
