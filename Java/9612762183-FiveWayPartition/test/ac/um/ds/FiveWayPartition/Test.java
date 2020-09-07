/* Copyright (C) Fateme Chaji, Ferdowsi Univerity of Mashhad
 * 25 Esfand 1396(Hijri shamsi)
 * 16 March 2018
 * Author: Fateme Chaji
 */

package ac.um.ds.FiveWayPartition;

import java.util.*;

public class Test {

    public static void main(String[] args) {

        int n = 10, pivot1, pivot2;
        int q1[], q2[], q3[], q4[];
        Integer A[];
        Random rand;
        IPartition<Integer> fwp;

        fwp = new FiveWayPartition<Integer>();
        A = new Integer[n];
        q1 = new int [1];
        q2 = new int [1];
        q3 = new int [1];
        q4 = new int [1];
        rand = new Random();
        pivot1 = rand.nextInt(Integer.MAX_VALUE);
        pivot2 = rand.nextInt(Integer.MAX_VALUE);

        if (pivot1 > pivot2)
        {
            int temp = pivot1;
            pivot1 = pivot2;
            pivot2 = temp;
        }

        if (pivot1 == pivot2) {
            pivot2 += 20;
        }

        System.out.println("Initial Array:");
        for (int i = 0; i < n; i++) {
            A[i] = rand.nextInt(Integer.MAX_VALUE);
            System.out.print(A[i] + ", ");
        }

        fwp.Partition(A, pivot1, pivot2, 0, n - 1, q1, q2, q3, q4);

        System.out.println("\n\nLess than " + pivot1 + ":");
        for (int i = 0; i <= q1[0]; i++) {
            System.out.print(A[i] + ", ");
        }

        System.out.println("\n\nEquals to " + pivot1 + ":");
        for (int i = q1[0] + 1; i < q2[0]; i++) {
            System.out.print(A[i] + ", ");
        }

        System.out.println("\n\nLess than " + pivot2 + " and greater than " + pivot1 + ":" );
        for (int i = q2[0]; i <= q3[0]; i++) {
            System.out.print(A[i] + ", ");
        }

        System.out.println("\n\nEquals to " + pivot2 + ":" );
        for (int i = q3[0] + 1; i < q4[0]; i++) {
            System.out.print(A[i] + ", ");
        }

        System.out.println("\n\nGreater than " + pivot2 + ":");
        for (int i = q4[0]; i < n; i++) {
            System.out.print(A[i] + ", ");
        }

    }
}
