# Network_Processing
Example of work done during ICS 340 - Algorithms and Data Structures. I was provided with a project template by my professor and am solely responsible for the contents of DelivA, DelivB, DelivC and DelivD and the comparator files. I also modified the Edge and Node classes. I have provided test files for each deliverable. The application works by running prog340 and engaging with the Swing generated GUI.A test file must be read before running its respective deliverable.


## Deliverable A

This project consists of four deliverables, each of which accomplishes a different goal. Deliverable A is more of a proof of concept, and shows how network information can be obtained by traversing the provided lists. For examples, all edges in the network are included in a list, with clear head and tail nodes that determine direction. 
There is also a separate list for all nodes, which independently contain their respective incoming and outgoing edges in private lists.


## Deliverable B

DelivB takes in a collection of nodes and edges and calculates the shortest bitonic tour between them. We were provided sources that explained ways to implement the bitonic tour algorithm and were required to implement the algorithm in code. I focused on implementing the necessary data strucures required by the algorithm such as the two dimensional b, p, and predecessor matrices and implemented conditions. I then used the rules provided by the algorithm to update the matrices. A good example of this is in the method that calculates the B matrix, in which I implement logic for a base case and two other conditions mentioned by the bitonic tour algorithm. The agorithm is carried out once for each node in the graph, after which the calculated shortest tour distance is displayed.

DelicB also required me to create and implement a comparator, which sorts nodes (which are analogous to cities on a map for DelivB) by their value, which represents latitude.


## Deliverable C

DelivC uses the given network information and carries out a depth first search, given a starting location. It also displays the following:

1. The discovery and finish times of each node in the depth first
search of the original graph.
2. The classification of each edge in the original graph.
3. The discovery and finish times of each node in the depth first search of the complementary graph.
4. Each strongly connected component of the original graph.

For this deliverable, I modified the Node class to include the following new fields, which were exclusive to depth first search;

	String color;  // determines whether a node is white (unvisited), gray (visited, but not completed) or black (done)
	int discoveryTime;
	int finishTime;
	Node pred; // determines node that precedes it
	int subtree; // used in DFS to detect cross edges (it begins at 1 and is incremented every time the algorithm visits the root node. This allows for easy detection of cross edges)
  
  I also created and implemented EdgeComparator, which sorts edges alphabeticaly and by distance. This is done becauuse the depth first search code just iterates over lists of edges wihtout any sorting logic. The comparator ensures that the depth first search code considers edges by alphabetical order and shortest distance by pre sorting the list. DiscoveryTimeComparator is used when the program displays the order of discovery and finish times. 
 

 ## Deliverable D 
  
For deliverable D, I implemented a constraint satisfaction algorithm. This algorithm receives a list of courses represented by nodes, and creates a valid four year course schedule. A valid schedule must meet the following constraints:
- It cannot be taken before its predecessor
- It cannot be taken on the same day as another class. The courses are offered on different days depending on the semester they are registered for.

My program works by first generating a valid schedule containing 32 courses. 31 are received as input and a dummy one is used to make semesters easier to assign. Constraint conflicts are calculated next, and each course is assigned a number equal to the constraints broken by its current placement. The two courses with the most conflicts are swapped and constraint conflicts are calculated once again for all courses. 

A list of schedules are kept in a string array of size 10,000, known as a tabu list. If the current course order is found in the tabu list, a random walk is performed. A random walk swaps the order of two courses at random, in order to prevent stagnation. 

Once every 100,000 iterations, a completely random schedule is chosen. This also helps prevent feedback cycles which might reduce the ability to find a valid solution. A condition with no constraint conflicts is considered valid and is displayed to the user once discovered.
