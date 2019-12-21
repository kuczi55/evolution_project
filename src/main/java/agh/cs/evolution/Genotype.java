package agh.cs.evolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Genotype {
    public Integer[] genotype;
    private static final int genotype_size = 32;
    public Genotype() {
        this.genotype = new Integer[genotype_size];
        this.genotype = generate();
        this.fix();
    }

    public Genotype(Integer[] gen) {
        this.genotype = gen;
        this.fix();
    }


    public Integer[] generate(){
        for(int i = 0; i < genotype_size; i++) {
            this.genotype[i] = generator();
        }
        Arrays.sort(genotype);
        return genotype;
    }

    protected Integer generator(){
        Random r = new Random();
        return r.nextInt(8);
    }

    protected void fix() {
        Integer amountOfGens [] = new Integer[8];
        Arrays.fill(amountOfGens, 0);
        for(Integer itr : this.genotype) {
            amountOfGens[itr]++;
        }

        int minimum = Collections.min(Arrays.asList(amountOfGens));

        if(minimum == 0) {
            Random r = new Random();

            for (int i = 0; i < 8; i++) {
                if(amountOfGens[i].equals(0)) {
                    while(amountOfGens[i].equals(0)) {
                        int possIdx = r.nextInt(genotype_size);
                        if(amountOfGens[this.genotype[possIdx]] > 1) {
                            amountOfGens[this.genotype[possIdx]]--;
                            this.genotype[possIdx] = i;
                            amountOfGens[i]++;
                        }
                    }
                }
            }
        }
        Arrays.sort(this.genotype);
    }

    public Genotype merge(Integer[] mother, Integer[] father) {
        if(mother.length != father.length) throw new IllegalArgumentException("Gens have different lengths");
        Random r = new Random();
        int size = mother.length;
        Integer[] newGenotype = new Integer[size];
        int firstIdxToDiv = r.nextInt(size-1);
        int secondIdxToDiv = firstIdxToDiv;
        while (secondIdxToDiv == firstIdxToDiv)
            secondIdxToDiv = r.nextInt(size-1);
        if(firstIdxToDiv > secondIdxToDiv) {
            int tmp = firstIdxToDiv;
            firstIdxToDiv = secondIdxToDiv;
            secondIdxToDiv = tmp;
        }
        if(r.nextBoolean()) {
            Integer [] temp = mother;
            mother = father;
            father = temp;
        }
        int whichGens = r.nextInt(3);
        ArrayList<Integer> partFather;
        ArrayList<Integer> partMother;

        if(whichGens == 0) {
            partFather = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(father, 0, firstIdxToDiv+1)));
            partMother = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(mother, firstIdxToDiv+1, size)));
        }
        else if(whichGens == 1) {
            partFather = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(father, firstIdxToDiv+1, secondIdxToDiv+1)));
            partMother = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(mother, 0, firstIdxToDiv+1)));
            ArrayList <Integer> partMother2 = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(mother, secondIdxToDiv+1, size)));
            partMother.addAll(partMother2);
        }
        else {
            partFather = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(father, secondIdxToDiv+1, size)));
            partMother = new ArrayList<Integer>(Arrays.asList(Arrays.copyOfRange(mother, 0, secondIdxToDiv+1)));
        }

        partFather.addAll(partMother);
        newGenotype = partFather.toArray(newGenotype);
        return new Genotype(newGenotype);
    }

    public Integer[] getGenotype(){
        return this.genotype;
    }

    @Override
    public int hashCode(){
        return Arrays.hashCode(this.genotype);
    }

    @Override
    public boolean equals(Object object){

        if(object == null){
            return false;
        }

        if(!(object instanceof Genotype)){
            return false;
        }

        return Arrays.equals(this.genotype, ((Genotype) object).getGenotype());
    }

}

