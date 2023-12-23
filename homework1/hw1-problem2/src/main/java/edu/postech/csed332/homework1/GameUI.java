package edu.postech.csed332.homework1;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.Random;

/**
 * A Game UI that contains a message board, a GUI panel, and two
 * control buttons (next, randomize).
 * NOTE: do not modify this file!
 */
public class GameUI {
    private final static int width = 24;
    private final static int height = 12;

    private GUIPanel gamePanel;
    private JButton nextButton;
    private JLabel message;

    GameUI(GameBoard board) {
        createGUI(board);
        randomizeGame(board);
        updateUI(board);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoardImpl(width, height);
            new GameUI(board);
        });
    }

    private void createGUI(GameBoard board) {
        var top = new JFrame("Problem 2");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridBagLayout());

        var gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        message = new JLabel();
        message.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 0;
        gbc.ipady = 30;
        top.add(message, gbc);

        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            board.step();
            updateUI(board);
        });
        gbc.gridy = 1;
        gbc.ipady = 0;
        top.add(nextButton, gbc);

        var initButton = new JButton("Randomize");
        initButton.addActionListener(e -> {
            randomizeGame(board);
            updateUI(board);
        });
        gbc.gridy = 2;
        top.add(initButton, gbc);

        gamePanel = new GUIPanel(board);
        gbc.gridy = 3;
        top.add(gamePanel, gbc);

        top.pack();
        top.setVisible(true);
    }

    private void updateUI(GameBoard board) {
        gamePanel.repaint();
        if (!board.isValidBoard()) {
            message.setForeground(Color.RED);
            message.setText("Invariants are violated!");
            nextButton.setEnabled(false);
        } else if (board.getNumMobs() > 0) {
            message.setForeground(Color.BLACK);
            message.setText(String.format("# Monsters : %d,    # Escaped : %d,    Killed : %d,    Total : %d",
                    board.getNumMobs(), board.getNumMobsEscaped(), board.getNumMobsKilled(),
                    board.getNumMobs() + board.getNumMobsEscaped() + board.getNumMobsKilled()));
            nextButton.setEnabled(true);
        } else {
            message.setForeground(Color.BLUE);
            message.setText(String.format("No monsters!    (%d escaped, %d killed)",
                    board.getNumMobsEscaped(), board.getNumMobsKilled()));
            nextButton.setEnabled(false);
        }
    }

    private void randomizeGame(GameBoard board) {
        final var total = board.getWidth() * board.getHeight() / 5;
        var rand = new Random();

        var positions = new HashSet<Position>(total);
        while (positions.size() < total) {
            var randomPos = new Position(rand.nextInt(board.getWidth()), rand.nextInt(board.getHeight()));
            if (randomPos.x() < board.getWidth() - 2)
                positions.add(randomPos);
        }

        board.clear();
        for (var pos : positions) {
            var unit = switch (rand.nextInt(8)) {
                case 0 -> new AirTower(board);
                case 1, 2 -> new GroundTower(board);
                case 3, 4 -> new AirMob(board);
                default -> new GroundMob(board);
            };
            board.placeUnit(unit, pos);
        }
    }

}
