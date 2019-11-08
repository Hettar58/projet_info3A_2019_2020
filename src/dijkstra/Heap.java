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
    Node[] tab;
    int capacity;
    int size;
    
    public Heap(int capacity){
        this.capacity = capacity;
        this.tab = new Node[capacity];
        this.size = 0;
    }
    
    private int pere(int index){
        return tab[index / 2].key;
    }
    
    private int filsGauche(int index){
        return tab[2 * index].key;
    }
    
    private int filsDroit(int index){
        return tab[2 * index + 1].key;
    }
    
    private void downHeap(int index){
        if (2 * index < capacity){
            if (filsGauche(index) < tab[index].getKey()){
                swap(index, 2 * index);
                downHeap(2 * index);
            }
            else if(filsDroit(index) < tab[index].getKey()){
                swap(index, 2 * index + 1);
                downHeap(2 * index + 1);
            }
        }
    }
    
    private void upHeap(int index){
        if (index / 2 > 1){
            if (pere(index) > tab[index].getKey()){
                swap(index, index / 2);
                upHeap(index / 2);
            }
        }
    }
    
    private void swap(int index_n1, int index_n2){
        Node temp = tab[index_n1];
        tab[index_n2] = tab[index_n1];
        tab[index_n1] = temp;
    }
    
    // A MODIFIER
    public Object extractRoot(){
        Object output = tab[0].getValue();
        tab[0] = tab[capacity - 1];
        downHeap(0);
        return output;
    }
    
    public void addObject(Object obj, int key){
        Node n = new Node(key, obj);
        boolean inserted = false;
        int i = 0;
        while(inserted == false && i < capacity / 2){
            if (tab[capacity - 1 - i] == null){
                tab[capacity - 1 - i] = n;
                inserted = true;
                i = capacity - 1 - i;
            }
            else if(tab[i] == null){
                tab[i] = n;
                inserted = true;
            }
        }
    }
    
    public void clear(){
        
    }
    
    private class Node{
        int key;
        Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
