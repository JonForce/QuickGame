package com.saucy.quickgame.main;

import processing.core.PApplet;
import processing.data.JSONObject;

public class GraphicsSettings {

  private PApplet applet;

  final String
    FILE_NAME = "data/graphicsSettings.json",
    JSON_BG_PARTICLES = "bgParticles", 
    JSON_BG_OBJECTS = "bgObjects", 
    JSON_EFFECTS = "effects";

  public float bgParticles = 1.0f;
  public float bgObjects = 1.0f;
  public float effects = 1.0f;

  GraphicsSettings(PApplet applet) {
    this.applet = applet;
  }

  void setAiliasing(boolean flag) {
    if (flag)
      applet.smooth();
    else
      applet.noSmooth();
  }

  void dynamicallyAdjust(float newSetting) {
    this.bgParticles =  newSetting;
    this.bgObjects = newSetting;
    this.effects = newSetting;
  }

  void save() {
    JSONObject object = new JSONObject();
    object.setFloat(JSON_BG_PARTICLES, bgParticles);
    object.setFloat(JSON_BG_OBJECTS, bgObjects);
    object.setFloat(JSON_EFFECTS, effects);
    applet.saveJSONObject(object, FILE_NAME);
  }

  void load() {
    JSONObject object = null;
    try {
      object = applet.loadJSONObject(FILE_NAME);
    } catch (RuntimeException e) {
      save();
      return;
    }
    this.bgParticles = object.getFloat(JSON_BG_PARTICLES);
    this.bgObjects = object.getFloat(JSON_BG_OBJECTS);
    this.effects = object.getFloat(JSON_EFFECTS);
  }
}