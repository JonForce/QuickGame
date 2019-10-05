package com.saucy.quickgame.main;

import com.saucy.quickgame.art.BloodAnimation;
import processing.core.PApplet;

public class DeathMenu extends Menu {

    PApplet applet;

    final int
            BLOOD_H_PADDING = 20;
    final String
            RESTART = "Restart",
            EXIT_TO_MAIN_MENU = "Quit to main menu",
            EXIT_TO_DESKTOP = "Quit to desktop";

    LevelState level;
    BloodAnimation animation;

    public DeathMenu(PApplet applet, LevelState level, Controller controller) {
        super(applet, controller);
        this.applet = applet;
        this.level = level;
        setTitle("You Died");
        options.add(RESTART);
        options.add(EXIT_TO_MAIN_MENU);
        options.add(EXIT_TO_DESKTOP);

        animation = new BloodAnimation(applet, x() + BLOOD_H_PADDING/2, this.y() + this.height(), this.width() - BLOOD_H_PADDING, 400, .25f, 20);
    }

    @Override
    void onSelect(String option) {
        if (option.equals(RESTART)) {
            level.resetGame();
            level.closeMenu();
        } else if (option.equals(EXIT_TO_MAIN_MENU)) {
            level.returnToMainMenu();
        } else if (option.equals(EXIT_TO_DESKTOP)) {
            applet.exit();
        }
    }

    @Override
    void render() {
        animation.render();
        super.render();
    }
}