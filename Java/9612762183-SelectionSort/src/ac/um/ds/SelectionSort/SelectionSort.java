package ac.um.ds.SelectionSort;



public class SelectionSort<T extends Comparable<T>> implements ISort<T>
{
	public void Sort(T[] A, int n)
	{
		for(int i = n - 1 ; i >= 1 ; i--){
			int idx = i;
			for(int j = 0 ; j <= i - 1 ; j++)
				if(A[j].compareTo(A[idx]) > 0)
					idx = j;
			T tmp = A[idx];
			A[idx] = A[i];
			A[i] = tmp;
		}

	}



}