/*
 * Copyright 2012-06-09 the original author or authors.
 */
package pl.com.softproject.utils.swingworker;

import javax.swing.JPanel;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public abstract class WindowBlocker extends JPanel {
    
    abstract public void block();
    abstract public void unBlock();
    
}
