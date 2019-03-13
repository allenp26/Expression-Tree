import java.util.*;

/**
 * Main user interface which interacts with the user to see
 * whether if they are entering a postfix expression or a infix
 * expression. After the user has enter the expression, the string is
 * passed onto another class for further processing
 *
 * @author Allen Pan
 */
public class UI {
    Scanner in = new Scanner(System.in);

    /**
     * main method to call the askForInput method
     * which does the interacting part with the user
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        UI runner = new UI();
        runner.askForInput();
    }

    /**
     * Asks the user if they want to enter a postfix expression or an infix expression
     * based on the entry, pass the parameter to the appropriate class for traversing the string
     * if user decides to quit the program, they can enter Q to do so.
     *
     */
    private void askForInput() {
        String input = "";
        ExpressionTree expIn = new ExpressionTree();

        try {
            while (!input.equals("q") && !input.equals("Q")) {
                System.out.println("Please enter P for postfix boolean expression, I for infix expression: ");
                input = in.nextLine();
                if (input.compareToIgnoreCase("p") == 0) {
                    System.out.println("Please enter a postfix boolean expression: " );
                    input = in.nextLine();

                    expIn = new ExpressionTree(input);
                    System.out.println("Prefix: " +expIn.getPrefixExp());
                    System.out.println("Infix: " +expIn.getInfixExp());
                    System.out.println("Postfix: " +expIn.getPostfixExp());
                    System.out.println();
                    System.out.println("Evaluated Result: " + expIn.evaluate());
                    System.out.println();
                }
                else if (input.compareToIgnoreCase("i") == 0) {
                    System.out.println("Please enter a infix boolean expression: " );
                    input = in.nextLine();

                    String inToPost = expIn.translateToPostfix(input);
                    expIn = new ExpressionTree(inToPost);
                    System.out.println("Prefix: " +expIn.getPrefixExp());
                    System.out.println("Infix: " +expIn.getInfixExp());
                    System.out.println("Postfix: " +expIn.getPostfixExp());
                    System.out.println();
                    System.out.println("Evaluated Result: " + expIn.evaluate());
                    System.out.println();
                }

                System.out.println("Enter Q to exit");
                input = in.nextLine();
            }
        }catch (NullPointerException e) {
            System.out.println("Invalid Expression");
        }

    }
}
