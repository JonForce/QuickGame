package com.saucy.quickgame.main;

import processing.core.PApplet;
import processing.data.JSONObject;

import java.util.HashMap;

public class SavedData {
  
  final String PATH = "data/save.json";

  private PApplet applet;
  
  JSONObject data;

  public SavedData(PApplet applet) {
    this.applet = applet;
  }
  
  void save() {
    applet.saveJSONObject(data, PATH);
  }

  void load() {
    try {
      data = applet.loadJSONObject(PATH);
    } catch (RuntimeException e) {
      data = new JSONObject();
      HashMap<String, String> defaults = getDefaults();
      for (String key : defaults.keySet())
        data.put(key, defaults.get(key));
      save();
      return;
    }
  }
  
  HashMap<String, String> getDefaults() {
    HashMap<String, String> defaults = new HashMap<String, String>();
    defaults.put("currentLevel", "level1.txt");
    return defaults;
  }
  
}