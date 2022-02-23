import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class PageRank {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream("links.srt.gz")), "UTF-8"));
            String s;
            while ((s = br.readLine()) != null) {
                String[] line = s.split("\\s+");
                Map<String, Integer> inlinkMap = new HashMap<>();
                String target = line[1];
                if (inlinkMap.containsKey(target))
                    inlinkMap.put(target, inlinkMap.get(target) + 1);
                else
                    inlinkMap.put(target, 1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
