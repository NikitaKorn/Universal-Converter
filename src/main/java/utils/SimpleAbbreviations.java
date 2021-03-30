package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SimpleAbbreviations {
    private static String fileName;
    private static Map<Integer, Map<Integer, Double>> adj = new HashMap<>();
    private static Set<Integer> visited = new HashSet<>();
    private static Set<Integer> copyVisited = new HashSet<>();

    public static Map<Integer, Map<Integer, Double>> getAdj(){
        return adj;
    }

    public static void setFileNameAndInit(String name) throws IOException{
        fileName = name;
        try {
            createList();
        } catch (IOException e){
            throw new IOException(e);
        }
        copyVisitedCreate();
    }

    public static double simpleConversation(String from, String to){
        if(from.equals(to)){
            return 1;
        } else {
            refreshVisited();
            return simpleConversion(from, to);
        }
    }

    private static void copyVisitedCreate(){
        for(Integer i : visited){
            copyVisited.add(i);
        }
    }

    private static void refreshVisited(){
        visited.clear();
        for(Integer i : copyVisited){
            visited.add(i);
        }
    }

    private static double simpleConversion(String from, String to) {
        Stack<Vertex> deck = new Stack<>();
        double lastValue = 0;
        double answer = 1;

        Integer firstVertex = from.hashCode();
        Integer endVertex = to.hashCode();
        if(adj.containsKey(firstVertex)){
            visited.remove(firstVertex);
            if(adj.get(firstVertex).containsKey(endVertex)){
                answer = adj.get(firstVertex).get(endVertex);
                return answer;
            }
            for(Map.Entry<Integer, Double> i : adj.get(firstVertex).entrySet()){
                Vertex vr = new Vertex(i.getKey());
                vr.weight = i.getValue();
                deck.push(vr);
            }
        } else {
            return -2;
        }

        while (!deck.empty()){
            lastValue = deck.peek().weight;
            Integer vertex = deck.peek().vertex;
            if(adj.containsKey(vertex) && visited.contains(vertex)){
                visited.remove(vertex);
                if(adj.get(vertex).containsKey(endVertex)){
                    answer = lastValue * adj.get(vertex).get(endVertex);
                    break;
                } else {
                    for(Map.Entry<Integer, Double> i : adj.get(vertex).entrySet()){
                        Vertex vr = new Vertex(i.getKey());
                        vr.weight = lastValue * i.getValue();
                        deck.push(vr);
                    }
                }
            }
            deck.pop();
            if(deck.empty()){
                return -1;
            }
        }
        return answer;
    }

    private static void createList() throws IOException {
        try(FileReader fr = new FileReader(fileName, StandardCharsets.UTF_8);
            BufferedReader bfR = new BufferedReader(fr)){
            while (bfR.ready()){
                String line = bfR.readLine();
                String[] split = line.split(",");
                Integer firstVertex = split[0].hashCode();
                Integer secondVertex = split[1].hashCode();
                Double weight = Double.parseDouble(split[2]);

                if(adj.containsKey(firstVertex)){
                    adj.get(firstVertex).put(secondVertex, weight);
                } else {
                    visited.add(firstVertex);
                    Map<Integer, Double> innerMap = new HashMap<>();
                    innerMap.put(secondVertex, weight);
                    adj.put(firstVertex, innerMap);
                }

                if(adj.containsKey(secondVertex)){
                    adj.get(secondVertex).put(firstVertex, 1 / weight);
                } else {
                    visited.add(secondVertex);
                    Map<Integer, Double> innerMap = new HashMap<>();
                    innerMap.put(firstVertex, 1 / weight);
                    adj.put(secondVertex, innerMap);
                }
            }

        } catch (IOException e){
            throw new IOException(e);
        }
    }
}
