/*
 * Copyright 2012-06-09 the original author or authors.
 */
package pl.com.softproject.utils.swingx;

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;
import org.jdesktop.swingx.JXBusyLabel;
import pl.com.softproject.utils.swingworker.WindowBlocker;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public class BusyLabelWindowBlocker extends WindowBlocker implements MouseInputListener {
    
    private Cursor cursor;
    private JXBusyLabel label;

    public BusyLabelWindowBlocker() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setOpaque(false);
        //JLabel label = new JLabel("Processing data, please wait...", JLabel.CENTER);
        label = new JXBusyLabel();
        label.setText("Processing data, please wait...");
        label.setForeground(Color.white);
        label.setFont(new java.awt.Font("Tahoma", Font.BOLD, 15));
        label.setHorizontalAlignment(JLabel.CENTER);
        add(label, BorderLayout.CENTER);
        
        addMouseListener(this);
        addMouseMotionListener(this);        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Toolkit.getDefaultToolkit().beep();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    public void block() {
        cursor = getCursor();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setVisible(true);
        setEnabled(true);
        label.setEnabled(true);
        label.setBusy(true);
    }

    public void unBlock() {
        setCursor(cursor);
        setVisible(false);
        setEnabled(false);
        label.setEnabled(false);
        label.setBusy(false);
    }

    @Override
    public void paint(Graphics g) {
        Dimension  size = getSize();
        Graphics2D g2   = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRect(0, 0, (int) size.getWidth(), (int) size.getHeight());
        super.paint(g2);
        g2.dispose();
    }
    
}
