/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

/**
 *
 * @author yt646712
 */
public class Heap {
    static Node[] tab;
    int capacity;   //capacité maximum
    int size;       //remplissage actuel
    
    public Heap(int capacity){
        this.capacity = capacity;
        this.tab = new Node[capacity];
        this.size = 0;
    }
    
    public double pere(int index){
        int v = index / 2;
        if (v > 0 && v <= capacity && tab[v] != null){
            return tab[index / 2].key;
        }
        else{
            return 0;
        }
    }
    
    public double filsGauche(int index){
        int v = 2 * index;
        if (v >= 0 && v < capacity && tab[v] != null){
            return tab[2 * index].key;
        }
        else{
            return Main.INFINI;
        }
    }
    
    
    public double filsDroit(int index){
        int v = 2 * index + 1;
        if (v >= 0 && v < capacity && tab[v] != null){
            return tab[2 * index + 1].key;
        }
        else{
            return Main.INFINI;
        }
    }
    
    private void downHeap(int index){
        if (index < size){
            if (tab[index] != null){
                System.out.println("downHeap");
                if (filsGauche(index) < tab[index].getKey()){
                    System.out.println("FG");
                    swap(index, 2 * index);
                    downHeap(2 * index);

                }
                else if(filsDroit(index) <= tab[index].getKey()){
                    System.out.println("FD");
                    swap(index, 2 * index + 1);
                    downHeap(2 * index + 1);

                }
            }
        }
    }
    
    private void upHeap(int index){
        //System.out.println(index / 2);
        if (pere(index) > tab[index].getKey()){
            swap(index, index / 2);
            upHeap(index / 2);
            System.out.println("upHeap");
        }
    }
    
    private void swap(int index_n1, int index_n2){
        if (index_n1 < size && index_n2 < size){
            if (tab[index_n1] != null && tab[index_n2] != null){
                Node temp = tab[index_n2];
                tab[index_n2] = tab[index_n1];
                tab[index_n1] = temp;
            }
        }
    }
    
    public Object extractRoot(){
        Object output = tab[0].getValue();
        System.out.println(output);

        //première valeur différente de null à la fin du tableau.
        tab[0] = tab[size - 1];
        tab[size - 1] = null;
        downHeap(0);
        return output;
    }
    
    public void addObject(Object obj, double key){
        Node n = new Node(key, obj);
        if (size < capacity){
            tab[size] = n;
        }
        //System.out.println(size);
        upHeap(size);
        size++;
    }
    
    public void clear(){
        for(int i = 0; i < capacity -1; i++){
            tab[i] = null;
        }
        this.size = 0;
    }
    
    public void updateKeyFromKey(double oldKey, double newKey){
        int index = getIndexFromKey(oldKey);
        System.out.println(index);
        tab[index].key = newKey;
        if (pere(index) > newKey){
            upHeap(index);
            System.out.println("update upHeap");
        }
        else if(filsGauche(index) < newKey || filsDroit(index) < newKey){
            downHeap(index);
            System.out.println("update downHeap");
        }
    }
    
    public void updateKeyFromValue(Object val, double newKey){
        int index = getIndexFromValue(val);
        updateKeyFromKey(index, newKey);
    }
    
    public int getIndexFromKey(double key){
        int output = -1;
        for (int i = 0; i < size; i++){
            if (tab[i] != null){
                if (tab[i].key == key){
                    output = i;
                }
            }
        }
        return output;
    }
    
    public int getIndexFromValue(Object val){
        int output = -1;
        for (int i = 0; i < size; i++){
            if (tab[i].value == val){
                output = i;
            }
        }
        return output;
    }
    
    public void removeFromValue(Object val){
        int index = getIndexFromValue(val);
        tab[index] = tab[size];
        tab[size] = null;
        downHeap(index);
    }
    
    public int getSize(){
        return size;
    }
    
    public Object getValueAt(int index){
        if (index < size){
            return tab[index].value;
        }
        else{
            return null;
        }
    }
    
    public double getKeyAt(int index){
        return tab[index].key;
    }
    
    public String toString(){
        String s = "";
        for (int i = 0; i < size; i++){
            if (tab[i] != null){
                s += "" + i + " || " +tab[i].toString() + "\n";
            }
        }
        return s;
    }
    
    private class Node{
        double key;
        Object value;

        public Node(double key, Object value) {
            this.key = key;
            this.value = value;
        }

        public double getKey() {
            return key;
        }

        public void setKey(double key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
        
        public String toString(){
            return ""+key +" - " + value;
        }
    }
}
