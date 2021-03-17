package managers;

import domain.MovingObject;
import factory.FactoryProvider;
import interfaces.gui.ITimer;
import interfaces.gui.IUpdate;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateManager extends Manager<IUpdate> implements ITimer {
    public final static int DEFAULT_INTERVAL = 500; // 1 sec

    private Timer timer;

    public UpdateManager() {
        items = new LinkedList<>();
        scheduledAdditions = new LinkedList<>();
        scheduledRemovals = new LinkedList<>();
    }

    @Override
    public void init() {
        FactoryProvider.getSolarSystemFactory().preprocessing();
        setTimer(DEFAULT_INTERVAL);
    }

    @Override
    public void reset() {
        items = new LinkedList<>();
        scheduledAdditions = new LinkedList<>();
        scheduledRemovals = new LinkedList<>();
    }

    @Override
    public void update() {
        reset();
        // TODO: This would need to be our timeline array
        FactoryProvider.getSolarSystemFactory().getPlanets().forEach(MovingObject::update);
        items.addAll(FactoryProvider.getSolarSystemFactory().getPlanets());

        FactoryProvider.getDrawingManager().update();

        //TODO: Also stop timer when timeline is finished.
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
}
