/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

/**
 *
 * @author yann
 */
public class HeapTest {
    public static void main(String[] args){
        int taille = 15;
        Heap tas = new Heap(taille);
        tas.addObject(null, 1);
        tas.addObject(null, 5);
        tas.addObject(null, 2);
        tas.addObject(null, 3);
        tas.addObject(null, 7);
        tas.addObject(null, 4);
        
        System.out.println(tas.filsGauche(4));
        System.out.println(tas.filsDroit(4));
       
        System.out.println();
        System.out.println(tas.toString());
        System.out.println(tas.size);
    }
}
