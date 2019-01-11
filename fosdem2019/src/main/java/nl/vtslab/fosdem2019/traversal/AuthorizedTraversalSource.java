/*
 * Copyright 2018 vtslab authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.vtslab.fosdem2019.traversal;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import org.apache.commons.configuration.Configuration;
import org.apache.tinkerpop.gremlin.process.computer.Computer;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.process.remote.RemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.Bindings;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategies;
import org.apache.tinkerpop.gremlin.process.traversal.TraversalStrategy;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.step.map.GraphStep;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 *  TraversalSource that enforces the use of a withAuthorization() method and returns an
 *  AuthorizedTraversal that filters all vertices and edges by their "authz" property.
 *
 *  The AuthorizedTraversalSource is not intended as a security measure per se (it is used in a
 *  the trusted context of a service using janusgraph), but rather:
 *   - it keep traversals readable [all has("authz", __.where(P.within(USERAUTHORIZATION)))
 *         filters are implied]
 *   - it makes the proper enforcement of user authorizations easily auditable, see below.
 *
 *  A source code audit to check the right application of data access authorizations would only need:
 *   - a check whether all JanusGraph queries are executed with the
 *       nl.vtslab.fosdem2019.traversal.AuthorizedTraversalSource (beware of similarly named classes)
 *   - a check of recent changes in nl.vtslab.fosdem2019.traversal.AuthorizedTraversalSource
 *   - a check of calls to TraversalStrategies.GlobalCache.registerStrategies() [a strategy can add,
 *     remove and reorder traversalsteps; this way of unauthorized access is not attempted below]
 *
 *  If you merely want to filter a traversal by authorization attributes without enforcing it, you
 *  are probably better off using TinkerPop's SubgraphStrategy.
 */
public final class AuthorizedTraversalSource extends GraphTraversalSource {

  Logger logger = LoggerFactory.getLogger("nl.vtslab.traversal.AuthorizedTraversalSource");
  public static final String USERAUTHORIZATION = "userAuthorization";
  private List<String> userAuthorization;
  private boolean authorized = false;
  private boolean vertexStepCalled = false;

  private static final String blockMessage = "Method not available for AuthorizedTraversalSource";
  static final String onceMessage = "Method withAuthorization() can only be called once";
  static final String orderMessage = "Method withAuthorization() should be called first";

  public AuthorizedTraversalSource withAuthorization(List<String> authorizationStrings) {
    if (authorized) {
      throw(new RuntimeException(onceMessage));
    }
    if (System.getSecurityManager() == null) {
      logger.warn("SecurityManager not set. Access to the entire graph still possible using reflection!");
    }
    userAuthorization = Collections.unmodifiableList(authorizationStrings);
    this.authorized = true;
    return this;
  }

  /*
   * Unauthorized Traversal objects could be created from the graph object
   */
  @Override
  public Graph getGraph() {
    if (!vertexStepCalled) {
      throw(new RuntimeException(blockMessage));
    }
    return super.getGraph();
  }

  /*
   * Vertex adds not needed for normal operation while posing a security risk in conjunction with Traversal.addE()
   */
  @Override
  public AuthorizedTraversal<Vertex, Vertex> addV() {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  public AuthorizedTraversal<Vertex, Vertex> addV(String label) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  public AuthorizedTraversal<Vertex, Vertex> V(Object... vertexIds) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    vertexStepCalled = true;
    AuthorizedTraversalSource clone = this.clone().
        withSideEffect(USERAUTHORIZATION, userAuthorization); //Collections.unmodifiableList(userAuthorization));
    clone.getBytecode().addStep(GraphTraversal.Symbols.V, vertexIds);
    DefaultAuthorizedTraversal traversal = new DefaultAuthorizedTraversal(clone);
    return (AuthorizedTraversal) traversal.asAdmin().
        addStep(new GraphStep(traversal, Vertex.class, true, vertexIds)).
        has("authz", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  public AuthorizedTraversal<Edge, Edge> E(Object... edgeIds) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    vertexStepCalled = true;
    AuthorizedTraversalSource clone = this.clone().
        withSideEffect(USERAUTHORIZATION, userAuthorization); // Collections.unmodifiableList(userAuthorization)));
    clone.getBytecode().addStep(GraphTraversal.Symbols.E, edgeIds);
    DefaultAuthorizedTraversal traversal = new DefaultAuthorizedTraversal(clone);
    return (AuthorizedTraversal) traversal.asAdmin().
        addStep(new GraphStep(traversal, Edge.class, true, edgeIds)).
        has("authz", __.where(P.within(USERAUTHORIZATION)));
  }

  /*
   * The constructors and methods below were generated initially by @GremlinDsl
   */
  public AuthorizedTraversalSource(Graph graph) {
    super(graph);
  }

  @Override
  public Optional<Class> getAnonymousTraversalClass() {
    return Optional.of(nl.vtslab.fosdem2019.traversal.__.class);  }

  @Override
  public AuthorizedTraversalSource clone() {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.clone();
  }

  /*
   * Strategies generally not needed for queries while posing a potential security risk (a strategy can add,
   * remove and reorder traversalsteps). NB: an alternative way to apply strategies remains in calling:
   *    TraversalStrategies.GlobalCache.registerStrategies()
   */
  public AuthorizedTraversalSource(Graph graph, TraversalStrategies strategies) {
//    super(graph, strategies);   Original line
    super(graph);
    // Throwing an error in the default constructor is not allowed
    logger.error("Providing strategies to AuthorizedTraversal is not allowed");
  }

  @Override
  public AuthorizedTraversalSource withStrategies(TraversalStrategy... traversalStrategies) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  public AuthorizedTraversalSource withoutStrategies(Class<? extends TraversalStrategy>... traversalStrategyClasses) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  public AuthorizedTraversalSource withBindings(Bindings bindings) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  public AuthorizedTraversalSource withComputer(Computer computer) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withComputer(computer);
  }

  @Override
  public AuthorizedTraversalSource withComputer(Class<? extends GraphComputer> graphComputerClass) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withComputer(graphComputerClass);
  }

  @Override
  public AuthorizedTraversalSource withComputer() {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withComputer();
  }

  @Override
  public <A> AuthorizedTraversalSource withSideEffect(String key, Supplier<A> initialValue, BinaryOperator<A> reducer) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSideEffect(key,initialValue,reducer);
  }

  @Override
  public <A> AuthorizedTraversalSource withSideEffect(String key, A initialValue, BinaryOperator<A> reducer) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSideEffect(key,initialValue,reducer);
  }

  @Override
  public <A> AuthorizedTraversalSource withSideEffect(String key, A initialValue) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSideEffect(key,initialValue);
  }

  @Override
  public <A> AuthorizedTraversalSource withSideEffect(String key, Supplier<A> initialValue) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSideEffect(key,initialValue);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(Supplier<A> initialValue,
      UnaryOperator<A> splitOperator, BinaryOperator<A> mergeOperator) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue,splitOperator,mergeOperator);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(A initialValue, UnaryOperator<A> splitOperator,
      BinaryOperator<A> mergeOperator) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue,splitOperator,mergeOperator);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(A initialValue) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(Supplier<A> initialValue) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(Supplier<A> initialValue, UnaryOperator<A> splitOperator) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue,splitOperator);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(A initialValue, UnaryOperator<A> splitOperator) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue,splitOperator);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(Supplier<A> initialValue, BinaryOperator<A> mergeOperator) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue,mergeOperator);
  }

  @Override
  public <A> AuthorizedTraversalSource withSack(A initialValue, BinaryOperator<A> mergeOperator) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withSack(initialValue,mergeOperator);
  }

  @Override
  public AuthorizedTraversalSource withBulk(boolean useBulk) {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withBulk(useBulk);
  }

  @Override
  public AuthorizedTraversalSource withPath() {
    if (!authorized) {
      throw(new RuntimeException(orderMessage));
    }
    return (AuthorizedTraversalSource) super.withPath();
  }

  @Override
  public AuthorizedTraversalSource withRemote(Configuration conf) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  public AuthorizedTraversalSource withRemote(String configFile) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  public AuthorizedTraversalSource withRemote(RemoteConnection connection) {
    throw(new RuntimeException(blockMessage));
  }
}
