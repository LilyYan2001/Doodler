package com.ciscx82.ciscx82doodler;

import android.graphics.Path;

// credit to https://www.geeksforgeeks.org/how-to-create-a-paint-application-in-android/

public class Stroke {
    // stroke params
    public int color;
    public float strokeWidth;
    public int opacity;
    public Path path;

    // constructor
    public Stroke(int color, float strokeWidth, int opacity, Path path) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.opacity = opacity;
        this.path = path;
    }
}
