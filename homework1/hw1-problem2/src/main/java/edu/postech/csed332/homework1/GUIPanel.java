package edu.postech.csed332.homework1;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A GUI panel that shows the current status of the game board
 * NOTE: do not modify this file!
 */
public class GUIPanel extends JPanel {
    private static final int unitSize = 64;
    private static final Image imgAirTow = getImage("airTower.png");
    private static final Image imgGrdTow = getImage("groundTower.png");
    private static final Image imgSlot = getImage("slot.png");
    private static final Image imgGround = getImage("ground.png");
    private static final Image imgAirMob = getImage("airMob.png");
    private static final Image imgGrdMob = getImage("groundMob.png");

    private final GameBoard board;

    public GUIPanel(GameBoard board) {
        this.board = board;
        var dim = new Dimension(unitSize * board.getWidth(), unitSize * board.getHeight());
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        paintGround(graphics);
        paintGoal(graphics);
        paintTowers(graphics);
        paintUnits(graphics);
    }

    private void paintGround(Graphics graphics) {
        for (var i = 0; i < board.getWidth(); ++i)
            for (var j = 0; j < board.getHeight(); ++j)
                graphics.drawImage(imgGround, i * unitSize, j * unitSize, null);
    }

    private void paintGoal(Graphics graphics) {
        for (var i = 0; i < board.getHeight(); ++i)
            paintItem(graphics, new Position(board.getWidth() - 1, i), imgSlot);
    }

    private void paintTowers(Graphics graphics) {
        for (var tower : board.getTowers()) {
            if (tower instanceof GroundTower)
                paintItem(graphics, tower.getPosition(), imgGrdTow);
            else if (tower instanceof AirTower)
                paintItem(graphics, tower.getPosition(), imgAirTow);
        }
    }

    private void paintUnits(Graphics graphics) {
        for (var mob : board.getMonsters()) {
            if (mob instanceof GroundMob)
                paintItem(graphics, mob.getPosition(), imgGrdMob);
            if (mob instanceof AirMob)
                paintItem(graphics, mob.getPosition(), imgAirMob);
        }
    }

    private void paintItem(Graphics graphics, Position p, Image img) {
        graphics.drawImage(img, p.x() * unitSize, p.y() * unitSize, null);
    }

    private static Image getImage(String filename) {
        return new ImageIcon(Objects.requireNonNull(GUIPanel.class.getResource("/" + filename))).getImage();
    }
}
