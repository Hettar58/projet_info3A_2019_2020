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
    
       public static void main(String[] args){
        int taille = 15;
        Heap tas = new Heap(taille);
        tas.addObject(null, 1);
        tas.addObject(null, 5);
        tas.addObject(null, 2);
        tas.addObject(null, 3);
        tas.addObject(null, 7);
        tas.addObject(null, 4);
        
        System.out.println(tas.filsGauche(1));
        System.out.println(tas.filsDroit(1));
       
        System.out.println();
        System.out.println(tas.toString());
        System.out.println(tas.size);
    }
        
        
    Node[] tab;
    int capacity;   //capacité maximum
    int size;       //remplissage actuel
    
    public Heap(int capacity){
        this.capacity = capacity;
        this.tab = new Node[capacity];
        this.size = 0;
    }
    
    public int pere(int index){
        if (index/2 > 0 && index/2 < capacity){
            return tab[index / 2].key;
        }
        else{
            return 0;
        }
    }
    
    public int filsGauche(int index){
        return tab[2 * index].key;
    }
    
    
    public int filsDroit(int index){
        return tab[2 * index + 1].key;
    }
    
    private void downHeap(int index){
        if (2 * index <= size){
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
        //System.out.println(index / 2);
            if (pere(index) > tab[index].getKey()){
                swap(index, index / 2);
                upHeap(index / 2);
                System.out.println("upHeap");
            }
      
    }
    
    private void swap(int index_n1, int index_n2){
        Node temp = tab[index_n1];
        tab[index_n2] = tab[index_n1];
        tab[index_n1] = temp;
    }
    
    public Object extractRoot(){
        Object output = tab[0].getValue();
        
        //première valeur différente de null à la fin du tableau.
        tab[0] = tab[size - 1];
        downHeap(0);
        return output;
    }
    
    public void addObject(Object obj, int key){
        Node n = new Node(key, obj);
        size++;
        int i = size;
        if (size < capacity){
            tab[size] = n;
        }
        System.out.println(size);
        upHeap(i);
    }
    
    public void clear(){
        
    }
    
    public int getSize(){
        return size;
    }
    
    public String toString(){
        String s = "";
        for (int i = 0; i <= size; i++){
            if (tab[i] != null){
                s += tab[i].toString() + "\n";
            }
        }
        return s;
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
        
        public String toString(){
            return ""+key +" | " + value;
        }
    }
}
