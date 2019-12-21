package agh.cs.evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiMapVisualizer extends JFrame implements ActionListener {
    private Parameters parameters;
    private final WorldMap map;
    private GridBagConstraints constraint = new GridBagConstraints();
    private double width= 1200;
    private double height = 700;
    private final GuiMapDraw guiMapDraw;
    public final Timer timer;
    private GuiStatistics statisticsPanel;


    GuiMapVisualizer(Parameters parameters) {
        this.parameters = parameters;
        Vector2d savannaSize = new Vector2d(parameters.WORLD_MAP_WIDTH-1, parameters.WORLD_MAP_HEIGHT-1);
        Vector2d jungleSize = new Vector2d
                (Math.toIntExact(Math.round(Double.valueOf(parameters.WORLD_MAP_WIDTH) * parameters.JUNGLE_RATIO))-1,
                        Math.toIntExact(Math.round(Double.valueOf(parameters.WORLD_MAP_HEIGHT) * parameters.JUNGLE_RATIO))-1);

        this.map = new WorldMap(savannaSize, jungleSize, this.parameters);

        this.timer = new Timer(parameters.STEP_TIME, this);

        getContentPane().setPreferredSize(new Dimension((int) width, (int) height));

        this.guiMapDraw = new GuiMapDraw(map, this, this.parameters);
        this.statisticsPanel = new GuiStatistics(map, this);

        GridBagLayout gridLayout = new GridBagLayout();
        setLayout(gridLayout);
        gridLayout.columnWeights = new double[]{0.7, 0.3};
        addSection(guiMapDraw, 0, 1);
        addSection(statisticsPanel, 1, 1);
        pack();
    }

    public void addSection(JPanel panel, int gridx, int gridwidth) {
        constraint.gridx = gridx;
        constraint.gridy = 0;
        constraint.gridwidth = gridwidth;
        constraint.weighty = 1.0;
        constraint.weightx = 0.0;
        constraint.fill = GridBagConstraints.BOTH;
        add(panel, constraint);
    }

    public void startSimulation() {
        setVisible(true);
        this.timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        statisticsPanel.repaint();
        guiMapDraw.repaint();

        map.run();
        statisticsPanel.era++;
    }

    public boolean isRunning() {
        if(this.timer.isRunning()) return true;
        return false;
    }

    public void choosingAnimal(Vector2d possiblePosition) {
        this.statisticsPanel.choosingAnimal(possiblePosition);
    }

    public void chooseAnimal(Animal animal) {
        this.statisticsPanel.chooseAnimal(animal);
    }

    public void stopSimulation() {
        this.timer.stop();
    }

    public void showDominators() {
        this.guiMapDraw.showingDominators();
    }

    public void setDead() {
        this.statisticsPanel.setDead();
    }

}
