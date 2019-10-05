package com.saucy.quickgame.main;


import org.gamecontrolplus.*;

import java.util.List;

public class Controller {
  /** This int represents the number of controllers that have been created. It auto-increments,
   do not modify it extenally. */
  static int CONTROLLERS_CONNECTED = 0;

  private QuickGame game;
  private ControlIO control;
  private Configuration config;
  private ControlDevice gpad;
  public ControlSlider sliderAX;
  public ControlSlider sliderAY;
  public ControlSlider sliderBX;
  public ControlSlider sliderBY;
  public ControlSlider leftTrigger;
  public ControlButton buttonA, buttonStart;

  public Controller(QuickGame game) {
    this.game = game;
    // Initialise the ControlIO
    control = ControlIO.getInstance(game);
    // Find a device that matches the configuration file
    //println(control.deviceListToText(""));

    connect();
  }
  
  void connect() {
    List<ControlDevice> devices = control.getDevices();
    int toSkip = CONTROLLERS_CONNECTED;
    for (ControlDevice device : devices) {
      if (deviceIsController(device)) {
        if (toSkip > 0) {
          toSkip --;
          continue;
        } else {
          gpad = device;
          break;
        }
      }
    }
    
    gpad.matches(Configuration.makeConfiguration(game, "gamepad"));
    setupWith(gpad);
    
    CONTROLLERS_CONNECTED ++;
  }

  void setupWith(ControlDevice gpad) {
    sliderAX = gpad.getSlider("XPOS");
    sliderAY = gpad.getSlider("YPOS");
    sliderBX = gpad.getSlider("XROT");
    sliderBY = gpad.getSlider("YROT");
    leftTrigger = gpad.getSlider("TRIGGER_LEFT");
    buttonA = gpad.getButton("BUTTON_A");
    buttonStart = gpad.getButton("BUTTON_START");
  }
  
  boolean deviceIsController(ControlDevice device) {
    return device.getName().toLowerCase().contains("controller");
  }
}