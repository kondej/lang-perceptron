import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    private double[] weights;
    private double bias;
    private final double learningRate;
    private final int epochs;
    private final String langLabel;

    private final List<Pair> trainSet = new ArrayList<>();

    public Perceptron(double learningRate, int epochs, String langLabel) {
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.langLabel = langLabel;
    }

    public void loadTrainSet(String trainFile) {
        trainSet.clear();
        trainSet.addAll(LangVector.vectorizeSet(trainFile));

        weights = new double[trainSet.getFirst().getValues().length];
        bias = Math.random();
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
    }

    // calc
    int predict(double[] input) {
        double prediction = 0.0;

        for (int i = 0; i < weights.length; i++) {
            prediction += weights[i] * input[i];
        }

        prediction -= bias;

        if (prediction >= 0.0) {
            return  1;
        } else {
            return 0;
        }
    }

    // delta
    void updateWeightsAndBias(double[] input, int label) {
        int prediction = predict(input);

        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] + learningRate * (label - prediction) * input[i];
        }

        bias = bias - learningRate * (label - prediction);
    }

    void train() {
        for (int i = 0; i < epochs; i++) {
            for (int j = 0; j < trainSet.size(); j++) {
                if (trainSet.get(j).getKey().equals(langLabel)) {
                    updateWeightsAndBias(trainSet.get(j).getValues(), 1);
                } else {
                    updateWeightsAndBias(trainSet.get(j).getValues(), 0);
                }
            }
        }
    }
}
