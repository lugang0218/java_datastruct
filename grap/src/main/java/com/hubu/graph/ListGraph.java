package com.hubu.graph;
import java.util.*;
/**
 *
 *
 * 图
 */
public class ListGraph<V,E> extends Graph<V,E>{
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();
    private Set<Edge<V, E>> edges = new HashSet<>();
    private Comparator<Edge<V, E>> edgeComparator = (Edge<V, E> e1, Edge<V, E> e2) -> {
        return weightManager.compare(e1.weight, e2.weight);
    };
    @Override
    public int edgesSize() {
        return 0;
    }

    @Override
    public int verticesSize() {
        return 0;
    }


    @Override
    public void bfs(V begin, VertexVisitor<V> visitor) {

    }

    @Override
    public void dfs(V begin, VertexVisitor<V> visitor) {

    }

    @Override
    public Set<EdgeInfo<V, E>> mst() {
        return null;
    }

    @Override
    public List<V> topologicalSort() {
        return null;
    }

    @Override
    public Map<V, PathInfo<V, E>> shortestPath(V begin) {
        return null;
    }

    @Override
    public Map<V, Map<V, PathInfo<V, E>>> shortestPath() {
        return null;
    }

    /**
     *
     *
     * 顶点
     */
    private static class Vertex<V,E>{
        V value;

        boolean visit=false;


        /**
         *
         * 进来的边
         */
        Set<Edge<V,E>> inEdges=new HashSet<>();


        /**
         * 出去的边
         */
        Set<Edge<V,E>> outEdges=new HashSet<>();



        Vertex(V value) {
            this.value = value;
        }
        @Override
        public boolean equals(Object obj) {
            return Objects.equals(value, ((Vertex<V, E>)obj).value);
        }
        @Override
        public int hashCode() {
            return value == null ? 0 : value.hashCode();
        }
        @Override
        public String toString() {
            return value == null ? "null" : value.toString();
        }

    }




    /**
     *
     * 边
     */
    private static class Edge<V,E>{


        /**
         * 权值
         */
        E wight;

        /**
         * 从哪个节点过来
         */
        Vertex<V, E> from;


        /**
         * 到哪个节点去
         */
        Vertex<V, E> to;
        E weight;

        Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public boolean equals(Object obj) {
            Edge<V, E> edge = (Edge<V, E>) obj;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }
        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
        }
    }

    public void print() {
        System.out.println("[顶点]-------------------");
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("out-----------");
            System.out.println(vertex.outEdges);
            System.out.println("in-----------");
            System.out.println(vertex.inEdges);
        });

        System.out.println("[边]-------------------");
        edges.forEach((Edge<V, E> edge) -> {
            System.out.println(edge);
        });
    }


    /**
     *
     *
     * 添加节点
     * @param v
     */
    @Override
    public void addVertex(V v) {

        /**
         *
         * 如果节点已经被包含，直接返回
         */
        if (vertices.containsKey(v)) return;


        /**
         * 创建节点，并把它添加到顶点的map集合中，v值就是键，顶点值就是value
         */
        vertices.put(v, new Vertex<>(v));
    }


    /**
     *
     *
     *
     * 添加边
     * @param from 从哪个节点来
     * @param to 到哪个节点去
     */
    @Override
    public void addEdge(V from, V to) {

        /**
         *
         *
         */
        addEdge(from, to, null);
    }

    @Override
    public void addEdge(V from, V to, E weight) {

        /**
         *
         * 先查找form对应的节点，如果节点不存在，创建出来，添加到顶点的map里面
         */
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from, fromVertex);
        }


        /**
         *
         * 先查找form对应的节点，如果节点不存在，创建出来，添加到顶点的map里面
         */




        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }



        /**
         *
         *
         * 先创建一条边
         */


        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        edge.weight = weight;

        /**
         *
         *
         * 如果两个顶点之前存在这条边，先将其删除掉
         */
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);


            /**
             *
             * 从边的集合中将其删除掉
             */
            edges.remove(edge);
        }


        /**
         * 重新添加新的边到各个集合中
         */
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
        edges.add(edge);
    }

    @Override
    public void removeEdge(V from, V to) {


        /**
         * 先从入节点获取，如果没有直接返回
         */
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) return;


        /**
         * 从出节点获取，如果没有直接返回
         */
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) return;


        /**
         * 创建出这条边，然后将其移除掉
         */
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        if (fromVertex.outEdges.remove(edge)) {
            toVertex.inEdges.remove(edge);
            edges.remove(edge);
        }
    }
    @Override
    public void removeVertex(V v) {
        Vertex<V, E> vertex = vertices.remove(v);
        if (vertex == null) return;

        for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            edge.to.inEdges.remove(edge);
            // 将当前遍历到的元素edge从集合vertex.outEdges中删掉
            iterator.remove();
            edges.remove(edge);
        }

        for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            edge.from.outEdges.remove(edge);
            // 将当前遍历到的元素edge从集合vertex.inEdges中删掉
            iterator.remove();
            edges.remove(edge);
        }
    }
    /**
     * 从某一个节点深度优先遍历图
     */
    public void dfs(V v){
        Vertex<V, E> vertex = vertices.get(v);
        if(vertex!=null){
            Set<Vertex<V,E>> set=new HashSet<>();
            doDfs(vertex,set);
        }
    }

    /**
     *
     * 深度优先算法
     * @param vertex
     */
    private void doDfs(Vertex<V,E> vertex,Set<Vertex<V,E>> set){
        System.out.println(vertex);
        Set<Edge<V, E>> outEdges = vertex.outEdges;
        for (Edge<V,E> currentEdge:outEdges) {
            if(set.contains(currentEdge.to)){
                continue;
            }
            set.add(currentEdge.to);
            doDfs(currentEdge.to,set);
        }
    }
    public void bfs(V v){
        Vertex<V, E> vertex = vertices.get(v);
        if(vertex!=null){
            doBfs(vertex);
        }

    }
    /**
     * 广度优先遍历算法
     * @param vertex
     */
    private void doBfs(Vertex<V, E> vertex) {
        /**
         * 如果当前节点为空或者当前节点已经遍历过了，直接返回
         */
        if (vertex == null || vertex.visit) {
            return;
        }
        Queue<Vertex<V, E>> vertexQueue = new LinkedList<>();
        vertexQueue.offer(vertex);
        while (!vertexQueue.isEmpty()) {
            Vertex<V, E> item = vertexQueue.poll();
            System.out.println(item.value);
            Set<Edge<V, E>> currentEdges = item.outEdges;


            for (Edge<V, E> edge : currentEdges) {
                Vertex<V, E> currentVertex = edge.to;
                /**
                 * 如果当前节点遍历过来，继续
                 */
                if (currentVertex.visit) {
                    continue;
                }
                currentVertex.visit = true;
                vertexQueue.offer(currentVertex);
            }
        }
    }
}
