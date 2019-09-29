package com.saucy.quickgame.main;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

import java.util.ArrayList;

public class TitleScreen extends GameState {
  
 CircleAnimation a, b, c;
 
 PFont font;
 
 String topWord = "Quick";
 String bottomWord = "Game";
 
 long startTime;
 
 Controller controller;
 QuickGame game;

 TitleScreen(QuickGame game) {
   this.game = game;
   this.controller = game.controllerA;
   startTime = game.millis();
   font = game.createFont("titlefont.ttf", 100);
   a = new CircleAnimation(game);
   a.innerSize = 230;
   a.connectOffset = 4;
   
   b = new CircleAnimation(game);
   b.innerSize = 300;
   b.rotateTogether = true;
   b.connectOffset = 9;
 }
 
 @Override
 public void update() {
   if (controller.buttonA.getValue() > 0)
     game.switchToState(new MainMenuState(game));
 }
 
 @Override
 public void render() {
   game.background(0);
   a.render();
   b.render();
   
   //fill(255,255,255, (millis() > 3000)? 255 : 0);
   game.fill(255);
   game.textFont(font);
     game.textAlign(game.CENTER);
     game.textSize(100);
     game.text(topWord.substring(0, game.min(topWord.length(), characters())), game.width/2, game.height/2 - 50);
     game.text(bottomWord.substring(0, game.min(bottomWord.length(), game.max(0,characters()-topWord.length()))), game.width/2, game.height/2 + 50);
   
 }
 
 int characters() {
   return (int) game.max(0, game.min(topWord.length() + bottomWord.length(), (game.millis()-startTime - 100)/100));
 }
  
}

class CircleAnimation {

    private PApplet applet;
  
  int
    innerNumber = 50;
  
  float
    innerSize = 300,
    innerAmplitudeDif = 20;
    
  int connectOffset = 16;
  long startTime;
  boolean rotateTogether = false;
  
  ArrayList<PVector> vectors = new ArrayList<PVector>();
  
  CircleAnimation(PApplet applet) {
      this.applet = applet;
      startTime = applet.millis();
    for (int i = 0; i < innerNumber; i ++) {
      PVector v = new PVector();
      v.x = applet.width/2;
      v.y = applet.height/2;
      vectors.add(v);
    }
  }
  
  void render() {
    for (int i = 0; i < innerNumber; i ++) {
      PVector vector1 = vectors.get(i);
      PVector vector2 = vectors.get((i + connectOffset)%innerNumber);
        applet.fill(255);
        applet.stroke(255);
        applet.ellipse(vector1.x, vector1.y, 5, 5);
        applet.line(vector1.x, vector1.y, vector2.x, vector2.y);
      
      vector1.x = (targetX(i) - vector1.x)*.3f + vector1.x;
      //if (millis() - startTime > 500)
      vector1.y = (targetY(i) - vector1.y)*.3f + vector1.y;
    }
        
    //connectOffset = ((millis()/2000) % (INNER_NUMBER/2)) * 2;
  }
  
  float targetX(int i) {
    return applet.cos(i * (2*applet.PI)/innerNumber + frequencyOffset(i)) * amplitude(i) + applet.width/2;
  }
  
  float targetY(int i) {
    return applet.sin(i * (2*applet.PI)/innerNumber + frequencyOffset(i)) * amplitude(i) + applet.height/2;
  }
  
  float frequencyOffset(int i) {
    if (i % 2 == 0 || rotateTogether)
      return applet.millis()/1000.0f;
    else
      return -applet.millis()/1000.0f;
  }
  
  float amplitude(int i) {
    return (innerSize + (i % 2 == 0? innerAmplitudeDif/2 : -innerAmplitudeDif/2));
  }
}