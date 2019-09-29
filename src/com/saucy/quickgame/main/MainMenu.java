package com.saucy.quickgame.main;

import processing.core.PApplet;

public class MainMenu extends Menu {

    final String
            PLAY_CAMPAIGN = "Play Campaign",
            PROCEDURAL_LEVEL = "Procedural Level",
            LOAD_LEVEL = "Load Level",
            LEVEL_EDITOR = "Level Editor",
            SETTINGS = "Settings",
            EXIT_GAME = "Exit Game";

    QuickGame game;
    MainMenuState mainMenu;

    public MainMenu(Controller controller, QuickGame game, MainMenuState mainMenu) {
        super(game, controller);
        this.game = game;
        this.mainMenu = mainMenu;
        options.add(PLAY_CAMPAIGN);
        options.add(PROCEDURAL_LEVEL);
        options.add(LOAD_LEVEL);
        options.add(LEVEL_EDITOR);
        options.add(SETTINGS);
        setTitle("Main Menu");
    }


    @Override
    void onSelect(String option) {
        if (option.equals(PLAY_CAMPAIGN)) {
            String level = game.savedData.data.getString("currentLevel");
            game.switchToState(new LevelEditor(game).getLevel(level));
        } else if (option.equals(PROCEDURAL_LEVEL)) {
            game.switchToState(new LevelState(game));
        } else if (option.equals(LOAD_LEVEL)) {
            game.switchToState(new LoadLevelState(game));
        } else if (option.equals(LEVEL_EDITOR)) {
            game.switchToState(new LevelEditor(game));
        } else if (option.equals(SETTINGS)) {
            mainMenu.menu = new SettingsMenu(game, mainMenu);
        } else if (option.equals(EXIT_GAME)) {
            game.exit();
        }
    }

}