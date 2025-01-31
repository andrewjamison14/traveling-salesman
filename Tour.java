/**
 * This class creates a Tour of Points using a
 * Linked List implementation.  The points can
 * be inserted into the list using two heuristics.
 *
 * @author Andrew Jamison
 * @author Kyle Thompson
 * @author Sneha Narayan, modified code 1-22-2019
 * @author Titus Klinge, modified code 09-25-2018
 * @author Eric Alexander, modified code 01-12-2018
 * @author Layla Oesper, modified code 09-22-2017
 */

public class Tour
{
    /**
     * A helper class that defines a single node for use in a tour.
     * A node consists of a Point, representing the location of that
     * city in the tour, and a pointer to the next Node in the tour.
     */
    private class Node
    {
        private Point p;
        private Node next;

        /**
         * Constructor creates a new Node at the given Point newP
         * with an initial next value of null.
         *
         * @param newP the point to associate with the Node.
         */
        public Node(Point newP)
        {
            p = newP;
            next = null;
        }

        /**
         * Constructor creates a new Node at the given Point newP
         * with the specified next node.
         *
         * @param newP the point to associate with the Node.
         * @param nextNode the nextNode this node should point to.
         */
        public Node(Point newP, Node nextNode)
        {
            p = newP;
            next = nextNode;
        }
    } // End Node class


    // Tour class Instance variables
    private Node head;
    private int size; //number of nodes

    private double distance = 0.0;
    //Add other instance variables you think might be useful.


    /**
     * Constructor for the Tour class.  By default sets head to null.
     * Modify this method as necessary if you choose to add other Instance
     * variables.
     */
    public Tour()
    {
        head = null;
        size = 0;
    }

    
    // ADD YOUR METHODS BELOW HERE
    public String toString() {
    String output = "";  
    Node current = head;

    // if empty
    if (current == null) {
        return "Tour is empty.";
    }

    // iterate through
    while (current != null && current != head) {
        output += current.p.toString();  
        output += "\n";
        current = current.next;
    } 
    return output;
    }

    public void draw() {
        
        Node current = head;
        
        //iterate through, draw each point and line to next one
        while (current.next != null){
            current.p.draw();
            current.p.drawTo(current.next.p);
            current = current.next;
        }
    }

    public int size() {
        return size;
    }

    public double distance() {
        // iterates through each node in the tour and calculates distance traveled between each
        Node current = head;
        while (current.next != null){
            distance = distance + current.p.distanceTo(current.next.p);
            current = current.next;
        }
        distance = distance + current.p.distanceTo(head.p);
        return distance;
    }

    public void insertNearest(Point p) {
        //creates start of tour
        if (head == null){
            head = new Node(p);
        }
        
        Node current = head;
        Node nearest = head;
        double nearestDistance = Double.MAX_VALUE;

       
        //iterates through every node in tour
        while (current.next != null){
            //finds distance between new and current
            double dist = p.distanceTo(current.p);
            
            //keeps track of nearest
            if (dist < nearestDistance) {
                nearestDistance = dist;
                nearest = current;
            }
            //moves to next
            current = current.next;
        }

        //inserts new point next to nearest node
        Node newNode = new Node(p, nearest.next);  
        nearest.next = newNode;  

        //increment
        size++;
    }

    

    public void insertSmallest(Point p) {
        // starts tour and adds first node
        if (head == null){
            head = new Node(p);
            size++;
            return;
        }
        
        Node current = head;
        Node nearest = null;

        if (size == 1) {
            nearest = new Node(p);
            head.next = nearest;
            size++;
            return;
        }

        /**
     * iterates through each node of tour and calculates extra distance added for each potential location
     * Inserts new point after node that adds the least extra distance
     */
        current = head;
        double smallestDifference = Double.MAX_VALUE;

        while (current.next != null && current.next != head){
            //distance calculation
            double diff = current.p.distanceTo(p) + current.next.p.distanceTo(p) - current.p.distanceTo(current.next.p);

            //tracking smallest
            if (diff < smallestDifference) {
                smallestDifference = diff;

                nearest = current;
            }
            current = current.next;
        }
        //checking end to head
        double diff = current.p.distanceTo(p) + head.p.distanceTo(p) - current.p.distanceTo(head.p);

        if (diff < smallestDifference) {
            smallestDifference = diff;

            nearest = current;
        }
        
        //insert and increment
        Node newNode = new Node(p, nearest.next); 
        nearest.next = newNode;
        size++;
    }

    public static void main(String[] args)
    {
        /* Use your main() function to test your code as you write it.
         * This main() will not actually be run once you have the entire
         * Tour class complete, instead you will run the NearestInsertion
         * and SmallestInsertion programs which call the functions in this
         * class.
         */


        Tour tour = new Tour();
        Point p = new Point(0,0);
        tour.insertNearest(p);
        p = new Point(0,100);
        tour.insertNearest(p);
        p = new Point(50, 40);
        tour.insertNearest(p);
        p = new Point(20, 30);
        tour.insertNearest(p);
        System.out.println("Tour distance =  "+tour.distance());
        System.out.println("Number of points = "+tour.size());
        System.out.println(tour);


        // the tour size should be 3 and the distance 341.42 (don't forget to include the trip back
        // to the original point)

        // uncomment the following section to draw the tour, setting w and h to the max x and y
        // values that occur in your tour points

        /*
        int w = 100 ; //Set this value to the max that x can take on
        int h = 100 ; //Set this value to the max that y can take on
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.setPenRadius(.005);
        tour.draw();
        */
        
    }
}
