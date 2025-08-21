import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel {
    private static final int WIDTH = 1800;
    private static final int HEIGHT = 1200;

    private final java.util.List<Body> bodies = Arrays.asList(
            new Body.Builder(1000).posX(400).posY(300).speedX(1).build(),
            new Body.Builder(1).posX(900).posY(800).speedX(-10).build(),
            new Body.Builder(20).posX(100).posY(800).speedX(-10).build()
    );

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        final int bodyDiameter = 20;

        for (final Body body : bodies) {
            g.setColor(Color.WHITE);
            g.fillOval((int) body.getPosX(), (int) body.getPosY(), bodyDiameter, bodyDiameter);
        }
    }

    public void animate() {
        Timer timer = new Timer(0, _ -> {
            updateBodyPositions();
            repaint();
        });

        timer.start();
    }

    public void updateBodyPositions() {
        Map<Body, List<Force>> bodyToForces = new HashMap<>();

        for (final Body b1 : bodies) {
            for (final Body b2 : bodies) {
                if (b1 == b2) continue;
                final Force force = b1.gravitationalForceFrom(b2);
                bodyToForces.computeIfAbsent(b1, _ -> new ArrayList<>()).add(force);
            }
        }

        final double timeInterval = 0.1;

        for (final Body b : bodies) {
            b.move(bodyToForces.get(b), timeInterval);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("3-Body Problem");
        Main simulationPanel = new Main();
        simulationPanel.setBackground(Color.BLACK);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(simulationPanel);
        frame.setVisible(true);

        simulationPanel.animate();
    }
}