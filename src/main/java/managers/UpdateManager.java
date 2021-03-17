package managers;

import domain.MovingObject;
import domain.Planet;
import factory.FactoryProvider;
import interfaces.gui.ITimer;
import interfaces.gui.IUpdate;
import javafx.application.Platform;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateManager extends Manager<IUpdate> implements ITimer {
    public final static int DEFAULT_INTERVAL = 10; // 1 sec

    private Timer timer;
    private int index = 0;

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
        try {
            reset();;

            List<MovingObject> tl = FactoryProvider.getSolarSystemFactory().getTimeLine().get(index);

            items.addAll(tl);

            FactoryProvider.getDrawingManager().update();
            index++;
        } catch (IndexOutOfBoundsException ex) {
            // Temp fix
            timer.cancel();
        }
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
