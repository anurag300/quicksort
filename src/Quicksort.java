import java.util.Arrays;
import java.util.Random;

/**
 * Based on the paper, "Multi-Pivot Quicksort: Theory and Experiments"
 * downloaded from: 
 * http://epubs.siam.org/doi/pdf/10.1137/1.9781611973198.6 
 * on Nov.19th, 2017
 * 
 * @author David McManamon
 */
public class Quicksort {
    private static final int INSERTION_SORT_THRESHOLD = 47;

    private static void insertionsort(int[] a, int lo, int hi) {
	for (int i = lo + 1; i < hi + 1; i++) {
	    int valueToSort = a[i];
	    int j = i;
	    while (j > 0 && a[j - 1] > valueToSort) {
		a[j] = a[j - 1];
		j--;
	    }
	    a[j] = valueToSort;
	}
    }

    /**
     * Naive choice of pivots p,q,r as a[lo], a[midpoint] & a[hi] elements.
     */
    public static void quicksort3PivotBasic(int[] A, int lo, int hi) {
	int length = hi - lo + 1;
	if (length < INSERTION_SORT_THRESHOLD) {
	    insertionsort(A, lo, hi);
	    return;
	}

	int midpoint = (lo + hi) >>> 1;
	// insertion sort lo,mid,hi elements
	if (A[midpoint] < A[lo]) { int t = A[midpoint]; A[midpoint] = A[lo]; A[lo] = t; }
        if (A[hi] < A[midpoint]) { int t = A[hi]; A[hi] = A[midpoint]; A[midpoint] = t;
            if (t < A[lo]) { A[midpoint] = A[lo]; A[lo] = t; }
        }
		
	int p = A[lo];
	int q = A[midpoint];
	int r = A[hi];
	// p,q & r are now sorted, place them at a[lo], a[lo+1] & a[hi]
	swap(A, lo+1, midpoint);
		
	// Pointers a and b initially point to the first element of the array while c
	// and d initially point to the last element of the array.
	int a = lo + 2;
	int b = lo + 2;
	int c = hi - 1;
	int d = hi - 1;

	while (b <= c) {
	    while (A[b] < q && b <= c) {
		if (A[b] < p) {
		    swap(A, a, b);
		    a++;
		}
		b++;
	    }
	    while (A[c] > q && b <= c) {
		if (A[c] > r) {
		    swap(A, c, d);
		    d--;
		}
		c--;
	    }
	    if (b <= c) {
		if (A[b] > r) {
		    if (A[c] < p) {
			swap(A, b, a); swap(A, a, c);
			a++;
		    } else {
			swap(A, b, c);
		    }
		    swap(A, c, d);
		    b++; c--; d--;
		} else {
		    if (A[c] < p) {
			swap(A, b, a); swap(A, a, c);
			a++;
		    } else {
			swap(A, b, c);
		    }
		    b++; c--;
		}
	    }
	}
	// swap the pivots to their correct positions
	a--; b--; c++; d++;
	swap(A, lo + 1, a); swap(A, a, b);
	a--;
	swap(A, lo, a);
	swap(A, hi, d);

	quicksort3PivotBasic(A, lo, a-1);
	quicksort3PivotBasic(A, a+1, b-1);
	quicksort3PivotBasic(A, b+1, d-1);
	quicksort3PivotBasic(A, d+1, hi);	
    }
	
    /**
     * Choose pivots p,q,r as the 2nd, 4th, & 6th of 7 elements.
     */
    public static void quicksort3Pivot(int[] A, int lo, int hi) {
	int length = hi - lo + 1;
	if (length < INSERTION_SORT_THRESHOLD) {
	    insertionsort(A, lo, hi);
	    return;
	}

	// approximation of length / 7
	int seventh = (length >> 3) + (length >> 6) + 1;

	int e4 = (lo + hi) >>> 1; // The midpoint
	int e3 = e4 - seventh;
        int e2 = e3 - seventh;
        int e1 = lo;
        int e5 = e4 + seventh;
        int e6 = e5 + seventh;
        int e7 = hi;

        // Sort using insertion sort
        if (A[e2] < A[e1]) { int t = A[e2]; A[e2] = A[e1]; A[e1] = t; }
        
        if (A[e3] < A[e2]) { int t = A[e3]; A[e3] = A[e2]; A[e2] = t;
            if (t < A[e1]) { A[e2] = A[e1]; A[e1] = t; }
        }
        if (A[e4] < A[e3]) { int t = A[e4]; A[e4] = A[e3]; A[e3] = t;
            if (t < A[e2]) { A[e3] = A[e2]; A[e2] = t;
                if (t < A[e1]) { A[e2] = A[e1]; A[e1] = t; }
            }
        }
        if (A[e5] < A[e4]) { int t = A[e5]; A[e5] = A[e4]; A[e4] = t;
            if (t < A[e3]) { A[e4] = A[e3]; A[e3] = t;
                if (t < A[e2]) { A[e3] = A[e2]; A[e2] = t;
                    if (t < A[e1]) { A[e2] = A[e1]; A[e1] = t; }
                }
            }
        }
        if (A[e6] < A[e5]) { int t = A[e6]; A[e6] = A[e5]; A[e5] = t;
        	    if (t < A[e4]) { A[e5] = A[e4]; A[e4] = t;
        	        if (t < A[e3]) { A[e4] = A[e3]; A[e3] = t;
        		    if (t < A[e2]) { A[e3] = A[e2]; A[e2] = t;
        		        if (t < A[e1]) { A[e2] = A[e1]; A[e1] = t; }
        		    }
        		}
        	    }
        }
        if (A[e7] < A[e6]) { int t = A[e7]; A[e7] = A[e6]; A[e6] = t;
        	    if (t < A[e5]) { A[e6] = A[e5]; A[e5] = t;
        		if (t < A[e4]) { A[e5] = A[e4]; A[e4] = t;
        		    if (t < A[e3]) { A[e4] = A[e3]; A[e3] = t;
        		        if (t < A[e2]) { A[e3] = A[e2]; A[e2] = t;
        		            if (t < A[e1]) { A[e2] = A[e1]; A[e1] = t; }
        			}
        		    }
        		}
        	    }
        }
        
        int p = A[e2];
	int q = A[e4];
	int r = A[e6];
	swap(A, lo, e2);
	swap(A, lo+1, e4);
	swap(A, hi, e6);
	// Pointers a and b initially point to the first element of the array while c
	// and d initially point to the last element of the array.
	int a = lo + 2;
	int b = lo + 2;
	int c = hi - 1;
	int d = hi - 1;
				
	while (b <= c) {
	    while (A[b] < q && b <= c) {
		if (A[b] < p) {
		    swap(A, a, b);
		    a++;
		}
		b++;
	    }
	    while (A[c] > q && b <= c) {
		if (A[c] > r) {
		    swap(A, c, d);
		    d--;
		}
		c--;
	    }
	    if (b <= c) {
		if (A[b] > r) {
		    if (A[c] < p) {
			swap(A, b, a); swap(A, a, c);
			a++;
		    } else {
			swap(A, b, c);
		    }
		    swap(A, c, d);
		    b++; c--; d--;
		} else {
		    if (A[c] < p) {
			swap(A, b, a); swap(A, a, c);
			a++;
		    } else {
			swap(A, b, c);
		    }
		    b++; c--;
		}
	    }
	}
	// swap the pivots to their correct positions
	a--; b--; c++; d++;
	swap(A, lo + 1, a); swap(A, a, b);
	a--;
	swap(A, lo, a);
	swap(A, hi, d);

	quicksort3Pivot(A, lo, a-1);
	quicksort3Pivot(A, a+1, b-1);
	quicksort3Pivot(A, b+1, d-1);
	quicksort3Pivot(A, d+1, hi);	
    }

    private static final void swap(int[] a, int x, int y) {
	int temp = a[x];
	a[x] = a[y];
	a[y] = temp;
    }

    private static boolean isSorted(int[] a, int lo, int hi) {
	for (int i = lo + 1; i <= hi; i++) {
	    if (a[i] < a[i - 1]) {
		return false;
	    }
	}
	return true;
    }

    public static void main(String[] args) {
	Random r = new Random();
	r.setSeed(0L);
	int[] a = new int[10000000];
	for (int i = 0; i < 10000000; i++) {
	    a[i] = r.nextInt();
	}

	long start = System.currentTimeMillis();
	quicksort3PivotBasic(a, 0, a.length - 1);
	//quicksort3Pivot(a, 0, a.length - 1);
	//Arrays.sort(a); // uncomment to compare against your JDK
	long stop = System.currentTimeMillis();
	java.text.NumberFormat f = java.text.NumberFormat.getNumberInstance(java.util.Locale.US);
	System.out.println((stop - start) + "ms to sort " + f.format(a.length) + " elements. Is sorted? " + isSorted(a, 0, a.length - 1));
    }
}