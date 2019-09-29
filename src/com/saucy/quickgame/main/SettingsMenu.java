package com.saucy.quickgame.main;

public class SettingsMenu extends Menu {

    final String
            BG_PARTICLES = "Background Particles",
            BG_OBJECTS = "Background objects",
            EFFECTS = "Effects",
            SAVE_AND_EXIT = "Save and exit";

    final float
            OPTION_ADJUST_SPEED = .01f;

    QuickGame game;
    MainMenuState mainMenu;

    SettingsMenu(QuickGame game, MainMenuState mainMenu) {
        super(game, game.controllerA);
        this.game = game;
        this.mainMenu = mainMenu;
        setTitle("Settings");
        super.options.add(BG_PARTICLES);
        super.options.add(BG_OBJECTS);
        super.options.add(EFFECTS);
        super.options.add(SAVE_AND_EXIT);

        BOX_WIDTH *= 1.5;
    }

    @Override
    void renderOptions() {
        for (int i = 0; i < options.size(); i ++) {
            if (i == selectedOption)
                game.fill(255);
            else
                game.fill(126);
            if (options.get(i).equals(SAVE_AND_EXIT)) {
                game.textSize(OPTION_TEXT_SIZE);
                game.text(SAVE_AND_EXIT, titleX(), titleY() + 220);
            } else {
                float optionValue = getOptionValue(options.get(i));

                game.textSize(OPTION_TEXT_SIZE);
                game.text(options.get(i), titleX() - 150, titleY() + i * (OPTION_TEXT_SIZE*1.75f) + 75);
                float
                        lineWidth = 150,
                        lineStartX = titleX() + 10,
                        lineStartY = titleY() + i * (OPTION_TEXT_SIZE*1.75f) + 75 - OPTION_TEXT_SIZE/2,
                        lineEndX = titleX() + 10 + lineWidth,
                        lineEndY = titleY() + i * (OPTION_TEXT_SIZE*1.75f) + 75 - OPTION_TEXT_SIZE/2;
                game.line(lineStartX, lineStartY,
                        lineEndX, lineEndY);
                game.ellipse(lineStartX + lineWidth * optionValue, lineStartY, 10, 10);
                game.textSize(OPTION_TEXT_SIZE * .75f);
                game.text(game.floor(optionValue * 100), lineEndX + 50, lineEndY + (OPTION_TEXT_SIZE*.75f)/2);
            }
        }
    }

    @Override
    void updateSelection() {
        super.updateSelection();

        if (options.get(selectedOption) != SAVE_AND_EXIT) {
            if (controller.sliderAX.getValue() > .5) {
                String option = options.get(selectedOption);
                float currentValue = getOptionValue(option);
                setOptionValue(option, game.min(1, currentValue + OPTION_ADJUST_SPEED));
            } else if (controller.sliderAX.getValue() < -.5) {
                String option = options.get(selectedOption);
                float currentValue = getOptionValue(option);
                setOptionValue(option, game.max(0, currentValue - OPTION_ADJUST_SPEED));
            }
        }
    }

    void setOptionValue(String option, float value) {
        if (option.equals(BG_PARTICLES))
            game.settings.bgParticles = value;
        else if (option.equals(BG_OBJECTS))
            game.settings.bgObjects = value;
        else if (option.equals(EFFECTS))
            game.settings.effects = value;
        else
            throw new RuntimeException("Option doesn't exist " + option);
    }

    float getOptionValue(String option) {
        if (option.equals(BG_PARTICLES))
            return game.settings.bgParticles;
        else if (option.equals(BG_OBJECTS))
            return game.settings.bgObjects;
        else if (option.equals(EFFECTS))
            return game.settings.effects;
        else
            throw new RuntimeException("Option doesn't exist " + option);
    }

    @Override
    void onSelect(String option) {
        if (option.equals(SAVE_AND_EXIT)) {
            game.settings.save();
            mainMenu.menu = new MainMenu(controller, game, mainMenu);
        }
    }
}