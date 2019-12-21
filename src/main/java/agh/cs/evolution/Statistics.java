package agh.cs.evolution;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class Statistics {
    private WorldMap map;
    private LinkedList<Animal> animals = new LinkedList<>();
    private HashMap<Genotype, Integer> animalsGenotypes;
    private LinkedList<StatsListElement> statsHisList = new LinkedList<>();

    public Statistics(WorldMap map) {
        this.map = map;
        this.setUpAnimalsList();
    }

    private void setUpAnimalsList(){
        this.animals = this.map.getAnimalsList();
    }

    public Double getAvgEne() {
        Double avgEnergy = 0.0;
        if(animals.size() > 0) {
            for(Animal itr : animals) {
                avgEnergy += (Double.valueOf(itr.getEnergy()));
            }
            avgEnergy /= animals.size();
        }
        return avgEnergy;
    }

    public Double getAvgChildren() {
        Double avgChildren = 0.0;
        if(animals.size() > 0) {
            for(Animal animal : animals) {
                avgChildren += (Double.valueOf(animal.getChildren()));
            }
            avgChildren/=animals.size();
        }
        return avgChildren;
    }

    public Double getAvgAge() {
        Double avgAge = 0.0;
        LinkedList<Animal> deadAnimals = this.map.getDeadAnimalsList();
        if(deadAnimals.size() > 0) {
            for(Animal animal : deadAnimals) {
                avgAge += (Double.valueOf(animal.getAge()));
            }
            avgAge /= deadAnimals.size();
        }
        return avgAge;
    }

    public Genotype getDominatingGenotype() {
        this.animalsGenotypes = this.map.getAnimalsGenotypes();
        if(animalsGenotypes.size() == 0) return null;
        Map.Entry<Genotype, Integer> dominatingGenotype = null;
        for (Map.Entry<Genotype, Integer> entry : animalsGenotypes.entrySet()) {
            if (dominatingGenotype == null || entry.getValue().compareTo(dominatingGenotype.getValue()) > 0)
                dominatingGenotype = entry;
        }
        return dominatingGenotype.getKey();
    }

    public void addStat(Integer era) {
        StatsListElement eraStat = new StatsListElement();
        eraStat.era = era;
        eraStat.animalsAmount = this.map.animalsMap.size();
        eraStat.grassAmount = this.map.grasses.size();
        eraStat.avgEnergy = this.getAvgEne();
        eraStat.avgChildren = this.getAvgChildren();
        eraStat.avgAge = this.getAvgAge();
        eraStat.dominatingGenotype = this.getDominatingGenotype();

        this.statsHisList.add(eraStat);
    }

    public Integer getEra() {
        if(this.statsHisList.isEmpty()) return -1;
        else return this.statsHisList.size();
    }

    public void saveStats(BufferedWriter writer) throws IOException {
        StringBuilder statistic = new StringBuilder(10000);
        StatsListElement update = new StatsListElement();
        int era = 0;

        for (StatsListElement statsElement : this.statsHisList) {
            era++;

            update = update.addStatistics(statsElement);

            statistic.append("Era: ");
            statistic.append(era);
            statistic.append(System.lineSeparator());

            statistic.append("Średnia ilość zwierząt: ");
            statistic.append(String.format("%.2f", update.animalsAmount / (double) era));
            statistic.append(System.lineSeparator());

            statistic.append("Średnia ilość roślin: ");
            statistic.append(String.format("%.2f", update.grassAmount / (double) era));
            statistic.append(System.lineSeparator());

            statistic.append("Średni poziom energii: ");
            statistic.append(String.format("%.2f", update.avgEnergy / (double) era));
            statistic.append(System.lineSeparator());

            statistic.append("Średnia ilość dzieci: ");
            statistic.append(String.format("%.2f", update.avgChildren / (double) era));
            statistic.append(System.lineSeparator());

            statistic.append("Średnia długość życia: ");
            statistic.append(String.format("%.2f", update.avgAge / (double) era));
            statistic.append(System.lineSeparator());

            statistic.append("Dominujący genotyp: ");
            statistic.append(Arrays.toString(update.dominatingGenotype.genotype));
            statistic.append(System.lineSeparator());

            statistic.append(System.lineSeparator());
            statistic.append(System.lineSeparator());

            writer.write(statistic.toString());

            statistic.setLength(0);
        }
    }
}
