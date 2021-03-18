package utils;

import domain.MovingObject;
import domain.Planet;
import domain.Vector3D;
import interfaces.Vector3dInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PlanetReader {
    private static final String[] MOONS = {"Moon", "Titan"};

    public static void main(String[] args) {
        System.out.println(getPlanets());
    }
    public static ArrayList<MovingObject> getPlanets(){
        ArrayList<MovingObject> planets = new ArrayList<MovingObject>();
        File dir = new File("src/main/resources/data");
        dir.mkdirs();
        File f = new File(dir, "solar_system_data-2020_04_01.txt");
        try {
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String line = new String();
            while((line = bf.readLine()) != null) {
                String name = line.split(":")[0];
                //System.out.println(name);
                line = line.substring(line.indexOf("{") + 1, line.indexOf("}"));
                //System.out.println(line);
                String[] split = line.split(",");
                //System.out.println(Arrays.toString(split));
                double mass = Double.parseDouble(split[0].substring(5));
                double x = Double.parseDouble(split[1].substring(2));
                double y = Double.parseDouble(split[2].substring(2));
                double z = Double.parseDouble(split[3].substring(2));
                double vx = Double.parseDouble(split[4].substring(3));
                double vy = Double.parseDouble(split[5].substring(3));
                double vz = Double.parseDouble(split[6].substring(3));

                Vector3dInterface position = new Vector3D(x, y, z), velocity = new Vector3D(vx, vy, vz);
                if(!Arrays.asList(MOONS).contains(name)) {//checks if it is a planet
                    planets.add(new MovingObject(mass, position, velocity, name));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return planets;
    }
}
