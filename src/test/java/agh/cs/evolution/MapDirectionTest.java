package agh.cs.evolution;

import org.junit.Assert;
import org.junit.Test;

public class MapDirectionTest {

    @Test
    public void toUnitVectorTest() {
        MapDirection test = MapDirection.NE;
        Vector2d pos = test.toUnitVector();
        Assert.assertEquals(pos, new Vector2d(1,1));
    }
}


