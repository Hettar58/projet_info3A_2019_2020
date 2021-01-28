package dijkstra.UI;

import dijkstra.Dijkstra;
import dijkstra.Main;
import static dijkstra.CONFIG.*;
import javax.swing.*;

public class CommandUI extends JDialog {
    Dijkstra dijkstra;

    private javax.swing.JButton ActivateGeneratorButton;
    private javax.swing.JTextField ObstaclesTF;
    private javax.swing.JTextField PointsTF;
    private javax.swing.JTextField R_TF;

    public CommandUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        dijkstra = Dijkstra.instance;
        PointsTF.setText(""+ nbPoints);
        ObstaclesTF.setText(""+ nbObstacles);
        R_TF.setText("" + R);
        this.setSize(470, 250);
    }

    private void initComponents() {

        JPanel north = new JPanel();
        JLabel jLabel1 = new JLabel();
        JPanel jPanel1 = new JPanel();
        JLabel pointsLabel = new JLabel();
        PointsTF = new javax.swing.JTextField();
        ActivateGeneratorButton = new javax.swing.JButton();
        JLabel obstaclesLabel = new JLabel();
        ObstaclesTF = new javax.swing.JTextField();
        JButton applyDijkstraButton = new JButton();
        JLabel jLabel2 = new JLabel();
        R_TF = new javax.swing.JTextField();
        JButton resetButton = new JButton();
        JPanel jPanel2 = new JPanel();
        JButton quitButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Commandes dijkstra");
        north.add(jLabel1);

        getContentPane().add(north, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridLayout(3, 3));

        pointsLabel.setText("Points");
        jPanel1.add(pointsLabel);

        PointsTF.setText("0");
        jPanel1.add(PointsTF);

        ActivateGeneratorButton.setText("Générer un graphe");
        ActivateGeneratorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivateGeneratorButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ActivateGeneratorButton);

        obstaclesLabel.setText("Obstacles");
        jPanel1.add(obstaclesLabel);

        ObstaclesTF.setText("0");
        jPanel1.add(ObstaclesTF);

        applyDijkstraButton.setText("Appliquer Dijkstra");
        applyDijkstraButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyDijkstraButtonActionPerformed(evt);
            }
        });
        jPanel1.add(applyDijkstraButton);

        jLabel2.setText("R");
        jPanel1.add(jLabel2);

        R_TF.setText("0");
        jPanel1.add(R_TF);

        resetButton.setText("Remettre a zéro");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });
        jPanel1.add(resetButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        quitButton.setText("Quitter");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitButtonActionPerformed(evt);
            }
        });
        jPanel2.add(quitButton);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>

    private void ApplyDijkstraButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dijkstra.applyDijkstra();
        ActivateGeneratorButton.setEnabled(false);
        PointsTF.setEnabled(false);
        ObstaclesTF.setEnabled(false);
        R_TF.setEnabled(false);

    }

    private void ActivateGeneratorButtonActionPerformed(java.awt.event.ActionEvent evt) {
        nbPoints = Integer.parseInt(PointsTF.getText());
        nbObstacles = Integer.parseInt(ObstaclesTF.getText());
        R = Integer.parseInt(R_TF.getText());
        dijkstra.init();
    }

    private void QuitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        Main.quit();
    }

    private void ResetButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dijkstra.reset();
        ActivateGeneratorButton.setEnabled(true);
        PointsTF.setEnabled(true);
        ObstaclesTF.setEnabled(true);
        R_TF.setEnabled(true);
    }

}
