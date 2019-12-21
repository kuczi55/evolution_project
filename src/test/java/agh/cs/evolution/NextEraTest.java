package agh.cs.evolution;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.LinkedList;

public class NextEraTest {
    WorldMap map;
    Parameters parameters = new Parameters();
    NextEra nextEra;

    @Before
    public void testingObjects() {
        map = new WorldMap(new Vector2d(100,100), new Vector2d(2,2), new Parameters());
        nextEra = new NextEra(map);
    }

    @Test
    public void removeDeadAnimalsTest() {
        Animal a = new Animal(map, new Vector2d(2,2), 0, parameters);
        map.place(a);
        nextEra.removeDeadAnimals();
        Assert.assertEquals(Integer.valueOf(this.map.animalsMap.size()), parameters.ANIMALS_AMOUNT);
    }

    @Test
    public void moveAnimalsTest() {
        nextEra.moveAnimals();
        LinkedList<Animal> animals = new LinkedList<>(this.map.animalsMap.values());
        boolean moveEnergy = true;
        for(Animal ani : animals) {
            if(ani.getEnergy() != parameters.START_ENERGY - parameters.ENERGY_LOST_PER_MOVE) moveEnergy = false;
        }
        Assert.assertTrue(moveEnergy);
    }

    @Test
    public void eatGrassesTest() {
        Grass g = new Grass(new Vector2d(2,2));
        this.map.grasses.putIfAbsent(new Vector2d(2, 2), g);
        Animal a = new Animal(map, new Vector2d(2,2), parameters.START_ENERGY, parameters);
        Animal b = new Animal(map, new Vector2d(2,2), parameters.START_ENERGY, parameters);
        this.map.place(a);
        this.map.place(b);
        nextEra.eatGrasses();
        LinkedList<Animal> samePos = new LinkedList<>(this.map.animalsMap.get(new Vector2d(2,2)));
        Assert.assertEquals(a.getEnergy(), Integer.valueOf(parameters.START_ENERGY + Grass.howMuchEnergy/samePos.size()));
    }

    @Test
    public void copulateTest() {
        Animal a = new Animal(map, new Vector2d(2,2), parameters.START_ENERGY, parameters);
        Animal b = new Animal(map, new Vector2d(2,2), parameters.START_ENERGY, parameters);
        this.map.place(a);
        this.map.place(b);
        nextEra.copulate();
        Assert.assertEquals(this.map.animalsMap.size(), this.parameters.ANIMALS_AMOUNT + 3);
    }
}
