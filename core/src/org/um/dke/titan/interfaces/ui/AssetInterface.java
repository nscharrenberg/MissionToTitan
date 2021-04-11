package org.um.dke.titan.interfaces.ui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;

import java.util.List;

public interface AssetInterface {
    void load();
    Model get(String filename);
    boolean update();
    List<String> getFileNames();
    void iterateAssets(FileHandle handler, List<String> fileNames);
    FileHandle getFileHandle(String path);
}
