package interfaces.gui;

public interface IDetailedDrawable extends IDrawable {
    void drawShape(IDrawableContext context);
    IDrawableDetails getDetails();
}
