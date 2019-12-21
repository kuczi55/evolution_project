package agh.cs.evolution;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GenotypeTest {

    @Test
    public void fixTest() {
        Integer[] genotype = new Integer[32];
        Arrays.fill(genotype, 0);
        Genotype gen = new Genotype(genotype);
        boolean containing = true;
        Integer[] g = gen.getGenotype();
        for(int i = 0; i < 8; i++)
        if(!Arrays.asList(g).contains(i)) containing = false;
        Assert.assertTrue(containing);
    }

    @Test
    public void mergeTest() {
        Genotype gen1 = new Genotype();
        Genotype gen2 = new Genotype();
        Genotype gen3 = new Genotype();
        gen1.merge(gen2.genotype, gen3.genotype);
        Assert.assertEquals(gen1.genotype.length, 32);
    }
}
