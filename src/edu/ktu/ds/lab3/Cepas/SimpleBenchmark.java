package edu.ktu.ds.lab3.Cepas;

import com.sun.tools.javac.code.Lint;
import edu.ktu.ds.lab3.gui.ValidationException;
import edu.ktu.ds.lab3.utils.HashType;
import edu.ktu.ds.lab3.utils.Ks;
import edu.ktu.ds.lab3.utils.ParsableHashMap;
import edu.ktu.ds.lab3.utils.ParsableMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.sound.sampled.AudioFormat;

/**
 * @author eimutis
 */
public class SimpleBenchmark {

    public static final String FINISH_COMMAND = "                               ";
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle("edu.ktu.ds.lab3.gui.messages");

    private final Timekeeper timekeeper;

    private final String[] BENCHMARK_NAMES = {"add0.75", "add0.25", "rem0.75", "rem0.25", "get0.75", "get0.25", "Remove()"};
    private final int[] COUNTS = {10000, 20000, 40000, 80000};

    private final ParsableMap<String, Item> carsMap
            = new ParsableHashMap<>(String::new, Item::new, 10, 0.75f, HashType.DIVISION);
    private final ParsableMap<String, Item> carsMap2
            = new ParsableHashMap<>(String::new, Item::new, 10, 0.25f, HashType.DIVISION);
    private final Queue<String> chainsSizes = new LinkedList<>();
    
    
    private static java.util.HashMap javaMap = new java.util.HashMap<>();
    private static edu.ktu.ds.lab3.utils.HashMap ktuMap = new edu.ktu.ds.lab3.utils.HashMap<>();
    
    public static void addElements(){
        String path = "D:/Programavimas/Java/Lab3_MaisosLenteles/src/edu/ktu/ds/lab3/Cepas/zodynas.txt";
        
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
            lines.forEach(x -> {javaMap.put(x, x); ktuMap.put(x, x); } );
        } catch (IOException ex) {
            Logger.getLogger(SimpleBenchmark.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    
    /**
     * For console benchmark
     */
    public SimpleBenchmark() {
        timekeeper = new Timekeeper(COUNTS);
    }

    /**
     * For Gui benchmark
     *
     * @param resultsLogger
     * @param semaphore
     */
    public SimpleBenchmark(BlockingQueue<String> resultsLogger, Semaphore semaphore) {
        semaphore.release();
        timekeeper = new Timekeeper(COUNTS, resultsLogger, semaphore);
    }

    public static void main(String[] args) {
        executeTest();
    }

    public static void executeTest() {
        // suvienodiname skaičių formatus pagal LT lokalę (10-ainis kablelis)
        addElements();
        Locale.setDefault(new Locale("LT"));
        Ks.out("Greitaveikos tyrimas:\n");
        new SimpleBenchmark().startBenchmark();
       
    }

    public void startBenchmark() {
        try {
            benchmark();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void benchmark() throws InterruptedException {
        try {
            chainsSizes.add(MESSAGES.getString("maxChainLength"));
            chainsSizes.add("   kiekis      " + BENCHMARK_NAMES[0] + "   " + BENCHMARK_NAMES[1]);
            for (int k : COUNTS) {
                Item[] carsArray = ItemsGenerator.generateShuffleCars(k);
                String[] carsIdsArray = ItemsGenerator.generateShuffleIds(k);
                carsMap.clear();
                carsMap2.clear();
                timekeeper.startAfterPause();
                timekeeper.start();

                for (int i = 0; i < k; i++) {
                    carsMap.put(carsIdsArray[i], carsArray[i]);
                }
                timekeeper.finish(BENCHMARK_NAMES[0]);

                String str = "   " + k + "          " + carsMap.getMaxChainSize();
                for (int i = 0; i < k; i++) {
                    carsMap2.put(carsIdsArray[i], carsArray[i]);
                }
                timekeeper.finish(BENCHMARK_NAMES[1]);

                str += "         " + carsMap2.getMaxChainSize();
                chainsSizes.add(str);

                Arrays.stream(carsIdsArray).forEach(carsMap::remove);
                timekeeper.finish(BENCHMARK_NAMES[2]);

                Arrays.stream(carsIdsArray).forEach(carsMap2::remove);
                timekeeper.finish(BENCHMARK_NAMES[3]);

                Arrays.stream(carsIdsArray).forEach(carsMap2::get);
                timekeeper.finish(BENCHMARK_NAMES[4]);

                Arrays.stream(carsIdsArray).forEach(carsMap2::get);
                timekeeper.finish(BENCHMARK_NAMES[5]);                
                
                timekeeper.seriesFinish();
            }
            
            long t0 = System.nanoTime();
            Ks.oun("javaMap remove()");
            
            String path = "D:/Programavimas/Java/Lab3_MaisosLenteles/src/edu/ktu/ds/lab3/Cepas/zodynas.txt";
                try (Stream<String> lines = Files.lines(Paths.get(path))) {
                 lines.forEach(x -> {javaMap.remove(x); } );
                 } catch (IOException ex) {
                    Logger.getLogger(SimpleBenchmark.class.getName()).log(Level.SEVERE, null, ex);
                 }
            
            long t1 = System.nanoTime();
            Ks.oun((t1-t0) / 1e9);
            
            long t2 = System.nanoTime();
            Ks.oun("ktuMap remove()");
            
                try (Stream<String> lines = Files.lines(Paths.get(path))) {
                 lines.forEach(x -> {ktuMap.remove(x); } );
                 } catch (IOException ex) {
                    Logger.getLogger(SimpleBenchmark.class.getName()).log(Level.SEVERE, null, ex);
                 }
            
            long t3 = System.nanoTime();
            Ks.oun((t3-t2) / 1e9);
                
            StringBuilder sb = new StringBuilder();
            chainsSizes.forEach(p -> sb.append(p).append(System.lineSeparator()));
            timekeeper.logResult(sb.toString());
            timekeeper.logResult(FINISH_COMMAND);
            
        } catch (ValidationException e) {
            timekeeper.logResult(e.getMessage());
        }
    }
}
