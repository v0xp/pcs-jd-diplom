import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    Map<String, List<PageEntry>> mapSearch = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        File[] files = pdfsDir.listFiles();
        for (File pdfFile : files) {
            PdfDocument doc = new PdfDocument(new PdfReader(pdfFile));
            for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                PdfPage page = doc.getPage(i);
                String text = PdfTextExtractor.getTextFromPage(page);
                String[] words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqs = new HashMap<>();
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    freqs.put(word.toLowerCase(), freqs.getOrDefault(word.toLowerCase(), 0) + 1);
                }
                for (Map.Entry<String, Integer> freg : freqs.entrySet()) {
                    List<PageEntry> entryList = new ArrayList<>();
                    if (mapSearch.containsKey(freg.getKey())) {
                        entryList = mapSearch.get(freg.getKey());
                        entryList.add(new PageEntry(pdfFile.getName(), i, freg.getValue()));
                        Collections.sort(entryList);
                        mapSearch.put(freg.getKey(), entryList);
                    } else {
                        entryList.add(new PageEntry(pdfFile.getName(), i, freg.getValue()));
                        mapSearch.put(freg.getKey(), entryList);
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        for (Map.Entry<String, List<PageEntry>> mapEntry : mapSearch.entrySet()) {
            if (word.equals(mapEntry.getKey())) {
                return mapEntry.getValue();
            }
        }
        return Collections.emptyList();
    }
}
