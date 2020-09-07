/* Copyright (C) Kamaledin Ghiasi-Shirazi, Ferdowsi Univerity of Mashhad
 * 24 Aban 1396(Hijri shamsi)
 * 15 November 2017
 * Authors: Pooya Hosseinzadeh, Fatemeh Tabadkani, Kamaledin Ghiasi-Shirazi
*/



import java.util.Iterator;
import ac.um.ds.LinkedLinearList;

public class Test {
    public static void testIteration() throws Exception {
       LinkedLinearList<Integer> lll = new LinkedLinearList<Integer>();
        Iterator itrFwd;
        Iterator itrBckwd;

        for (int i = 0; i < 4; i++) {

            lll.insert(i, (Integer) i); // returned itr refers to the inserted item

        }

        System.out.print("List should be: 0	1	2	3\n");
        System.out.print(lll);
        System.out.print("\n");

        System.out.print("Forward Iteration should be: 0	1	2	3\n");
        itrFwd = lll.ForwardIterator();
        while (itrFwd.hasNext())
		{
			System.out.print(itrFwd.next());
			System.out.print("\t");
		}
        System.out.println("");

        System.out.print("Backward Iteration should be: 3	2	1	0\n");
        itrBckwd = lll.BackwardIterator();
		while (itrBckwd.hasNext())
		{
			System.out.print(itrBckwd.next());
			System.out.print("\t");
		}
        System.out.println("");
    }


    public static void testInsertRemove() throws Exception {
        LinkedLinearList<Integer> lll = new LinkedLinearList<Integer>();

        int N = 10;
        for (int i = 0; i < N; ++i) {
            lll.insert(i, (Integer) i);
        }
        System.out.println(lll);
        for (int i = N-2; i >= 0; i -= 2) {
            lll.remove(i);
            System.out.println(lll);
        }

        System.out.print("List should be: 1 3 5 7 9\n");
        System.out.println(lll);


    }

    public static void main(String[] args) throws Exception {
        testInsertRemove();
        testIteration();
    }
}
