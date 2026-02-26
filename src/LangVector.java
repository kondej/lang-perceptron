import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LangVector {

    public static int langCount;
    public static List<String> langs = new ArrayList<>();
    public static final List<String> rawTestSet = new ArrayList<>();


    public static List<Pair> vectorizeSet(String file) {
        String path = System.getProperty("user.dir") + "/data/";
        List<Pair> set = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Path.of(path + file))) {
            lines.forEach(line -> {
                String[] attributes = line.split(",", 2);

                set.add(new Pair(attributes[0], vectorize(attributes[1])));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return set;
    }

    public static double[] vectorize(String text) {
        int[] letterCounts = new int[26];
        int totalLetters = 0;

        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                letterCounts[c - 'A']++;
            } else if (c >= 'a' && c <= 'z') {
                letterCounts[c - 'a']++;
            }
            totalLetters++;
        }

        double[] normalizedCounts = new double[26];
        if (totalLetters > 0) {
            for (int i = 0; i < 26; i++) {
                normalizedCounts[i] = (double) letterCounts[i] / totalLetters;
            }
        }

        return normalizedCounts;
    }

    public static void setRawTestSet(String testFile) {
        String path = System.getProperty("user.dir") + "/data/";

        try (Stream<String> lines = Files.lines(Path.of(path + testFile))) {
            lines.forEach(line -> {
                String[] attributes = line.split(",", 2);

                rawTestSet.add((attributes[1]));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void findLangs(String trainFile) {
        String path = System.getProperty("user.dir") + "/data/";


        try (Stream<String> lines = Files.lines(Path.of(path + trainFile))) {
            lines.forEach(line -> {
                String[] attributes = line.split(",", 2);

                if (!langs.contains(attributes[0])) {
                    langs.add(attributes[0]);
                    langCount++;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
