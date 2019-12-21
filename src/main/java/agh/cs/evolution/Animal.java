package agh.cs.evolution;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Animal {
    private Integer energy;
    private IWorldMap map;
    private Genotype genotype;
    private Integer age;
    private Parameters parameters;
    private Integer costOfMove;
    private MapDirection orientation;
    private Vector2d position;
    private Integer children;
    List<IPositionChangeObserver> observer = new ArrayList<>();
    private LinkedList<Animal> posterity = new LinkedList<>();

    public Animal(IWorldMap map, Parameters parameters){
        this.parameters = parameters;
        this.costOfMove = this.parameters.ENERGY_LOST_PER_MOVE;
        this.map = map;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, Integer energy, Parameters parameters){
        this.parameters = parameters;
        this.costOfMove = this.parameters.ENERGY_LOST_PER_MOVE;
        this.map = map;
        this.position = initialPosition;
        this.energy = energy;
        this.genotype = new Genotype();
        this.orientation = MapDirection.getRandomDirection();
        this.age = 0;
        this.children = 0;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, Genotype genotype, Integer energy, Parameters parameters){
        this.parameters = parameters;
        this.costOfMove = this.parameters.ENERGY_LOST_PER_MOVE;
        this.map = map;
        this.position = initialPosition;
        this.energy = energy;
        this.genotype = genotype;
        this.orientation = MapDirection.getRandomDirection();
        this.age = 0;
        this.children = 0;
    }

    public Animal(Vector2d initialPosition, Integer energy, Integer costOfMove, Genotype genotype, WorldMap map, Parameters parameters){
        this.position = initialPosition;
        this.energy = energy;
        this.orientation = MapDirection.getRandomDirection();
        this.costOfMove = costOfMove;
        this.genotype = genotype;
        this.map = map;
        this.parameters = parameters;
        this.age = 0;
        this.children = 0;
    }

    public String toString(){
        switch(this.orientation) {
            case NORTH :
                return "N";
            case SOUTH :
                return "S";
            case EAST :
                return "E";
            case WEST :
                return "W";
            case NE:
                return "↗";
            case NW:
                return "↖";
            case SE:
                return "↘";
            case SW:
                return "↙";
        }
        return null;
    }

    public String toStringAll(){
        return "Orientacja: " + this.toString() + "\nPolozenie: " + this.position;
    }

    private MapDirection getOrientationToMove() {
        Random r = new Random();
        int gen = r.nextInt(this.genotype.genotype.length);
        return orientation.getDirection(this.genotype.genotype[gen]);
    }

    public Vector2d possiblePosition(){
        this.orientation = this.getOrientationToMove();
        Vector2d toUnit = this.orientation.toUnitVector();
        return this.position.add(toUnit);
        }

    public void move(Vector2d newPos) {
        Vector2d oldPos = this.position;
        this.position = newPos;
        this.removeEnergy(costOfMove);
        notifyObservers(oldPos);
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void eat(Grass g) {
        this.energy += g.getEnergy();
    }

    public void removeEnergy(int energy) {
        this.energy -= energy;
        if(this.energy < 0) this.energy = 0;
    }

    public Integer getEnergy(){
        return this.energy;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public Animal copulate(Animal father, Vector2d childPosition) {
        int motherEnergy = (int) (0.25 * this.energy);
        int fatherEnergy = (int) (0.25 * father.getEnergy());
        int childEnergy = motherEnergy + fatherEnergy;
        this.energy -= motherEnergy;
        father.removeEnergy(fatherEnergy);
        Genotype childGenotype = this.genotype.merge(this.genotype.genotype, father.getGenotype().genotype);
        return new Animal(this.map, childPosition, childGenotype, childEnergy, this.parameters);
    }

    public void addObserver(IPositionChangeObserver observer){
        this.observer.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        this.observer.remove(observer);
    }

    private void notifyObservers(Vector2d oldPos){
        this.observer.forEach(obs -> obs.positionChanged(oldPos, this));
    }

    public void incrementAge() { this.age++; }

    public void incrementChildren() { this.children++; }

    public Integer getAge() { return this.age; }

    public Integer getChildren() { return this.children; }

    public void addDescendant(Animal animal) {
        this.posterity.add(animal);
    }

    private Stream<Animal> getProperPosterity() {
        return Stream.concat(this.posterity.stream(), this.posterity.stream().flatMap(Animal::getProperPosterity)).distinct();
    }

    public Integer getPosterityAmount() {
        return (int) this.getProperPosterity().count();
    }
}
