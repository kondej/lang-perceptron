public class Pair {
    private final String key;
    private final double[] values;

    public Pair(String key, double[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public double[] getValues() {
        return values;
    }
}

