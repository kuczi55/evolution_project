package agh.cs.evolution;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

public class WorldMapTest {
    Parameters parameters;
    WorldMap map;

    @Before
    public void setUp() {
        parameters = new Parameters();
        map = new WorldMap(new Vector2d(100,100), new Vector2d(50,50), parameters);
    }

    @Test
    public void translatePositionTest() {
        Vector2d pos = new Vector2d(101, 101);
        pos = map.translatePosition(pos);
        Assert.assertEquals(pos, new Vector2d(0,0));
    }

    @Test
    public void highestEnergyTest() {
        LinkedList<Animal> animals = new LinkedList<>();
        animals.add(new Animal(new Vector2d(0,0), 200, 10, new Genotype(), map, parameters));
        animals.add(new Animal(new Vector2d(0,0), 201, 10, new Genotype(), map, parameters));
        animals.add(new Animal(new Vector2d(0,0), 300, 10, new Genotype(), map, parameters));
        animals.add(new Animal(new Vector2d(0,0), 98, 10, new Genotype(), map, parameters));
        animals.add(new Animal(new Vector2d(0,0), 2, 10, new Genotype(), map, parameters));

        Assert.assertEquals(map.highestEnergy(animals), 300);
    }

    @Test
    public void spawnAnimalsTest() {
        this.map.spawnAnimals(40);
        Assert.assertEquals(this.parameters.ANIMALS_AMOUNT + 40, this.map.animalsMap.size());
    }

    @Test
    public void findSpotForChildTest() {
        Vector2d startPos = new Vector2d(2,2);
        Vector2d endPos = this.map.findSpotForChild(startPos);
        Vector2d testPos = null;
        int[] x = {1, 1, 1, 0, 0, -1, -1, -1, 0};
        int[] y = {1, -1, 0, 1, -1, -1, 0, 1, 0};
        for(int i = 0; i < 8; i++) {
            if(startPos.add(new Vector2d(x[i], y[i])).equals(endPos)) {
                testPos = startPos.add(new Vector2d(x[i], y[i]));
                break;
            }
        }
        Assert.assertEquals(endPos, testPos);
    }
}
