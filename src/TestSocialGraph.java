/**
 * TestSocialGraph.java
 * 
 * CMSC 350
 * Final Project
 * 
 * Alan Johnson
 * 1 December 2014
 * NetBeans IDE 8.0.1
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Lessons Learned:  Keeping track of whether a node index coincides with an
 * adjacency list or the overall vertices list gave me a little trouble at
 * first, while designing SocialGraph class methods.
 * 
 * Designing the isAcquaintance() method worried me at first, because the graph
 * is connected: the expected result would be every node combination would
 * result in 'true'.  But, there could still be a mistake in the method that
 * makes it always yield true.  To verify that this was not the case, I changed
 * my input file to create a disjoint graph and chose two nodes that were not
 * connected.
 */


public class TestSocialGraph {
    
    private static final String filename = "socialgraph.txt";
    
    public static void main(String[] args) {
        
        //                       ----  Setup  ----
        
        System.out.println("Alan Johnson, CMSC 350, Final Project \n");
        
        
        
        //                       ----  User Input  ----
       
        SocialGraph sg = parseFileInput(filename);
        
        if (sg != null) {
        
            sg.printEdges();
        
            userInput(sg);
        
        }  else {
            
            System.out.println("File " + filename + " not found.");
        }
        
    }  //  end main() method
    
    
    
    /**
     * Calls printOptions() and then proceeds to operate on the specified tree
     * based on the user input
     */
    public static void userInput(SocialGraph sg) {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int response = 0;
        
        do {
            
            printOptions();
            
            try {
                
                response = Integer.parseInt(br.readLine());
                
                String input1, input2;
            
                switch (response) {
                    case 1:
                        System.out.print("Enter the name of the vertex: ");
                        input1 = br.readLine();
                        System.out.println("Normalized Degree of Centrality (" + input1 + "): " + sg.normalizedDegreeOfCentrality(input1));
                        break;
                        
                    case 2:
                        System.out.print("Enter the name of the vertex: ");
                        input1 = br.readLine();
                        System.out.println("Number of Triangles Incident to Vertex (" + input1 + "): " + sg.numberOfTrianglesIncidentToVertex(input1));
                        break;
                    
                    case 3:
                        System.out.print("Enter the name of the vertex: ");
                        input1 = br.readLine();
                        System.out.println("List of Triangles Incident to Vertex (" + input1 + "): ");
                        List triangleList = sg.listOfTrianglesIncidentToVertex(input1);
                        
                        if (triangleList != null) {
                            
                            for (Object s : triangleList) {
                                
                                System.out.println(s);
                                
                            }
                            
                        }
                        break;
                        
                    case 4:
                        System.out.print("Enter the name of the vertex: ");
                        input1 = br.readLine();
                        System.out.println("Cluster of " + input1 + ": " + sg.clusterIndividual(input1));
                        break;
                        
                    case 5:
                        System.out.println("Average Clustering :" + sg.averageClustering());
                        break;
                        
                    case 6:
                        System.out.print("Enter the name of the first vertex: ");
                        input1 = br.readLine();
                        System.out.print("Enter the name of the second vertex: ");
                        input2 = br.readLine();
                        System.out.println("Vertices " + input1 + " and " + input2 + " are acquaintances: " + sg.isAcquaintance(input1, input2));
                        break;
                        
                    case 7:
                        System.out.print("Enter the name of new vertex: ");
                        input1 = br.readLine();
                        sg.addVertex(input1);
                        System.out.println("Added vertex (" + input1 + ") at index " + sg.getIndex(input1));
                        sg.printEdges();
                        break;
                        
                    case 8:
                        System.out.print("Enter starting node: ");
                        input1 = br.readLine();
                        System.out.print("Enter ending node: ");
                        input2 = br.readLine();
                        sg.addEdge(sg.getIndex(input1),sg.getIndex(input2));
                        sg.printEdges();
                        break;
                        
                    case 9:
                        System.out.println("Printing edges:");
                        sg.printEdges();
                        break;
                    
                    default:
                        System.out.println("Please enter a number 0-11");
                        break;
                    
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    
                    
                }
            
            } catch (IOException e) {
                
                System.out.println("Invalid entry.");
                
            } catch (NumberFormatException e) {
                
                System.out.println("Non-numeric entry.");
                
                response = 10;
                
            }
            
        } while (response != 0);
        
    }
    
    
    
    /**
     * Displays all of the user options
     */
    private static void printOptions() {
        
        System.out.println("\n\nSelect an Option:");
        
        System.out.println(" (1) Normalized Degree of Centrality\n"
                         + " (2) Number of Triangles Incident to Vertex\n"
                         + " (3) List of Triangles Incident to Vertex\n"
                         + " (4) Cluster of Individual\n"
                         + " (5) Average Clustering\n"
                         + " (6) Indirect Acquaintance\n"
                         + " (7) Add Vertex\n"
                         + " (8) Add Edge\n"
                         + " (9) Print Edges\n"
                         + " (0) Exit the Loop and the Program\n\n");
        
        System.out.print("Choice: ");
        
    }
    
    
    
    /**
     * This method is called by main().  It uses the filename parameter only
     * in order to pass it to the getFileInput() method.
     * 
     * It takes the Scanner object returned by getFileInput() and reads from
     * it line-by-line.  With each line String, it parses the text using the
     * String.split() method, saving the String array returned by that
     * method.
     */
    private static SocialGraph parseFileInput(String filename) {
        
        Scanner scanner = getFileInput(filename);
        
        SocialGraph sg = new SocialGraph();
        
        String[] vertices;
        
        int[][] neighbors;
        
        if (scanner != null) {
            
            
            if (scanner.hasNextLine()) {               //  Parse vertices
            
                String delim = "[;]+";
            
                String nextLine = scanner.nextLine();
                
                vertices = nextLine.split(delim);
                
                List<Integer[]> edgeList = new ArrayList<>();
                
                int vertexCount = 0;
                
                if (scanner.hasNext()) {
                    
                    if (scanner.nextLine().equalsIgnoreCase("#")) {
                    
                        while (scanner.hasNextLine()) {
                        
                            delim = "\\s";
                        
                            nextLine = scanner.nextLine();
                        
                            String[] edges = nextLine.split(delim);
                        
                            for (int i = 1; i < edges.length; i++) {
                            
                                try {
                                    
                                    Integer[] newArray = {Integer.parseInt(edges[0]), Integer.parseInt(edges[i])};
                                    
                                    edgeList.add(newArray);
                                    
                                } catch (NumberFormatException e) {
                                
                                    System.out.println("Error in parsing edges");
                                
                                }
                            
                            }  //  for loop : adding edges to edgeList
                        
                            vertexCount++;
                            
                        }  //  while scanner has another edge line
                        
                        neighbors = new int[edgeList.size()][2];
                        
                        for (int row = 0; row < edgeList.size(); row++) {
                            
                            for (int column = 0; column < edgeList.get(row).length; column++) {
                                
                                neighbors[row][0] = (edgeList.get(row))[0];
                                neighbors[row][1] = (edgeList.get(row))[1];
                                
                            }
                            
                        }
                        
                        return new SocialGraph(neighbors, vertices);
                    
                    }  // if "#" character is found
                        
                }  //  if vertices are present
                
            }
            
        }  // if textReader exists
        
        return null;
        
    }  // method parseInput()
    
    
    
    /**
     * This method passes the filename parameter to the getResourceAsStream()
     * method to open the intended file.
     * 
     * The Scanner object containing the file's data is returned.
     */
    private static Scanner getFileInput(String filename) {
        
        Scanner scanner;
        
        try {
            
        InputStream in = (new TestSocialGraph()).getClass().getResourceAsStream(filename);
        
        scanner = new Scanner(in);
        
        } catch (NullPointerException e) {
            
            System.out.println("File input fail!");
            
            return null;
            
        }  //  catch
        
        return scanner;
        
    }  //  end getInput()
    
    
}
