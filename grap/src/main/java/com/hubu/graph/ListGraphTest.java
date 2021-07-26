package com.hubu.graph;

public class ListGraphTest {

    public static void main(String[] args) {
        test();
    }
    public static void test() {
		ListGraph<String, Integer> graph = new ListGraph<>();
		graph.addEdge("V0", "V1",12);
		graph.addEdge("V1", "V2",14);
		graph.addEdge("V1", "V3",16);
		graph.addEdge("V0", "V4",19);
		graph.addEdge("V3","V8",20);
		graph.addEdge("V8","V16",24);
		System.out.println("bfs");
		graph.bfs("V0");

		System.out.println("dfs");
		graph.dfs("V0");
    }
}
