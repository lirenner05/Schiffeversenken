package pack1;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private feld playerField;
    private feld opponentField;
    private JLabel statusLabel;
    private JButton rotationButton; // Button zum Drehen der Schiffe
    
    private logik spiellogik;

    public GUI() {
        spiellogik = new logik();

        setTitle("Schiffe versenken - Battleship");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Statuszeile oben aktualisieren
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(statusLabel, BorderLayout.NORTH);
        updateStatusText(); // Initialisiert den Text für das erste Schiff

        // 2. Container für die zwei Spielfelder
        JPanel fieldContainer = new JPanel(new GridLayout(1, 2, 30, 0));
        fieldContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // In der Setzphase ist DEIN Feld klickbar, das GEGNER-Feld gesperrt
        playerField = new feld("Dein Spielfeld", true, e -> {
            Point koordinaten = (Point) e.getSource();
            handleShipPlacement(koordinaten.x, koordinaten.y);
        });
        
        opponentField = new feld("Gegnerisches Feld (KI)", false, e -> {
            Point koordinaten = (Point) e.getSource();
            handleAttack(koordinaten.x, koordinaten.y);
        });

        fieldContainer.add(playerField);
        fieldContainer.add(opponentField);
        add(fieldContainer, BorderLayout.CENTER);

        // 3. Steuerungs-Panel unten (Süden) für die Ausrichtung
        JPanel southPanel = new JPanel(new FlowLayout());
        rotationButton = new JButton("Ausrichtung: HORIZONTAL");
        rotationButton.setFont(new Font("Arial", Font.PLAIN, 12));
        rotationButton.addActionListener(e -> {
            spiellogik.toggleRichtung();
            if (spiellogik.getIstHorizontal()) {
                rotationButton.setText("Ausrichtung: HORIZONTAL");
            } else {
                rotationButton.setText("Ausrichtung: VERTIKAL");
            }
        });
        southPanel.add(rotationButton);
        add(southPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Hilfsmethode, um den Hinweistext oben anzupassen
    private void updateStatusText() {
        if (!spiellogik.alleSchiffePlatziert()) {
            statusLabel.setText("Setzphase: Platziere ein Schiff der Länge " + spiellogik.getAktuelleSchiffsLaenge());
        } else {
            statusLabel.setText("Kampfphase! Klicke auf das rechte Feld, um die KI anzugreifen.");
        }
    }

    // Verarbeitet die Klicks beim Aufstellen der Schiffe
    private void handleShipPlacement(int r, int c) {
        int laenge = spiellogik.getAktuelleSchiffsLaenge();
        boolean horizontal = spiellogik.getIstHorizontal();

        // Versuche, das Schiff in der Logik einzutragen
        if (spiellogik.platziereSpielerSchiff(r, c)) {
            // Wenn erfolgreich, zeichne das Schiff auf deinem GUI-Feld (Grau)
            for (int i = 0; i < laenge; i++) {
                if (horizontal) {
                    playerField.setZellenFarbe(r, c + i, Color.GRAY);
                } else {
                    playerField.setZellenFarbe(r + i, c, Color.GRAY);
                }
            }

            // Prüfen, ob jetzt alle Schiffe bereitstehen
            if (spiellogik.alleSchiffePlatziert()) {
                starteKampfphase();
            } else {
                updateStatusText();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ungültige Position! Schiff passt dort nicht hin.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Schaltet die Steuerung von Aufstellen auf Kämpfen um
    private void starteKampfphase() {
        updateStatusText();
        rotationButton.setEnabled(false); // Ausrichtungs-Button deaktivieren
        
        // Wichtig: Wir müssten hier streng genommen die Klickbarkeit in der 'feld'-Klasse umschalten.
        // Für den schnellen Übergang erlauben wir Angriffe in handleAttack nur, wenn alle platziert sind.
    }

    // Verarbeitet die Klicks beim Angreifen (Rechtes Feld)
    private void handleAttack(int r, int c) {
        // Angriffe blockieren, solange Schiffe noch gesetzt werden
        if (!spiellogik.alleSchiffePlatziert()) return;

        int result = spiellogik.shootAtOpponent(r, c);

        if (result == 1) { 
            opponentField.setZellenFarbe(r, c, Color.LIGHT_GRAY); 
            statusLabel.setText("Fehlschuss auf Reihe " + (r + 1) + ", Spalte " + (c + 1));
        } else if (result == 2) { 
            opponentField.setZellenFarbe(r, c, Color.RED); 
            statusLabel.setText("TREFFER auf Reihe " + (r + 1) + ", Spalte " + (c + 1) + "!");
        } else {
            statusLabel.setText("Hier hast du schon hingeschossen!");
        }
    }
}