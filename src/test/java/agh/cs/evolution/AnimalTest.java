package agh.cs.evolution;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class AnimalTest {
    WorldMap map;
    Parameters parameters = new Parameters();

    @Before
    public void testingObjects() {
        map = new WorldMap(new Vector2d(100,100), new Vector2d(2,2), new Parameters());
   }

    @Test
    public void moveTest() {

        Animal animal = new Animal(new Vector2d(0,0), 200, 10, new Genotype(), map, parameters);
        animal.move(new Vector2d(1,1));
        Assert.assertEquals(animal.getPosition(), new Vector2d(1,1));
        Assert.assertEquals(animal.getEnergy(), Integer.valueOf(190));
    }

    @Test
    public void copulateTest() {
        Vector2d position = new Vector2d(1,1);
        Animal mother = new Animal(position, 100, 10, new Genotype(), map, parameters);
        Animal father = new Animal(position, 100, 10, new Genotype(), map, parameters);
        map.place(mother);
        map.place(father);
        mother.copulate(father, position);
        Assert.assertEquals(mother.getEnergy(), Integer.valueOf(75));
        Assert.assertEquals(father.getEnergy(), Integer.valueOf(75));
    }
    @Test
    public void addObserverTest(){
        Animal animal = new Animal(new Vector2d(0,0), 200, 10, new Genotype(), map, parameters);
        animal.addObserver(map);
        Assert.assertEquals(1, animal.observer.size());
    }

    @Test
    public void removeObserverTest(){
        Animal animal = new Animal(new Vector2d(0,0), 200, 10, new Genotype(), map, parameters);
        animal.addObserver(map);
        animal.removeObserver(map);
        Assert.assertEquals(0, animal.observer.size());
    }
}
