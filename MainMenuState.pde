class MainMenuState extends GameState {
  
  Menu menu;
  
  MainMenuState(QuickGame game) {
    menu = new MainMenu(game.controllerA, game);
  }
  
  @Override
  void update() {
     
  }
  
  @Override
  void render() {
    background(255);
    
    menu.render();
  }
  
}

class MainMenu extends Menu {
  
  final String
    PROCEDURAL_LEVEL = "Procedural Level",
    LOAD_LEVEL = "Load Level",
    LEVEL_EDITOR = "Level Editor";
    
  QuickGame game;
  
  MainMenu(Controller controller, QuickGame game) {
    super(controller);
    this.game = game;
    options.add(PROCEDURAL_LEVEL);
    options.add(LOAD_LEVEL);
    options.add(LEVEL_EDITOR);
    setTitle("Main Menu");
  }
  
  
  @Override
  void onSelect(String option) {
    if (option.equals(PROCEDURAL_LEVEL)) {
      game.switchToState(new LevelState(game));
    } else if (option.equals(LOAD_LEVEL)) {
      game.switchToState(new LevelEditor(game).getLevel(sketchPath() + "\\data\\test.txt"));
    } else if (option.equals(LEVEL_EDITOR)) {
      game.switchToState(new LevelEditor(game));
    }
  }
  
}