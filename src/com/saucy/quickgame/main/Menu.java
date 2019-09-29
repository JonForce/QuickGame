package com.saucy.quickgame.main;

import processing.core.PApplet;

import java.util.ArrayList;

public abstract class Menu {

  PApplet applet;

  int
    TITLE_TEXT_SIZE = 55, 
    OPTION_TEXT_SIZE = 30, 
    BOX_WIDTH = 500, 
    // The number of milliseconds the cursor will blink back and forth on the sected option.
    OPTION_SELECTED_BLINK_PERIOD = 500;

  final ArrayList<String> options = new ArrayList<String>();
  String title;
  int selectedOption = 0;
  long lastOptionSwitch;
  Controller controller;
  long timeMenuEntered;

  public Menu(PApplet applet, Controller controller, String ... options) {
    this.applet = applet;
    this.controller = controller;
    for (String o : options)
      this.options.add(o);
    timeMenuEntered = applet.millis();
  }

  void renderBox() {
    applet.fill(0);
    applet.rect(this.x(), this.y(), this.width(), this.height());

    applet.textAlign(applet.CENTER);
    applet.fill(255);
    applet.textSize(TITLE_TEXT_SIZE);
    applet.text(title, titleX(), titleY());
  }

  void renderOptions() {
    applet.textSize(OPTION_TEXT_SIZE);
    for (int i = 0; i < options.size(); i ++) {
      if (i == selectedOption)
        applet.fill(255);
      else
        applet.fill(126);
      String text = options.get(i);
      if (i == selectedOption && applet.millis() % (OPTION_SELECTED_BLINK_PERIOD*2) < OPTION_SELECTED_BLINK_PERIOD)
        text = "[ " + text + " ]";
      else if (i == selectedOption)
        text = "[  " + text + "  ]";
      applet.text(text, applet.width/2, titleY() + i*(OPTION_TEXT_SIZE + 50) + 75);
    }
  }

  void render() {
    renderBox();

    renderOptions();

    if (applet.millis() - timeMenuEntered > 200)
      updateSelection();
  }

  void updateSelection() {
    if (controller.sliderAY.getValue() > .9 && applet.millis() - lastOptionSwitch > 200) {
      selectedOption += 1;
      selectedOption = selectedOption % options.size();
      lastOptionSwitch = applet.millis();
    } else if (controller.sliderAY.getValue() < -.9 && applet.millis() - lastOptionSwitch > 200) {
      selectedOption -= 1;
      if (selectedOption == -1)
        selectedOption = options.size()-1;
      lastOptionSwitch = applet.millis();
    }

    if (controller.buttonA.getValue() > 0 || controller.buttonStart.getValue() > 0)
      onSelect(options.get(selectedOption));
  }

  float titleX() {
    return applet.width/2;
  }

  float titleY() {
    return applet.height/2 - this.height()/2 + TITLE_TEXT_SIZE + 30;
  }

  int width() {
    return BOX_WIDTH;
  }

  int height() {
    return 200 + options.size()*(OPTION_TEXT_SIZE + 50);
  }

  int x() {
    return applet.width/2 - this.width()/2;
  }

  int y() {
    return applet.height/2 - this.height()/2;
  }

  abstract void onSelect(String option);

  void setTitle(String title) {
    this.title = title;
  }
}







