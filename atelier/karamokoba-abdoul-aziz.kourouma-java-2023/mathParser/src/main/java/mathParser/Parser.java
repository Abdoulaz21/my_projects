package mathParser;

import java.io.*;

public class Parser {
    private final String filename;

    public Parser(String filename) {
        this.filename = filename;
    }

    public void parse() {
        BufferedReader br;
        StringBuilder builder = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch(IOException e) {
            System.err.println("Invalid input file " + filename);
            builder.append("Invalid input file " + filename + "\n");
            return;
        }
        while (true) {
            String line;
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            var lineSplited = line.split(",");
            if (!lineSplited[0].equals("add") && !lineSplited[0].equals("div")
                    && !lineSplited[0].equals("mul") && !lineSplited[0].equals("sub")) {
                System.err.println("Invalid operation");
                builder.append("Invalid operation\n");

                continue;
            }
            int number1;
            int number2;
            try {
                number1 = Integer.parseInt(lineSplited[1]);
                number2 = Integer.parseInt(lineSplited[2]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid number");
                builder.append("Invalid number\n");
                continue;
            }
            if (lineSplited[0].equals("div") && number2 == 0) {
                System.err.println("Invalid number");
                builder.append("Invalid number\n");
            } else {
                char operator = switch (lineSplited[0]) {
                    case "add" -> '+';
                    case "div" -> '/';
                    case "mul" -> '*';
                    default -> '-';
                };
                int result = switch (operator) {
                    case '+' -> number1 + number2;
                    case '/' -> number1 / number2;
                    case '*' -> number1 * number2;
                    default -> number1 - number2;
                };
                builder.append(number1).append(" ").append(operator).append(" ").append(number2).append(" = ").append(result).append("\n");
            }
        }
        try (var bw = new BufferedWriter(new FileWriter("output.txt"))) {
            bw.write(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}