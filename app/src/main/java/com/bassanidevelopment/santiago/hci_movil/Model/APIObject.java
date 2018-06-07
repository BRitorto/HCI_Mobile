package com.bassanidevelopment.santiago.hci_movil.Model;

import org.json.JSONObject;

public interface APIObject {

    String getName();
    String getId();
    JSONObject getMeta();
    void setName(String name);
    void setId(String id);
    void setMeta(JSONObject meta);
}
