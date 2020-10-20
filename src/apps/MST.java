package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
		
		PartialTreeList L = new PartialTreeList();
		
		if (graph == null)
			return null;
	
		for (Vertex v : graph.vertices){
			
			PartialTree T = new PartialTree(v);
			
			for (Vertex.Neighbor n = v.neighbors; n != null; n = n.next){
				PartialTree.Arc a = new PartialTree.Arc(v, n.vertex, n.weight);
				T.getArcs().insert(a);
			}
						
			L.append(T);
		}
		
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		ArrayList<PartialTree.Arc> result = new ArrayList<PartialTree.Arc>();
		
		while (ptlist.size() > 1){
			PartialTree PTX = ptlist.remove();
			MinHeap<PartialTree.Arc> PQX = PTX.getArcs();
			PartialTree.Arc a = PQX.deleteMin();
			Vertex v1 = a.v1, v2 = a.v2;
			
			while (v2.getRoot() == PTX.getRoot()){
				a = PQX.deleteMin();
				v2 = a.v2;
			}
			
			result.add(a);
			PartialTree PTY = ptlist.removeTreeContaining(v2);
			PTX.merge(PTY);
			ptlist.append(PTX);
		}
		
		
		return result;
	}
}