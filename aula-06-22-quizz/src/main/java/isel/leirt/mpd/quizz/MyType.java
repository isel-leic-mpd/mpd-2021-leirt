package isel.leirt.mpd.quizz;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MyType {
	public int m1() { return 0; }
	public static int m2(MyType mt) { return 0; }
	public static int m3(MyType mt, int i) { return 0; };
	public Function<Integer,String> m4() {return null; }

	/*

		Function<MyType, Integer>
		BiFunction<MyType, Integer, Function<Integer,String>>
		BiFunction<Integer, Function<Integer,String>>
		Supplier<Integer>
		Function<Integer,String>
		BiFunction<MyType, Integer, Integer>

	*/

	private void response() {
		Function<MyType, Integer> x1 = MyType::m1;
		Function<MyType, Integer> x2 = MyType::m2;

		BiFunction<MyType, Integer, Integer> x3 = MyType::m3;
		Function<MyType,  Function<Integer,String>> x4 = mt-> mt.m4();

	}

}



