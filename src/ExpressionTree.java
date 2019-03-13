import java.util.*;

/**
 * Main processing class to populate an expression tree based
 * on the expression that the user has entered.
 * If it is a postfix expression, populate the tree based on the
 * characters of the expression. If it is a infix expression,
 * transverse it to postfix expression first then populate the tree
 *
 * @author Allen Pan
 * 
 */
public class ExpressionTree {
    private Scanner in = new Scanner(System.in);
    private Stack stackOne = new Stack();
    private Hashtable <Character, Character> hashVal = new Hashtable<>();
    private TreeNode root;
    private int size;

    /**
     * Default constructor to set all values to null
     */
    public ExpressionTree() {
        root = null;
        size = 0;
    }

    /**
     * constructor to validate the postfix expression
     * and populate the tree if it is valid
     *
     * @param postfixExp entered by the user
     */
    public ExpressionTree (String postfixExp) {
        root = null;

        if (validatePostfixExp(postfixExp)) {
            populateTree(postfixExp);
        }
        else {
            throw new NullPointerException();
        }

    }

    /**
     * constructor to set the root value
     *
     * @param root entered by the user
     */
    public ExpressionTree (TreeNode root) {
        this.root = root;
    }

    /**
     * Populate the tree based on the characters of the expression
     * if it is a 0 or 1, it becomes the root of the tree and it is
     * pushed onto the stack.
     * if it is a binary operator, pops the existing nodes from the tree
     * which becomes the left and right node of the binary operator in the tree
     * if it is a unary operator, only pop the right node from the tree whice becomes
     * the right node of the unary operator
     *
     * @param postfixExp entered by the user
     */
    private void populateTree (String postfixExp) {
        ArrayDeque<TreeNode> nodes = new ArrayDeque<>();
        TreeNode right;
        TreeNode left;

        for (int i = 0; i < postfixExp.length(); i++) {
            switch (postfixExp.charAt(i)) {
                case '0':
                    // create node, push to stack
                    root = new TreeNode('0');
                    nodes.push(root);
                    break;

                case '1':
                    // create node, push to stack
                    root = new TreeNode('1');
                    nodes.push(root);
                    break;

                case '|':
                    // create node with left and right subtrees attached from stack, push to stack
                    root = new TreeNode('|');
                    right = nodes.pop();
                    left = nodes.pop();
                    root.setRightNode(right);
                    root.setLeftNode(left);
                    nodes.push(root);
                    break;

                case '&':
                    // create node with left and right subtrees attached from stack, push to stack
                    root = new TreeNode('&');
                    right = nodes.pop();
                    left = nodes.pop();
                    root.setRightNode(right);
                    root.setLeftNode(left);
                    nodes.push(root);
                    break;

                case '^':
                    // create node with left and right subtrees attached from stack, push to stack
                    root = new TreeNode('^');
                    right = nodes.pop();
                    left = nodes.pop();
                    root.setRightNode(right);
                    root.setLeftNode(left);
                    nodes.push(root);
                    break;

                case '!':
                    // create node with subtree attached, push to stack
                    root = new TreeNode('!');
                    right = nodes.pop();
                    root.setRightNode(right);
                    nodes.push(root);
                    break;
                default:
                    root = new TreeNode(postfixExp.charAt(i));
                    nodes.push(root);
                    break;
            }
        }

        root = nodes.pop();
    }

    /**
     * validate the expression based on the position of the
     * operators in the expression. If the operators are
     * in the wrong place for a postfix expression then
     * the counter will go below zero at some point in the process
     * which will result in a invalid postfix expression
     *
     * @param postfixExp entered by the user
     * @return true if the expression is valid, vice versa
     */
    private boolean validatePostfixExp(String postfixExp) {
        boolean retval = false;
        boolean invalidExp = false;
        int counter = 0;

        for (int i = 0; i < postfixExp.length(); i++) {
            char c = postfixExp.charAt(i);

            if (foundLiteral(c)) {
                counter++;
            }
            else if (foundBinaryOp(c)) {
                counter--;
                counter--;
                if (counter < 0) {
                    invalidExp = true;
                }
                counter++;
            }
            else if (foundUnaryOp(c)) {
                counter--;
                if (counter < 0) {
                    invalidExp = true;
                }
                counter++;
            }

        }

        if (counter == 1 && !invalidExp) {
            retval = true;
        }

        return retval;
    }

    /**
     * checks to see if the character is a literal
     *
     * @param c character from the expression
     * @return true is is literal
     */
    private boolean foundLiteral(char c) {
        boolean retval = false;

        if (Character.isLetter(c) || c == '1' || c == '0') {
            retval = true;
        }

        return retval;
    }

    /**
     * checks to see if the character is a binary operator
     *
     * @param c character from the expression
     * @return true if c is a binary operator
     */
    private boolean foundBinaryOp(char c) {
        boolean retval = false;

        if (c == '&' || c == '|' || c == '^') {
            retval = true;
        }

        return retval;
    }

    /**
     * checks to see if the character is an unary operator
     *
     * @param c character from the expression
     * @return true if c is an unary operator
     */
    private boolean foundUnaryOp(char c) {
        boolean retval = false;

        if (c == '!') {
            retval = true;
        }

        return retval;
    }

    /**
     * getter for the postfix expression, which
     * is built from the helper method
     *
     *
     * @return postfix expression
     */
    public String getPostfixExp()
    {
        StringBuilder postfixExp = new StringBuilder();
        return buildPostfixExp(root, postfixExp);
    }


    /**
     * builds the postfix expression based on the tree nodes
     *
     * @param node TreeNodes that have been populated from the expression
     * @param postfixExp StringBuilder to append the characters
     * @return postfix expression
     */
    private String buildPostfixExp(TreeNode node, StringBuilder postfixExp)
    {

        // traverse the tree in postfix order to build postfix expression
        if (node != null)
        {
            if (foundBinaryOp(node.getData())) {
                buildPostfixExp(node.getLeftNode(), postfixExp);
                buildPostfixExp(node.getRightNode(), postfixExp);
            }
            else if (foundUnaryOp(node.getData())) {
                buildPostfixExp(node.getRightNode(), postfixExp);
            }

            postfixExp.append(node.getData());
        }

        return postfixExp.toString();
    }


    /**
     * getter for the infix expression, which is built by a
     * helper method
     *
     * @return infix expression
     */
    public String getInfixExp()
    {
        StringBuilder infixExp = new StringBuilder();
        return buildInfixExp(root, infixExp);
    }

    /**
     * build infix expression based on the tree nodes using stringbuilder
     *
     * @param node root node from the tree
     * @param infixExp StringBuilder
     * @return  infix expression
     */
    private String buildInfixExp(TreeNode node, StringBuilder infixExp)
    {
        // traverse tree in in-fix order to create infix expression
        if (node != null)
        {
            if (foundBinaryOp(node.getData())) {
                infixExp.append("(");
                buildInfixExp(node.getLeftNode(), infixExp);
                infixExp.append(node.getData());
                buildInfixExp(node.getRightNode(), infixExp);
                infixExp.append(")");
            }
            else if (foundUnaryOp(node.getData())) {
                infixExp.append("(");
                infixExp.append(node.getData());
                buildInfixExp(node.getRightNode(), infixExp);
                infixExp.append(")");
            }
            else if (foundLiteral(node.getData())) {
                infixExp.append(node.getData());
            }

        }
        return infixExp.toString();
    }

    /**
     * gettier for the prefix expression
     *
     * @return buildPrefixExp StringBuilder for the expression
     */
    public String getPrefixExp()
    {
        StringBuilder prefixExp = new StringBuilder();
        return buildPrefixExp(root, prefixExp);
    }

    /**
     * builder for the prefix expression based on the nodes from the tree
     *
     * @param node root node from the expression tree
     * @param prefixExp StringBuilder for prefix expression
     * @return prefix expression string
     */
    private String buildPrefixExp(TreeNode node, StringBuilder prefixExp)
    {
        // traverse tree in prefix order to create prefix expression
        if ( node != null )
        {
            prefixExp.append(node.getData());
            buildPrefixExp(node.getLeftNode(), prefixExp);
            buildPrefixExp(node.getRightNode(), prefixExp);
        }
        return prefixExp.toString();
    }

    /**
     * getter method for the evaluate value,
     * if the helper method returns 0 then it is false
     * else it is true
     *
     * @return false if retval is zero
     */
    public boolean evaluate() {
        char retval = evaluate(root);

        if (retval == '0') {
            return false;
        }
        else {
            return true;
        }

    }

    /**
     * evaluate each node and its left and right child to see if
     * any of the nodes are letters, if so pass on to another method
     * which grabs the value from the user
     *
     * @param n node from the tree
     * @return evaluateExp char value
     */
    private char evaluate (TreeNode n) {

        if (n.getLeftNode() == null && n.getRightNode() == null) {
            return n.getData();
        }
        else {
            char retval = ' ';
            char L = ' ';
            char R = evaluate(n.getRightNode());
            if (n.getLeftNode() != null) {
                L = evaluate(n.getLeftNode());
            }
            if (Character.isLetter(L) || Character.isLetter(R)) {
                if (Character.isDigit(L)) {
                    L = n.getLeftNode().getData();
                    R = getValue(R);
                }
                else if (Character.isDigit(R)) {
                    L = getValue(L);
                    R = n.getRightNode().getData();
                }
                else {
                    L = getValue(L);
                    R = getValue(R);
                }
            }

            char op = n.getData();
            return evaluateExp(L, R, op);

        }
    }

    /**
     * evaluate the expression based on the operator and the left
     * and right child, based on the operator type, call different
     * helper method to determine the result
     *
     * @param l left node
     * @param r right node
     * @param op operator
     * @return char value of 0 or 1
     */
    private char evaluateExp(char l, char r, char op) {
        char retval = ' ';

        if (op == '!') {
            retval = NOTop(r);
        }
        else if (op == '&') {
            retval = ANDop(l, r);
        }
        else if (op == '|') {
            retval = ORop(l, r);
        }
        else if (op == '^') {
            retval = XORop(l, r);
        }

        return retval;
    }

    /**
     * checks to see if the comparison returns true(1) or
     * false (0) based on the operator AND
     *
     * @param l left node
     * @param r right node
     * @return 1 or 0
     */
    private char ANDop(char l, char r) {
        char retval;

        if (l == '1' && r == '1') {
            retval = 1;
        }
        else if (l == '0' && r == '0') {
            retval = 1;
        }
        else {
            retval = 0;
        }

        return retval;
    }

    /**
     * checks to see if the comparison returns true(1) or
     * false (0) based on the operator OR
     *
     * @param l left node
     * @param r right node
     * @return 1 or 0
     */
    private char ORop(char l, char r) {
        char retval;

        if (l == '1' || r == '1') {
            retval = '1';
        }
        else {
            retval = '0';
        }

        return retval;
    }

    /**
     *checks to see if the comparison returns true(1) or
     * false (0) based on the operator NOT
     *
     * @param r right node
     * @return 1 or 0
     */
    private char NOTop(char r) {
        char retval;

        if (r == '0') {
            retval = '1';
        }
        else {
            retval = '0';
        }

        return retval;
    }

    /**
     * checks to see if the comparison returns true(1) or
     * false (0) based on the operator XOR
     *
     * @param l left node
     * @param r right node
     * @return 1 or 0
     */
    private char XORop(char l, char r) {
        char retval;

        if (l == '0' && r == '1') {
            retval = '1';
        }
        else if (l == '1' && r == '0') {
            retval = '1';
        }
        else {
            retval = '0';
        }

        return retval;
    }

    /**
     * grab the value of a letter from the user, which can
     * only be 1 or 0
     *
     *
     * @param c letter in the expression
     * @return 1 or 0
     */
    private char getValue(char c) {
        char inp;

        if (hashVal.containsKey(c)) {
            inp = hashVal.get(c);
        }
        else {
            System.out.println("Enter a value ('0' or '1') for variable:" + c);
            inp = in.nextLine().charAt(0);
            if (inp == '0' || inp == '1') {
                hashVal.put(c, inp);
            }
            else {
                System.out.println("The number you have entered is invalid.");
                System.out.println("Please enter '0' or '1': ");
            }
        }

        return inp;
    }

    /** Translates an infix expression to a postfix expression using the shunting yard algorithm
     *
     * The following steps are performed :
     * <ul>While there are tokens to be read:
     * <li>Read a token.
     * <li>If the token is a boolean, then push it to the output queue.
     * <li>If the token is an operator, o1, then:
     * <li>  while there is an operator token o2, at the top of the operator stack and its precedence is less than or equal to that of o2
     * <li>    pop o2 off the operator stack, onto the output queue;
     * <li>  at the end of iteration push o1 onto the operator stack.
     * <li>If the token is a left parenthesis (i.e. "("), then push it onto the stack.
     * <li>If the token is a right parenthesis (i.e. ")"):
     * <li>  Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
     * <li>  Pop the left parenthesis from the stack, but not onto the output queue.
     * <li>  If the token at the top of the stack is a function token, pop it onto the output queue.
     * <li>  If the stack runs out without finding a left parenthesis, then there are mismatched parentheses.
     * <li>When there are no more tokens to read:
     * <li>  While there are still operator tokens in the stack:
     * <li>    If the operator token on the top of the stack is a parenthesis, then there are mismatched parentheses.
     * <li>    Pop the operator onto the output queue.
     * </ul>
     *
     */
    public String translateToPostfix(String infix)
    {
        StringBuilder postfix = new StringBuilder();
        Stack<String> operator = new Stack<>();

        // perform shunting yard algorithm

        for (String token: infix.split("")) {
            if (ops.containsKey(token)) {
                while (!operator.isEmpty() && GreaterPrec(token, operator.peek())) {
                    postfix.append(operator.pop());
                }
                operator.push(token);
            }
            else if (token.equals("(")) {
                operator.push(token);
            }
            else if (token.equals(")")) {
                while (!operator.peek().equals("(")) {
                    postfix.append(operator.pop());
                }
                operator.pop();
            }
            else {
                postfix.append(token);
            }
        }

        while (!operator.isEmpty()) {
            postfix.append(operator.pop());
        }

        // return the postfix expression as a String
        return postfix.toString();
    }

    /**
     * enum class for the operators and its precedence
     */
    private enum Operator {
        NOT(1), AND(2), XOR(3), OR(4);
        final int precedence;
        Operator(int p) { precedence = p; }
    }

    /**
     * assigns each operator with its symbol
     */
    private Map<String, Operator> ops = new HashMap<>() {{
        put("!", Operator.NOT);
        put("&", Operator.AND);
        put("^", Operator.XOR);
        put("|", Operator.OR);
    }};

    /**
     * checks to see if the operator has greater precedence than the other
     *
     * @param op operator from the string
     * @param sub another operator from the stack
     * @return true or false
     */
    private boolean GreaterPrec(String op, String sub) {
        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
    }


}
