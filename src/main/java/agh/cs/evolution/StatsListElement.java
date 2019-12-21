package agh.cs.evolution;

public class StatsListElement {
    public Integer era = 0;
    public Integer animalsAmount = 0;
    public Integer grassAmount = 0;
    public Double avgEnergy = 0.0;
    public Double avgChildren = 0.0;
    public Double avgAge = 0.0;
    public Genotype dominatingGenotype;

    public StatsListElement addStatistics(StatsListElement element) {
        StatsListElement newElement = new StatsListElement();
        newElement.animalsAmount = element.animalsAmount + this.animalsAmount;
        newElement.grassAmount = element.grassAmount + this.grassAmount;
        newElement.avgEnergy = element.avgEnergy + this.avgEnergy;
        newElement.avgChildren = element.avgChildren + this.avgChildren;
        newElement.avgAge = element.avgAge + this.avgAge;
        newElement.dominatingGenotype = element.dominatingGenotype;
        return newElement;
    }
}
