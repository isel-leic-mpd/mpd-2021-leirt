public interface I1 {
	static void sm1() {
		System.out.println("On static method sm1 of interface I1");
	}

	default void dm1() {
		System.out.println("On default method dm1 of interface I1");
	}
}
