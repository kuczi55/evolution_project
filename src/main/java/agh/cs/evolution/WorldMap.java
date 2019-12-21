package agh.cs.evolution;

import java.util.*;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class WorldMap implements IWorldMap, IPositionChangeObserver {
    private Parameters parameters;
    public Integer energyInit;
    private  Integer grassPercentageJungle = 70;
    private  Integer grassPercentageSavanna = 20;
    public final RectangularMap savannaMap;
    public final RectangularMap jungleMap;
    public ArrayList<Vector2d> freeFieldsInJungle = new ArrayList<Vector2d>();
    public ArrayList<Vector2d> freeFieldsInSavanna = new ArrayList<Vector2d>();
    public Multimap <Vector2d, Animal> animalsMap = LinkedListMultimap.create();
    public LinkedHashMap<Vector2d, Grass> grasses = new LinkedHashMap<Vector2d, Grass>();
    public LinkedList<Animal> deadAnimals = new LinkedList<>();
    private HashMap<Genotype, Integer> animalsGenotypes = new HashMap<>();
    private Integer animalsAmount;

    public WorldMap(Vector2d savannaSize, Vector2d jungleSize, Parameters parameters) {
        this.savannaMap = new RectangularMap(new Vector2d(0,0), savannaSize);
        Vector2d lowerLeftJungle = new Vector2d((savannaSize.x - jungleSize.x)/2, (savannaSize.y - jungleSize.y)/2);
        this.jungleMap = new RectangularMap(lowerLeftJungle, lowerLeftJungle.add(jungleSize));
        this.parameters = parameters;
        this.energyInit = parameters.START_ENERGY;
        this.animalsAmount = parameters.ANIMALS_AMOUNT;

        this.fillFreeFields();
        this.spawnAnimals(animalsAmount);
        this.fillMapWithGrasses();
    }

    private void fillFreeFields() {
        for(int i = 0; i <= savannaMap.mapEnd.x; i++) {
            for(int j = 0; j <= savannaMap.mapEnd.y; j++) {
                this.freeFieldsInSavanna.add(new Vector2d(i, j));
            }
        }
        for(int i = jungleMap.mapStart.x; i <= jungleMap.mapEnd.x; i++) {
            for(int j = jungleMap.mapStart.y; j <= jungleMap.mapEnd.y; j++) {
                Vector2d field = new Vector2d(i,j);
                this.freeFieldsInJungle.add(field);
                this.freeFieldsInSavanna.remove(field);
            }
        }
    }

    public boolean isInJungle (Vector2d position) {
        if(position.x >= jungleMap.mapStart.x && position.y >= jungleMap.mapStart.y
                && position.x <= jungleMap.mapEnd.x && position.y <= jungleMap.mapEnd.y)
            return true;
        return false;
    }

    private void fillMapWithGrasses() {
        Random generator = new Random();
        int jungleAmount = getAmount(this.jungleMap, null, this.grassPercentageJungle);
        int savannaAmount = getAmount(this.savannaMap, this.jungleMap, this.grassPercentageSavanna);
        if(jungleAmount >= this.freeFieldsInJungle.size()){
            putAllGrasses(this.freeFieldsInJungle);
        }
        else {
            while(jungleAmount-- > 0) {
                int randomField = generator.nextInt(this.freeFieldsInJungle.size());
                this.grasses.put(this.freeFieldsInJungle.get(randomField), new Grass(this.freeFieldsInJungle.get(randomField)));
                this.freeFieldsInJungle.remove(randomField);
            }
        }
        if(savannaAmount >= this.freeFieldsInSavanna.size()){
            putAllGrasses(this.freeFieldsInSavanna);
        }
        else {
            while(savannaAmount-- > 0) {
                int randomField = generator.nextInt(this.freeFieldsInSavanna.size());
                this.grasses.put(this.freeFieldsInSavanna.get(randomField), new Grass(this.freeFieldsInSavanna.get(randomField)));
                this.freeFieldsInSavanna.remove(randomField);
            }
        }
    }

    private void putAllGrasses(ArrayList<Vector2d> fields) {
        for(Vector2d pos : fields) {
            this.grasses.put(pos, new Grass(pos));
        }
        fields.clear();
    }

    public int getAmount(RectangularMap map, RectangularMap exclude, int percentage){
        if(exclude != null) {
            return ((map.mapEnd.x+1) * (map.mapEnd.y+1) - ((exclude.mapEnd.x+1) - (exclude.mapStart.x)) * ((exclude.mapEnd.y+1) - (exclude.mapStart.y)))*percentage/100;
        }
        else
            return (((map.mapEnd.x+1) - (map.mapStart.x)) * ((map.mapEnd.y+1) - (map.mapStart.y)))*percentage/100;
    }

    public void spawnGrass() {
        Random r = new Random();
        if(this.freeFieldsInJungle.size() > 0) {
            int newJungleGrass = r.nextInt(this.freeFieldsInJungle.size());
            Vector2d posNewJunGrass = this.freeFieldsInJungle.get(newJungleGrass);
            this.grasses.put(posNewJunGrass, new Grass(posNewJunGrass));
            this.freeFieldsInJungle.remove(newJungleGrass);
        }
        if(this.freeFieldsInSavanna.size() > 0) {
            int newSavannaGrass = r.nextInt(this.freeFieldsInSavanna.size());
            Vector2d posNewSavGrass = this.freeFieldsInSavanna.get(newSavannaGrass);
            this.grasses.put(posNewSavGrass, new Grass(posNewSavGrass));
            this.freeFieldsInSavanna.remove(newSavannaGrass);
        }

    }

    public void removeGrass(Vector2d position) {
        this.grasses.remove(position);
    }

    public Vector2d translatePosition(Vector2d position){
        int x = position.x;
        int y = position.y;
        if(x < 0){
            x = this.savannaMap.mapEnd.x;
        }
        if(y < 0){
            y = this.savannaMap.mapEnd.y;
        }

        return new Vector2d(x % ((this.savannaMap.mapEnd.x)+1), y % ((this.savannaMap.mapEnd.y)+1));
    }

    public String toString() {
        System.out.println("Ilość zwierząt: " + this.animalsMap.size());
        System.out.println("Ilość roślin: " + this.grasses.size());
        return new MapVisualizer(this).draw(new Vector2d(0,0), this.savannaMap.mapEnd);
    }

    public Grass grassAt(Vector2d position){
        return grasses.get(position);
    }

    public int highestEnergy(LinkedList<Animal> ani) {
        int highest = 0;
        for(Animal anim : ani) {
            if(highest < anim.getEnergy()) highest = anim.getEnergy();
        }
        return  highest;
    }

    public LinkedList<Animal> getAnimalsList() {
        return new LinkedList<Animal>(this.animalsMap.values());
    }

    public LinkedList<Animal> getDeadAnimalsList() {
        return this.deadAnimals;
    }

    @Override
    public void place(Animal newAnimal) {
        Vector2d position = newAnimal.getPosition();
        animalsMap.put(position, newAnimal);
        newAnimal.addObserver(this);
        this.addToAnimalsGenotypes(newAnimal);
    }

    @Override
    public boolean isOccupied(Vector2d possiblePosition) {
        return this.animalsMap.containsKey(possiblePosition);
    }

    @Override
    public Object objectAt(Vector2d position) {
        ArrayList<Animal> animals = new ArrayList<>(this.animalsMap.get(position));
        if(animals.size() > 1) return this.animalsMap.get(position);
        if(animals.size() == 1) return animals.get(0);
        return this.grassAt(position);
    }

    @Override
    public void run() {
        NextEra nextEra = new NextEra(this);

        nextEra.removeDeadAnimals();
        nextEra.moveAnimals();
        nextEra.eatGrasses();
        nextEra.copulate();

        this.spawnGrass();
    }

    public void spawnAnimals(int n) {
        Random r = new Random();
        int pos;
        if(n > parameters.WORLD_MAP_HEIGHT * parameters.WORLD_MAP_HEIGHT) throw new IllegalArgumentException("Zwierzęta nie mieszczą się na mapie");
        while(n > 0) {
            Vector2d position = null;
            if(r.nextBoolean() && this.freeFieldsInJungle.size() > 0) {
                pos = r.nextInt(this.freeFieldsInJungle.size());
                position = this.freeFieldsInJungle.get(pos);
                this.freeFieldsInJungle.remove(pos);
            }
            else {
                if(this.freeFieldsInSavanna.size() > 0) {
                    pos = r.nextInt(this.freeFieldsInSavanna.size());
                    position = this.freeFieldsInSavanna.get(pos);
                    this.freeFieldsInSavanna.remove(pos);
                }
            }
            if(position != null) this.place(new Animal(this, position, energyInit, parameters));
            n--;
        }
    }


    public Vector2d findSpotForChild(Vector2d parPos) {
        MapDirection[] allDir = MapDirection.getAllDir();
        ArrayList<Vector2d> positions = new ArrayList<>();
        for(MapDirection dir : allDir) {
            positions.add(parPos.add(Objects.requireNonNull(dir.toUnitVector())));
        }
        Random r = new Random();
        Vector2d position = null;
        while(positions.size() > 0) {
            int p = r.nextInt(positions.size());
            Vector2d tmp = positions.get(p);
            position = translatePosition(tmp);
            if(this.freeFieldsInJungle.contains(position)) {
                this.freeFieldsInJungle.remove(position);
                break;
            }
            if(this.freeFieldsInSavanna.contains(position)) {
                this.freeFieldsInSavanna.remove(position);
                break;
            }
            positions.remove(p);
        }
        return position;
    }

    private void addToAnimalsGenotypes(Animal animal) {
        this.animalsGenotypes.put(animal.getGenotype(), this.animalsGenotypes.getOrDefault(animal.getGenotype(), 0) + 1);
    }

    public void removeFromAnimalsGenotypes(Animal animal) {
        this.animalsGenotypes.put(animal.getGenotype(), this.animalsGenotypes.getOrDefault(animal.getGenotype(), 1) - 1);
    }

    public HashMap<Genotype, Integer> getAnimalsGenotypes(){
        return this.animalsGenotypes;
    }

    public Animal getStrongest(Vector2d position) {
        LinkedList<Animal> samePos = new LinkedList<>(this.animalsMap.get(position));
        if(samePos.size() > 0) {
            if (samePos.size() == 1) return samePos.get(0);
            int strongest = highestEnergy(samePos);
            Collections.shuffle(samePos);
            for(Animal animal : samePos) {
                if(animal.getEnergy().equals(strongest)) return animal;
            }
        }
        return null;
    }

    public Animal getStrongestFromList(LinkedList<Animal> animals) {
        int strongest = highestEnergy(animals);
        Collections.shuffle(animals);
        for(Animal animal : animals) {
            if(animal.getEnergy().equals(strongest)) return animal;
        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Animal a) {
        this.animalsMap.remove(oldPosition, a);
        this.animalsMap.put(a.getPosition(), a);
    }
}
