package agh.cs.evolution;

import org.junit.Assert;
import org.junit.Test;

public class Vector2dTest {

    @Test
    public void equlasTest(){
        Vector2d a = new Vector2d(3,3);
        Vector2d b = new Vector2d(3, 3);
        Assert.assertTrue(a.equals(b));
        Vector2d c = new Vector2d(-7,3);
        Vector2d d = new Vector2d(5, 3);
        Assert.assertFalse(c.equals(d));
        Vector2d e = new Vector2d(-6,3);
        Vector2d f = new Vector2d(-6, 2);
        Assert.assertFalse(e.equals(f));
        Vector2d g = new Vector2d(-23,-3);
        Vector2d h = new Vector2d(5, 1);
        Assert.assertFalse(g.equals(h));
    }

    @Test
    public void toStringTest(){
        Assert.assertEquals(new Vector2d(2,4).toString(), "(2,4)");
        Assert.assertEquals(new Vector2d(-12,27).toString(), "(-12,27)");
        Assert.assertEquals(new Vector2d(-24,-1).toString(), "(-24,-1)");
    }

    @Test
    public void precedesTest(){
        Vector2d a = new Vector2d(1,2);
        Vector2d b = new Vector2d(2, 3);
        Assert.assertTrue(a.precedes(b));
        Vector2d c = new Vector2d(6,4);
        Vector2d d = new Vector2d(5, 12);
        Assert.assertFalse(c.precedes(d));
        Vector2d e = new Vector2d(2,4);
        Vector2d f = new Vector2d(2, 7);
        Assert.assertTrue(e.precedes(f));
        Vector2d g = new Vector2d(23,11);
        Vector2d h = new Vector2d(5, 1);
        Assert.assertFalse(g.precedes(h));
        Vector2d i = new Vector2d(2,11);
        Vector2d j = new Vector2d(4, 10);
        Assert.assertFalse(i.precedes(j));
        Vector2d k = new Vector2d(1,1);
        Vector2d l = new Vector2d(1, 1);
        Assert.assertTrue(k.precedes(l));
    }

    @Test
    public void followsTest(){
        Vector2d a = new Vector2d(23,31);
        Vector2d b = new Vector2d(-6, -13);
        Assert.assertTrue(a.follows(b));
        Vector2d c = new Vector2d(-10,-1);
        Vector2d d = new Vector2d(-9, -8);
        Assert.assertFalse(c.follows(d));
        Vector2d e = new Vector2d(0,14);
        Vector2d f = new Vector2d(0, 7);
        Assert.assertTrue(e.follows(f));
        Vector2d g = new Vector2d(31,42);
        Vector2d h = new Vector2d(32, 100);
        Assert.assertFalse(g.follows(h));
        Vector2d i = new Vector2d(-5,101);
        Vector2d j = new Vector2d(-8, 102);
        Assert.assertFalse(i.follows(j));
        Vector2d k = new Vector2d(1,1);
        Vector2d l = new Vector2d(1, 1);
        Assert.assertTrue(k.follows(l));
    }

    @Test
    public void upperRightTest(){
        Vector2d a = new Vector2d(1,4);
        Vector2d b = new Vector2d(2, 5);
        Assert.assertEquals(a.upperRight(b), new Vector2d(2,5));
        Vector2d c = new Vector2d(5,7);
        Vector2d d = new Vector2d(3, 5);
        Assert.assertEquals(c.upperRight(d), new Vector2d(5,7));
        Vector2d e = new Vector2d(-1,-5);
        Vector2d f = new Vector2d(-1, 0);
        Assert.assertEquals(e.upperRight(f), new Vector2d(-1,0));
        Vector2d g = new Vector2d(-10,14);
        Vector2d h = new Vector2d(-12, 14);
        Assert.assertEquals(g.upperRight(h), new Vector2d(-10,14));
        Vector2d i = new Vector2d(0,0);
        Vector2d j = new Vector2d(0, 0);
        Assert.assertEquals(i.upperRight(j), new Vector2d(0,0));
    }

    @Test
    public void lowerLeftTest(){
        Vector2d a = new Vector2d(4,2);
        Vector2d b = new Vector2d(3, 1);
        Assert.assertEquals(a.lowerLeft(b), new Vector2d(3,1));
        Vector2d c = new Vector2d(-12,-1);
        Vector2d d = new Vector2d(-10, -3);
        Assert.assertEquals(c.lowerLeft(d), new Vector2d(-12,-3));
        Vector2d e = new Vector2d(3,5);
        Vector2d f = new Vector2d(3, -3);
        Assert.assertEquals(e.lowerLeft(f), new Vector2d(3,-3));
        Vector2d g = new Vector2d(-2,4);
        Vector2d h = new Vector2d(0, 4);
        Assert.assertEquals(g.lowerLeft(h), new Vector2d(-2,4));
        Vector2d i = new Vector2d(0,0);
        Vector2d j = new Vector2d(0, 0);
        Assert.assertEquals(i.lowerLeft(j), new Vector2d(0,0));
    }

    @Test
    public void addTest(){
        Vector2d a = new Vector2d(1,1);
        Vector2d b = new Vector2d(2, 4);
        Assert.assertEquals(a.add(b), new Vector2d(3,5));
        Vector2d c = new Vector2d(-2,-1);
        Vector2d d = new Vector2d(-14, 8);
        Assert.assertEquals(c.add(d), new Vector2d(-16,7));
        Vector2d e = new Vector2d(-3,-5);
        Vector2d f = new Vector2d(-3, -3);
        Assert.assertEquals(e.add(f), new Vector2d(-6,-8));
    }

    @Test
    public void subtractTest(){
        Vector2d a = new Vector2d(1,0);
        Vector2d b = new Vector2d(4, 1);
        Assert.assertEquals(a.subtract(b), new Vector2d(-3,-1));
        Vector2d c = new Vector2d(0,0);
        Vector2d d = new Vector2d(-14, 8);
        Assert.assertEquals(c.subtract(d), new Vector2d(14,-8));
        Vector2d e = new Vector2d(-3,-5);
        Vector2d f = new Vector2d(-3, -3);
        Assert.assertEquals(e.subtract(f), new Vector2d(0,-2));
    }

    @Test
    public void oppositeTest(){
        Vector2d a = new Vector2d(5,7);
        Assert.assertEquals(a.opposite(), new Vector2d(-5,-7));
        Vector2d b = new Vector2d(0,0);
        Assert.assertEquals(b.opposite(), new Vector2d(0,0));
        Vector2d c = new Vector2d(-12,-11);
        Assert.assertEquals(c.opposite(), new Vector2d(12,11));
    }
}
