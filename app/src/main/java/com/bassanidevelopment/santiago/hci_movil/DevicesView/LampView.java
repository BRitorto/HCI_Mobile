package com.bassanidevelopment.santiago.hci_movil.DevicesView;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.LampState;
import com.bassanidevelopment.santiago.hci_movil.R;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

public class LampView extends DevicesView {


    // specific

    private Switch aSwitch;
    private SeekBar seekBar;
    // em queda uno que ni puta idea

    private LampState lampState;

    public LampView(View view ,String devId, Context context){
        this.context = context;
        this.devId = devId;
        aSwitch = view.findViewById(R.id.switch_lamp);
        seekBar = view.findViewById(R.id.seekBar_lamp);

    }
}
