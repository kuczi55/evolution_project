package agh.cs.evolution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class GuiMapDraw extends JPanel implements MouseMotionListener, MouseListener {
    private Parameters parameters;
    private Integer initEnergy;
    private final WorldMap map;
    private final GuiMapVisualizer guiVisualizer;
    private int widthScale;
    private int heightScale;
    private Animal chosen;
    private boolean showDominators = false;


    GuiMapDraw(WorldMap map, GuiMapVisualizer guiVisualizer, Parameters parameters) {
        this.parameters = parameters;
        this.initEnergy = this.parameters.START_ENERGY;
        this.map = map;
        this.guiVisualizer = guiVisualizer;
        addMouseMotionListener(this);
        addMouseListener(this);
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        this.widthScale = getWidth() / (parameters.WORLD_MAP_WIDTH);
        this.heightScale = getHeight() / (parameters.WORLD_MAP_HEIGHT);
        this.setSize(widthScale * (parameters.WORLD_MAP_WIDTH), heightScale * (parameters.WORLD_MAP_HEIGHT));

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.scale(1.0,-1.0);
        graphics2D.translate(0,-getHeight());
        graphics.setColor(new Color(190, 154, 47));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(new Color(54, 240, 0));
        graphics.fillRect(this.map.jungleMap.mapStart.x*widthScale,
                this.map.jungleMap.mapStart.y*heightScale,
                (this.map.jungleMap.mapEnd.x-this.map.jungleMap.mapStart.x+1)*widthScale,
                (this.map.jungleMap.mapEnd.y-this.map.jungleMap.mapStart.y+1)*heightScale);
        for(int i = 0; i < this.parameters.WORLD_MAP_WIDTH; i++){
            for(int j = 0; j < this.parameters.WORLD_MAP_HEIGHT; j++){
                Object obj = this.map.objectAt(new Vector2d(i, j));
                if(obj instanceof Animal) {
                    Animal ani = (Animal) obj;
                    Integer energy = ani.getEnergy();
                    if(energy < 0.25 * initEnergy) graphics.setColor(new Color(100, 0, 0));
                    else if(energy < 0.75 * initEnergy) graphics.setColor(new Color(200, 200, 0));
                    else graphics.setColor(new Color(0, 0, 100));
                    Statistics statistics = new Statistics(this.map);
                    Genotype dominator = statistics.getDominatingGenotype();
                    if(showDominators) {
                        if(Arrays.equals(ani.getGenotype().getGenotype(), dominator.getGenotype())) {
                            graphics.setColor(new Color(197,36,255));
                        }
                    }
                    graphics.fillOval(i*widthScale, j*heightScale, widthScale, heightScale);
                }
                else if(obj instanceof Grass){
                    graphics.setColor(new Color(15, 154, 0));
                    graphics.fillRect(i*widthScale, j*heightScale, widthScale, heightScale);
                }
                else if(obj != null) {
                    graphics.setColor(new Color(0, 0, 70));
                    if(showDominators) {
                        Animal ani = this.map.getStrongest(new Vector2d(i,j));
                        Statistics statistics = new Statistics(this.map);
                        Genotype dominator = statistics.getDominatingGenotype();
                        if(Arrays.equals(ani.getGenotype().getGenotype(), dominator.getGenotype())) {
                            graphics.setColor(new Color(197,36,255));
                        }
                    }
                    graphics.fillRect(i*widthScale, j*heightScale, widthScale, heightScale);
                }
            }
        }
        if(chosen != null){

            graphics.setColor(new Color(0,130,170));
            graphics.fillOval(chosen.getPosition().x * widthScale, chosen.getPosition().y * heightScale, widthScale, heightScale);
            if(chosen.getEnergy() <= 0){
                guiVisualizer.stopSimulation();
                this.guiVisualizer.setDead();

                chosen = null;
            }
        }
    }

    private Vector2d getCursorCoords(MouseEvent event){
        int x = event.getX();
        int y = getHeight() - event.getY();
        return new Vector2d(x/widthScale, y/heightScale);

    }

    public void showingDominators() {
        this.showDominators = !this.showDominators;
        this.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent event) {
        if(guiVisualizer.isRunning()){
            return;
        }

        guiVisualizer.choosingAnimal(getCursorCoords(event));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(guiVisualizer.isRunning()){
            return;
        }
        chosen = map.getStrongest(getCursorCoords(e));
        if(chosen != null){
            guiVisualizer.chooseAnimal(chosen);
            this.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
