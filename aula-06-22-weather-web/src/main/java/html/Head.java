package html;

public class Head extends Element {
    private static final String NAME="head";

    public Head(Title title, Node... childs) {
        super(NAME, childs);
        this.childs.add(0, title);
    }

}
