package nl.vtslab.fosdem2019.traversal;

import java.lang.Override;
import java.util.*;

import org.apache.tinkerpop.gremlin.process.traversal.Step;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.Traverser;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.util.DefaultTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;


public final class DefaultAuthorizedTraversal<S, E> extends DefaultTraversal<S, E> implements AuthorizedTraversal<S, E> {

  /*
   * The constructors and methods below were generated initially by @GremlinDSL
   */
  public DefaultAuthorizedTraversal() {
    super();
  }

  public DefaultAuthorizedTraversal(Graph graph) {
    super(graph);
  }

  public DefaultAuthorizedTraversal(AuthorizedTraversalSource traversalSource) {
    super(traversalSource);
  }

  public DefaultAuthorizedTraversal(AuthorizedTraversalSource traversalSource, GraphTraversal.Admin traversal) {
    super(traversalSource, traversal.asAdmin());
  }

  @Override
  public AuthorizedTraversal<S, E> iterate() {
    return (AuthorizedTraversal) super.iterate();
  }

  @Override
  public GraphTraversal.Admin<S, E> asAdmin() {
    return (GraphTraversal.Admin) super.asAdmin();
  }

  @Override
  public DefaultAuthorizedTraversal<S, E> clone() {
    return (DefaultAuthorizedTraversal) super.clone();
  }

  /*
   * Hacks that close some wide open backdoors for defeating AuthorizedTraversal's filtering
   * The hacks are ugly because they depend on the implementation, which can change, and on
   * namespaces, which can be forged.
   *
   * ToDo: Faster ways than getting the stacktrace exist, see
   * https://stackoverflow.com/questions/421280/how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection
   */

  @Override
  public Optional<Graph> getGraph() {

    List<String> callerMatch = Arrays.asList(
        "gremlin.process.traversal.step",
        "gremlin.process.traversal.strategy.optimization",
        "tinkergraph.process.traversal.step",
        "janusgraph.graphdb.tinkerpop.optimize"
    );
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (callerMatch.stream().noneMatch(x -> stackTraceElements[2].getClassName().contains(x) ||
            stackTraceElements[3].getClassName().contains(x))) {
      throw (new RuntimeException(blockMessage));
    }
    return super.getGraph();
  }

  @Override
  public void addStart(Traverser.Admin<S> start) {
    List<String> callerMatch = Collections.singletonList("org.apache.tinkerpop.gremlin.process.traversal");
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (callerMatch.stream().noneMatch(x -> stackTraceElements[2].getClassName().contains(x))){
      throw (new RuntimeException(blockMessage));
    }
    super.addStart(start);
  }

  @Override
  public void addStarts(Iterator<Traverser.Admin<S>> starts) {
    List<String> callerMatch = Collections.singletonList("org.apache.tinkerpop.gremlin.process.traversal");
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (callerMatch.stream().noneMatch(x -> stackTraceElements[2].getClassName().contains(x))){
      throw (new RuntimeException(blockMessage));
    }
    super.addStarts(starts);
  }

  @Override
  public <S2,E2> Traversal.Admin<S2,E2> addStep(int index, Step<?,?> step) {
    List<String> callerMatch = Arrays.asList(
            "org.apache.tinkerpop.gremlin.process.traversal",
            "nl.vtslab.fosdem2019.traversal.AuthorizedTraversalSource"
    );
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (callerMatch.stream().noneMatch(x -> stackTraceElements[2].getClassName().contains(x) ||
            stackTraceElements[3].getClassName().contains(x))) {
      throw (new RuntimeException(blockMessage));
    }
    return super.addStep(index, step);
  }

  @Override
  public <E2> GraphTraversal.Admin<S,E2> addStep(Step<?,E2> step) {
    List<String> callerMatch = Arrays.asList(
      "org.apache.tinkerpop.gremlin.process.traversal",
      "nl.vtslab.fosdem2019.traversal.AuthorizedTraversalSource"
    );
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (callerMatch.stream().noneMatch(x -> stackTraceElements[2].getClassName().contains(x) ||
                                            stackTraceElements[3].getClassName().contains(x))) {
      throw (new RuntimeException(blockMessage));
    }
    return (GraphTraversal.Admin<S,E2>)super.addStep(step);
  }

  @Override
  public <S2,E2> Traversal.Admin<S2,E2> removeStep(int index) {
    List<String> callerMatch = Arrays.asList(
            "org.apache.tinkerpop.gremlin.process.traversal",
            "process.traversal.strategy",
            "janusgraph.graphdb.tinkerpop.optimize"
    );
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (callerMatch.stream().noneMatch(x -> stackTraceElements[2].getClassName().contains(x) ||
            stackTraceElements[3].getClassName().contains(x))) {
      throw (new RuntimeException(blockMessage));
    }
    return super.removeStep(index);
  }

  @Override
  public <S2,E2> Traversal.Admin<S2,E2> removeStep(Step<?,?> step) {
    List<String> callerMatch = Arrays.asList(
        "org.apache.tinkerpop.gremlin.process.traversal",
        "process.traversal.strategy",
        "janusgraph.graphdb.tinkerpop.optimize"
    );
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    if (callerMatch.stream().noneMatch(x -> stackTraceElements[2].getClassName().contains(x) ||
            stackTraceElements[3].getClassName().contains(x))) {
      throw (new RuntimeException(blockMessage));
    }
    return super.removeStep(step);
  }
}
