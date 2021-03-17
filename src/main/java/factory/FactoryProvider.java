package factory;

import managers.DrawingManager;
import managers.UpdateManager;
import repositories.SolarSystemRepository;

public class FactoryProvider {
    private static SolarSystemRepository solarSystemRepository = new SolarSystemRepository();
    private static DrawingManager drawingManager = new DrawingManager();
    private static UpdateManager updateManager = new UpdateManager();

    public static SolarSystemRepository getSolarSystemFactory() {
        return FactoryProvider.solarSystemRepository;
    }
    public static DrawingManager getDrawingManager() { return FactoryProvider.drawingManager; }
    public static UpdateManager getUpdateManager() { return FactoryProvider.updateManager; }
}
