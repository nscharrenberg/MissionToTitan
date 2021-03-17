package interfaces.gui;

import java.util.Collection;

public interface IRecursive {
    Collection<IRecursive> getChildren();
}
