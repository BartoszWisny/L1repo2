package com.javasim.javaapp;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Program do prezentacji dzialania watkow - glowna klasa MyFrame.
 */

public class MyFrame {
   
  public static int blockSize = 40;
    
  /**
   * Glowna funkcja programu wyswietlajaca podstawowe GUI.
   */
  
  public static void main(final String[] args) {
    int rows = Integer.parseInt(JOptionPane.showInputDialog("Rows: "));
    int columns = Integer.parseInt(JOptionPane.showInputDialog("Columns: "));
    int time = Integer.parseInt(JOptionPane.showInputDialog("Time: "));
    double probability = Double.parseDouble(JOptionPane.showInputDialog("Probability: "));

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Random Colors");
    frame.setSize(blockSize * columns + 14, blockSize * rows + 37);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);

    MyRectangle rect = new MyRectangle(rows, columns, time, probability);
    frame.add(rect);

    frame.setVisible(true);

    rect.start();
  }
}