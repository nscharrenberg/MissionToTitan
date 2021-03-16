package factory;

import managers.DrawingManager;
import repositories.SolarSystemRepository;

public class FactoryProvider {
    private static SolarSystemRepository solarSystemRepository = new SolarSystemRepository();
    private static DrawingManager drawingManager = new DrawingManager();

    public static SolarSystemRepository getSolarSystemFactory() {
        return FactoryProvider.solarSystemRepository;
    }
    public static DrawingManager getDrawingManager() { return FactoryProvider.drawingManager; }
}
