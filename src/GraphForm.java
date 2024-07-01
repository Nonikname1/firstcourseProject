import logic.Algorithm;
import logic.Paint;
import logic.Point;
import util.JTableUtils;
import util.SwingUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.List;
import java.awt.*;

public class GraphForm extends JFrame {
    private JFileChooser fileChooserOpen;
    private JPanel mainPanel;
    private JButton buttonProcess;
    private JSpinner spinnerMutation;
    private JSpinner spinnerGenerations;
    private JPanel panelPaintArea;
    private JLabel lenth;
    private JSpinner spinnerNumberOfPopulation;
    private JButton buttonLoadInputFromFile;
    private JLabel time;

    private JPanel paintPanel;

    private List<Point> solution = null;
    private List<Point> points = null;

    public GraphForm() {
        this.setTitle("FrameMain");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        this.pack();
//        scrollPane.setPreferredSize(new Dimension(1000,1000));
//        this.pack();
        fileChooserOpen = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        spinnerMutation.setValue(50);
        spinnerGenerations.setValue(100);
        spinnerNumberOfPopulation.setValue(100);

        buttonProcess.addActionListener(actionEvent -> {
            long srartTime = System.nanoTime();
            solution = Algorithm.findPath((int)spinnerGenerations.getValue(), (int) spinnerMutation.getValue(), (int) spinnerNumberOfPopulation.getValue(), points);
            long endTime = System.nanoTime();
            double duration = (double) ((endTime - srartTime)/1_000_000.0);
            paintPanel.repaint();
            lenth.setText("  Длинна пути: " + Algorithm.getFinalLength(solution));
            time.setText("  Время работы: " + duration + " мс");
        });

        buttonLoadInputFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (fileChooserOpen.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
                        points = JTableUtils.readPointsFromFile(fileChooserOpen.getSelectedFile().getPath());
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        paintPanel = new JPanel() {
            private Dimension paintSize = new Dimension(0, 0);

            @Override
            public void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                Graphics2D gr2 = (Graphics2D) gr;
                if (solution != null && solution.size() > 1) {
                    AffineTransform at = gr2.getTransform();
//                    gr2.translate(100, -100);
//                    gr2.scale( paintPanel.getWidth() / 500.0, paintPanel.getHeight() / 300.0);
                    Paint.paint(gr2, solution, paintPanel.getWidth(), paintPanel.getHeight());
                    gr2.setTransform(at);
                }
            }
        };
//        paintPanel.setMinimumSize(new Dimension(2000, 2000));
        JScrollPane paintJScrollPane = new JScrollPane(paintPanel);
//        SwingUtils.setFixedSize(paintPanel, 2000, 2000);
        panelPaintArea.add(paintJScrollPane);

        /*
        minX, minY, maxX, maxY
        b = 20
        w, h
        xk = (w - 2 * b) / (maxX - minX)
        yk = (h - 2 * b) / (maxY - minY)
        k = min(xk, yk)

        xx = b + (x - x0) * k
        yy = b + (y - y0) * k

         */

        setVisible(true);
    }
}
