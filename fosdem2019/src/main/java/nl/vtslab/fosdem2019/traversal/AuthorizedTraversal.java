package nl.vtslab.fosdem2019.traversal;

import java.lang.Integer;
import java.lang.Long;
import java.lang.Number;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.tinkerpop.gremlin.process.computer.VertexProgram;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.Pop;
import org.apache.tinkerpop.gremlin.process.traversal.Scope;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.Traverser;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.Tree;
import org.apache.tinkerpop.gremlin.process.traversal.traverser.util.TraverserSet;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalMetrics;
import org.apache.tinkerpop.gremlin.structure.Column;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

import static nl.vtslab.fosdem2019.traversal.AuthorizedTraversalSource.USERAUTHORIZATION;

public interface AuthorizedTraversal<S, E> extends GraphTraversal.Admin<S, E> {

  String blockMessage = "Method not available for AuthorizedTraversal";

  @Override
  default AuthorizedTraversal<S, Vertex> V(Object... vertexIdsOrElements) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.V(vertexIdsOrElements).
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> out(String... edgeLabels) {
    // The naive implementation ".out(edgeLabels).has("generiekeAutorisatie", where(within(USERAUTHORIZATION))"
    // would simply use the edges without question.
    // In an authorized graph, the edges may have authorizations different from the vertices they refer to.
    // Hence, we need to make sure that we can reach the vertex via authorized edges, even if the vertex itself
    // would be authorized by itself (e.g., as a target for a direct initial search).
    return (AuthorizedTraversal) GraphTraversal.Admin.super.
            outE(edgeLabels).has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION))).
            otherV().has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> in(String... edgeLabels) {
    // The naive implementation ".in(edgeLabels).has("generiekeAutorisatie", where(within(USERAUTHORIZATION))"
    // would simply use the edges without question.
    // In an authorized graph, the edges may have authorizations different from the vertices they refer to.
    // Hence, we need to make sure that we can reach the vertex via authorized edges, even if the vertex itself
    // would be authorized by itself (e.g., as a target for a direct initial search).
    return (AuthorizedTraversal) GraphTraversal.Admin.super.
            inE(edgeLabels).has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION))).
            otherV().has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> both(String... edgeLabels) {
    // The naive implementation ".both(edgeLabels).has("generiekeAutorisatie", where(within(USERAUTHORIZATION))"
    // would simply use the edges without question.
    // In an authorized graph, the edges may have authorizations different from the vertices they refer to.
    // Hence, we need to make sure that we can reach the vertex via authorized edges, even if the vertex itself
    // would be authorized by itself (e.g., as a target for a direct initial search).
    return (AuthorizedTraversal) GraphTraversal.Admin.super.
            bothE(edgeLabels).has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION))).
            otherV().has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Edge> outE(String... edgeLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.outE(edgeLabels).
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Edge> inE(String... edgeLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.inE(edgeLabels).
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Edge> bothE(String... edgeLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.bothE(edgeLabels).
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> inV() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.inV().
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> outV() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.outV().
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> bothV() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.bothV().
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> otherV() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.otherV().
        has("generiekeAutorisatie", __.where(P.within(USERAUTHORIZATION)));
  }

  // The constructors and methods below were generated initially by @GremlinDSL
  @Override
  default <E2> AuthorizedTraversal<S, E2> map(Function<Traverser<E>, E2> function) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> map(Traversal<?, E2> mapTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.map(mapTraversal);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> flatMap(Function<Traverser<E>, Iterator<E2>> function) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> flatMap(Traversal<?, E2> flatMapTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.flatMap(flatMapTraversal);
  }

  @Override
  default AuthorizedTraversal<S, Object> id() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.id();
  }

  @Override
  default AuthorizedTraversal<S, String> label() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.label();
  }

  @Override
  default AuthorizedTraversal<S, E> identity() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.identity();
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> constant(E2 e) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.constant(e);
  }

  @Override
  default AuthorizedTraversal<S, Vertex> to(Direction direction, String... edgeLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.to(direction,edgeLabels);
  }

  @Override
  default AuthorizedTraversal<S, Edge> toE(Direction direction, String... edgeLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.toE(direction,edgeLabels);
  }

  @Override
  default AuthorizedTraversal<S, Vertex> toV(Direction direction) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.toV(direction);
  }

  @Override
  default AuthorizedTraversal<S, E> order() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.order();
  }

  @Override
  default AuthorizedTraversal<S, E> order(Scope scope) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.order(scope);
  }

  @Override
  default <E2> AuthorizedTraversal<S, ? extends Property<E2>> properties(String... propertyKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.properties(propertyKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> values(String... propertyKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.values(propertyKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, Map<String, E2>> propertyMap(String... propertyKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.propertyMap(propertyKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, Map<String, E2>> valueMap(String... propertyKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.valueMap(propertyKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, Map<String, E2>> valueMap(boolean includeTokens,
      String... propertyKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.valueMap(includeTokens,propertyKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> mapValues() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.mapValues();
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> mapKeys() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.mapKeys();
  }

  @Override
  default AuthorizedTraversal<S, String> key() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.key();
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> value() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.value();
  }

  @Override
  default AuthorizedTraversal<S, Path> path() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.path();
  }

  @Override
  default <E2> AuthorizedTraversal<S, Map<String, E2>> match(Traversal<?, ?>... matchTraversals) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.match(matchTraversals);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> sack() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sack();
  }

  @Override
  default AuthorizedTraversal<S, Integer> loops() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.loops();
  }

  @Override
  default <E2> AuthorizedTraversal<S, Map<String, E2>> project(String projectKey,
      String... otherProjectKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.project(projectKey,otherProjectKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, Map<String, E2>> select(Pop pop, String selectKey1,
      String selectKey2, String... otherSelectKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.select(pop,selectKey1,selectKey2,otherSelectKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, Map<String, E2>> select(String selectKey1, String selectKey2,
      String... otherSelectKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.select(selectKey1,selectKey2,otherSelectKeys);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> select(Pop pop, String selectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.select(pop,selectKey);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> select(String selectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.select(selectKey);
  }

  @Override
  default <E2> AuthorizedTraversal<S, Collection<E2>> select(Column column) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.select(column);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> unfold() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.unfold();
  }

  @Override
  default AuthorizedTraversal<S, List<E>> fold() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.fold();
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> fold(E2 seed, BiFunction<E2, E, E2> foldFunction) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.fold(seed,foldFunction);
  }

  @Override
  default AuthorizedTraversal<S, Long> count() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.count();
  }

  @Override
  default AuthorizedTraversal<S, Long> count(Scope scope) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.count(scope);
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> sum() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sum();
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> sum(Scope scope) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sum(scope);
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> max() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.max();
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> max(Scope scope) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.max(scope);
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> min() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.min();
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> min(Scope scope) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.min(scope);
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> mean() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.mean();
  }

  @Override
  default <E2 extends Number> AuthorizedTraversal<S, E2> mean(Scope scope) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.mean(scope);
  }

  @Override
  default <K, V> AuthorizedTraversal<S, Map<K, V>> group() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.group();
  }

  @Override
  default <K, V> AuthorizedTraversal<S, Map<K, V>> groupV3d0() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.groupV3d0();
  }

  @Override
  default <K> AuthorizedTraversal<S, Map<K, Long>> groupCount() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.groupCount();
  }

  @Override
  default AuthorizedTraversal<S, Tree> tree() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.tree();
  }

  /*
   * Vertex adds not needed for normal operation while posing a security risk in conjunction with Traversal.addE()
   */
  @Override
  default AuthorizedTraversal<S, Vertex> addV(String vertexLabel) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> addV() {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, Vertex> addV(Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  /*
   * Security risk by creating alternative paths to vertices "behind" unauthorized edges
   */
  @Override
  default AuthorizedTraversal<S, Edge> addE(String edgeLabel) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> to(String toStepLabel) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.to(toStepLabel);
  }

  @Override
  default AuthorizedTraversal<S, E> from(String fromStepLabel) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.from(fromStepLabel);
  }

  @Override
  default AuthorizedTraversal<S, E> to(Traversal<E, Vertex> toVertex) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.to(toVertex);
  }

  @Override
  default AuthorizedTraversal<S, E> from(Traversal<E, Vertex> fromVertex) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.from(fromVertex);
  }

  @Override
  default AuthorizedTraversal<S, Edge> addE(Direction direction, String firstVertexKeyOrEdgeLabel,
      String edgeLabelOrSecondVertexKey, Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, Edge> addOutE(String firstVertexKeyOrEdgeLabel,
      String edgeLabelOrSecondVertexKey, Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, Edge> addInE(String firstVertexKeyOrEdgeLabel,
      String edgeLabelOrSecondVertexKey, Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> filter(Predicate<Traverser<E>> predicate) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> filter(Traversal<?, ?> filterTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.filter(filterTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> or(Traversal<?, ?>... orTraversals) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.or(orTraversals);
  }

  @Override
  default AuthorizedTraversal<S, E> and(Traversal<?, ?>... andTraversals) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.and(andTraversals);
  }

  @Override
  default AuthorizedTraversal<S, E> inject(E... injections) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.inject(injections);
  }

  @Override
  default AuthorizedTraversal<S, E> dedup(Scope scope, String... dedupLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.dedup(scope,dedupLabels);
  }

  @Override
  default AuthorizedTraversal<S, E> dedup(String... dedupLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.dedup(dedupLabels);
  }

  @Override
  default AuthorizedTraversal<S, E> where(String startKey, P<String> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.where(startKey,predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> where(P<String> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.where(predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> where(Traversal<?, ?> whereTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.where(whereTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> has(String propertyKey, P<?> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(propertyKey,predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> has(T accessor, P<?> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(accessor,predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> has(String propertyKey, Object value) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(propertyKey,value);
  }

  @Override
  default AuthorizedTraversal<S, E> has(T accessor, Object value) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(accessor,value);
  }

  @Override
  default AuthorizedTraversal<S, E> has(String label, String propertyKey, P<?> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(label,propertyKey,predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> has(String label, String propertyKey, Object value) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(label,propertyKey,value);
  }

  @Override
  default AuthorizedTraversal<S, E> has(T accessor, Traversal<?, ?> propertyTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(accessor,propertyTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> has(String propertyKey, Traversal<?, ?> propertyTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(propertyKey,propertyTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> has(String propertyKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.has(propertyKey);
  }

  @Override
  default AuthorizedTraversal<S, E> hasNot(String propertyKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasNot(propertyKey);
  }

  @Override
  default AuthorizedTraversal<S, E> hasLabel(String label, String... otherLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasLabel(label,otherLabels);
  }

  @Override
  default AuthorizedTraversal<S, E> hasLabel(P<String> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasLabel(predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> hasId(Object id, Object... otherIds) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasId(id,otherIds);
  }

  @Override
  default AuthorizedTraversal<S, E> hasId(P<Object> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasId(predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> hasKey(String label, String... otherLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasKey(label,otherLabels);
  }

  @Override
  default AuthorizedTraversal<S, E> hasKey(P<String> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasKey(predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> hasValue(Object value, Object... otherValues) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasValue(value,otherValues);
  }

  @Override
  default AuthorizedTraversal<S, E> hasValue(P<Object> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.hasValue(predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> is(P<E> predicate) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.is(predicate);
  }

  @Override
  default AuthorizedTraversal<S, E> is(Object value) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.is(value);
  }

  @Override
  default AuthorizedTraversal<S, E> not(Traversal<?, ?> notTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.not(notTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> coin(double probability) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.coin(probability);
  }

  @Override
  default AuthorizedTraversal<S, E> range(long low, long arg1) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.range(low,arg1);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> range(Scope scope, long low, long arg2) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.range(scope,low,arg2);
  }

  @Override
  default AuthorizedTraversal<S, E> limit(long limit) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.limit(limit);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> limit(Scope scope, long limit) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.limit(scope,limit);
  }

  @Override
  default AuthorizedTraversal<S, E> tail() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.tail();
  }

  @Override
  default AuthorizedTraversal<S, E> tail(long limit) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.tail(limit);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> tail(Scope scope) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.tail(scope);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> tail(Scope scope, long limit) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.tail(scope,limit);
  }

  @Override
  default AuthorizedTraversal<S, E> timeLimit(long timeLimit) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.timeLimit(timeLimit);
  }

  @Override
  default AuthorizedTraversal<S, E> simplePath() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.simplePath();
  }

  @Override
  default AuthorizedTraversal<S, E> cyclicPath() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.cyclicPath();
  }

  @Override
  default AuthorizedTraversal<S, E> sample(int amountToSample) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sample(amountToSample);
  }

  @Override
  default AuthorizedTraversal<S, E> sample(Scope scope, int amountToSample) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sample(scope,amountToSample);
  }

  @Override
  default AuthorizedTraversal<S, E> drop() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.drop();
  }

  @Override
  default AuthorizedTraversal<S, E> sideEffect(Consumer<Traverser<E>> consumer) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> sideEffect(Traversal<?, ?> sideEffectTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sideEffect(sideEffectTraversal);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> cap(String sideEffectKey, String... sideEffectKeys) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.cap(sideEffectKey,sideEffectKeys);
  }

  @Override
  default AuthorizedTraversal<S, Edge> subgraph(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.subgraph(sideEffectKey);
  }

  @Override
  default AuthorizedTraversal<S, E> aggregate(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.aggregate(sideEffectKey);
  }

  @Override
  default AuthorizedTraversal<S, E> group(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.group(sideEffectKey);
  }

  @Override
  default AuthorizedTraversal<S, E> groupV3d0(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.groupV3d0(sideEffectKey);
  }

  @Override
  default AuthorizedTraversal<S, E> groupCount(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.groupCount(sideEffectKey);
  }

  @Override
  default AuthorizedTraversal<S, E> tree(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.tree(sideEffectKey);
  }

  @Override
  default <V, U> AuthorizedTraversal<S, E> sack(BiFunction<V, U, V> sackOperator) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sack(sackOperator);
  }

  @Override
  default <V, U> AuthorizedTraversal<S, E> sack(BiFunction<V, U, V> sackOperator,
      String elementPropertyKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.sack(sackOperator,elementPropertyKey);
  }

  @Override
  default AuthorizedTraversal<S, E> store(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.store(sideEffectKey);
  }

  @Override
  default AuthorizedTraversal<S, E> profile(String sideEffectKey) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.profile(sideEffectKey);
  }

  @Override
  default AuthorizedTraversal<S, TraversalMetrics> profile() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.profile();
  }

  /*
   * Security risk by adding userAuthorization for other users
   */
  @Override
  default AuthorizedTraversal<S, E> property(VertexProperty.Cardinality cardinality, Object key,
      Object value, Object... keyValues) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> property(Object key, Object value, Object... keyValues) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default <M, E2> AuthorizedTraversal<S, E2> branch(Traversal<?, M> branchTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.branch(branchTraversal);
  }

  @Override
  default <M, E2> AuthorizedTraversal<S, E2> branch(Function<Traverser<E>, M> function) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default <M, E2> AuthorizedTraversal<S, E2> choose(Traversal<?, M> choiceTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.choose(choiceTraversal);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> choose(Traversal<?, ?> traversalPredicate,
      Traversal<?, E2> trueChoice, Traversal<?, E2> falseChoice) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.choose(traversalPredicate,trueChoice,falseChoice);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> choose(Traversal<?, ?> traversalPredicate,
      Traversal<?, E2> trueChoice) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.choose(traversalPredicate,trueChoice);
  }

  @Override
  default <M, E2> AuthorizedTraversal<S, E2> choose(Function<E, M> choiceFunction) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.choose(choiceFunction);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> choose(Predicate<E> choosePredicate,
      Traversal<?, E2> trueChoice, Traversal<?, E2> falseChoice) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.choose(choosePredicate,trueChoice,falseChoice);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> choose(Predicate<E> choosePredicate,
      Traversal<?, E2> trueChoice) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.choose(choosePredicate,trueChoice);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> optional(Traversal<?, E2> optionalTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.optional(optionalTraversal);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> union(Traversal<?, E2>... unionTraversals) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.union(unionTraversals);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> coalesce(Traversal<?, E2>... coalesceTraversals) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.coalesce(coalesceTraversals);
  }

  @Override
  default AuthorizedTraversal<S, E> repeat(Traversal<?, E> repeatTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.repeat(repeatTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> emit(Traversal<?, ?> emitTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.emit(emitTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> emit(Predicate<Traverser<E>> emitPredicate) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> emit() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.emit();
  }

  @Override
  default AuthorizedTraversal<S, E> until(Traversal<?, ?> untilTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.until(untilTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> until(Predicate<Traverser<E>> untilPredicate) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> times(int maxLoops) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.times(maxLoops);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E2> local(Traversal<?, E2> localTraversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.local(localTraversal);
  }

  @Override
  default AuthorizedTraversal<S, E> pageRank() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.pageRank();
  }

  @Override
  default AuthorizedTraversal<S, E> pageRank(double alpha) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.pageRank(alpha);
  }

  @Override
  default AuthorizedTraversal<S, E> peerPressure() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.peerPressure();
  }

  /*
   * VertexPrograms have access to all vertices and edges
   */
  @Override
  default AuthorizedTraversal<S, E> program(VertexProgram<?> vertexProgram) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> as(String stepLabel, String... stepLabels) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.as(stepLabel,stepLabels);
  }

  @Override
  default AuthorizedTraversal<S, E> barrier() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.barrier();
  }

  @Override
  default AuthorizedTraversal<S, E> barrier(int maxBarrierSize) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.barrier(maxBarrierSize);
  }

  @Override
  default AuthorizedTraversal<S, E> barrier(Consumer<TraverserSet<Object>> barrierConsumer) {
    throw(new RuntimeException(blockMessage));
  }

  @Override
  default AuthorizedTraversal<S, E> by() {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by();
  }

  @Override
  default AuthorizedTraversal<S, E> by(Traversal<?, ?> traversal) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(traversal);
  }

  @Override
  default AuthorizedTraversal<S, E> by(T token) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(token);
  }

  @Override
  default AuthorizedTraversal<S, E> by(String key) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(key);
  }

  @Override
  default <V> AuthorizedTraversal<S, E> by(Function<V, Object> function) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(function);
  }

  @Override
  default <V> AuthorizedTraversal<S, E> by(Traversal<?, ?> traversal, Comparator<V> comparator) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(traversal,comparator);
  }

  @Override
  default AuthorizedTraversal<S, E> by(Comparator<E> comparator) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(comparator);
  }

  @Override
  default AuthorizedTraversal<S, E> by(Order order) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(order);
  }

  @Override
  default <V> AuthorizedTraversal<S, E> by(String key, Comparator<V> comparator) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(key,comparator);
  }

  @Override
  default <U> AuthorizedTraversal<S, E> by(Function<U, Object> function, Comparator comparator) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.by(function,comparator);
  }

  @Override
  default <M, E2> AuthorizedTraversal<S, E> option(M pickToken, Traversal<E, E2> traversalOption) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.option(pickToken,traversalOption);
  }

  @Override
  default <E2> AuthorizedTraversal<S, E> option(Traversal<E, E2> traversalOption) {
    return (AuthorizedTraversal) GraphTraversal.Admin.super.option(traversalOption);
  }

  @Override
  default AuthorizedTraversal<S, E> iterate() {
    GraphTraversal.Admin.super.iterate();
    return this;
  }
}
