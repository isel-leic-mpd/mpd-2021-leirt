package isel.leirt.mpd.reflection_utils.test_classes;

public class B  extends A {
	public int bi;

	private boolean flag;

	public B() {

	}
	public B(int bi) {
		this.bi = bi;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) return false;
		B b = (B) obj;

	    return bi == b.bi && flag == b.flag;
	}
}
