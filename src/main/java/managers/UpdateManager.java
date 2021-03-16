package managers;

import interfaces.gui.ITimer;
import interfaces.gui.IUpdate;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateManager extends Manager<IUpdate> implements ITimer {
    public final static int DEFAULT_INTERVAL = 10;

    private Timer timer;

    public UpdateManager() {
        reset();
    }

    @Override
    public void init() {
        setTimer(DEFAULT_INTERVAL);
        isRunning = !isRunning;
    }

    @Override
    public void setTimer(int interval) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> update());
            }
        }, 0, interval);
    }

    @Override
    public void update() {
        if (isRunning()) {
            for (IUpdate toUpdate : items) {
                toUpdate.update();
            }
        }

        refresh();
    }

    @Override
    public void reset() {
        items = new LinkedList<>();
        scheduledRemovals = new LinkedList<>();
        scheduledAdditions = new LinkedList<>();
        isRunning = true;
    }
}
