// Copyright (C) Kamaledin Ghiasi-Shirazi, Ferdowsi Univerity of Mashhad
//
// Author: Kamaledin Ghiasi-Shirazi

#pragma once
#include "ISort.h"


template <class T>
class SelectionSort: public ISort<T>{
public:
	virtual void Sort (T* A, int n){
		
		for(int i = n - 1 ; i >= 1 ; i--){
			int idx = i;
			for(int j = 0 ; j <= i - 1 ; j++)
				if(A[j] > A[idx])
					idx = j;
			T tmp = A[idx];
			A[idx] = A[i];
			A[i] = tmp;
		}
	}
};
