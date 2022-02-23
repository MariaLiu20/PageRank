import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class PageRank {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream("links.srt.gz")), "UTF-8"));
            String s;
            Map<String, Integer> inlinkMap = new HashMap<>();
            Map<String, List<String>> graph = new HashMap<>();
            while ((s = br.readLine()) != null) {
                String[] line = s.split("\\s+");
                //PAGE : INLINK COUNT
                String source = line[0];
                String target = line[1];
                //represent graph w adjacency list
                //Map<String, List<String>>
                //one key = one source
                graph.putIfAbsent(source, new ArrayList<>());
                graph.get(source).add(target);

                if (inlinkMap.containsKey(target)) {
                    inlinkMap.put(target, inlinkMap.get(target) + 1);
                }
                else
                    inlinkMap.put(target, 1);
            }
            // Get top 100 pages w most inlinks and put in priority queue
            PriorityQueue<Map.Entry<String, Integer>> inlinkPQ =
                    new PriorityQueue<>(Map.Entry.<String, Integer>comparingByValue());
            for (Map.Entry<String,Integer> entry : inlinkMap.entrySet()) {
                inlinkPQ.add(new AbstractMap.SimpleEntry<String, Integer>(entry.getKey(), entry.getValue()));
                if (inlinkPQ.size() > 100)
                    inlinkPQ.poll();
            }
            // Add top pages to ArrayList and sort by descending value
            ArrayList<Map.Entry<String, Integer>> inlinkList = new ArrayList<>();
            inlinkList.addAll(inlinkPQ);
            inlinkList.sort(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()));
            // Write page & inlink count into file
            FileWriter fw = new FileWriter("inlinks.txt");
            PrintWriter pw = new PrintWriter(fw);
            for (Map.Entry<String, Integer> entry : inlinkList) {
                pw.println(entry.getKey() + " " + entry.getValue());
            }
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
