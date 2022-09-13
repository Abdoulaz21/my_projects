package memento;

public class WebPage implements Memento<String> {

    private final String url;

    public WebPage(String url) {
        this.url = url;
    }

    @Override
    public String getState() {
        return url;
    }
}
