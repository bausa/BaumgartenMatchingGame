package com.makrr;

import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * Created by sambaumgarten on 3/24/15
 */
public interface PathfindingAlgorithm {
    public ArrayList<Location> findPath(Location l1, Location l2);
}
