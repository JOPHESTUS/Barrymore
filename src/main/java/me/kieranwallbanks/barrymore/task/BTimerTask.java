package me.kieranwallbanks.barrymore.task;

import me.kieranwallbanks.barrymore.Barrymore;

import java.util.TimerTask;

/**
 * A special version of {@link TimerTask} with some more features.
 */
public abstract class BTimerTask extends TimerTask {
    private final Barrymore barrymore;

    /**
     * Constructs a new instance of {@link BTimerTask}
     *
     * @param instance an instance of {@link Barrymore} stored in a variable
     *                 named {@code barrymore}
     */
    public BTimerTask(Barrymore instance) {
        barrymore = instance;
    }

}
