package Controller;

import Entity.Node;
import Manager.NodeManager;
import javafx.collections.ObservableList;

import java.util.*;

public class SearchEngine {
    NodeManager nm;

    public SearchEngine(NodeManager nm){
        this.nm = nm;
    }

    DirectoryController dc = new DirectoryController(nm);

    private  List<Node> places(){
        HashMap<String,ObservableList<Node>>  directory = dc.getDirectory();
        Collection<ObservableList<Node>> nodeCollection = directory.values();
        Node[] nodes = nodeCollection.toArray(new Node[0]);
        List<Node> places = Arrays.asList(nodes);
        return places;
    }
    //put list of list into lists of names

    private  int LevenshteinDistance(String src, String dest)
    {
    int[][] d = new int[(src.length() + 1)] [(dest.length() + 1)];
        int i, j, cost;
        char[] str1 = src.toCharArray();
        char[] str2 = dest.toCharArray();

        for (i = 0; i <= str1.length; i++)
        {
            d[i][0] = i;
        }
        for (j = 0; j <= str2.length; j++)
        {
            d[0][j] = j;
        }
        for (i = 1; i <= str1.length; i++)
        {
            for (j = 1; j <= str2.length; j++)
            {

                if (str1[i - 1] == str2[j - 1])
                    cost = 0;
                else
                    cost = 1;

                d[i][ j] = Math.min(d[i - 1][j] + 1,Math.min(d[i][j - 1] + 1,d[i - 1][ j - 1] + cost));

                if ((i > 1) && (j > 1) && (str1[i - 1] ==
                        str2[j - 2]) && (str1[i - 2] == str2[j - 1]))
                {
                    d[i][j] = Math.min(d[i][j], d[i - 2][j - 2] + cost);
                }
            }
        }

        return d[str1.length][ str2.length];
    }


    public  List<Node> Search(String word){
        List<Node> places = places();
        List<String> wordList =  new ArrayList<>();
        for(Node n: places){
            wordList.add(n.getShortName());
        }
        List<Node> results = new ArrayList<>();
        double fuzzyness = 0.5;//basically the allowed error - adjust as needed
        List<Integer> matches = new ArrayList<Integer>();

        for (int i = 0; i < wordList.size(); i++){
            String s = wordList.get(i);
            // Calculate the Levenshtein-distance:
            int levenshteinDistance = LevenshteinDistance(word, s);

            // Length of the longer string:
            int length = Math.max(word.length(), s.length());

            // Calculate the score:
            double score = 1.0 - (double)levenshteinDistance / length;

            // Match?
            if (score > fuzzyness) {
                matches.add(i);
            }
        }

        for (int i : matches){
            Node node = places.get(i);
            results.add(node);
        }
        return results;
    }
}
