/*
 * Copyright 2012-06-08 the original author or authors.
 */
package pl.com.softproject.utils.swingx;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;
import pl.com.softproject.utils.swingworker.AbstractTask;
import pl.com.softproject.utils.swingworker.WindowBlocker;

/**
 *
 * @author Adrian Lapierre <adrian@softproject.com.pl>
 */
public abstract class BaseTask<T , V> extends AbstractTask<T, V> {

    Logger logger = Logger.getLogger(getClass());
    WindowBlocker blocker;
    
    @Override
    protected void failed(Throwable cause) {
        super.failed(cause);
        logger.error(cause.getMessage(), cause);
        JOptionPane.showMessageDialog(null, cause.getLocalizedMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        unBlock();
    }

    @Override
    protected void finished() {
        super.finished();
        unBlock();
    }
    
    public void setBlocker(WindowBlocker blocker) {
        this.blocker = blocker;
    }
    
    public void block() {
        if(blocker!=null) blocker.block();
    }
    
    public void unBlock() {
        if(blocker!=null) blocker.unBlock();
    }
}
