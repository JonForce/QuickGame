package com.saucy.quickgame.main;

import java.util.HashMap;

public class LoadLevelMenu extends Menu {

    HashMap<String, String> namePathMap = new HashMap<String, String>();
    QuickGame game;

    LoadLevelMenu(QuickGame game, String folder) {
        super(game, game.controllerA);
        this.game = game;
        setTitle("Load Level");

        for (String file : game.listPaths(game.sketchPath() + "\\data\\" +folder + "\\")) {
            String[] split = file.split("\\Q\\\\E");
            String fileName = split[split.length - 1];
            // Remove the last four characters so the user doesn't see the .txt
            String name = fileName.substring(0, fileName.length() - 4);
            // Assert that a map with that name doesn't already exist.
            assert !namePathMap.containsKey(name);

            namePathMap.put(name, file);
            options.add(name);
        }
    }

    LoadLevelMenu(QuickGame game) {
        this(game, "custom");
    }

    @Override
    void onSelect(String option) {
        String path = namePathMap.get(option);
        loadLevel(path);
    }

    /** This method is overwritten, do not modify. */
    void loadLevel(String levelPath) {
        game.switchToState(new LevelEditor(game).getLevel(levelPath));
    }
}