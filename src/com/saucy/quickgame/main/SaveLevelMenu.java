package com.saucy.quickgame.main;

import processing.core.PApplet;

public class SaveLevelMenu extends Menu {

    final String
            CANCEL = "Cancel",
            SAVE = "Save";

    LevelEditor editor;
    String levelName = "";
    boolean keyReleased = true;

    SaveLevelMenu(PApplet applet, LevelEditor editor) {
        super(applet, editor.controllerA);
        this.editor = editor;
        setTitle("Level Name : ");
        super.options.add(CANCEL);
        super.options.add(SAVE);
    }

    @Override
    void render() {
        setTitle("Level Name : " + levelName);
        super.render();

        if (applet.keyPressed && keyReleased) {
            // 8 = Backspace
            if (applet.key == 8) {
                if (levelName.length() > 0)
                    levelName = levelName.substring(0, levelName.length() - 1);
            } else {
                levelName += applet.key;
            }
            keyReleased = false;
        }
        if (!applet.keyPressed)
            keyReleased = true;
    }

    @Override
    void onSelect(String option) {
        if (option.equals(CANCEL))
            editor.closeMenu();
        else if (option.equals(SAVE)) {
            String path;
            if (levelName.startsWith("-")) {
                levelName = levelName.substring(1, levelName.length());
                path = applet.sketchPath() + "\\data\\campaign\\" + levelName + ".txt";
            } else {
                path = applet.sketchPath() + "\\data\\custom\\" + levelName + ".txt";
            }
            editor.saveTo(path);
            editor.resume();
            editor.closeMenu();
        }
    }
}