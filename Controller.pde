class Controller {

  String controllerName;
  QuickGame game;
  ControlIO control;
  Configuration config;
  ControlDevice gpad;
  ControlSlider sliderA;
  ControlSlider sliderBX;
  ControlSlider sliderBY;
  ControlSlider leftTrigger;
  ControlButton buttonA, buttonStart;

  Controller(QuickGame game, String controllerName) {
    this.controllerName = controllerName;
    this.game = game;
    // Initialise the ControlIO
    control = ControlIO.getInstance(game);
    // Find a device that matches the configuration file
    println(control.deviceListToText(""));
    
    connect();
  }

  void connect() {
    gpad = control.getDevice(controllerName);
    gpad.matches(Configuration.makeConfiguration(game, "gamepad"));

    sliderA = gpad.getSlider("XPOS");
    sliderBX = gpad.getSlider("XROT");
    sliderBY = gpad.getSlider("YROT");
    leftTrigger = gpad.getSlider("TRIGGER_LEFT");
    buttonA = gpad.getButton("BUTTON_A");
    buttonStart = gpad.getButton("BUTTON_START");
  }
}