public class PageEntry implements Comparable<PageEntry> {
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    @Override
    public int compareTo(PageEntry pageEntry) {
        return this.getCount() < pageEntry.getCount() ? 1 : -1;
    }


    @Override
    public String toString() {
        return "\nPageEntry {\n " +
                "pdfName = '" + pdfName + '\'' +
                ",\n page = " + page +
                ",\n count = " + count +
                "\n}";
    }

    public int getCount() {
        return count;
    }
}