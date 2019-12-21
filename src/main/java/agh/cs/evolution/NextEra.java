package agh.cs.evolution;

import java.util.ArrayList;
import java.util.LinkedList;

public class NextEra {
    private WorldMap map;

    public NextEra(WorldMap map) {
        this.map = map;
    }

    public void removeDeadAnimals() {
        ArrayList<Animal> animals = new ArrayList<>(this.map.animalsMap.values());
        for(Animal currentAnimal : animals) {
            if(currentAnimal.getEnergy() <= 0) {
                Vector2d position = currentAnimal.getPosition();
                LinkedList<Animal> samePos = new LinkedList<>(this.map.animalsMap.get(position));
                if(samePos.size() == 1){
                    if(this.map.isInJungle(position)) this.map.freeFieldsInJungle.add(position);
                    else this.map.freeFieldsInSavanna.add(position);
                }
                this.map.deadAnimals.add(currentAnimal);
                this.map.removeFromAnimalsGenotypes(currentAnimal);
                this.map.animalsMap.remove(position, currentAnimal);
            }
        }
    }

    public void moveAnimals() {
        ArrayList<Animal> animals = new ArrayList<>(this.map.animalsMap.values());
        for(Animal anim : animals) {
            Vector2d oldPos = anim.getPosition();
            LinkedList<Animal> samePos = new LinkedList<>(this.map.animalsMap.get(oldPos));
            Vector2d possiblePosition = this.map.translatePosition(anim.possiblePosition());
            anim.move(possiblePosition);
            anim.incrementAge();
            if(this.map.isInJungle(possiblePosition)){
                if(this.map.freeFieldsInJungle.contains(possiblePosition)) this.map.freeFieldsInJungle.remove(possiblePosition);
            }
            else {
                if(this.map.freeFieldsInSavanna.contains(possiblePosition)) this.map.freeFieldsInSavanna.remove(possiblePosition);
            }
            samePos.remove(anim);
            if(samePos.size() == 0) {
                if(this.map.isInJungle(oldPos)) this.map.freeFieldsInJungle.add(oldPos);
                else this.map.freeFieldsInSavanna.add(oldPos);
            }
        }
    }

    public void eatGrasses() {
        ArrayList<Animal> animals = new ArrayList<>(this.map.animalsMap.values());
        for(Animal anim : animals) {
            LinkedList<Animal> samePos = new LinkedList<>(this.map.animalsMap.get(anim.getPosition()));
            Grass grassToEat = this.map.grassAt(anim.getPosition());
            if(grassToEat != null) {
                if(samePos.size() > 1) {
                    int strongest = this.map.highestEnergy(samePos);
                    LinkedList<Animal> strongestAnimals = new LinkedList<>();
                    for(Animal ani : samePos){
                        if(ani.getEnergy() == strongest) strongestAnimals.add(ani);
                    }
                    if(strongestAnimals.size() > 1) {
                        int newEnergy = grassToEat.getEnergy()/strongestAnimals.size();
                        Grass tmpGrass = new Grass(new Vector2d(-1,-1), newEnergy);
                        for(Animal ani : strongestAnimals) {
                            ani.eat(tmpGrass);
                        }
                    }
                    else anim.eat(grassToEat);
                }
                else anim.eat(grassToEat);
                this.map.removeGrass(grassToEat.getPosition());
            }
        }
    }

    public void copulate() {
        LinkedList<Vector2d> animalsPos = new LinkedList<>(this.map.animalsMap.keySet());
        for(Vector2d pos : animalsPos) {
            Animal mother;
            Animal father;
            LinkedList<Animal> samePos = new LinkedList<>(this.map.animalsMap.get(pos));
            if(samePos.size() > 1) {
                mother = this.map.getStrongestFromList(samePos);
                if (mother.getEnergy() >= this.map.energyInit / 2) {
                    samePos.remove(mother);
                    father = this.map.getStrongestFromList(samePos);
                    if (father.getEnergy() >= this.map.energyInit / 2) {
                        Animal newAnimal = mother.copulate(father, this.map.findSpotForChild(mother.getPosition()));
                        this.map.place(newAnimal);
                        mother.incrementChildren();
                        father.incrementChildren();
                        mother.addDescendant(newAnimal);
                        father.addDescendant(newAnimal);
                    }
                }
            }
        }
    }
}
