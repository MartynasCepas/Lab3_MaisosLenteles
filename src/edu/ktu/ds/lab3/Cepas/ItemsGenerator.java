package edu.ktu.ds.lab3.Cepas;

import edu.ktu.ds.lab3.gui.ValidationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

public class ItemsGenerator {

    private static final String ID_CODE = "TA";      //  ***** Nauja
    private static int serNr = 10000;               //  ***** Nauja

    private Item[] cars;
    private String[] keys;

    private int currentCarIndex = 0, currentCarIdIndex = 0;

    public static Item[] generateShuffleCars(int size) {
        Item[] cars = IntStream.range(0, size)
                .mapToObj(i -> new Item.Builder().buildRandom())
                .toArray(Item[]::new);
        Collections.shuffle(Arrays.asList(cars));
        return cars;
    }

    public static String[] generateShuffleIds(int size) {
        String[] keys = IntStream.range(0, size)
                .mapToObj(i -> ID_CODE + (serNr++))
                .toArray(String[]::new);
        Collections.shuffle(Arrays.asList(keys));
        return keys;
    }

    public Item[] generateShuffleCarsAndIds(int setSize,
            int setTakeSize) throws ValidationException {

        if (setTakeSize > setSize) {
            setTakeSize = setSize;
        }
        cars = generateShuffleCars(setSize);
        keys = generateShuffleIds(setSize);
        this.currentCarIndex = setTakeSize;
        return Arrays.copyOf(cars, setTakeSize);
    }

    // Imamas po vienas elementas iš sugeneruoto masyvo. Kai elementai baigiasi sugeneruojama
    // nuosava situacija ir išmetamas pranešimas.
    public Item getCar() {
        if (cars == null) {
            throw new ValidationException("carsNotGenerated");
        }
        if (currentCarIndex < cars.length) {
            return cars[currentCarIndex++];
        } else {
            throw new ValidationException("allSetStoredToMap");
        }
    }

    public String getCarId() {
        if (keys == null) {
            throw new ValidationException("carsIdsNotGenerated");
        }
        if (currentCarIdIndex < keys.length) {
            return keys[currentCarIdIndex++];
        } else {
            throw new ValidationException("allKeysStoredToMap");
        }
    }
}
