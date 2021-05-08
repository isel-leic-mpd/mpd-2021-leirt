import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

public class FunctionTests {

	public interface MFunction<T,R> {

		R apply(T t);

		default <U> MFunction<U,R> compose(MFunction<U,T> other) {
			return (u) -> apply(other.apply(u));
		}

		default <U> MFunction<T,U> andThen(MFunction<R,U> other) {
			return (t) -> other.apply(apply(t));
		}
	}

	private static int minc(int i) {
		return i+1;
	}

	public static int  mtriple(int i) {
		return 3*i;
	}


	@Test
	public void checkAndThenComposeTest() {
		MFunction<Integer,Integer> inc = FunctionTests::minc;
		MFunction<Integer,Integer> triple = FunctionTests::mtriple;

		MFunction<Integer,Integer> comb1 = inc.andThen(triple);

		MFunction<Integer,Integer> comb2 = inc.compose(triple);

		Assert.assertEquals(15, comb1.apply(4).intValue());
		Assert.assertEquals(13, comb2.apply(4).intValue());

	}

}
