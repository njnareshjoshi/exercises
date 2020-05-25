package org.programming.mitra.exercises.ds;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.programming.mitra.exercises.ds.RedBlackTree.Color.BLACK;
import static org.programming.mitra.exercises.ds.RedBlackTree.Color.RED;

/**
 * Red Black Tree (RBT) is a self-balanced binary search tree with one extra info of color at each node.
 * The leaf nodes of RBT do not contain data and called extended nodes or explicit nodes,
 * These can be simply be Null or a single sentinel node.
 *
 * Complexities
 * Space    O(n)
 * Search	O(log n)
 * Insert	O(log n)
 * Delete	O(log n)
 *
 * Rules for RBT
 * 1. Every node is either red or back,
 * 2. Root node is always black,
 * 3. All external nodes or leaf nodes are black
 * 4. If a node is red, then both its children are black, A red node can never has red child,
 * 5. Every path from a given node to any of its descendant leaf nodes goes through the same number of black nodes.
 *
 * Black Depth of a node = number of black nodes from the root to that node,
 * Black Height of the tree = number of black nodes in any path from the root to the leaves,
 * According to point 5, Black Depth of any leaf node = Black Height of tree
 *
 * Which means the path from the root to the farthest leaf is no more than twice as long as the path from the root to the nearest leaf.
 *
 * @author Naresh Joshi
 */
public class RedBlackTree {

    public enum Color {
        RED, BLACK
    }

    public static class Node {
        int data;
        Color color;

        Node parent;
        Node leftChild;
        Node rightChild;

        boolean isLeaf;
    }

    private static Node createBlackNode(int data) {
        Node node = new Node();
        node.data = data;
        node.color = BLACK;
        node.leftChild = createLeafNode(node);
        node.rightChild = createLeafNode(node);
        return node;
    }

    private static Node createLeafNode(Node parent) {
        Node leaf = new Node();
        leaf.color = BLACK;
        leaf.isLeaf = true;
        leaf.parent = parent;
        return leaf;
    }

    private static Node createRedNode(Node parent, int data) {
        Node node = new Node();
        node.data = data;
        node.color = RED;
        node.parent = parent;
        node.leftChild = createLeafNode(node);
        node.rightChild = createLeafNode(node);
        return node;
    }

    /**
     * Main insert method of red black tree.
     */
    public Node insert(Node root, int data) {
        return insert(null, root, data);
    }

    /**
     * Main delete method of red black tree.
     */
    public Node delete(Node root, int data) {
        AtomicReference<Node> rootReference = new AtomicReference<>();
        delete(root, data, rootReference);
        if (rootReference.get() == null) {
            return root;
        } else {
            return rootReference.get();
        }
    }

    /**
     * Main print method of red black tree.
     */
    public void printRedBlackTree(Node root) {
        printRedBlackTree(root, 0);
    }

    /**
     * Main validate method of red black tree.
     */
    public boolean validateRedBlackTree(Node root) {

        if (root == null) {
            return true;
        }
        //check if root is black
        if (root.color != BLACK) {
            System.out.print("Root is not black");
            return false;
        }
        //Use of AtomicInteger solely because java does not provide any other mutable int wrapper.
        AtomicInteger blackCount = new AtomicInteger(0);
        //make sure black count is same on all path and there is no red red relationship
        return checkBlackNodesCount(root, blackCount, 0) && noRedRedParentChild(root, BLACK);
    }

    private void rightRotate(Node root, boolean changeColor) {
        Node parent = root.parent;
        root.parent = parent.parent;
        if (parent.parent != null) {
            if (parent.parent.rightChild == parent) {
                parent.parent.rightChild = root;
            } else {
                parent.parent.leftChild = root;
            }
        }
        Node right = root.rightChild;
        root.rightChild = parent;
        parent.parent = root;
        parent.leftChild = right;
        if (right != null) {
            right.parent = parent;
        }
        if (changeColor) {
            root.color = BLACK;
            parent.color = RED;
        }
    }

    private void leftRotate(Node root, boolean changeColor) {
        Node parent = root.parent;
        root.parent = parent.parent;
        if (parent.parent != null) {
            if (parent.parent.rightChild == parent) {
                parent.parent.rightChild = root;
            } else {
                parent.parent.leftChild = root;
            }
        }
        Node left = root.leftChild;
        root.leftChild = parent;
        parent.parent = root;
        parent.rightChild = left;
        if (left != null) {
            left.parent = parent;
        }
        if (changeColor) {
            root.color = BLACK;
            parent.color = RED;
        }
    }

    private Optional<Node> findSiblingNode(Node root) {
        Node parent = root.parent;
        if (isLeftChild(root)) {
            return Optional.ofNullable(parent.rightChild.isLeaf ? null : parent.rightChild);
        } else {
            return Optional.ofNullable(parent.leftChild.isLeaf ? null : parent.leftChild);
        }
    }

    private boolean isLeftChild(Node root) {
        Node parent = root.parent;
        if (parent.leftChild == root) {
            return true;
        } else {
            return false;
        }
    }

    private Node insert(Node parent, Node root, int data) {
        if (root == null || root.isLeaf) {
            //if parent is not null means tree is not empty
            //so create a red leaf node
            if (parent != null) {
                return createRedNode(parent, data);
            } else { //otherwise create a black root node if tree is empty
                return createBlackNode(data);
            }
        }

        //duplicate insertion is not allowed for this tree.
        if (root.data == data) {
            throw new IllegalArgumentException("Duplicate date " + data);
        }
        //if we go on left side then isLeft will be true
        //if we go on right side then isLeft will be false.
        boolean isLeft;
        if (root.data > data) {
            Node left = insert(root, root.leftChild, data);
            //if left becomes root parent means rotation
            //happened at lower level. So just return left
            //so that nodes at upper level can set their
            //child correctly
            if (left == root.parent) {
                return left;
            }
            //set the left child returned to be left of root node
            root.leftChild = left;
            //set isLeft to be true
            isLeft = true;
        } else {
            Node right = insert(root, root.rightChild, data);
            //if right becomes root parent means rotation
            //happened at lower level. So just return right
            //so that nodes at upper level can set their
            //child correctly
            if (right == root.parent) {
                return right;
            }
            //set the right child returned to be right of root node
            root.rightChild = right;
            //set isRight to be true
            isLeft = false;
        }

        if (isLeft) {
            //if we went to left side check to see Red-Red conflict
            //between root and its left child
            if (root.color == RED && root.leftChild.color == RED) {
                //get the sibling of root. It is returning optional means
                //sibling could be empty
                Optional<Node> sibling = findSiblingNode(root);
                //if sibling is empty or of BLACK color
                if (!sibling.isPresent() || sibling.get().color == BLACK) {
                    //check if root is left child of its parent
                    if (isLeftChild(root)) {
                        //this creates left left situation. So do one right rotate
                        rightRotate(root, true);
                    } else {
                        //this creates left-right situation so do one right rotate followed
                        //by left rotate

                        //do right rotation without change in color. So sending false.
                        //when right rotation is done root becomes right child of its left
                        //child. So make root = root.parent because its left child before rotation
                        //is new root of this subtree.
                        rightRotate(root.leftChild, false);
                        //after rotation root should be root's parent
                        root = root.parent;
                        //then do left rotate with change of color
                        leftRotate(root, true);
                    }

                } else {
                    //we have sibling color as RED. So change color of root
                    //and its sibling to Black. And then change color of their
                    //parent to red if their parent is not a root.
                    root.color = BLACK;
                    sibling.get().color = BLACK;
                    //if parent's parent is not null means parent is not root.
                    //so change its color to RED.
                    if (root.parent.parent != null) {
                        root.parent.color = RED;
                    }
                }
            }
        } else {
            //this is mirror case of above. So same comments as above.
            if (root.color == RED && root.rightChild.color == RED) {
                Optional<Node> sibling = findSiblingNode(root);
                if (!sibling.isPresent() || sibling.get().color == BLACK) {
                    if (!isLeftChild(root)) {
                        leftRotate(root, true);
                    } else {
                        leftRotate(root.rightChild, false);
                        root = root.parent;
                        rightRotate(root, true);
                    }
                } else {
                    root.color = BLACK;
                    sibling.get().color = BLACK;
                    if (root.parent.parent != null) {
                        root.parent.color = RED;
                    }
                }
            }
        }
        return root;
    }

    /**
     * Using atomicreference because java does not provide mutable wrapper. Its like
     * double pointer in C.
     */
    private void delete(Node root, int data, AtomicReference<Node> rootReference) {
        if (root == null || root.isLeaf) {
            return;
        }
        if (root.data == data) {
            //if node to be deleted has 0 or 1 null children then we have
            //deleteOneChild use case as discussed in video.
            if (root.rightChild.isLeaf || root.leftChild.isLeaf) {
                deleteOneChild(root, rootReference);
            } else {
                //otherwise look for the inorder successor in right subtree.
                //replace inorder successor data at root data.
                //then delete inorder successor which should have 0 or 1 not null child.
                Node inorderSuccessor = findSmallest(root.rightChild);
                root.data = inorderSuccessor.data;
                delete(root.rightChild, inorderSuccessor.data, rootReference);
            }
        }
        //search for the node to be deleted.
        if (root.data < data) {
            delete(root.rightChild, data, rootReference);
        } else {
            delete(root.leftChild, data, rootReference);
        }
    }

    private Node findSmallest(Node root) {
        Node prev = null;
        while (root != null && !root.isLeaf) {
            prev = root;
            root = root.leftChild;
        }
        return prev != null ? prev : root;
    }

    /**
     * Assumption that node to be deleted has either 0 or 1 non leaf child
     */
    private void deleteOneChild(Node nodeToBeDelete, AtomicReference<Node> rootReference) {
        Node child = nodeToBeDelete.rightChild.isLeaf ? nodeToBeDelete.leftChild : nodeToBeDelete.rightChild;
        //replace node with either one not null child if it exists or null child.
        replaceNode(nodeToBeDelete, child, rootReference);
        //if the node to be deleted is BLACK. See if it has one red child.
        if (nodeToBeDelete.color == BLACK) {
            //if it has one red child then change color of that child to be Black.
            if (child.color == RED) {
                child.color = BLACK;
            } else {
                //otherwise we have double black situation.
                deleteCase1(child, rootReference);
            }
        }
    }


    /**
     * If double black node becomes root then we are done. Turning it into
     * single black node just reduces one black in every path.
     */
    private void deleteCase1(Node doubleBlackNode, AtomicReference<Node> rootReference) {
        if (doubleBlackNode.parent == null) {
            rootReference.set(doubleBlackNode);
            return;
        }
        deleteCase2(doubleBlackNode, rootReference);
    }

    /**
     * If sibling is red and parent and sibling's children are black then rotate it
     * so that sibling becomes black. Double black node is still double black so we need
     * further processing.
     */
    private void deleteCase2(Node doubleBlackNode, AtomicReference<Node> rootReference) {
        Node siblingNode = findSiblingNode(doubleBlackNode).get();
        if (siblingNode.color == RED) {
            if (isLeftChild(siblingNode)) {
                rightRotate(siblingNode, true);
            } else {
                leftRotate(siblingNode, true);
            }
            if (siblingNode.parent == null) {
                rootReference.set(siblingNode);
            }
        }
        deleteCase3(doubleBlackNode, rootReference);
    }

    /**
     * If sibling, sibling's children and parent are all black then turn sibling into red.
     * This reduces black node for both the paths from parent. Now parent is new double black
     * node which needs further processing by going back to case1.
     */
    private void deleteCase3(Node doubleBlackNode, AtomicReference<Node> rootReference) {

        Node siblingNode = findSiblingNode(doubleBlackNode).get();

        if (doubleBlackNode.parent.color == BLACK && siblingNode.color == BLACK && siblingNode.leftChild.color == BLACK
                && siblingNode.rightChild.color == BLACK) {
            siblingNode.color = RED;
            deleteCase1(doubleBlackNode.parent, rootReference);
        } else {
            deleteCase4(doubleBlackNode, rootReference);
        }
    }

    /**
     * If sibling color is black, parent color is red and sibling's children color is black then swap color b/w sibling
     * and parent. This increases one black node on double black node path but does not affect black node count on
     * sibling path. We are done if we hit this situation.
     */
    private void deleteCase4(Node doubleBlackNode, AtomicReference<Node> rootReference) {
        Node siblingNode = findSiblingNode(doubleBlackNode).get();
        if (doubleBlackNode.parent.color == RED && siblingNode.color == BLACK && siblingNode.leftChild.color == BLACK
                && siblingNode.rightChild.color == BLACK) {
            siblingNode.color = RED;
            doubleBlackNode.parent.color = BLACK;
            return;
        } else {
            deleteCase5(doubleBlackNode, rootReference);
        }
    }

    /**
     * If sibling is black, double black node is left child of its parent, siblings right child is black
     * and sibling's left child is red then do a right rotation at siblings left child and swap colors.
     * This converts it to delete case6. It will also have a mirror case.
     */
    private void deleteCase5(Node doubleBlackNode, AtomicReference<Node> rootReference) {
        Node siblingNode = findSiblingNode(doubleBlackNode).get();
        if (siblingNode.color == BLACK) {
            if (isLeftChild(doubleBlackNode) && siblingNode.rightChild.color == BLACK && siblingNode.leftChild.color == RED) {
                rightRotate(siblingNode.leftChild, true);
            } else if (!isLeftChild(doubleBlackNode) && siblingNode.leftChild.color == BLACK && siblingNode.rightChild.color == RED) {
                leftRotate(siblingNode.rightChild, true);
            }
        }
        deleteCase6(doubleBlackNode, rootReference);
    }

    /**
     * If sibling is black, double black node is left child of its parent, sibling left child is black and sibling's right child is
     * red, sibling takes its parent color, parent color becomes black, sibling's right child becomes black and then do
     * left rotation at sibling without any further change in color. This removes double black and we are done. This
     * also has a mirror condition.
     */
    private void deleteCase6(Node doubleBlackNode, AtomicReference<Node> rootReference) {
        Node siblingNode = findSiblingNode(doubleBlackNode).get();
        siblingNode.color = siblingNode.parent.color;
        siblingNode.parent.color = BLACK;
        if (isLeftChild(doubleBlackNode)) {
            siblingNode.rightChild.color = BLACK;
            leftRotate(siblingNode, false);
        } else {
            siblingNode.leftChild.color = BLACK;
            rightRotate(siblingNode, false);
        }
        if (siblingNode.parent == null) {
            rootReference.set(siblingNode);
        }
    }

    private void replaceNode(Node root, Node child, AtomicReference<Node> rootReference) {
        child.parent = root.parent;
        if (root.parent == null) {
            rootReference.set(child);
        } else {
            if (isLeftChild(root)) {
                root.parent.leftChild = child;
            } else {
                root.parent.rightChild = child;
            }
        }
    }

    private void printRedBlackTree(Node root, int space) {
        if (root == null || root.isLeaf) {
            return;
        }
        printRedBlackTree(root.rightChild, space + 5);
        for (int i = 0; i < space; i++) {
            System.out.print(" ");
        }
        System.out.println(root.data + " " + (root.color == BLACK ? "B" : "R"));
        printRedBlackTree(root.leftChild, space + 5);
    }

    private boolean noRedRedParentChild(Node root, Color parentColor) {
        if (root == null) {
            return true;
        }
        if (root.color == RED && parentColor == RED) {
            return false;
        }

        return noRedRedParentChild(root.leftChild, root.color) && noRedRedParentChild(root.rightChild, root.color);
    }

    private boolean checkBlackNodesCount(Node root, AtomicInteger blackCount, int currentCount) {

        if (root.color == BLACK) {
            currentCount++;
        }

        if (root.leftChild == null && root.rightChild == null) {
            if (blackCount.get() == 0) {
                blackCount.set(currentCount);
                return true;
            } else {
                return currentCount == blackCount.get();
            }
        }
        return checkBlackNodesCount(root.leftChild, blackCount, currentCount) && checkBlackNodesCount(root.rightChild, blackCount, currentCount);
    }

    public static void main(String args[]) {
        Node root = null;
        RedBlackTree redBlackTree = new RedBlackTree();

        root = redBlackTree.insert(root, 10);
        root = redBlackTree.insert(root, 15);
        root = redBlackTree.insert(root, -10);
        root = redBlackTree.insert(root, 20);
        root = redBlackTree.insert(root, 30);
        root = redBlackTree.insert(root, 40);
        root = redBlackTree.insert(root, 50);
        root = redBlackTree.insert(root, -15);
        root = redBlackTree.insert(root, 25);
        root = redBlackTree.insert(root, 17);
        root = redBlackTree.insert(root, 21);
        root = redBlackTree.insert(root, 24);
        root = redBlackTree.insert(root, 28);
        root = redBlackTree.insert(root, 34);
        root = redBlackTree.insert(root, 32);
        root = redBlackTree.insert(root, 26);
        root = redBlackTree.insert(root, 35);
        root = redBlackTree.insert(root, 19);
        redBlackTree.printRedBlackTree(root);

        root = redBlackTree.delete(root, 50);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 40);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, -10);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 15);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 17);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 24);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 21);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 32);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 26);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 19);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 25);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 17);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, -15);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 20);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 35);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 34);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 30);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 28);
        System.out.println(redBlackTree.validateRedBlackTree(root));
        root = redBlackTree.delete(root, 10);
        System.out.println(redBlackTree.validateRedBlackTree(root));
    }
}

