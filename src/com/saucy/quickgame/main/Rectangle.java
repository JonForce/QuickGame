package com.saucy.quickgame.main;

import processing.core.PApplet;

/** This class represents a simple rectangle in the game world. It is a tool used by block
 for collision detection. */
public class Rectangle {

    private PApplet applet;

    float x, y, w, h;

    Rectangle(PApplet applet, float x, float y, float w, float h) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    Rectangle() { }

    Rectangle intercept(Rectangle other) {
        Rectangle intercept = new Rectangle();
        if (x > other.x && x < other.x + other.w) {
            intercept.x = x;
            intercept.w = (other.x + other.w) - x;
        }
        if (other.x > x && other.x < x + w) {
            intercept.x = other.x;
            intercept.w = (x + w) - other.x;
        }
        // Contained
        if (x > other.x && x + w < other.x + other.w) {
            intercept.x = x;
            intercept.w = w;
        }
        if (other.x > x && other.x + other.w < x + w) {
            intercept.x = other.x;
            intercept.w = other.w;
        }

        if (y > other.y && y < other.y + other.y) {
            intercept.y = y;
            intercept.h = (other.y + other.h) - y;
        }
        if (other.y > y && other.y < y + h) {
            intercept.y = other.y;
            intercept.h = (y + h) - other.y;
        }
        // Contained
        if (y > other.y && y + h < other.y + other.h) {
            intercept.y = y;
            intercept.h = h;
        }
        if (other.y > y && other.y + other.h < y + h) {
            intercept.y = other.y;
            intercept.h = other.h;
        }

        if (intercept.w <= 0 || intercept.h <= 0) {
            intercept.x = 0;
            intercept.y = 0;
            intercept.w = 0;
            intercept.h = 0;
        }
        return intercept;
    }

    boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + this.w && y >= this.y && y <= this.y + this.h;
    }

    void render(Camera camera) {
        applet.fill(255, 0, 0);
        applet.rect(x - camera.x + applet.width/2, y - camera.y, w, h);
    }
}