import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Layer {

    private final List<Perceptron> perceptrons = new ArrayList<>();

    public void train(String trainFile, double learningRate, int epochs) {
        LangVector.findLangs(trainFile);

        for (int i = 0; i < LangVector.langCount; i++) {
            perceptrons.add(new Perceptron(learningRate, epochs, LangVector.langs.get(i)));
        }

        for (Perceptron perceptron : perceptrons) {
            perceptron.loadTrainSet(trainFile);
            perceptron.train();
        }
    }

    public void fromTestFile(String testFile) {
        LangVector.setRawTestSet(testFile);

        List<Pair> testSet = new ArrayList<>(LangVector.vectorizeSet(testFile));

        int count = 0;

        for (int i = 0; i < testSet.size(); i++) {
            List<Integer> classified = new ArrayList<>();

            for (Perceptron perceptron : perceptrons) {
                classified.add(perceptron.predict(testSet.get(i).getValues()));
            }

            for (int j = 0; j < classified.size(); j++) {
                if (classified.get(j) == 1) {
                    if (LangVector.langs.get(j).equals(testSet.get(i).getKey())) {
                        count++;
                    } else {
                        System.out.println(LangVector.langs.get(j) + ": " + LangVector.rawTestSet.get(i));
                    }
                    break;
                }
            }
        }

        double accuracy = (double) count / testSet.size() * 100;
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("\nDokładność: " + df.format(accuracy) + "% (" + count + "/" + testSet.size() + ")");
    }

    public void fromText(String text) {
        List<Integer> classified = new ArrayList<>();

        for (Perceptron perceptron : perceptrons) {
            classified.add(perceptron.predict(LangVector.vectorize(text)));
        }

        for (int j = 0; j < classified.size(); j++) {
            if (classified.get(j) == 1) {
                System.out.println(LangVector.langs.get(j) + ": " + text);
                break;
            }
        }
    }
}
