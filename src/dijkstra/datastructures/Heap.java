package dijkstra.datastructures;

public class Heap<K extends Comparable<K>, V> {
    Node<K, V>[] tab;
    int capacity;   //capacité max
    int size;       //index de la 1ere case libre, aussi la valeur de remplissage du tableau.

    public Heap(int capacity)
    {
        this.capacity = capacity;
        this.size = 0;
        this.tab = new Node[capacity];
    }

    public K pere(int index)
    {
        int v = index/2;
        if (inRange(v) && tab[v] != null)
        {
            return tab[v].key;
        }
        else return null;    //TODO: faire attention aux appels
    }

    public K filsGauche(int index)
    {
        int v = 2 * index;
        if (inRange(v) && tab[v] != null)
        {
            return tab[v].key;
        }
        else return null;   //TODO: faire attention aux appels
    }

    public K filsDroit(int index)
    {
        int v = 2 * index + 1;
        if (inRange(v) && tab[v] != null)
        {
            return tab[v].key;
        }
        else return null;   //TODO: faire attention aux appels.
    }

    private void downHeap(int index)
    {
        if (index < size && tab[index] != null)
        {
            if (filsGauche(index) != null && filsGauche(index).compareTo(tab[index].getKey()) < 0)
            {
                swap(index, 2 * index);
                downHeap(2*index);
            }

            if (filsDroit(index) != null && filsDroit(index).compareTo(tab[index].getKey()) < 0)
            {
                swap(index, 2 * index + 1);
                downHeap(2 * index + 1);
            }
        }
    }

    private void upHeap(int index)
    {
        if (inRange(index) && tab[index] != null)
        {
            if (pere(index).compareTo(tab[index].getKey()) > 0)
            {
                swap(index, index / 2);
                upHeap(index / 2);
                //downHeap(index);
            }
        }
    }

    public void add(K key, V value)
    {
        Node n = new Node(key, value);
        if (size < capacity)
        {
            tab[size] = n;
        }

        upHeap(size);
        size++;
        //System.out.println(size);
    }

    public void removeRoot()
    {
        swap(0, size -1);
        tab[size - 1] = null;
        downHeap(0);
        size--;
    }

    public V getRootValue()
    {
        return tab[0].value;
    }

    public void updateKeyFromValue(V value, K newKey)
    {
        //on recherche le noeud qui correspond à la valeur.
        //une fois que l'on a, on change sa valeur
        //puis on regarde si l'on doit remonter ou redescendre le noeud
        //une fois terminé, on sort de la boucle.
        for (int i = 0; i < size; i++)
        {
            if (tab[i].value == value)
            {
                tab[i].key = newKey;

                if (pere(i).compareTo(newKey) > 0)
                    upHeap(i);
                else if ((filsGauche(i) != null && filsGauche(i).compareTo(newKey) < 0) || (filsDroit(i) != null && filsDroit(i).compareTo(newKey) < 0))
                    downHeap(i);

                i = size + 1;
            }
        }
    }

    public void clear()
    {
        this.size = 0;
        for (int i = 0; i < capacity; i++)
            tab[i] = null;
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < size; i++)
        {
            s += "" + i + " | pere: " + (i/2) + " || " + tab[i].toString() + "\n";
        }
        return s;
    }

    public int getSize(){return this.size;}

    public V getValueAt(int index)
    {
        if (inRange(index)) {
            if (tab[index] != null) {
                return tab[index].value;
            }
            else return null;
        }
        else return null;
    }

    private void swap(int index1, int index2)
    {
        if (inRange(index1) && inRange(index2))
        {
            Node tmp = tab[index1];
            tab[index1] = tab[index2];
            tab[index2] = tmp;
        }
    }

    private boolean inRange(int index)
    {
        // a voir si borne maximum est size ou capacité.
        return (index >= 0 && index < size && index < capacity);
    }


    private class Node<K, V>
    {
        K key;
        V value;

        public Node(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public String toString(){
            return ""+key.toString() +" - " + value.toString();
        }
    }
}
