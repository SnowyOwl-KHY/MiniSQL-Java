import interpreter.Interpreter;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String remain = "";
        while (true) {
            String command = remain;
            while (!command.contains(";")) {
                command += in.nextLine();
                if (!command.endsWith(";")) {
                    command += '\n';
                }
            }
            int semicolonIndex = command.indexOf(';');
            remain = command.substring(semicolonIndex + 1);
            command = command.substring(0, semicolonIndex);

            if (command.trim().equals("exit")) {
                System.out.println("Bye.");
                break;
            }

            Interpreter.getInstance().execute(command);
        }
    }

}
