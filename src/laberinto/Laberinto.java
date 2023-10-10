/*
Autor: María José Corredor
 */
package laberinto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Laberinto extends JPanel {
    private final int[][] matrizLaberinto;
    private int filaPersonaje;
    private int columnaPersonaje;
    private final JLabel mensajeLabel;

    public Laberinto() {
        matrizLaberinto = new int[][] {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 1, 1, 0, 1, 0, 1, 1, 0, 1},
            {1, 1, 0, 0, 1, 0, 0, 1, 0, 1},
            {1, 1, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 1, 0, 0, 0, 1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };

        filaPersonaje = 1;
        columnaPersonaje = 1;

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                String comando = "";

                switch (keyCode) {
                    case KeyEvent.VK_P:
                        moverPersonaje(0, 1); // Derecha
                        comando = "Derecha";
                        break;
                    case KeyEvent.VK_L:
                        moverPersonaje(-1, 0); // Arriba
                        comando = "Arriba";
                        break;
                    case KeyEvent.VK_A:
                        moverPersonaje(1, 0); // Abajo
                        comando = "Abajo";
                        break;
                    case KeyEvent.VK_Y:
                        moverPersonaje(0, -1); // Izquierda
                        comando = "Izquierda";
                        break;
                    default:
                        JOptionPane.showMessageDialog(Laberinto.this, "Esta tecla no se puede usar, siga intentando", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        break;
                }

                actualizarMensaje(comando);
                repaint();
            }
        });

        mensajeLabel = new JLabel("Comando: ");
        mensajeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(mensajeLabel, BorderLayout.SOUTH);
    }

    private void moverPersonaje(int filaDelta, int columnaDelta) {
        int nuevaFila = filaPersonaje + filaDelta;
        int nuevaColumna = columnaPersonaje + columnaDelta;

        
        if (esPosicionValida(nuevaFila, nuevaColumna)) {
            filaPersonaje = nuevaFila;
            columnaPersonaje = nuevaColumna;
        }
    }

    private boolean esPosicionValida(int fila, int columna) {
        if (fila >= 0 && fila < matrizLaberinto.length &&
            columna >= 0 && columna < matrizLaberinto[0].length &&
            matrizLaberinto[fila][columna] == 0) {
            return true;
        }
        return false;
    }

    private void actualizarMensaje(String comando) {
        mensajeLabel.setText("Comando: " + comando);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int anchoCelda = getWidth() / matrizLaberinto[0].length;
        int altoCelda = getHeight() / matrizLaberinto.length;

        // Dibujar el laberinto
        for (int fila = 0; fila < matrizLaberinto.length; fila++) {
            for (int columna = 0; columna < matrizLaberinto[0].length; columna++) {
                if (matrizLaberinto[fila][columna] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(columna * anchoCelda, fila * altoCelda, anchoCelda, altoCelda);
                }
            }
        }

        g.setColor(Color.GRAY);
        g.fillOval(columnaPersonaje * anchoCelda, filaPersonaje * altoCelda, anchoCelda, altoCelda);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("play");
            Laberinto laberinto = new Laberinto();
            frame.add(laberinto);
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
