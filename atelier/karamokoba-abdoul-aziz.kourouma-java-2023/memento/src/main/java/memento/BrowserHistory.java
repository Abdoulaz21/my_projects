package memento;

import java.util.ArrayList;
import java.util.List;

public class BrowserHistory implements Caretaker<WebPage> {

    private List<WebPage> history = new ArrayList<WebPage>();
    private int location;

    public BrowserHistory(final WebPage homeBrowserPage) {
        history.add(homeBrowserPage);
        location = 0;
    }

    public WebPage getPreviousInHistory() {
        if (location == 0)
            return null;
        location--;
        return history.get(location);
    }

    public WebPage getFollowingInHistory() {
        if (location >= history.size() - 1)
            return null;
        location++;
        return history.get(location);
    }

    @Override
    public void storeState(WebPage memento) {
        history.add(memento);
        location = history.size() - 1;
    }
}
