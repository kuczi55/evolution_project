package agh.cs.evolution;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class GuiStatistics extends JPanel implements ActionListener{

    public WorldMap map;
    public GuiMapVisualizer simulation;
    public Integer era;
    public Animal animal;
    private Statistics statistics;
    private Animal choosing = null;
    private Animal chosen = null;
    private boolean isDead = false;

    public GuiStatistics(WorldMap map, GuiMapVisualizer simulation) {
        this.map = map;
        this.statistics = new Statistics(map);
        this.simulation = simulation;
        era = 0;
        setLayout(new FlowLayout());
        JButton pauseButton = new JButton("Start/Stop");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(simulation.timer.isRunning()) simulation.timer.stop();
                else {
                        if(chosen != null && chosen.getEnergy() <= 0) chosen = null;
                        choosing = null;
                        simulation.timer.start();
                }
            }
        });
        this.add(pauseButton);

        JButton dominatorsButton = new JButton("Dominatorzy");
        dominatorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                simulation.showDominators();
            }
        });
        this.add(dominatorsButton);

        JButton statisticsButton = new JButton("Zapisz statystyki");
        statisticsButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                chooser.setMultiSelectionEnabled(false);

                int dialog = chooser.showSaveDialog(null);

                if (dialog == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = chooser.getSelectedFile();

                    try(BufferedWriter writer = new BufferedWriter(new FileWriter(chosenFile.getAbsolutePath()))){
                        statistics.saveStats(writer);
                        JOptionPane.showMessageDialog(null, "Pomyślnie zapisano statystyki", "Ewolucja", 1);
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Błąd zapisu", "Ewolucja", 1);
                    }
                }
            }
        }));
        this.add(statisticsButton);
    }

    public void choosingAnimal(Vector2d possiblePosition) {
        this.choosing = this.map.getStrongest(possiblePosition);
        this.repaint();
    }

    public void chooseAnimal(Animal choose) {
        this.chosen = choose;
        this.isDead = false;
        this.repaint();
    }

    public void setDead() {
        this.isDead = true;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setSize(getWidth(), simulation.getHeight());

        g.drawString("Symulacja trwa już: " + era + " epok", 10, 60) ;

        g.drawString("Ilość zwierząt: " + map.animalsMap.size(), 10, 85) ;

        g.drawString("Ilość trawy: " + map.grasses.size(), 10, 110) ;

        g.drawString("Średnia energia: " + String.format("%.2f", statistics.getAvgEne()) , 10, 135);

        g.drawString("Średnia ilość dzieci: " + String.format("%.2f", statistics.getAvgChildren()) , 10, 160);

        g.drawString("Średnia długość życia: " + String.format("%.2f", statistics.getAvgAge()) + " epok", 10, 185);

        if(this.statistics.getDominatingGenotype() != null) {
            Genotype dominatingGenotype = this.statistics.getDominatingGenotype();
            g.drawString("Dominujący genotyp: ", 10, 210);
            g.drawString(Arrays.toString(dominatingGenotype.getGenotype()), 10, 225);
        }

        if(this.choosing != null) {
            Genotype gen = this.choosing.getGenotype();
            g.drawString("Genotyp wskazywanego zwierzęcia: ", 10, 275);
            g.drawString(Arrays.toString(gen.getGenotype()), 10, 290);
        }

        if(this.chosen != null) {
            Genotype gen = this.chosen.getGenotype();
            g.drawString("Genotyp wybranego zwierzęcia: ", 10, 340);
            g.drawString(Arrays.toString(gen.getGenotype()), 10, 355);
            g.drawString("Ilość dzieci: " + this.chosen.getChildren(), 10, 380);
            g.drawString("Ilość potomków: " + this.chosen.getPosterityAmount(), 10, 405);
            g.drawString("Wiek zwierzęcia: " + this.chosen.getAge() + " epok", 10, 430);
            g.drawString("Energia zwierzęcia: " + this.chosen.getEnergy(), 10, 455);
            if(isDead) {
                g.drawString("Zwierzę zmarło w: " + era + " epoce", 10, 480);
            }
        }

        if(this.statistics.getEra() != this.era) {
            this.statistics.addStat(this.era);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.choosing != null) this.repaint();

    }

}
