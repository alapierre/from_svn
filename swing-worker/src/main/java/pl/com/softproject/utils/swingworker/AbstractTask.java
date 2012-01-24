/*
 * Copyright 2012-01-20 the original author or authors.
 */
package pl.com.softproject.utils.swingworker;

import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;

/**
 *
 * @author Sebastian Łukasiewicz <slukasiewicz@softproject.com.pl>
 */
public abstract class AbstractTask<T, V> extends SwingWorker<T, V> {

    @Override
    protected final void done() {
        try {
            if (isCancelled()) {
                cancelled();
            } else {
                try {
                    succeeded(get());
                } catch (InterruptedException e) {
                    interrupted(e);
                } catch (ExecutionException e) {
                    failed(e.getCause());
                }
            }
        } finally {
            finished();
        }
    }

    protected void cancelled() {
    }

    protected void succeeded(T result) {
    }

    protected void interrupted(InterruptedException e) {
    }

    protected void failed(Throwable cause) {
    }

    protected void finished() {
    }
}
