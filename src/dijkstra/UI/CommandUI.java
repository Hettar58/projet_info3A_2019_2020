package dijkstra.UI;

import dijkstra.Dijkstra;
import dijkstra.Main;
import javax.swing.*;

public class CommandUI extends JDialog {
    Dijkstra dijkstra;
    Main main;

    private javax.swing.JButton ActivateGeneratorButton;
    private javax.swing.JButton ApplyDijkstraButton;
    private javax.swing.JPanel North;
    private javax.swing.JLabel ObstaclesLabel;
    private javax.swing.JTextField ObstaclesTF;
    private javax.swing.JLabel PointsLabel;
    private javax.swing.JTextField PointsTF;
    private javax.swing.JButton QuitButton;
    private javax.swing.JTextField R_TF;
    private javax.swing.JButton ResetButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;

    public CommandUI(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        dijkstra = Dijkstra.instance;
        PointsTF.setText(""+ dijkstra.nbPoints);
        ObstaclesTF.setText(""+ dijkstra.nbObstacles);
        R_TF.setText("" + dijkstra.R);
        this.setSize(470, 250);
    }

    private void initComponents() {

        North = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        PointsLabel = new javax.swing.JLabel();
        PointsTF = new javax.swing.JTextField();
        ActivateGeneratorButton = new javax.swing.JButton();
        ObstaclesLabel = new javax.swing.JLabel();
        ObstaclesTF = new javax.swing.JTextField();
        ApplyDijkstraButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        R_TF = new javax.swing.JTextField();
        ResetButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        QuitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Commandes dijkstra");
        North.add(jLabel1);

        getContentPane().add(North, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridLayout(3, 3));

        PointsLabel.setText("Points");
        jPanel1.add(PointsLabel);

        PointsTF.setText("0");
        jPanel1.add(PointsTF);

        ActivateGeneratorButton.setText("Générer un graphe");
        ActivateGeneratorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActivateGeneratorButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ActivateGeneratorButton);

        ObstaclesLabel.setText("Obstacles");
        jPanel1.add(ObstaclesLabel);

        ObstaclesTF.setText("0");
        jPanel1.add(ObstaclesTF);

        ApplyDijkstraButton.setText("Appliquer Dijkstra");
        ApplyDijkstraButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyDijkstraButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ApplyDijkstraButton);

        jLabel2.setText("R");
        jPanel1.add(jLabel2);

        R_TF.setText("0");
        jPanel1.add(R_TF);

        ResetButton.setText("Remettre a zéro");
        ResetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetButtonActionPerformed(evt);
            }
        });
        jPanel1.add(ResetButton);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        QuitButton.setText("Quitter");
        QuitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitButtonActionPerformed(evt);
            }
        });
        jPanel2.add(QuitButton);

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
        dijkstra.nbPoints = Integer.parseInt(PointsTF.getText());
        dijkstra.nbObstacles = Integer.parseInt(ObstaclesTF.getText());
        dijkstra.R = Integer.parseInt(R_TF.getText());
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
