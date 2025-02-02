import ia.framework.common.ArgParse;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.GameEngine;
import ia.framework.jeux.Player;

import ia.problemes.MnkGame;
import ia.problemes.TicTacToe;
import ia.problemes.ConnectFour;

import java.io.FileWriter;
import java.io.IOException;

public class LancerEtudeJeux {

    public static void main(String[] args) throws IOException {
        // Paramètres de l’étude
        int[] taillesPlateau = {3, 5, 7, 10};  // Différentes tailles de jeu
        int[] streaks = {3, 4, 5};             // Nombre de jetons nécessaires pour gagner

        // Fichier CSV pour enregistrer les résultats
        String fileName = "docs/resultEtudeJeux.csv";
        FileWriter writer = new FileWriter(fileName, true);
        writer.write("Jeu;Taille;Streak;Joueur1;Joueur2;Gagnant;TempsExecution;TotalCoups\n");

        // Jeux à tester
        String[] jeux = {"tictactoe","mnk", "connect4"};

        // Stratégies des joueurs
        String[] strategies = {"random", "minmax", "alphabeta"};

        for (String jeu : jeux) {
            for (int taille : taillesPlateau) {
                for (int streak : streaks) {
                    for (String p1_type : strategies) {
                        for (String p2_type : strategies) {
                            if (jeu.equals("tictactoe") && taille != 3) continue; // Tic-Tac-Toe est toujours en 3x3
                            if (jeu.equals("connect4") && (taille != 6 || streak != 4)) continue; // Connect4 est 6x7 avec 4 en ligne

                            // Créer le jeu
                            Game game;
                            if (jeu.equals("tictactoe")) {
                                game = new TicTacToe();
                            } else if (jeu.equals("connect4")) {
                                game = new ConnectFour();
                            } else {
                                game = new MnkGame(taille, taille, streak);
                            }

                            // Créer les joueurs
                            Player p1 = ArgParse.makePlayer(p1_type, game, true, args);
                            Player p2 = ArgParse.makePlayer(p2_type, game, false, args);

                            // Lancer la partie
                            GameEngine gameEngine = new GameEngine(game, p1, p2);
                            long startTime = System.currentTimeMillis();
                            GameState endGame = gameEngine.gameLoop();
                            long totalTime = System.currentTimeMillis() - startTime;

                            // Résultats
                            Player winner = gameEngine.getWinner(endGame);
                            String winnerName = (winner != null) ? winner.getName() : "Draw";
                            int totalMoves = gameEngine.getTotalMoves();

                            // Enregistrer dans le fichier
                            writer.write(jeu + ";" + taille + ";" + streak + ";" + p1_type + ";" + p2_type + ";" +
                                    winnerName + ";" + totalTime + ";" + totalMoves + "\n");

                            System.out.println("Jeu: " + jeu + ", Taille: " + taille + "x" + taille +
                                    ", Streak: " + streak + ", P1: " + p1_type + ", P2: " + p2_type +
                                    ", Gagnant: " + winnerName + ", Temps: " + totalTime + "ms, Coups: " + totalMoves);
                        }
                    }
                }
            }
        }

        writer.close();
        System.out.println("Étude terminée, résultats enregistrés dans " + fileName);
    }
}
