package agh.cs.evolution;

import javax.swing.*;

public class InputValuesFrame extends JFrame{
    private final Integer width = 250;
    private final Integer height = 400;


    public InputValuesFrame(Parameters parameters){

        setLocationRelativeTo(null);
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel parametersContent = new InputValuesPanel(parameters);
        add(parametersContent);

        setVisible(true);
    }
}
