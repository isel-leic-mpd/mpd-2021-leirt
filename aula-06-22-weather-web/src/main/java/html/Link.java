package html;

public class Link  extends Element {
	public Link(String rel, String type, String href ) {

		super("link");
		addAtribute("rel", rel);
		addAtribute("type", type);
		addAtribute("href", href);
	}

}
