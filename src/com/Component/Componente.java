/**
 * Componente gráfico que permite dividir un panel en múltiples secciones redimensionables.
 * 
 * <h2>Descripción General</h2>
 * Este componente proporciona una interfaz visual que permite dividir una ventana en múltiples
 * paneles ajustables mediante arrastre. Es ideal para interfaces donde se requiere la distribución
 * flexible del espacio, como editores de texto, visores de contenido o herramientas de análisis de datos.
 * 
 * <h2>Instalación y Uso</h2>
 * Para utilizar este componente en un proyecto Java:
 * <pre>
 * {@code
 * Componente componente = new Componente();
 * JPanel panel1 = new JPanel();
 * JPanel panel2 = new JPanel();
 * 
 * componente.addPanel(panel1);
 * componente.addPanel(panel2);
 * 
 * JFrame frame = new JFrame();
 * frame.add(componente);
 * frame.setSize(800, 600);
 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * frame.setVisible(true);
 * }
 * </pre>
 * 
 * <h2>Dependencias</h2>
 * - JDK 8 o superior  
 * - Biblioteca estándar de Java (javax.swing, java.awt)  
 */
package com.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Clase Componente que gestiona múltiples paneles redimensionables dentro de un contenedor.
 */
public class Componente extends JPanel {
    private ArrayList<JPanel> panels = new ArrayList<>();
    private ArrayList<CustomDivider> dividers = new ArrayList<>();
    private ArrayList<Double> proportions = new ArrayList<>();
    private boolean resizing = false; // Bandera para evitar conflictos al redimensionar

    /**
     * Constructor de Componente. Inicializa el layout y maneja redimensionamientos.
     */
    public Componente() {
        setLayout(null);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (!resizing && !panels.isEmpty()) {
                    updateLayout();
                }
            }
        });
    }

    /**
     * Agrega un nuevo panel al componente y ajusta la distribución del espacio.
     * 
     * @param panel El JPanel a agregar. Si ya existen paneles, el espacio se redistribuye proporcionalmente.
     */
    public void addPanel(JPanel panel) {
        panels.add(panel);
        add(panel);

        if (panels.size() == 1) {
            proportions.add(1.0);
        } else {
            double newProportion = 1.0 / panels.size();
            for (int i = 0; i < proportions.size(); i++) {
                proportions.set(i, proportions.get(i) * (1.0 - newProportion));
            }
            proportions.add(newProportion);

            CustomDivider divider = new CustomDivider(this, panels.size() - 1);
            add(divider);
            dividers.add(divider);
        }
        
        updateLayout();
    }

    /**
     * Elimina un panel del componente y redistribuye el espacio entre los paneles restantes.
     * 
     * @param panel El JPanel a eliminar. Si el panel no está en la lista, no ocurre ninguna acción.
     */
    public void removePanel(JPanel panel) {
        if (!panels.contains(panel)) return;

        int index = panels.indexOf(panel);
        panels.remove(panel);
        remove(panel);
        proportions.remove(index);

        if (index < dividers.size()) {
            remove(dividers.get(index));
            dividers.remove(index);
        }

        double total = proportions.stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < proportions.size(); i++) {
            proportions.set(i, proportions.get(i) / total);
        }

        updateLayout();
    }

    /**
     * Actualiza la distribución de los paneles y los divisores según las proporciones actuales.
     */
    public void updateLayout() {
        if (panels.isEmpty()) return;

        resizing = true;

        int width = getWidth();
        int height = getHeight();
        int currentX = 0;

        for (int i = 0; i < panels.size(); i++) {
            int panelWidth = (int) (width * proportions.get(i));
            JPanel panel = panels.get(i);
            panel.setBounds(currentX, 0, panelWidth, height);
            currentX += panelWidth;

            if (i < dividers.size()) {
                dividers.get(i).setBounds(currentX - 5, 0, 10, height);
            }
        }

        revalidate();
        repaint();
        resizing = false;
    }

    /**
     * Maneja el redimensionamiento de los paneles al arrastrar un divisor.
     * 
     * @param index Índice del divisor arrastrado.
     * @param newX Nueva posición X del divisor.
     */
    public void handleDrag(int index, int newX) {
        if (index <= 0 || index >= panels.size()) return;
        int prevX = panels.get(index - 1).getX();
        int nextX = panels.get(index).getX() + panels.get(index).getWidth();
        int containerWidth = getWidth();

        if (newX > prevX + 50 && newX < nextX - 50) {
            int newWidthPrev = newX - prevX;
            int newWidthNext = nextX - newX;

            panels.get(index - 1).setBounds(prevX, 0, newWidthPrev, getHeight());
            panels.get(index).setBounds(newX, 0, newWidthNext, getHeight());
            dividers.get(index - 1).setBounds(newX - 5, 0, 10, getHeight());

            revalidate();
            repaint();
        }
    }
}

/**
 * Clase CustomDivider que representa un divisor entre paneles.
 */
class CustomDivider extends JPanel {
    private Componente parent;
    private int index;
    private int offsetX;

    /**
     * Constructor del divisor.
     * 
     * @param parent Referencia al componente padre.
     * @param index Índice del divisor en la lista de divisores.
     */
    public CustomDivider(Componente parent, int index) {
        this.parent = parent;
        this.index = index;
        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
        setBackground(Color.BLACK);
        
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                offsetX = e.getX();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int newX = getX() + e.getX() - offsetX;
                parent.handleDrag(index, newX);
            }
        });
    }
}
