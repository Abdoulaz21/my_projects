package memento;

public class WebBrowserApp {

    private WebBrowser webBrowser;
    private BrowserHistory browserHistory;

    public WebBrowserApp(final String browserUrl) {
        webBrowser = new WebBrowser(browserUrl);
        browserHistory = new BrowserHistory(new WebPage(browserUrl));
    }

    public void search(final String url) {
        webBrowser.setUrl(url);
        browserHistory.storeState(new WebPage(url));
    }

    public void goBackInHistory() {
        webBrowser.restoreState(browserHistory.getPreviousInHistory());
    }

    public void goForwardInHistory() {
        webBrowser.restoreState(browserHistory.getFollowingInHistory());
    }

    public String getUrl() {
        return webBrowser.getUrl();
    }

    public void printUrl() {
        System.out.println("Current url: " + webBrowser.getUrl());
    }
}
