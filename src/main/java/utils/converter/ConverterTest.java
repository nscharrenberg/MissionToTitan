package utils.converter;

import domain.Planet;
import domain.Vector3D;
import utils.PlanetReader;

import java.util.ArrayList;

public class ConverterTest {
    public static void main(String[] args) {
        double width = 1000;
        double height = 800;
		/*Vector3D v = new Vector3D(-9.435345478592035E11, 4.95035955103367E12, 6.131453014410347E9);
		System.out.println(v);
		System.out.println(PositionConverter.convertToPixel(v, width, height));*/
        ArrayList<Planet> planets = PlanetReader.getPlanets();
        for(Planet p : planets) {
//            System.out.println(p.getName());
            Vector3D w = (Vector3D) p.getPosition();
//            System.out.println(w);
//            Vector3D v = (Vector3D) PositionConverter.convertToPixel(w, width, height);
//            System.out.println(v.toString());
//            System.out.println("-----------------------");
        }
    }
}
