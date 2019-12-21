package agh.cs.evolution;

import java.util.Random;

enum MapDirection {
    NORTH,
    NE,
    EAST,
    SE,
    SOUTH,
    SW,
    WEST,
    NW;

    private static final MapDirection[] arrayOfDir = MapDirection.values();

    public static MapDirection getRandomDirection(){
        return getDirection(new Random().nextInt(arrayOfDir.length));
    }

    public static MapDirection getDirection(int x){
        return arrayOfDir[x];
    }

    public static MapDirection[] getAllDir() { return arrayOfDir; }

    public String toString(){
        switch(this) {
            case NORTH: return "Polnoc";
            case SOUTH: return "Poludnie";
            case WEST: return "Zachod";
            case EAST: return "Wschod";
            case NW: return "Polnocny-zachod";
            case NE: return "Polnocny-wschod";
            case SE: return "Poludniowy-wschod";
            case SW: return "Poludniwoy-zachd";
        }
        return null;
    }

    public Vector2d toUnitVector() {
        switch(this) {
            case EAST: return new Vector2d(1,0);
            case NORTH: return new Vector2d(0,1);
            case WEST: return new Vector2d(-1, 0);
            case SOUTH: return new Vector2d(0, -1);
            case NW: return new Vector2d(-1, 1);
            case NE: return new Vector2d(1,1);
            case SW: return new Vector2d(-1, -1);
            case SE: return new Vector2d(1, -1);
        }
        return null;
    }
}

