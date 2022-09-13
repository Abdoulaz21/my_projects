package yml;

public class Main
{
    public static void main(String[] args) {
    	Document doc = new Document();

    	MarkupYml mh1 = new MarkupYml();
    	MarkupP mp1 = new MarkupP("first");
    	MarkupRaw mr1 = new MarkupRaw("Hello");

    	MarkupP mp2 = new MarkupP("second");
    	MarkupRaw mr2 = new MarkupRaw("World");

		MarkupYml mh2 = new MarkupYml();
		MarkupP mp3 = new MarkupP("third");
		MarkupRaw mr3 = new MarkupRaw("!");

    	mh1.addMarkup(mp1);
    	mp1.addMarkup(mr1);

    	mh1.addMarkup(mp2);
    	mp2.addMarkup(mr2);

		mh1.addMarkup(mh2);
		mh2.addMarkup(mp3);
		mp3.addMarkup(mr3);

    	doc.addMarkup(mh1);
    	doc.print();
    }
}
