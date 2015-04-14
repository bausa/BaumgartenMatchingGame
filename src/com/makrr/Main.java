package com.makrr;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Main implements MatchingGrid.AnimationDelegate {
    static SamActorWorld world;
    static MatchingGrid currentGrid;

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        currentGrid = new MatchingGrid(8, 8, BacktrackingAlgorithm.class);

        world = new SamActorWorld(currentGrid);

        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentGrid.animationDelegate = new Main();
                world.show();
            }
        });

        t.start();
    }

    public void animatePath(final ArrayList<Location> path) {
        final MatchActor actor = (MatchActor) currentGrid.get(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            final int finalI = i;
            Timer t = new Timer(i * 75, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (finalI == path.size() - 1) {
                        currentGrid.get(path.get(finalI)).removeSelfFromGrid();
                        currentGrid.get(path.get(finalI - 1)).removeSelfFromGrid();
                    } else actor.moveTo(path.get(finalI));
                }
            });
            t.setRepeats(false);
            t.start();
        }
    }

    public static void newGame() {

    }
}
