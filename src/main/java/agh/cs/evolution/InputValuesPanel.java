package agh.cs.evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class InputValuesPanel extends JPanel implements ActionListener {
    private static final int INPUT_SIZE = 7;

    private HashMap<String, JPanel> panels = new HashMap<>();
    private HashMap<String, JTextField> textFields = new HashMap<>();
    private HashMap<String, JLabel> labels = new HashMap<>();
    private Parameters defaultParameters;

    private JButton runButton;


    public InputValuesPanel (Parameters parameters){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.defaultParameters = parameters;
        initializeLabels();
        initializeTextFields();
        configureLabels();
        preparePanels();
        createLayout();
    }

    private void initializeLabels() {
        labels.put("width", new JLabel("Szerokość świata: "));
        labels.put("height", new JLabel("Wysokość świata: "));
        labels.put("jungleRatio", new JLabel("Proporcje dżungli: "));
        labels.put("startEnergy", new JLabel("Energia początkowa: "));
        labels.put("moveEnergy", new JLabel("Koszt ruchu: "));
        labels.put("stepTime", new JLabel("Szybkość animacji (ms): "));
        labels.put("animalsAmount", new JLabel("Ilość zwierząt: "));
    }

    private void initializeTextFields() {
        JTextField mapWidth = new JTextField(INPUT_SIZE);
        JTextField mapHeight = new JTextField(INPUT_SIZE);
        JTextField startEnergy = new JTextField(INPUT_SIZE);
        JTextField moveEnergy = new JTextField(INPUT_SIZE);
        JTextField jungleRatio = new JTextField(INPUT_SIZE);
        JTextField stepTime = new JTextField(INPUT_SIZE);
        JTextField animalsAmount = new JTextField(INPUT_SIZE);

        mapWidth.setText(defaultParameters.WORLD_MAP_WIDTH.toString());
        textFields.put("width", mapWidth);

        mapHeight.setText(defaultParameters.WORLD_MAP_HEIGHT.toString());
        textFields.put("height", mapHeight);

        startEnergy.setText(defaultParameters.START_ENERGY.toString());
        textFields.put("startEnergy", startEnergy);

        moveEnergy.setText(defaultParameters.ENERGY_LOST_PER_MOVE.toString());
        textFields.put("moveEnergy", moveEnergy);

        jungleRatio.setText(defaultParameters.JUNGLE_RATIO.toString());
        textFields.put("jungleRatio", jungleRatio);

        stepTime.setText(defaultParameters.STEP_TIME.toString());
        textFields.put("stepTime", stepTime);

        animalsAmount.setText(defaultParameters.ANIMALS_AMOUNT.toString());
        textFields.put("animalsAmount", animalsAmount);
    }

    private void configureLabels() {
        for(String key : labels.keySet()){
            labels.get(key).setLabelFor(textFields.get(key));
        }
    }

    private void preparePanels() {
        for(String key : labels.keySet()){
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.add(labels.get(key));
            panel.add(textFields.get(key));
            panel.setMinimumSize(new Dimension(1,1));
            panels.put(key, panel);
        }
    }

    private void createLayout() {
        JPanel worldSize = new JPanel();
        worldSize.setLayout(new BoxLayout(worldSize, BoxLayout.Y_AXIS));
        worldSize.setBorder(BorderFactory.createTitledBorder("Rozmiary świata"));
        worldSize.add(panels.get("width"));
        worldSize.add(panels.get("height"));
        worldSize.add(panels.get("jungleRatio"));
        add(worldSize);

        JPanel energyParameters = new JPanel();
        energyParameters.setLayout(new BoxLayout(energyParameters, BoxLayout.Y_AXIS));
        energyParameters.setBorder(BorderFactory.createTitledBorder("Energia"));
        energyParameters.add(panels.get("startEnergy"));
        energyParameters.add(panels.get("moveEnergy"));
        add(energyParameters);

        JPanel startParameters = new JPanel();
        startParameters.setLayout(new BoxLayout(startParameters, BoxLayout.Y_AXIS));
        startParameters.setBorder(BorderFactory.createTitledBorder("Parametry początkowe"));
        startParameters.add(panels.get("animalsAmount"));
        startParameters.add(panels.get("stepTime"));
        add(startParameters);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        runButton = new JButton("Rozpocznij symulację");
        runButton.addActionListener(this);
        btnPanel.add(runButton, Component.CENTER_ALIGNMENT);
        add(btnPanel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == runButton){
            Parameters parameters = getParameters();
            renderMap(parameters);
        }
    }

    private void renderMap(Parameters parameters) {
        GuiMapVisualizer map = new GuiMapVisualizer(parameters);
//       map.setLocation(0,0);
        map.startSimulation();
    }

    private Parameters getParameters() {
        Parameters parameters = new Parameters();
        parameters.WORLD_MAP_WIDTH = Integer.valueOf(textFields.get("width").getText());
        parameters.WORLD_MAP_HEIGHT = Integer.valueOf(textFields.get("height").getText());
        parameters.JUNGLE_RATIO = Double.valueOf(textFields.get("jungleRatio").getText());
        parameters.START_ENERGY = Integer.valueOf(textFields.get("startEnergy").getText());
        parameters.ENERGY_LOST_PER_MOVE = Integer.valueOf(textFields.get("moveEnergy").getText());
        parameters.STEP_TIME = Integer.valueOf(textFields.get("stepTime").getText());
        parameters.ANIMALS_AMOUNT = Integer.valueOf(textFields.get("animalsAmount").getText());
        return parameters;
    }
}
