package agh.cs.evolution;

public class Grass {
    private Vector2d position;
    public static int howMuchEnergy = 60;
    private int energy;
    public Grass(Vector2d position) {
        this.position = position;
        this.energy = howMuchEnergy;
    }

    public Grass(Vector2d position, int energy) {
        this.position = position;
        this.energy = energy;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public int getEnergy() {
        return this.energy;
    }

    public String toString() {
        return "*";
    }
}
