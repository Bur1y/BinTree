import java.util.LinkedList;
import java.util.Objects;

public class BinaryTree {

    static Node root;

    private static int smallestElement(Node root) {
        return root.left == null ? root.value : smallestElement(root.left);
    }

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }

    private Node addElement(Node current, int value) { // Добавить элемент
        if (current == null) { // Если корня дерева нет то вставляем в корень это значение
            return new Node(value);
        }

        if (value < current.value) { // Если < то элемент идёт налево
            current.left = addElement(current.left, value);
        } else if (value > current.value) { // Если > то элемент идёт направо
            current.right = addElement(current.right, value);
        } else {
            return current;
        }

        return current;
    }

    private void add(int value) {
        root = addElement(root, value);
    }

    private static void createBinaryTree() { // Реализуем дерево и заполняем его данными
        BinaryTree bt = new BinaryTree();

        bt.add(5);
        bt.add(1);
        bt.add(8);
        bt.add(7);
        bt.add(9);
        bt.add(4);
        bt.add(2);
    }

    private static Node deleteElement(Node current, int value) { // Удаление элемента
        if (current == null) {
            return null;
        }

        if (value == current.value) {
            if (current.left == null && current.right == null) { // Нет дочерних элементов - просто удаляем
                return null;
            }
            if (current.left != null && current.right != null) { // Если оба дочерних элемента notNull то заменяем на большее
                int smallestInt = smallestElement(current.right);
                current.value = smallestInt;
                current.right = deleteElement(current.right, smallestInt);
                return current;
            }
            return Objects.requireNonNullElseGet(current.right, () -> current.left);
        }

        if (value < current.value) {
            current.left = deleteElement(current.left, value);
            return current;
        }
        current.right = deleteElement(current.right, value);
        return current;
    }

    public static void printByLevel(Node root){
        LinkedList<Node> curLevel = new LinkedList<>();
        LinkedList<Node> nextLevel = curLevel;

        StringBuilder sb = new StringBuilder();
        curLevel.add(root);
        sb.append(root.value).append("\n");

        while(nextLevel.size() > 0){
            nextLevel = new LinkedList<>();
            for (Node cur : curLevel) {
                if (cur.left != null) {
                    nextLevel.add(cur.left);
                    sb.append(cur.left.value).append("__");
                }
                if (cur.right != null) {
                    nextLevel.add(cur.right);
                    sb.append(cur.right.value).append("       ");
                }
            }
            if (nextLevel.size() > 0) {
                sb.append("\n");
                curLevel = nextLevel;

            }
        }
        System.out.println(sb.toString());
    }

    public static void main(String... args) {
        createBinaryTree();
        printByLevel(root);
        System.out.println("_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-\n");
        deleteElement(root,8);
        printByLevel(root);
    }

}
