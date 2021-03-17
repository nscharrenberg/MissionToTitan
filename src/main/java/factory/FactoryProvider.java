package factory;

import managers.DrawingManager;
import managers.UpdateManager;
import repositories.SettingRepository;
import repositories.SolarSystemRepository;
import repositories.interfaces.ISettingRepository;

public class FactoryProvider {
    private static SolarSystemRepository solarSystemRepository = new SolarSystemRepository();
    private static ISettingRepository settingRepository = new SettingRepository();
    private static DrawingManager drawingManager = new DrawingManager();
    private static UpdateManager updateManager = new UpdateManager();

    public static SolarSystemRepository getSolarSystemFactory() {
        return FactoryProvider.solarSystemRepository;
    }
    public static ISettingRepository getSettingRepository() { return FactoryProvider.settingRepository; }
    public static DrawingManager getDrawingManager() { return FactoryProvider.drawingManager; }
    public static UpdateManager getUpdateManager() { return FactoryProvider.updateManager; }
}
