package repositories;

public class SettingRepository implements repositories.interfaces.ISettingRepository {
    private double canvasWidth = 700;
    private double sidebarWidth = 200;
    private double appHeight = 700;
    private int refreshInterval = 1;
    private int dayCount = 365;
    private int yearCount = 1;
    private boolean guiFormatting = true;

    @Override
    public double getCanvasWidth() {
        return canvasWidth;
    }

    @Override
    public void setCanvasWidth(double canvasWidth) {
        this.canvasWidth = canvasWidth;
    }

    @Override
    public double getSidebarWidth() {
        return sidebarWidth;
    }

    @Override
    public void setSidebarWidth(double sidebarWidth) {
        this.sidebarWidth = sidebarWidth;
    }

    @Override
    public double getAppHeight() {
        return appHeight;
    }

    @Override
    public void setAppHeight(double appHeight) {
        this.appHeight = appHeight;
    }

    @Override
    public int getRefreshInterval() {
        return refreshInterval;
    }

    @Override
    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    @Override
    public int getDayCount() {
        return dayCount;
    }

    @Override
    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    @Override
    public int getYearCount() {
        return yearCount;
    }

    @Override
    public void setYearCount(int yearCount) {
        this.yearCount = yearCount;
    }

    @Override
    public boolean isGuiFormatting() {
        return guiFormatting;
    }

    @Override
    public void setGuiFormatting(boolean guiFormatting) {
        this.guiFormatting = guiFormatting;
    }
}
