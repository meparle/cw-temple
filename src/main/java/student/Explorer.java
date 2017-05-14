package student;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;

import java.util.*;

/**
 * @author eileenparle
 */
public class Explorer {
    private Set<Long> visited = new HashSet<>();
    private Stack<Long> path = new Stack<>();

  /**
   * Explore the cavern, trying to find the orb in as few steps as possible.
   * Once you find the orb, you must return from the function in order to pick
   * it up. If you continue to move after finding the orb rather
   * than returning, it will not count.
   * If you return from this function while not standing on top of the orb,
   * it will count as a failure.
   *   
   * <p>There is no limit to how many steps you can take, but you will receive
   * a score bonus multiplier for finding the orb in fewer steps.</p>
   * 
   * <p>At every step, you only know your current tile's ID and the ID of all
   * open neighbor tiles, as well as the distance to the orb at each of these tiles
   * (ignoring walls and obstacles).</p>
   * 
   * <p>To get information about the current state, use functions
   * getCurrentLocation(),
   * getNeighbours(), and
   * getDistanceToTarget()
   * in ExplorationState.
   * You know you are standing on the orb when getDistanceToTarget() is 0.</p>
   *
   * <p>Use function moveTo(long id) in ExplorationState to move to a neighboring
   * tile by its ID. Doing this will change state to reflect your new position.</p>
   *
   * <p>A suggested first implementation that will always find the orb, but likely won't
   * receive a large bonus multiplier, is a depth-first search.</p>
   *
   * @param state the information available at the current state
   */
  public void explore(ExplorationState state) {
      while (state.getDistanceToTarget() != 0) {
          boolean moved = false;
          long currentNodeId = state.getCurrentLocation();
          visited.add(currentNodeId);
          path.push(currentNodeId);
          Collection<NodeStatus> neighbours = state.getNeighbours();
          for (NodeStatus x : neighbours) {
              if (!visited.contains(x.getId())) {
                  state.moveTo(x.getId());
                  moved = true;
                  break;
              }
          }
          if (!moved) {
              path.pop();
              state.moveTo(path.pop());
          }
      }
  }

  /**
   * Escape from the cavern before the ceiling collapses, trying to collect as much
   * gold as possible along the way. Your solution must ALWAYS escape before time runs
   * out, and this should be prioritized above collecting gold.
   *
   * <p>You now have access to the entire underlying graph, which can be accessed 
   * through EscapeState.
   * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
   * will return a collection of all nodes on the graph.</p>
   * 
   * <p>Note that time is measured entirely in the number of steps taken, and for each step
   * the time remaining is decremented by the weight of the edge taken. You can use
   * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
   * on your current tile (this will fail if no such gold exists), and moveTo() to move
   * to a destination node adjacent to your current node.</p>
   * 
   * <p>You must return from this function while standing at the exit. Failing to do so before time
   * runs out or returning from the wrong location will be considered a failed run.</p>
   * 
   * <p>You will always have enough time to escape using the shortest path from the starting
   * position to the exit, although this will not collect much gold.</p>
   *
   * @param state the information available at the current state
   */
  public void escape(EscapeState state) {
      LinkedList<Node> route = findRoute(state.getCurrentNode(), state.getExit());
      
      return;
  //create a queue of paths through the maze (list of nodes), without actually moving, breadth first search

  //choose best path and move, checking for gold as you go
  }

    private LinkedList<Node> findRoute(Node start, Node exit) {
        Queue<LinkedList<Node>> waysOut = new LinkedList<>();
        LinkedList<Node> path = new LinkedList<>();
        path.add(start);
        waysOut.add(path);
        while (true) {
            //take the next LinkedList in the queue and investigate the most recent Node's neighbours
            LinkedList<Node> candidate = waysOut.remove();
            Set<Node> neighbours = candidate.peekLast().getNeighbours();
            for (Node x : neighbours) {
                LinkedList<Node> copiedCandidate = new LinkedList<>(candidate);
                copiedCandidate.add(x);
                waysOut.add(copiedCandidate);
                if (x.equals(exit)) {
                    return copiedCandidate;
                }
            }
        }
    }
}
