package com.saucy.quickgame.main;

import processing.core.PApplet;

public class PauseMenu extends Menu {
    final String
            RESUME = "Resume",
            RESTART = "Restart",
            EXIT_TO_MAIN_MENU = "Quit to main menu",
            EXIT_TO_DESKTOP = "Quit to desktop";

    final LevelState level;

    PauseMenu(PApplet applet, Controller controller, LevelState level) {
        super(applet, controller);
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
            level.returnToMainMenu();
        } else if (option.equals(EXIT_TO_DESKTOP)) {
            applet.exit();
        }
    }
}