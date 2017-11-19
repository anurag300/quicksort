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
	
	private static void insertionsort(int[] arr, int lo, int hi) {
		for (int i = lo+1; i < hi+1; i++) {
			int valueToSort = arr[i];
			int j = i;
			while (j > 0 && arr[j - 1] > valueToSort) {
				arr[j] = arr[j - 1];
				j--;
			}
			arr[j] = valueToSort;
		}
	}
	
	/**
	 * Naive choice of pivots p,q,r as a[lo], a[midpoint] & a[hi] elements.
	 */
	public static void quicksort3PivotBasic(int [] A, int lo, int hi) {
		if (hi == lo) 
			return;
		if (hi-lo < INSERTION_SORT_THRESHOLD) { 
			insertionsort(A, lo, hi);
			return;
		}
		
		int midpoint = (lo+hi) >>> 1; // The midpoint
		if (A[lo] > A[midpoint]) 
			swap(A, lo, midpoint);
		if (A[midpoint] > A[hi]) 
			swap(A, midpoint, hi);
		if (A[lo] > A[midpoint]) 
			swap(A, lo, midpoint);
		
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
		swap(A, lo+1, a); swap(A, a, b);
		a--;
		swap(A, lo, a); swap(A, hi, d);
		
		quicksort3PivotBasic(A, lo, a-1);
		quicksort3PivotBasic(A, a+1, b);
		quicksort3PivotBasic(A, c, d-1);
		quicksort3PivotBasic(A, d+1, hi);
	}

	private static void swap(int[] a, int a2, int b) {
		int temp = a[a2];
		a[a2] = a[b];
		a[b] = temp;
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
		int [] a = new int[10000000];
		for (int i=0; i < 10000000; i++) {
			a[i] = r.nextInt();
		}
		
		long start = System.currentTimeMillis();
		//quicksort3PivotBasic(a, 0, a.length - 1);
		Arrays.sort(a); // uncomment to compare against your JDK
		//quicksort(a, 0, a.length-1);
		long stop = System.currentTimeMillis();
		System.out.println((stop-start) + "ms to sort " + a.length + " elements. Is sorted? " + isSorted(a, 0, a.length-1));
	}
}