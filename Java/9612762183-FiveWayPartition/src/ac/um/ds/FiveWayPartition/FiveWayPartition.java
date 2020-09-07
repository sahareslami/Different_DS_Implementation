/* Copyright (C) Fateme Chaji, Ferdowsi Univerity of Mashhad
 * 25 Esfand 1396(Hijri shamsi)
 * 16 March 2018
 * Author: Fateme Chaji
 */

package ac.um.ds.FiveWayPartition;

public class FiveWayPartition< T extends Comparable< T > > implements IPartition< T >{

    public void Partition(T[] A, T pivot1, T pivot2, int p, int r, int[] q1, int[]  q2, int[] q3, int[] q4) {
        int i = p, j = p , k = r;
        while(j <= k) {
            if (A[j].compareTo(pivot1) < 0) {
                T tmp = A[j];
                A[j] = A[i];
                A[i] = tmp;
                i++;
                j++;
            } else if (A[j].compareTo(pivot1) > 0) {
                T tmp = A[j];
                A[j] = A[k];
                A[k] = tmp;
                k--;
            } else {
                j++;
            }
        }
        q1[0] = i - 1;
        q2[0] = j;
        i = j ;
        k = r;



        while(j <= k){
            if (A[j].compareTo(pivot2) < 0) {
                //swap(A[j] , A[i]);
                T tmp = A[j];
                A[j] = A[i];
                A[i] = tmp;
                i++;
                j++;
            } else if (A[j].compareTo(pivot2) > 0) {
                T tmp = A[j];
                A[j] = A[k];
                A[k] = tmp;
                k--;
            } else {
                j++;
            }
        }
        q3[0] = i - 1;
        q4[0] = j;

    }

}

