package html;

public class Anchor extends Element {
    public Anchor(Node text, String href) {
        super("a", text);
        addAtribute("href", href);
    }

    public Anchor(String text, String href) {
        super("a", new Text(text));
        addAtribute("href", href);
    }
}
