package com.javasim.javaapp;

import java.awt.GridLayout;
import java.awt.Panel;
import java.util.Random;

/**
 * Klasa MyRectangle odpowiada za wygenerowanie planszy i uruchamia watki.
 */

public class MyRectangle extends Panel {
  private static final long serialVersionUID = 1L;
  public int rows;
  public int columns;
  public int time;
  public double probability;
  public Random random;
  public Square[][] squares;

  /**
   * Konstruktor klasy MyRectangle generuje kolorowe kwadraty w oknie aplikacji.
   */
  
  public MyRectangle(int rows, int columns, int time, double probability) {
    super();

    this.rows = rows;
    this.columns = columns;
    this.time = time;
    this.probability = probability;

    setLayout(new GridLayout(rows, columns));
    setSize(MyFrame.blockSize * columns, MyFrame.blockSize * rows);

    random = new Random();
    Square.panel = this;
    squares = new Square[rows][columns];

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        squares[i][j] = new Square(this, random, squares, i, j, rows, columns, time, probability);
      }
    }
  }
  
  /**
   * Funkcja start uruchamia watki.
   */

  public synchronized void start() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        squares[i][j].start();
      }
    }
  }
}