package pack1;

public class logik {
    // 0 = Wasser, 1 = Schiff, 2 = Fehlschuss, 3 = Treffer
    private int[][] playerBoard = new int[10][10];
    private int[][] opponentBoard = new int[10][10];

    public logik() {
        // Zum Start platzieren wir testweise ein paar Schiffe fest im Code,
        // damit wir prüfen können, ob Treffer richtig erkannt werden.
        
        // Platziere ein kleines Schiff beim Spieler (Reihe 2, Spalte 3 und 4)
        playerBoard[2][3] = 1;
        playerBoard[2][4] = 1;

        // Platziere ein kleines Schiff beim Gegner (Reihe 5, Spalte 5 und 6)
        opponentBoard[5][5] = 1;
        opponentBoard[5][6] = 1;
    }

    /**
     * Verarbeitet einen Schuss auf das gegnerische Feld.
     * @return 1 für Fehlschuss, 2 für Treffer, 0 wenn bereits beschossen
     */
    public int shootAtOpponent(int row, int col) {
        if (opponentBoard[row][col] == 0) {
            opponentBoard[row][col] = 2; // Fehlschuss merken
            return 1; 
        } else if (opponentBoard[row][col] == 1) {
            opponentBoard[row][col] = 3; // Treffer merken
            return 2;
        }
        return 0; // Feld wurde schon mal beschossen
    }

    /**
     * Verarbeitet den Schuss der KI auf das Spieler-Feld.
     */
    public int shootAtPlayer(int row, int col) {
        if (playerBoard[row][col] == 0) {
            playerBoard[row][col] = 2;
            return 1; // Fehlschuss
        } else if (playerBoard[row][col] == 1) {
            playerBoard[row][col] = 3;
            return 2; // Treffer
        }
        return 0;
    }
}