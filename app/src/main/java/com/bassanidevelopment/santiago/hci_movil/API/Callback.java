package com.bassanidevelopment.santiago.hci_movil.API;

import org.json.JSONObject;

public interface Callback {

    boolean handleResponse(JSONObject response);
    void showSpinner();
    void hideSpinner();

}
