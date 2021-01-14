package dijkstra.datastructures;

public class KDtree {
    Node root;

    public KDtree()
    {
        root = null;
    }

    public void add(IPosition obj)
    {
        Node n = new Node(obj);
        if (root == null)
        {
            root = n;
        }
        else
        {
            insert(root, n, 0);
        }
    }

    public void insert(Node current, Node toAdd, int depth)
    {
        if (depth % 2 == 0) //tester l'axe X
        {
        }
    }

    private class Node
    {
        IPosition value;
        Node left;
        Node right;

        public Node(IPosition obj)
        {
            value = obj;
            left = null;
            right = null;
        }
    }
}
