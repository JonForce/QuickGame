abstract class Menu {
  final int
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

  Menu(Controller controller, String ... options) {
    this.controller = controller;
    for (String o : options)
      this.options.add(o);
    timeMenuEntered = millis();
  }

  void render() {
    int totalBoxHeight =
      200 + options.size()*(OPTION_TEXT_SIZE + 50);
    
    fill(0);
    rect(width/2 - BOX_WIDTH/2, height/2 - totalBoxHeight/2, BOX_WIDTH, totalBoxHeight);
  
    int
      titleX = width/2,
      titleY = height/2 - totalBoxHeight/2 + TITLE_TEXT_SIZE + 30;
    textAlign(CENTER);
    fill(255);
    textSize(TITLE_TEXT_SIZE);
    text(title, titleX, titleY);

    textSize(OPTION_TEXT_SIZE);
    for (int i = 0; i < options.size(); i ++) {
      String text = options.get(i);
      if (i == selectedOption && millis() % (OPTION_SELECTED_BLINK_PERIOD*2) < OPTION_SELECTED_BLINK_PERIOD)
        text = "[ " + text + " ]";
      else if (i == selectedOption)
        text = "[  " + text + "  ]";
      text(text, width/2, titleY + i*(OPTION_TEXT_SIZE + 50) + 75);
    }
    
    if (millis() - timeMenuEntered > 200)
      updateSelection();
  }

  void updateSelection() {
    if (controller.sliderAY.getValue() > .9 && millis() - lastOptionSwitch > 200) {
      selectedOption += 1;
      selectedOption = selectedOption % options.size();
      lastOptionSwitch = millis();
    } else if (controller.sliderAY.getValue() < -.9 && millis() - lastOptionSwitch > 200) {
      selectedOption -= 1;
      if (selectedOption == -1)
        selectedOption = options.size()-1;
      lastOptionSwitch = millis();
    }

    if (controller.buttonA.getValue() > 0 || controller.buttonStart.getValue() > 0)
      onSelect(options.get(selectedOption));
  }

  abstract void onSelect(String option);

  void setTitle(String title) {
    this.title = title;
  }
}

class PauseMenu extends Menu {
  final String
    RESUME = "Resume", 
    RESTART = "Restart", 
    EXIT_TO_MAIN_MENU = "Quit to main menu", 
    EXIT_TO_DESKTOP = "Quit to desktop";

  final LevelState level;

  PauseMenu(Controller controller, LevelState level) {
    super(controller);
    this.level = level;
    options.add(RESUME);
    options.add(RESTART);
    options.add(EXIT_TO_MAIN_MENU);
    options.add(EXIT_TO_DESKTOP);
    setTitle("Paused");
  }

  @Override
    void onSelect(String option) {
    if (option.equals(RESUME)) {
      level.resume();
      level.closeMenu();
    } else if (option.equals(RESTART)) {
      level.resetGame();
      level.closeMenu();
    } else if (option.equals(EXIT_TO_MAIN_MENU)) {
      println("Exit to main menu! : )");
    } else if (option.equals(EXIT_TO_DESKTOP)) {
      exit();
    }
  }
}

class DeathMenu extends Menu {

  final String
    RESTART = "Restart", 
    EXIT_TO_MAIN_MENU = "Quit to main menu", 
    EXIT_TO_DESKTOP = "Quit to desktop";

  LevelState level;

  DeathMenu(LevelState level, Controller controller) {
    super(controller);
    this.level = level;
    setTitle("You Died");
    options.add(RESTART);
    options.add(EXIT_TO_MAIN_MENU);
    options.add(EXIT_TO_DESKTOP);
  }

  @Override
    void onSelect(String option) {
    if (option.equals(RESTART)) {
      level.resetGame();
      level.closeMenu();
    } else if (option.equals(EXIT_TO_MAIN_MENU)) {
      println("Exit to main menu! : )");
    } else if (option.equals(EXIT_TO_DESKTOP)) {
      exit();
    }
  }
}