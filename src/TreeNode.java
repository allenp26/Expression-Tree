/**
 * Node class to support the expression tree, which
 * stores data and the left/right child of the tree
 *
 * @author Allen Pan
 */
public class TreeNode {
    private char data;
    private TreeNode leftNode;
    private TreeNode rightNode;

    /**
     * default constructor to reset all variables to null
     */
    public TreeNode() {
        this.data = ' ';
        this.leftNode = null;
        this.rightNode = null;
    }

    /**
     * contructor to set the value of a node without
     * assigning the left and right child
     *
     * @param d node value from the string
     */
    public TreeNode (char d) {
        this(d, null, null);
    }

    /**
     * constructor to set the valud of a node with its left
     * and right child
     *
     * @param d node value from the string
     * @param left left child of the node
     * @param right right child of the node
     */
    public TreeNode (char d, TreeNode left, TreeNode right) {
        this.data = d;
        this.leftNode = left;
        this.rightNode = right;
    }

    //getters
    public char getData() { return data; }

    public TreeNode getLeftNode() {
        return leftNode;
    }

    public TreeNode getRightNode() {
        return rightNode;
    }

    //setters
    public void setData(char data) {
        this.data = data;
    }

    public void setLeftNode(TreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(TreeNode rightNode) {
        this.rightNode = rightNode;
    }
}

