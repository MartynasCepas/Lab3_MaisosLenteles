package edu.ktu.ds.lab3.Cepas;

import edu.ktu.ds.lab3.utils.HashMap;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;

import java.util.Locale;

public class ManualTest {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        executeTest();
    }

    public static void executeTest() {
        Item c1 = new Item("Renault", "Laguna", 1997, 50000, 1700);
        Item c2 = new Item("Renault", "Megane", 2001, 20000, 3500);
        Item c3 = new Item("Toyota", "Corolla", 2001, 20000, 8500.8);
        Item c4 = new Item("Renault Laguna 2001 115900 7500");
        Item c5 = new Item.Builder().buildRandom();
        Item c6 = new Item("Honda   Civic  2007  36400 8500.3");
        Item c7 = new Item("Renault Laguna 2001 115900 7500");
        Item c8 = new Item("Audi 100 2001 115900 7500");

        // Raktų masyvas
        String[] carsIds = {"TA156", "TA102", "TA178", "TA171", "TA105", "TA106", "TA107"};
        int id = 0;
        ParsableMap<String, Item> carsMap
                = new ParsableHashMap<>(String::new, Item::new, HashType.DIVISION);

        // Reikšmių masyvas
        Item[] cars = {c1, c2, c3, c4, c5, c6, c7};
        for (Item c : cars) {
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
        
        HashMap<String, Item> anotherMap = new HashMap<>();
        id = 0;
        for (Item c : cars) {
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
        
        // keyset testas
        Ks.oun("Keyset testas");
        Ks.oun(anotherMap.keyset());
        
        
        }
  }

