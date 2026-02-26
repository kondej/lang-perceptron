import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Layer layer = new Layer();

        if (args.length == 4) {
            layer.train(args[2], Double.parseDouble(args[0]), Integer.parseInt(args[1]));
            layer.fromTestFile(args[3]);
        } else if (args.length == 3) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("=================================");
            System.out.println("Stała uczenia: " + args[0]);
            System.out.println("Liczba epok: " + args[1]);
            System.out.println("=================================");
            System.out.println("Podaj tekst do klasyfikacji: ");

            layer.train(args[2], Double.parseDouble(args[0]), Integer.parseInt(args[1]));

            String input = scanner.nextLine();

            while (!input.equals("q")) {
                layer.fromText(input);

                input = scanner.nextLine();
            }
        } else {
            System.err.println("Usage: java -jar c4sjedn.jar <learningRate> <epochs> <trainFile> <testFile>");
        }
    }
}
