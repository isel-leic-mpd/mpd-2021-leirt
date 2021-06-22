package html;

public class Img extends Element {
	public Img(String src, String alt) {
		super("img");
		addAtribute("src", src);
		addAtribute("alt", alt);
	}
}
