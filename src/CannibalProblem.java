package cannibals;

import java.util.ArrayList;
import java.util.Arrays;


// for the first part of the assignment, you might not extend UUSearchProblem,
//  since UUSearchProblem is incomplete until you finish it.

public class CannibalProblem extends UUSearchProblem{

	// the following are the only instance variables you should need.
	//  (some others might be inherited from UUSearchProblem, but worry
	//  about that later.)

	private int goalm, goalc, goalb;
	private int totalMissionaries, totalCannibals; 
	
	public CannibalProblem(int sm, int sc, int sb, int gm, int gc, int gb) {
		// I (djb) wrote the constructor; nothing for you to do here.

		startNode = new CannibalNode(sm, sc, 1, 0);
		goalm = gm;
		goalc = gc;
		goalb = gb;
		totalMissionaries = sm;
		totalCannibals = sc;
		
	}
	
	// node class used by searches.  Searches themselves are implemented
	//  in UUSearchProblem.
	private class CannibalNode implements UUSearchNode{

		// do not change BOAT_SIZE without considering how it affect
		// getSuccessors. 
		
		private final static int BOAT_SIZE = 2;
	
		// how many missionaries, cannibals, and boats
		// are on the starting shore
		private int[] state; 
		
		// how far the current node is from the start.  Not strictly required
		//  for search, but useful information for debugging, and for comparing paths
		private int depth;  

		public CannibalNode(int m, int c, int b, int d) {
			state = new int[3];
			this.state[0] = m;
			this.state[1] = c;
			this.state[2] = b;
			
			depth = d;

		}

		public ArrayList<UUSearchNode> getSuccessors() {

			// add actions (denoted by how many missionaries and cannibals to put
			// in the boat) to current state. 

			// You write this method.  Factoring is usually worthwhile.  In my
			//  implementation, I wrote an additional private method 'isSafeState',
			//  that I made use of in getSuccessors.  You may write any method
			//  you like in support of getSuccessors.
			
			ArrayList<UUSearchNode> reachableStates = new ArrayList<UUSearchNode>();

			// send i missionaries and j cannibals across the river
			if (this.state[2] == 1){
				for (int i = 0; i <= 2; i++)
					for (int j = 0; j <= 2; j++){
						if (i + j == 0 || i + j > 2){
							continue;
						}
						if (this.state[0] - i >= 0 && this.state[1] - j >= 0 && (this.state[0] - i == 0 || this.state[0] - i == totalMissionaries || (this.state[0] - i >= this.state[1] - j && totalMissionaries - this.state[0] + i >= totalCannibals - this.state[1] + j))){
							reachableStates.add(new CannibalNode(this.state[0] - i, this.state[1] - j, 0, depth + 1));
						}
				}
			}
			else {
				// send i missionaries and j cannibals back to the starting shore
				for (int i = 0; i <= 2; i++)
					for (int j = 0; j <= 2; j++){
						if (i + j == 0 || i + j > 2){
							continue;
						}
						if (this.state[0] + i <= totalMissionaries && this.state[1] + j <= totalCannibals && (this.state[0] + i == 0 || this.state[0] + i == totalMissionaries || (this.state[0] + i >= this.state[1] + j && totalMissionaries - this.state[0] - i >= totalCannibals - this.state[1] - j))){
							reachableStates.add(new CannibalNode(this.state[0] + i, this.state[1] + j, 1, depth + 1));
						}
				}
			}
			return reachableStates;
		}
		
		@Override
		public boolean goalTest() {
			// you write this method.  (It should be only one line long.)
			return (this.state[0] == goalm && this.state[1] == goalc && this.state[2] == goalb);
		}

		// an equality test is required so that visited lists in searches
		// can check for containment of states
		@Override
		public boolean equals(Object other) {
			return Arrays.equals(state, ((CannibalNode) other).state);
		}

		@Override
		public int hashCode() {
			return state[0] * 100 + state[1] * 10 + state[2];
		}

		@Override
		public String toString() {
			// you write this method
			String node_state = "(" + this.state[0] + ", " +  this.state[1] + ", " +  this.state[2] + ")";
			return node_state;
		}

		/*
        You might need this method when you start writing 
        (and debugging) UUSearchProblem.*/
        
		@Override
		public int getDepth() {
			return depth;
		}
		

	}
	

}