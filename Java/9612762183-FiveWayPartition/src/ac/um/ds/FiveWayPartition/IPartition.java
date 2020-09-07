/* Copyright (C) Fateme Chaji, Ferdowsi Univerity of Mashhad
 * 25 Esfand 1396(Hijri shamsi)
 * 16 March 2018
 * Author: Fateme Chaji
 */

package ac.um.ds.FiveWayPartition;

public interface IPartition< T extends Comparable<T> >{

    public abstract void Partition(T[] A, T pivot1, T pivot2, int p, int r, int[] q1, int[]  q2, int[] q3, int[] q4);
}
