class Controller {

  ControlIO control;
  Configuration config;
  ControlDevice gpad;
  ControlSlider sliderA;
  ControlSlider leftTrigger;
  ControlButton buttonA;

  Controller(QuickGame game, String controllerName) {
    // Initialise the ControlIO
    control = ControlIO.getInstance(game);
    // Find a device that matches the configuration file
    gpad = control.getDevice(controllerName);
    gpad.matches(Configuration.makeConfiguration(game, "gamepad"));



    if (gpad == null) {
      println("Gamepad null");
      System.exit(0);
    }
    sliderA = gpad.getSlider("XPOS");
    leftTrigger = gpad.getSlider("TRIGGER_LEFT");
    buttonA = gpad.getButton("BUTTON_A");
  }
}