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
import java.util.ResourceBundle;

public class Main {
    static SamActorWorld world;

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        world = new SamActorWorld(new BoundedGrid<Actor>(10, 10));
        world.setMessage(getResources().getString("message.default"));
        world.show();
    }

    public static ResourceBundle getResources() {
        return ResourceBundle.getBundle(SamWorldFrame.class.getName() + "Resources");
    }
}
