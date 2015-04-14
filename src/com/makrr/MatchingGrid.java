package com.makrr;

import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sambaumgarten on 3/23/15
 */
public class MatchingGrid extends BoundedGrid<Actor> {
    private PathfindingAlgorithm pathfindingAlgorithm;

    public interface AnimationDelegate {
        public void animatePath(ArrayList<Location> path);
    }

    private Random random;
    MatchActor[] pickedActors = new MatchActor[2];
    public AnimationDelegate animationDelegate;


    public MatchingGrid(int rows, int cols, Class pathfindingAlgorithmClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        super(rows, cols);

        Constructor<?> constructor = pathfindingAlgorithmClass.getConstructor(Grid.class);
        this.pathfindingAlgorithm = (PathfindingAlgorithm) constructor.newInstance(new Object[] { this });

        setupGridWithRandomActors();
    }

    public void setupGridWithRandomActors() {
        int numAssignedWithCurrentColor = 0;
        Color color = getRandomColor();
        while (this.getOccupiedLocations().size() < (this.getNumRows() - 2) * (this.getNumCols() - 2)) {
            Location randomLocation = getRandomLocation();
            while (this.getOccupiedLocations().contains(randomLocation)) {
                randomLocation = getRandomLocation();
            }

            MatchActor actor = new MatchActor();
            actor.setColor(color);
            actor.putSelfInGrid(this, randomLocation);
            numAssignedWithCurrentColor++;

            if (numAssignedWithCurrentColor == 2) {
                numAssignedWithCurrentColor = 0;
                color = getRandomColor();
            }
        }
    }

    public void locationClicked(Location loc) {
        MatchActor actor = (MatchActor) this.get(loc);

        if (pickedActors[0] == null) {
            pickedActors[0] = actor;
            if (actor != null && actor instanceof MatchActor) ((MatchActor)actor).select();
        } else if (pickedActors[1] == null) {
            pickedActors[1] = actor;
            if (actor != null && actor instanceof MatchActor) ((MatchActor)actor).select();
        }

        if (pickedActors[0] != null && pickedActors[1] != null) {
            if (pickedActors[0].getActualColor().equals(pickedActors[1].getActualColor())) {
                ArrayList<Location> res = this.pathfindingAlgorithm.findPath(pickedActors[0].getLocation(), pickedActors[1].getLocation());

                if (res != null) {
                    if (this.animationDelegate != null) animationDelegate.animatePath(res);
                    else {
                        pickedActors[0].removeSelfFromGrid();
                        pickedActors[1].removeSelfFromGrid();
                    }
                }
            }

            pickedActors[0].unselect();
            pickedActors[1].unselect();

            pickedActors[0] = null;
            pickedActors[1] = null;
        }

        checkForGameEnd();
    }

    public void checkForGameEnd() {
        if (this.getOccupiedLocations().size() == 0 || this.getOccupiedLocations().size() == 1) this.setupGridWithRandomActors();
    }

    private Location getRandomLocation() {
        int row = 0;
        int col = 0;

        while (!(row < this.getNumRows() - 1 && row > 0 && col < this.getNumCols() - 1 && col > 0)) {
            row = getRandomInt(this.getNumRows());
            col = getRandomInt(this.getNumRows());
        }

        return new Location(row, col);
    }

    private Color getRandomColor() {
        Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN};

//        return new Color(getRandomFloat(), getRandomFloat(), getRandomFloat());
        return colors[getRandomInt(colors.length)];
    }

    private int getRandomInt(int max) {
        if (random == null) random = new Random();
        if (max > 0) return random.nextInt(max);
        else return random.nextInt();
    }

    private float getRandomFloat() {
        if (random == null) random = new Random();
        return random.nextFloat();
    }
}
