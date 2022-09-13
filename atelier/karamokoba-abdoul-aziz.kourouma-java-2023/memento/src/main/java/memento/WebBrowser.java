package memento;

public class WebBrowser implements Originator<WebPage, String> {

    private String browserUrl;

    public WebBrowser(final String browserUrl) {
        this.browserUrl = browserUrl;
    }

    public String getUrl() {
        return browserUrl;
    }

    public void setUrl(final String url) {
        this.browserUrl = url;
    }

    @Override
    public WebPage saveState() {
        return new WebPage(browserUrl);
    }

    @Override
    public void restoreState(WebPage state) {
        this.browserUrl = state.getState();
    }
}
