package utils;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class WriteToFile {
    public static void writeToFile(double distance, String velocity) {
        try {
            velocity = velocity.replaceAll(" ", "");
            FileWriter myWriter = new FileWriter(velocity+".txt");
            myWriter.write(velocity+"\n");
            myWriter.write(distance+"");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}