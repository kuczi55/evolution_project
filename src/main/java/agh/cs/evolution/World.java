package agh.cs.evolution;

import javax.swing.*;

public class World {
    public static void main(String[] args) {

        Parameters parameters = new Parameters();

        try{
            if(args.length > 0)
            parameters.init(args[0]);
            else parameters.init("src/main/Resources/parameters.json");
            SwingUtilities.invokeLater(() -> {
                new InputValuesFrame(parameters);
            });

        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }
}
