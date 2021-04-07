package isel.leirt.mpd.reflection_utils.test_classes;

public class A  {
	public  int i1 = 20;

	public  double d;

	private String msg;

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) return false;
		A a = (A) obj;

		if (d != a.d || i1 != a.i1) return false;
		if (msg == null) return a.msg == null;
		return msg.equals(a.msg);
	}
}
