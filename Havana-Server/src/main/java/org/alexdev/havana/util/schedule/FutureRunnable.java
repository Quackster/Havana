package org.alexdev.havana.util.schedule;

import java.util.concurrent.Future;

public abstract class FutureRunnable implements Runnable {
    private Future<?> future;

    public Future<?> getFuture() {
        return future;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }

    public void cancelFuture() {
        if (this.future != null) {
            this.future.cancel(false);
            this.future = null;
        }
    }
}