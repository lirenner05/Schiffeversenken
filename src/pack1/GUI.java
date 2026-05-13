package pack1;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private feld playerField;
    private feld opponentField;
    private JLabel statusLabel;
    
    // Das Gehirn wird in die GUI eingebunden
    private logik spiellogik;

    public GUI() {

        spiellogik = new logik();

        setTitle("Schiffe versenken - Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        statusLabel = new JLabel("Finde die gegnerischen Schiffe! Tipp: Schieße auf Reihe 6, Spalte 6.", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(statusLabel, BorderLayout.NORTH);

        JPanel fieldContainer = new JPanel(new GridLayout(1, 2, 30, 0));
        fieldContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        playerField = new feld("Dein Spielfeld", false, null);
        opponentField = new feld("Gegnerisches Feld (KI)", true, e -> {
            Point koordinaten = (Point) e.getSource();
            handleAttack(koordinaten.x, koordinaten.y);
        });

        fieldContainer.add(playerField);
        fieldContainer.add(opponentField);
        add(fieldContainer, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleAttack(int r, int c) {
        // Schuss in der Logik auswerten
        int result = spiellogik.shootAtOpponent(r, c);

        if (result == 1) { // Fehlschuss
            opponentField.setZellenFarbe(r, c, Color.LIGHT_GRAY); // Wasser spritzt auf
            statusLabel.setText("Fehlschuss auf Reihe " + (r + 1) + ", Spalte " + (c + 1));
        } else if (result == 2) { // Treffer
            opponentField.setZellenFarbe(r, c, Color.RED); // Schiff brennt
            statusLabel.setText("TREFFER auf Reihe " + (r + 1) + ", Spalte " + (c + 1) + "!");
        } else {
            statusLabel.setText("Hier hast du schon hingeschossen!");
        }
    }
}