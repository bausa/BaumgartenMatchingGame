package com.makrr;

import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.awt.*;

/**
 * Created by sambaumgarten on 3/23/15
 */
public class MatchActor extends Actor {
    private Color actualColor;
    private Location moveLoc;

    @Override
    public void setColor(Color newColor) {
        super.setColor(newColor);
        if (newColor != Color.BLACK) actualColor = newColor;
    }

    @Override
    public void act() {
        if (this.moveLoc != null) this.moveTo(this.moveLoc);
    }

    public void setMoveLoc(Location moveLoc) {
        this.moveLoc = moveLoc;
    }

    protected void unselect() {
        this.setColor(actualColor);
    }

    protected void select() {
        this.setColor(Color.black);
    }

    public Color getActualColor() {
        return actualColor;
    }
}
