import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws IOException {
        String html = Jsoup.connect("https://www.pashabank.az/").get().html();
        Document doc = Jsoup.parse(html, "utf-8");
        Elements hrefs = doc.getElementsByTag("a");
        Set<String> set = hrefs.stream()
                .map(href -> href.attr("href"))
                .filter(href -> href.startsWith("https://www.pashabank.az"))
                .collect(Collectors.toSet());
        System.out.println(rec(set, set, set.size()).size());
    }

    public static Set<String> rec(Set<String> allset, Set<String> checkSet, int count) throws IOException {
        if (count == 0) {
            return allset;
        }
        int incount = 0;
        int a = count;
        Set<String> inset;
        Set<String> returnset = new HashSet<>();
        for (String href : checkSet) {
            try {
                String inhtml = Jsoup.connect(href).get().html();
                Document indoc = Jsoup.parse(inhtml, "utf-8");
                Elements inhrefs = indoc.getElementsByTag("a");
                inset = inhrefs.stream()
                        .map(inhref -> inhref.attr("href"))
                        .filter(inhref -> inhref.startsWith("https://www.pashabank.az"))
                        .collect(Collectors.toSet());

                for (String h : inset) {
                    if (!allset.contains(h) && !returnset.contains(h)) {
                        returnset.add(h);
                        System.out.println(a + " " + h);
                        a++;
                        incount++;
                    }
                }
            } catch (Exception e) {

            }
        }
        allset.addAll(returnset);
        System.out.println(allset.size());
        return rec(allset, returnset, incount);
    }
}
