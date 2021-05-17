package isel.leirt.mpd.weather4.queries.iterators;

import java.util.Iterator;

public class PrimesIterator implements Iterator<Long> {

	private long curr_prime = 1; // to return first prime

	public PrimesIterator() {
		int a;
		a=3;
	}

	public static boolean isPrime(long n) {
		if (n == 2) return true;
		if (n < 2 || n % 2 == 0) return false;
		for (int i = 3; i <= Math.sqrt(n); i += 2)
			if (n % i == 0) return false;
		return true;
	}

	public static long nextPrime(long p) {
		if (p < 2) return 2;
		else {
			if (p == 2) return 3;
			p = (p %2 == 0) ? p+1 : p+2;
			while(!isPrime(p)) p+= 2;
		}
		return p;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public Long next() {
		curr_prime = nextPrime(curr_prime);
		return curr_prime;
	}
}
