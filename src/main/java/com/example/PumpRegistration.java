package com.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;




public class PumpRegistration {
    public static void main(String[] args) {
        // Creating 10 pump credentials (ID → Passcode)
        Map<Integer, Integer> pumps = new HashMap<>();
        pumps.put(1234, 567);
        pumps.put(2345, 678);
        pumps.put(3456, 789);
        pumps.put(4567, 890);
        pumps.put(5678, 901);
        pumps.put(6789, 123);
        pumps.put(7890, 234);
        pumps.put(8901, 345);
        pumps.put(9012, 456);
        pumps.put(1023, 567);

        // Write to binary file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pumps.dat"))) {
            oos.writeObject(pumps);
            System.out.println("Pump registration file (pumps.dat) created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
