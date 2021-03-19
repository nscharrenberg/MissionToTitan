package repositories.interfaces;

import domain.LayoutView;

public interface ISettingRepository {
    double getCanvasWidth();

    void setCanvasWidth(double canvasWidth);

    double getSidebarWidth();

    void setSidebarWidth(double sidebarWidth);

    double getAppHeight();

    void setAppHeight(double appHeight);

    int getRefreshInterval();

    void setRefreshInterval(int refreshInterval);

    int getDayCount();

    void setDayCount(int dayCount);

    int getYearCount();

    void setYearCount(int yearCount);

    boolean isGuiFormatting();

    void setGuiFormatting(boolean guiFormatting);

    boolean isRealisticSize();
    void setRealisticSize(boolean realisticSize);

    LayoutView getLayoutView();
    void setLayoutView(LayoutView layoutView);
}
