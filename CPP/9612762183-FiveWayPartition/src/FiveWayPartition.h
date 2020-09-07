/* Copyright (C) Fateme Chaji, Ferdowsi Univerity of Mashhad
* 25 Esfand 1396(Hijri shamsi)
* 16 March 2018
* Author: Fateme Chaji
*/

#pragma once



template <class T>
class FiveWayPartition {

public:
	void Partition(T* A, T pivot1, T pivot2, int p, int r, int& q1, int& q2, int& q3, int& q4){
		int i = p, j = p , k = r;
		while(j <= k){

			if(A[j] < pivot1){
				swap_T(A[j] , A[i]);
				i++;
				j++;
			}
			else if(A[j] == pivot1){
				j++;
			}
			else{
				swap_T(A[j] , A[k]);
				k--;
			}
		}
	
		q1 = i - 1;
		q2 = j;
		i = j , k = r;

		while(j <= k){
			if(A[j] < pivot2){
				swap_T(A[j] , A[i]);
				i++;
				j++;
			}
			else if(A[j] == pivot2){
				j++;
			}
			else{
				swap_T(A[j] , A[k]);
				k--;
			}
		}
		q3 = i - 1;
		q4 = j;

	}
	void swap_T(T& a,T& b){
		T tmp = a;
		a = b;
		b = tmp;
	}
};
