import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Practica09MendozaReyesAngelEmanuel {
    private static Timer temporizadorHorario;
    private static Timer temporizadorAntihorario;
    private static double[] angulosHorario;
    private static double[] angulosAntihorario;
    private static Random aleatorio = new Random();

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Practica 09 - Mendoza Reyes Angel Emanuel");
        ventana.setSize(800, 600);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(null);

        JLayeredPane panelCapas = new JLayeredPane();
        panelCapas.setBounds(0, 0, 800, 600);
        ventana.add(panelCapas);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                ImageIcon icono = new ImageIcon("fondo.jpg");
                g2d.drawImage(icono.getImage(), 0, 0, getWidth(), getHeight(), this);

                int diametroCirculo = 500;
                int xCirculo = (getWidth() - diametroCirculo) / 2;
                int yCirculo = (getHeight() - diametroCirculo) / 2;

                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setClip(new Ellipse2D.Float(xCirculo, yCirculo, diametroCirculo, diametroCirculo));
                g2d.drawImage(icono.getImage(), 0, 0, getWidth(), getHeight(), this);

                int numAnillos = 4;
                int[] figurasPorAnillo = {12, 12, 24, 24};
                int espacioAnillo = 60;
                int[] colores = {Color.RED.getRGB(), Color.GREEN.getRGB(), Color.BLUE.getRGB(), Color.YELLOW.getRGB()};

                int indiceAngulo = 0;
                for (int anillo = 0; anillo < numAnillos; anillo++) {
                    double pasoAngulo = 2 * Math.PI / figurasPorAnillo[anillo];
                    int radio = (anillo + 1) * espacioAnillo;
                    for (int i = 0; i < figurasPorAnillo[anillo]; i++) {
                        double angulo = angulosHorario[indiceAngulo++];
                        int xFigura = (int) (xCirculo + diametroCirculo / 2 + radio * Math.cos(angulo) - espacioAnillo / 2);
                        int yFigura = (int) (yCirculo + diametroCirculo / 2 + radio * Math.sin(angulo) - espacioAnillo / 2);

                        Color colorBase = new Color(colores[anillo % colores.length]);
                        GradientPaint gradiente = new GradientPaint(xFigura, yFigura, colorBase.brighter(), xFigura + espacioAnillo, yFigura + espacioAnillo, colorBase.darker());

                        g2d.setPaint(gradiente);

                        AffineTransform transformacionOriginal = g2d.getTransform();
                        g2d.translate(xFigura + espacioAnillo / 2, yFigura + espacioAnillo / 2);
                        g2d.rotate(Math.toRadians(aleatorio.nextInt(360)));
                        g2d.shear(aleatorio.nextDouble() * 0.5 - 0.25, aleatorio.nextDouble() * 0.5 - 0.25);
                        g2d.scale(aleatorio.nextDouble() * 0.5 + 0.75, aleatorio.nextDouble() * 0.5 + 0.75);
                        g2d.translate(-xFigura - espacioAnillo / 2, -yFigura - espacioAnillo / 2);

                        switch (anillo) {
                            case 0:
                                g2d.fill(new Rectangle2D.Float(xFigura, yFigura, espacioAnillo, espacioAnillo));
                                break;
                            case 1:
                                g2d.fill(new Rectangle2D.Float(xFigura, yFigura, espacioAnillo * 1.5f, espacioAnillo));
                                break;
                            case 2:
                                Path2D triangulo = new Path2D.Float();
                                triangulo.moveTo(xFigura, yFigura + espacioAnillo);
                                triangulo.lineTo(xFigura + espacioAnillo / 2, yFigura);
                                triangulo.lineTo(xFigura + espacioAnillo, yFigura + espacioAnillo);
                                triangulo.closePath();
                                g2d.fill(triangulo);
                                break;
                            case 3:
                                g2d.fill(new Ellipse2D.Float(xFigura, yFigura, espacioAnillo, espacioAnillo));
                                break;
                        }

                        g2d.setTransform(transformacionOriginal);
                    }
                }

                indiceAngulo = 0;
                for (int anillo = 0; anillo < numAnillos; anillo++) {
                    double pasoAngulo = 2 * Math.PI / figurasPorAnillo[anillo];
                    int radio = (anillo + 1) * espacioAnillo + 20;
                    for (int i = 0; i < figurasPorAnillo[anillo]; i++) {
                        double angulo = angulosAntihorario[indiceAngulo++];
                        int xFigura = (int) (xCirculo + diametroCirculo / 2 + radio * Math.cos(angulo) - espacioAnillo / 2);
                        int yFigura = (int) (yCirculo + diametroCirculo / 2 + radio * Math.sin(angulo) - espacioAnillo / 2);

                        Color colorBase;
                        switch (anillo) {
                            case 0:
                                colorBase = Color.BLUE;
                                break;
                            case 1:
                                colorBase = Color.RED;
                                break;
                            case 2:
                                colorBase = Color.YELLOW;
                                break;
                            case 3:
                                colorBase = Color.GREEN;
                                break;
                            default:
                                colorBase = Color.WHITE;
                        }

                        GradientPaint gradiente = new GradientPaint(xFigura, yFigura, colorBase.brighter(), xFigura + espacioAnillo, yFigura + espacioAnillo, colorBase.darker());
                        g2d.setPaint(gradiente);

                        AffineTransform transformacionOriginal = g2d.getTransform();
                        g2d.translate(xFigura + espacioAnillo / 2, yFigura + espacioAnillo / 2);
                        g2d.rotate(Math.toRadians(aleatorio.nextInt(360)));
                        g2d.shear(aleatorio.nextDouble() * 0.5 - 0.25, aleatorio.nextDouble() * 0.5 - 0.25);
                        g2d.scale(aleatorio.nextDouble() * 0.5 + 0.75, aleatorio.nextDouble() * 0.5 + 0.75);
                        g2d.translate(-xFigura - espacioAnillo / 2, -yFigura - espacioAnillo / 2);

                        switch (anillo) {
                            case 0:
                                g2d.fill(new Rectangle2D.Float(xFigura, yFigura, espacioAnillo, espacioAnillo));
                                break;
                            case 1:
                                g2d.fill(new Rectangle2D.Float(xFigura, yFigura, espacioAnillo * 1.5f, espacioAnillo));
                                break;
                            case 2:
                                Path2D triangulo = new Path2D.Float();
                                triangulo.moveTo(xFigura, yFigura + espacioAnillo);
                                triangulo.lineTo(xFigura + espacioAnillo / 2, yFigura);
                                triangulo.lineTo(xFigura + espacioAnillo, yFigura + espacioAnillo);
                                triangulo.closePath();
                                g2d.fill(triangulo);
                                break;
                            case 3:
                                g2d.fill(new Ellipse2D.Float(xFigura, yFigura, espacioAnillo, espacioAnillo));
                                break;
                        }

                        g2d.setTransform(transformacionOriginal);
                    }
                }
            }
        };

        panel.setBounds(0, 0, 800, 600);
        panelCapas.add(panel, JLayeredPane.DEFAULT_LAYER);

        int totalFiguras = 12 + 12 + 24 + 24;
        angulosHorario = new double[totalFiguras];
        angulosAntihorario = new double[totalFiguras];
        for (int i = 0; i < totalFiguras; i++) {
            angulosHorario[i] = i * (2 * Math.PI / totalFiguras);
            angulosAntihorario[i] = i * (2 * Math.PI / totalFiguras);
        }

        JButton botonIniciar = new JButton("Iniciar") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.GREEN);
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g2);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
        botonIniciar.setForeground(Color.WHITE);
        botonIniciar.setBounds(20, 450, 100, 100);
        botonIniciar.setFocusPainted(false);
        botonIniciar.setContentAreaFilled(false);
        botonIniciar.setBorderPainted(false);
        panelCapas.add(botonIniciar, JLayeredPane.PALETTE_LAYER);

        JButton botonDetener = new JButton("Detener") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.RED);
                g2.fillOval(0, 0, getWidth(), getHeight());
                super.paintComponent(g2);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
        botonDetener.setForeground(Color.WHITE);
        botonDetener.setBounds(680, 450, 100, 100);
        botonDetener.setFocusPainted(false);
        botonDetener.setContentAreaFilled(false);
        botonDetener.setBorderPainted(false);
        panelCapas.add(botonDetener, JLayeredPane.PALETTE_LAYER);

        temporizadorHorario = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < angulosHorario.length; i++) {
                    angulosHorario[i] += 0.01;
                }
                panel.repaint();
            }
        });

        temporizadorAntihorario = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < angulosAntihorario.length; i++) {
                    angulosAntihorario[i] -= 0.01;
                }
                panel.repaint();
            }
        });

        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temporizadorHorario.start();
                temporizadorAntihorario.start();
            }
        });

        botonDetener.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                temporizadorHorario.stop();
                temporizadorAntihorario.stop();
            }
        });

        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
    }
}