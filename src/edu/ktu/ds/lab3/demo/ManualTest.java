package edu.ktu.ds.lab3.demo;

import edu.ktu.ds.lab3.utils.HashMap;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;

import java.util.Locale;
import sun.tools.tree.Node;

public class ManualTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() {
        Car c1 = new Car("Renault", "Laguna", 1997, 50000, 1700);
        Car c2 = new Car("Renault", "Megane", 2001, 20000, 3500);
        Car c3 = new Car("Toyota", "Corolla", 2001, 20000, 8500.8);
        Car c4 = new Car("Renault Laguna 2001 115900 7500");
        Car c5 = new Car.Builder().buildRandom();
        Car c6 = new Car("Honda   Civic  2007  36400 8500.3");
        Car c7 = new Car("Renault Laguna 2001 115900 7500");
        Car c8 = new Car("Audi 100 2001 115900 7500");

        // Raktų masyvas
        String[] carsIds = {"TA156", "TA102", "TA178", "TA171", "TA105", "TA106", "TA107"};
        int id = 0;
        ParsableMap<String, Car> carsMap
                = new ParsableHashMap<>(String::new, Car::new, HashType.DIVISION);

        // Reikšmių masyvas
        Car[] cars = {c1, c2, c3, c4, c5, c6, c7};
        for (Car c : cars) {
            carsMap.put(carsIds[id++], c);
        }
        carsMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Ar egzistuoja pora atvaizdyje?");
        Ks.oun(carsMap.contains(carsIds[6]));
        Ks.oun(carsMap.contains(carsIds[6]));
        Ks.oun("Pašalinamos poros iš atvaizdžio:");
        Ks.oun(carsMap.remove(carsIds[1]));
        Ks.oun(carsMap.remove(carsIds[6]));
        carsMap.println("Porų išsidėstymas atvaizdyje pagal raktus");
        Ks.oun("Atliekame porų paiešką atvaizdyje:");
        Ks.oun(carsMap.get(carsIds[2]));
        Ks.oun(carsMap.get(carsIds[6]));
        Ks.oun("Išspausdiname atvaizdžio poras String eilute:");
        Ks.ounn(carsMap);
        
        HashMap<String, Car> anotherMap = new HashMap<>();
        id = 0;
        for (Car c : cars) {
            anotherMap.put(carsIds[id++], c);
        }
        
        // Contains value testas
        Ks.oun("Contains Value testas");
        Ks.oun(anotherMap.containsValue(c2));
        
        // numberOfEmpties testas
        Ks.oun("NumberOfEmpties Testas");
        Ks.oun(anotherMap.numberOfEmpties());
        
        // putIfAbscent testas
        Ks.oun("putIfAbscent Testas");
        Ks.oun(anotherMap.putIfAbsent(carsIds[2], c8));
        
        // getNumberOfCollisions testas
        Ks.oun("GetNumberOfCollisions testas");
        Ks.oun(anotherMap.getNumberOfCollisions());
        
        // getNumberOfCollisions testas
        Ks.oun("Keyset testas");
        Ks.oun(anotherMap.keyset());
        
        
        }
  }

