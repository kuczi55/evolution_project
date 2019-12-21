package agh.cs.evolution;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


class Parameters {

    public Integer WORLD_MAP_WIDTH = 60;
    public Integer WORLD_MAP_HEIGHT = 50;
    public Double JUNGLE_RATIO = 0.4;
    public Integer ENERGY_LOST_PER_MOVE = 1;
    public Integer START_ENERGY = 100;
    public Integer ANIMALS_AMOUNT = 50;
    public Integer STEP_TIME = 50;


    public void init(String filename){
        try(Reader reader = new FileReader(filename)) {
            JSONObject jo =(JSONObject) new JSONParser().parse(reader);

            WORLD_MAP_WIDTH = Math.toIntExact((Long) jo.get("width"));
            WORLD_MAP_HEIGHT = Math.toIntExact((Long) jo.get("height"));
            START_ENERGY = Math.toIntExact((Long) jo.get("startEnergy"));
            ENERGY_LOST_PER_MOVE = Math.toIntExact((Long) jo.get("moveEnergy"));
            ANIMALS_AMOUNT = Math.toIntExact((Long) jo.get("animalsAmount"));
            STEP_TIME = Math.toIntExact((Long) jo.get("stepTime"));
            JUNGLE_RATIO = ((Double) jo.get("jungleRatio"));


        } catch (IOException | ParseException e) {
            System.out.println("Błąd parsowania JSON");
            e.printStackTrace();
        }
    }
}
