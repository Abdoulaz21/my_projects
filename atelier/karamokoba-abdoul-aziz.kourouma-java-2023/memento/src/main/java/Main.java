import memento.WebBrowserApp;

public class Main {

    public static void main(String[] args) {

        WebBrowserApp browserApp = new WebBrowserApp("https://www.google.com/");
        browserApp.printUrl();

        browserApp.search("https://fr.wikipedia.org/wiki/Wikip%C3%A9dia:Accueil_principal");
        browserApp.printUrl();

        browserApp.goBackInHistory();
        browserApp.printUrl();

        browserApp.search("https://www.youtube.com");
        browserApp.printUrl();

        browserApp.goBackInHistory();
        browserApp.printUrl();

        browserApp.goForwardInHistory();
        browserApp.printUrl();
    }
}
