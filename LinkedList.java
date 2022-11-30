/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package i.d;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author santi
 */
public class LinkedList {

    private static int cont;
    
    
    public LinkedList(){
        cont = 0;
    }
    
    public int comparisons(){
        return cont;
    }
    
    public static double [] bruteForce(Lista coordinates, double minDist){
        double min = minDist;
        double[] minDistV = new double [3];
        minDistV[0] = min;
        for (int i = 0; i < coordinates.size; i++) {
            for (int j = i + 1; j < coordinates.size; j++) {
                double dis = distancia(coordinates, i, j);
                if (dis < min) {
                    cont = cont + 1;
                    min = dis;
                    minDistV[0] = dis;
                    minDistV[1] = coordinates.get(i).p.pos;
                    minDistV[2] = coordinates.get(i).p.pos;
                } else{
                    cont = cont + 1;
                }
            }
        }
        return minDistV;
    }
    
    public static void Print(List<int[]> coordinates){
        for (int i = 0; i < coordinates.size(); i++) {
            System.out.println("x: " + coordinates.get(i)[0] + " y: " + coordinates.get(i)[1] + " pos: " + coordinates.get(i)[2]);
        }
    }
    
    public static Lista EncCandidatos(Lista coords, double min) { // We check the pairs near the middles closer to each other than that found in the quadrants.
        ArrayList<Punto> cand = new ArrayList<Punto>();
        int i = 0;
        while (i < coords.size / 2) { // Comparing the distance in both x and y position of the points in the border
            // of both subsets.
            if (Math.abs(coords.get(i).p.x - coords.get(coords.size / 2).p.x) < min && Math.abs(coords.get(i).p.y - coords.get(coords.size / 2).p.y) < min) {
                cont++;
                cand.add(coords.get(i).p); // If the distance is less than the actual minimum distance, they are candidates.
                i++;
            } else {
                cont++;
                i = coords.size / 2;
            }
        }
        while (i < coords.size) { // We compare first the points in one subset and then the other to avoid
                                  // repeating points.
            if (Math.abs(coords.get(i).p.x - coords.get(coords.size / 2 - 1).p.x) < min
                    && Math.abs(coords.get(i).p.y - coords.get(coords.size / 2 - 1).p.y) < min) {
                cont++;
                cand.add(coords.get(i).p);
                i++;
            } else {
                cont++;
                i = coords.size;
            }
        }
        // Converting ArrayList to Listy
        Lista listc;
        if (cand.isEmpty()) {
            listc = new Lista(null, cand.size());
        } else {
            listc = new Lista(new Nodo(cand.get(0)), cand.size());
            Nodo P = listc.head;
            for (int j = 1; j < cand.size(); j++) {
                P.next = new Nodo(cand.get(j));
                P = P.next;
            }
        }
        return listc;
    }
    
    public static double[] ClosestPair(int N, Lista x, double mdis) {
        // Finds closest pair of a given set of coordinates via Brute Force Algorithm,
        // but using recursion.
        if (N > 3) { // If there are more than 3 points in a region, we divide it in 2.
            double[] g1 = new double[3];
            double[] g2 = new double[3];
            int offset = 0;
            cont++;
            if (N % 2 == 1) {
                offset = 1; // Taking into account an odd sized sample. The second half will 1 unit bigger
                            // thean the first half.
            }
            // Splits in half the set of coordinates until there are only 3 or less coords
            // and apply the algorithm in both halfs, then compare them.
            Lista list2 = x.subList(N / 2, N, N / 2);
            Lista list1 = x.subList(0, N / 2, N / 2);
            g1 = ClosestPair(N / 2, list1, mdis);
            g2 = ClosestPair(N / 2, list2, mdis);
            double[] g = new double[3];
            if (g1[0] < g2[0]) { // Store the closest pair of the regions.
                g = g1;
                cont++;
            } else {
                cont++;
                g = g2;
            }
            Lista candidatos = EncCandidatos(x, g[0]); // List to store possible candidates.
            // Apply the Brute Force algorithm to the set of candidates if there are at
            // least 2.
            if (candidatos.size > 1) {
                cont++;
                g1 = bruteForce(candidatos, mdis);
                if (g1[0] < g[0]) {
                    cont++;
                    return g1; // Compare the actual min_dis with the one obtained form the candidates and
                               // return the lowest.
                } else {
                    cont++;
                    return g;
                }
            } else {
                cont++;
                return g;
            }
        } else {
            cont++;
            double[] vec = new double[3];
            return bruteForce(x, mdis); // Apply BruteForce algorithm when there are 3 or less coords.
        }
    }
    public static double distancia(Lista coords, int i, int j) { // computes the distance between the ith and jth
                                                                // elements
        // unpacks coordinates of the ith and jth elements.
        int x1 = coords.get(i).p.x;
        int x2 = coords.get(j).p.x;
        int y1 = coords.get(i).p.y;
        int y2 = coords.get(j).p.y;
        double d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)); // computes their distance
        return d;
    }
    public static Lista Initialize(int tam) { // Creating a set of random coordinates in a Singly Linked List and sorting it.
        Random rand = new Random();
        Nodo P = null;
        Nodo PTR = null;
        for (int i = 0; i < tam; i++) {
            Punto temp = new Punto(rand.nextInt(10000), rand.nextInt(10000), i);
            Nodo tempn = new Nodo(temp);
            if (i == 0) {
                PTR = tempn;
                P = PTR;
            } else {
                P.next = tempn;
                P = P.next;
            }
        }
        SortList(PTR);
        Lista list = new Lista(PTR, tam);
        P = list.head;
        while (P.next != null) {
            P = P.next;
        }
        // P.next = list.head;
        return list;
    }
    private static void SortList(Nodo PTR) { //Sorts a Linked List by the position in the x coordinate of each point.
        Nodo P = PTR;
        int tam = 0;
        while (P != null) {
            tam++;
            P = P.next;
        }
        P = PTR;
        for (int i = 0; i < tam; i++) {
            int min = P.p.x;
            Nodo P2 = P.next;
            while (P2 != null) {
                if (P2.p.x < min) {
                    Punto temp = P2.p;
                    P2.p = P.p;
                    P.p = temp;
                    min = P.p.x;
                }
                P2 = P2.next;
            }
            P = P.next;
        }
    }
    
    public static void main(String[] args) {
        ComplejidadTemporal tca = new ComplejidadTemporal();
        tca.ADD(50000);
    }


    
}
