package com.makrr;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sambaumgarten on 3/24/15
 */
public class BacktrackingAlgorithm implements PathfindingAlgorithm {
    private Grid grid;

    public BacktrackingAlgorithm(Grid grid) {
        this.grid = grid;
    }

    @Override
    public ArrayList<Location> findPath(Location l1, Location l2) {
        return findPath(null, l1, l2);
    }

    public ArrayList<Location> findPath(ArrayList<Location> path, Location l1, Location l2) {
        if (path == null) {
            path = new ArrayList<Location>();
            path.add(l1);
        }

        if (path.size() > 25) return null;
        if (numberOfTurnsInPath(path) > 1) return null;

        ArrayList<Location> locs = possibleLocationsForLocation(path.get(path.size() - 1), l2);
        for (Location l : locs) {
            if (path.contains(l)) continue;
            ArrayList<Location> tempPath = (ArrayList<Location>) path.clone();
            tempPath.add(l);

            if (l.equals(l2)) {
                return tempPath;
            }

            if (this.grid.get(l) != null) continue;

            ArrayList<Location> result = findPath(tempPath, l1, l2);
            if (result != null) return result;
        }

        return null;
    }

    public int numberOfTurnsInPath(ArrayList<Location> path) {
        if (path.size() < 2) return 0;
        int currentDirection = path.get(0).getDirectionToward(path.get(1));
        int turns = 0;

        for (int i = 1; i < path.size(); i++) {
            int d = path.get(i - 1).getDirectionToward(path.get(i));
            if (d != currentDirection) {
                currentDirection = d;
                turns++;
            }
        }

        return turns;

    }

    public ArrayList<Location> possibleLocationsForLocation(Location loc, Location l2) {
        ArrayList<Location> result = new ArrayList<Location>(4);

        int iL = loc.getDirectionToward(l2);

        if (iL >= 0 && iL <= 45) iL = 0;
        else if (iL > 45 && iL <= 135) iL = 90;
        else if (iL > 135 && iL <= 225) iL = 180;
        else if (iL > 225 && iL <= 315) iL = 270;
        else if (iL > 315 && iL <= 405) iL = 360;


        for (int i = 0; i < 4; i++) {
            int ang = (iL + (i * 90)) % 360;
            Location l = loc.getAdjacentLocation(ang);
            if (this.grid.isValid(l)) result.add(l);
        }

        return result;
    }
}
