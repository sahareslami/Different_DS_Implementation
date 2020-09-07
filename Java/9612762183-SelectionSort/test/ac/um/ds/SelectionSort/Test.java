package ac.um.ds.SelectionSort;



import ac.um.ds.SelectionSort.SelectionSort;
import ac.um.ds.SelectionSort.ISort;
import java.util.Random;

public class Test {
	public static void main(String args[]) {
		int i;
		int n = 10;
        Random rand=new Random();
		ISort<Integer> sorter;
		Integer[] A;

		sorter = new SelectionSort<Integer>();
		A = new Integer[n];

		for (i = 0; i < n; i++)
		{
			A[i] = rand.nextInt()/10000000;
		}

		System.out.print("Initial array:\n");
		for (i = 0; i < n; i++)
		{
			System.out.print(A[i]);
			System.out.print(" , ");
		}

		sorter.Sort(A, n);

		System.out.print("\n\nSorted array:\n");
		for (i = 0; i < n; i++)
		{
			System.out.print(A[i]);
			System.out.print(" , ");
		}		
		
	}
}
