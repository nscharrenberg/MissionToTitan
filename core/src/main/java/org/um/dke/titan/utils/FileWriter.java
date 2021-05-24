package org.um.dke.titan.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import org.um.dke.titan.interfaces.StateInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

public class FileWriter {
    public static void write(StateInterface[][] timeline) {
        String path = Gdx.files.internal(String.format("data/solar_system.json")).path();
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(path))), StandardCharsets.UTF_8);

            Json json = new Json();
            osw.write(json.prettyPrint(timeline));
            osw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
