/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import java.util.ArrayList;

public class SocialGraph extends Graph <String>{
    
    public SocialGraph() {}
    
    
    
    public SocialGraph(int[][] edges, String[] vertices) {
        
        super(edges, vertices);
        
    }
    
    
    
    /**
     * calculates and returns the normalized degree of centrality for a given
     * vertex v. The required value is calculated as: 
     * 
     * degree(v) / (n-1)
     * 
     * where degree(v) represents the number of vertex incident edges and n
     * represents the number of graph vertices. For social graphs, a high degree
     * of centrality for a person v reflects his/her dominant position in the
     * group or his/her social interaction skills.
     */
    public double normalizedDegreeOfCentrality(String vertex) {
        
        if (getIndex(vertex) >= 0) {
        
            return ((double)getDegree(getIndex(vertex))) / (getSize() - 1);
        
        } else {
            
            System.out.println("Vertex " + vertex + " not in graph.");
            return 0;
            
        }
        
    }
    
    
    
    /**
     * calculates and returns the number of triangles incident to vertex v. The
     * algorithm below calculates the number of triangles incident for all graph
     * vertices (V is the set of vertices, E is the set of edges):
              
     * for each v in V
     *     for each pair of vertices (p, q) in AdjacencyList(v)
     *         if (p, q) is in E then add 1 to triangles[v]
     */
    public int numberOfTrianglesIncidentToVertex(String vertex) {
        
        int vertexIndex = getIndex(vertex);
        
        if (vertexIndex >= 0) {
        
            List <Integer> adjacentNodes = neighbors.get(vertexIndex);
        
            int triangles = 0;
        
            for (int a = 0; a < adjacentNodes.size(); a++) {
            
                for (int b = (a + 1); b < adjacentNodes.size(); b++) {
                
                    if (neighbors.get(adjacentNodes.get(a)).contains(adjacentNodes.get(b))) {
                        
                                                         //  If nodes a and b are
                                                         //  adjacent, increment
                        triangles++;                     //  triangles
                    
                    }  // if a and b are adjacent
                
                }  //  for integer b in adjacentNodes
            
            }  //  for integer a in adjacentNodes
        
            return triangles;
        
        } else {
            
            System.out.println("Vertex " + vertex + " not in graph.");
            
            return 0;
            
        }
        
    }  //  end numberOfTrianglesIncidentToVertec() method
    
    
    
    /**
     * calculates and returns the list of triangles incident to vertex v.  A
     * triangle should be specified by its vertices.
     */
    public List<String> listOfTrianglesIncidentToVertex(String vertex) {
        
        int vertexIndex = getIndex(vertex);
        
        if (vertexIndex >= 0) {
        
            List <Integer> adjacentNodes = neighbors.get(vertexIndex);
        
            List <String> returnList = new ArrayList<>();
        
            for (int a = 0; a < adjacentNodes.size(); a++) {
            
                for (int b = (a + 1); b < adjacentNodes.size(); b++) {
                
                    if (neighbors.get(adjacentNodes.get(a)).contains(adjacentNodes.get(b))) {
                    
                                                         //  If nodes a and b are
                                                         //  adjacent, proceed with
                                                         //  recording triangle
                    
                        String nextTriangle = "(" + vertex + " - ";
                        nextTriangle += (vertices.get(adjacentNodes.get(a)) + " - ");
                        nextTriangle += vertices.get(adjacentNodes.get(b)) + ")";
                    
                        returnList.add(nextTriangle);
                    
                    }  // if a and b are adjacent
                
                }  //  for integer b in adjacentNodes
            
            }  //  for integer a in adjacentNodes
        
            return returnList;
        
        } else {
            
            System.out.println("Vertex " + vertex + " not in graph.");
            
            return null;
        }
        
    }  //  end listOfTrianglesIncidentToVertex() method
    
    
    
    /**
     * For a given vertex v, calculates and returns the percentage indicating
     * how close its neighbors are to make a complete graph and is calculated
     * as:

     * [(number of edges connecting v's neighbor vertices) / (number of edges, potentially connecting  v's neighbor vertices)] * 100

     * where:
     * 
     * -    the number of edges connecting v’s neighbor vertices is calculated as:
     * 
     *      (number of triangles incident to vertex v)
     * 
     * 
     * -    the number of edges, potentially connecting v's neighbor vertices is
     *      calculated as:
     * 
     *      [degree(v) * (degree(v) - 1)] / 2
     * 
     * For social graphs this value measures of how close wrapped are the persons
     * in the social graph around the given person.
     */
    public double clusterIndividual(String vertex) {
        
        int vertexIndex = getIndex(vertex);
        
        if (vertexIndex >= 0) {
        
            double numerator = numberOfTrianglesIncidentToVertex(vertex);
        
            double denominator = (getDegree(vertexIndex) * (getDegree(vertexIndex) - 1)) / 2;
        
            if(denominator != 0) {
                
                return numerator / denominator * 100;
                
            } 
                
            
        
        }  else System.out.println("Vertex " + vertex + " not in graph.");
        
        return 0;
        
    }
    
    
    
    /**
     * For the social graph, calculated as (the sum applies to all vertices v in
     * V):
     * (1 / n) * ∑ clusterIndividual (v)
     * 
     * This value indicates the overall density of the social graph.
     */
    public double averageClustering() {
        
        double sum  = 0;
        
        for (String vertice : vertices) {
            
            sum += clusterIndividual(vertice);
            
        }
        
        return ((1 / (double) vertices.size()) * sum);
        
    }
    
    
    
    /**
     * Determines whether two persons supplied as parameters can establish
     * social contact direct or through a chain of transitive acquaintance
     * relationships (in terms of graphs it means that there is a path between
     * the two nodes representing the two persons).
     */
    public boolean isAcquaintance(String node1, String node2) {
        
        int vertexIndex = getIndex(node1);
        
        if (vertexIndex >= 0) {
        
            List <Integer> adjacentNodes = neighbors.get(vertexIndex);
        
            List <String> visitedNodes = new ArrayList();
        
            visitedNodes.add(node1);
        
            //  Look for node2 in node1's adjacency list
            for (Integer i : adjacentNodes) {
            
                if (getVertex(i).equalsIgnoreCase(node2)) {
                
                    return true;
                
                }
            
            }
        
            //  Continue the search
            for (Integer i : adjacentNodes) {
            
                if (!visitedNodes.contains(getVertex(i))) {
                
                    visitedNodes.add(getVertex(i));
            
                    if (isAcquaintance(getVertex(i), node2, visitedNodes)) {
                    
                        return true;
                    
                    }
            
                }
            
            
            }
        
        }  else {  //  if node1 is found
            
            System.out.println("One or both nodes are not in the graph");
            
        }
        
        return false;
        
    }
    
    
    /**
     * Private, recursive version of isAcquaintance()
     */
    private boolean isAcquaintance(String node1, String node2, List<String> visitedNodes) {
        
        int vertexIndex = getIndex(node1);
        
        List <Integer> adjacentNodes = neighbors.get(vertexIndex);
        
        visitedNodes.add(node1);
        
        //  Look for node2 in node1's adjacency list
        for (Integer i : adjacentNodes) {
            
            if (getVertex(i).equalsIgnoreCase(node2)) {
                
                return true;
                
            }
            
        }
        
        //  Continue the search
        for (Integer i : adjacentNodes) {
            
            if (!visitedNodes.contains(getVertex(i))) {
                
                visitedNodes.add(getVertex(i));
            
                if (isAcquaintance(getVertex(i), node2, visitedNodes)) {
                    
                    return true;
                    
                }
            
            }
            
            
        }
        
        return false;
        
    }
    
    
    
}
