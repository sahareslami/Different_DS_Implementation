package ac.um.ds.SelectionSort;



import ac.um.ds.SelectionSort.SelectionSort;
import ac.um.ds.SelectionSort.ISort;
import java.util.Random;

public class TestTemplate {
	public static void main (String args[]) {
		
		
		int i;
		int n = 10;
                Random rand=new Random();

		ISort<Double> sorter;
		sorter= new SelectionSort<Double>();

		Double[] A;
		A = new Double[n];


		for (i = 0; i < n; i++)
		{
			A[i] = rand.nextDouble()*100000 ;
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
