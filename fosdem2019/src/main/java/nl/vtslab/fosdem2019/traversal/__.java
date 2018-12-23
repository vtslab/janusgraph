package nl.vtslab.fosdem2019.traversal;

import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.Pop;
import org.apache.tinkerpop.gremlin.process.traversal.Scope;
import org.apache.tinkerpop.gremlin.process.traversal.Traversal;
import org.apache.tinkerpop.gremlin.process.traversal.Traverser;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.Tree;
import org.apache.tinkerpop.gremlin.process.traversal.traverser.util.TraverserSet;
import org.apache.tinkerpop.gremlin.structure.Column;
import org.apache.tinkerpop.gremlin.structure.Direction;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Property;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;

public final class __ {

  static final String blockMessage = "Method not available for nl.vtslab.fosdem2019.traversal.__";

  // The constructors and methods below were generated initially by @GremlinDSL
  private __() {
  }

  public static <A> AuthorizedTraversal<A, A> start() {
    return new DefaultAuthorizedTraversal<>();
  }

  public static <A> AuthorizedTraversal<A, A> __(A... starts) {
    return inject(starts);
  }

  public static <A, B> AuthorizedTraversal<A, B> map(Function<Traverser<A>, B> function) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A, B> AuthorizedTraversal<A, B> map(Traversal<?, B> mapTraversal) {
    return __.<A>start().map(mapTraversal);
  }

  public static <A, B> AuthorizedTraversal<A, B> flatMap(Function<Traverser<A>, Iterator<B>> function) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A, B> AuthorizedTraversal<A, B> flatMap(Traversal<?, B> flatMapTraversal) {
    return __.<A>start().flatMap(flatMapTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> identity() {
    return __.<A>start().identity();
  }

  public static <A> AuthorizedTraversal<A, A> constant(A a) {
    return __.<A>start().constant(a);
  }

  public static <A> AuthorizedTraversal<A, String> label() {
    return __.<A>start().label();
  }

  public static <A> AuthorizedTraversal<A, Object> id() {
    return __.<A>start().id();
  }

  public static <A> AuthorizedTraversal<A, Vertex> V(Object... vertexIdsOrElements) {
    return __.<A>start().V(vertexIdsOrElements);
  }

  public static <A> AuthorizedTraversal<A, Vertex> to(Direction direction, String... edgeLabels) {
    return __.<A>start().to(direction,edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Vertex> out(String... edgeLabels) {
    return __.<A>start().out(edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Vertex> in(String... edgeLabels) {
    return __.<A>start().in(edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Vertex> both(String... edgeLabels) {
    return __.<A>start().both(edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Edge> toE(Direction direction, String... edgeLabels) {
    return __.<A>start().toE(direction,edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Edge> outE(String... edgeLabels) {
    return __.<A>start().outE(edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Edge> inE(String... edgeLabels) {
    return __.<A>start().inE(edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Edge> bothE(String... edgeLabels) {
    return __.<A>start().bothE(edgeLabels);
  }

  public static <A> AuthorizedTraversal<A, Vertex> toV(Direction direction) {
    return __.<A>start().toV(direction);
  }

  public static <A> AuthorizedTraversal<A, Vertex> inV() {
    return __.<A>start().inV();
  }

  public static <A> AuthorizedTraversal<A, Vertex> outV() {
    return __.<A>start().outV();
  }

  public static <A> AuthorizedTraversal<A, Vertex> bothV() {
    return __.<A>start().bothV();
  }

  public static <A> AuthorizedTraversal<A, Vertex> otherV() {
    return __.<A>start().otherV();
  }

  public static <A> AuthorizedTraversal<A, A> order() {
    return __.<A>start().order();
  }

  public static <A> AuthorizedTraversal<A, A> order(Scope scope) {
    return __.<A>start().order(scope);
  }

  public static <A, B> AuthorizedTraversal<A, ? extends Property<B>> properties(String... propertyKeys) {
    return __.<A>start().properties(propertyKeys);
  }

  public static <A, B> AuthorizedTraversal<A, B> values(String... propertyKeys) {
    return __.<A>start().values(propertyKeys);
  }

  public static <A, B> AuthorizedTraversal<A, Map<String, B>> propertyMap(String... propertyKeys) {
    return __.<A>start().propertyMap(propertyKeys);
  }

  public static <A, B> AuthorizedTraversal<A, Map<String, B>> valueMap(String... propertyKeys) {
    return __.<A>start().valueMap(propertyKeys);
  }

  public static <A, B> AuthorizedTraversal<A, Map<String, B>> valueMap(boolean includeTokens,
      String... propertyKeys) {
    return __.<A>start().valueMap(includeTokens,propertyKeys);
  }

  public static <A, B> AuthorizedTraversal<A, Map<String, B>> project(String projectKey,
      String... projectKeys) {
    return __.<A>start().project(projectKey,projectKeys);
  }

  public static <A, B> AuthorizedTraversal<A, Collection<B>> select(Column column) {
    return __.<A>start().select(column);
  }

  public static <A, B> AuthorizedTraversal<A, B> mapValues() {
    return __.<A>start().mapValues();
  }

  public static <A, B> AuthorizedTraversal<A, B> mapKeys() {
    return __.<A>start().mapKeys();
  }

  public static <A> AuthorizedTraversal<A, String> key() {
    return __.<A>start().key();
  }

  public static <A, B> AuthorizedTraversal<A, B> value() {
    return __.<A>start().value();
  }

  public static <A> AuthorizedTraversal<A, Path> path() {
    return __.<A>start().path();
  }

  public static <A, B> AuthorizedTraversal<A, Map<String, B>> match(Traversal<?, ?>... matchTraversals) {
    return __.<A>start().match(matchTraversals);
  }

  public static <A, B> AuthorizedTraversal<A, B> sack() {
    return __.<A>start().sack();
  }

  public static <A> AuthorizedTraversal<A, Integer> loops() {
    return __.<A>start().loops();
  }

  public static <A, B> AuthorizedTraversal<A, B> select(Pop pop, String selectKey) {
    return __.<A>start().select(pop,selectKey);
  }

  public static <A, B> AuthorizedTraversal<A, B> select(String selectKey) {
    return __.<A>start().select(selectKey);
  }

  public static <A, B> AuthorizedTraversal<A, Map<String, B>> select(Pop pop, String selectKey1,
      String selectKey2, String... otherSelectKeys) {
    return __.<A>start().select(pop,selectKey1,selectKey2,otherSelectKeys);
  }

  public static <A, B> AuthorizedTraversal<A, Map<String, B>> select(String selectKey1,
      String selectKey2, String... otherSelectKeys) {
    return __.<A>start().select(selectKey1,selectKey2,otherSelectKeys);
  }

  public static <A> AuthorizedTraversal<A, A> unfold() {
    return __.<A>start().unfold();
  }

  public static <A> AuthorizedTraversal<A, List<A>> fold() {
    return __.<A>start().fold();
  }

  public static <A, B> AuthorizedTraversal<A, B> fold(B seed, BiFunction<B, A, B> foldFunction) {
    return __.<A>start().fold(seed,foldFunction);
  }

  public static <A> AuthorizedTraversal<A, Long> count() {
    return __.<A>start().count();
  }

  public static <A> AuthorizedTraversal<A, Long> count(Scope scope) {
    return __.<A>start().count(scope);
  }

  public static <A> AuthorizedTraversal<A, Double> sum() {
    return __.<A>start().sum();
  }

  public static <A> AuthorizedTraversal<A, Double> sum(Scope scope) {
    return __.<A>start().sum(scope);
  }

  public static <A, B extends Number> AuthorizedTraversal<A, B> min() {
    return __.<A>start().min();
  }

  public static <A, B extends Number> AuthorizedTraversal<A, B> min(Scope scope) {
    return __.<A>start().min(scope);
  }

  public static <A, B extends Number> AuthorizedTraversal<A, B> max() {
    return __.<A>start().max();
  }

  public static <A, B extends Number> AuthorizedTraversal<A, B> max(Scope scope) {
    return __.<A>start().max(scope);
  }

  public static <A> AuthorizedTraversal<A, Double> mean() {
    return __.<A>start().mean();
  }

  public static <A> AuthorizedTraversal<A, Double> mean(Scope scope) {
    return __.<A>start().mean(scope);
  }

  public static <A, K, V> AuthorizedTraversal<A, Map<K, V>> group() {
    return __.<A>start().group();
  }

  public static <A, K, V> AuthorizedTraversal<A, Map<K, V>> groupV3d0() {
    return __.<A>start().groupV3d0();
  }

  public static <A, K> AuthorizedTraversal<A, Map<K, Long>> groupCount() {
    return __.<A>start().groupCount();
  }

  public static <A> AuthorizedTraversal<A, Tree> tree() {
    return __.<A>start().tree();
  }

  /*
   * Vertex adds not needed for normal operation while posing a security risk in conjunction with Traversal.addE()
   */
  public static <A> AuthorizedTraversal<A, Vertex> addV(String vertexLabel) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, Vertex> addV() {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, Vertex> addV(Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  /*
   * Security risk by creating alternative paths to vertices "behind" unauthorized edges
   */
  public static <A> AuthorizedTraversal<A, Edge> addE(Direction direction,
      String firstVertexKeyOrEdgeLabel, String edgeLabelOrSecondVertexKey,
      Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, Edge> addOutE(String firstVertexKeyOrEdgeLabel,
      String edgeLabelOrSecondVertexKey, Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, Edge> addInE(String firstVertexKeyOrEdgeLabel,
      String edgeLabelOrSecondVertexKey, Object... propertyKeyValues) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, Edge> addE(String edgeLabel) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, A> filter(Predicate<Traverser<A>> predicate) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, A> filter(Traversal<?, ?> filterTraversal) {
    return __.<A>start().filter(filterTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> and(Traversal<?, ?>... andTraversals) {
    return __.<A>start().and(andTraversals);
  }

  public static <A> AuthorizedTraversal<A, A> or(Traversal<?, ?>... orTraversals) {
    return __.<A>start().or(orTraversals);
  }

  public static <A> AuthorizedTraversal<A, A> inject(A... injections) {
    return __.<A>start().inject(injections);
  }

  public static <A> AuthorizedTraversal<A, A> dedup(String... dedupLabels) {
    return __.<A>start().dedup(dedupLabels);
  }

  public static <A> AuthorizedTraversal<A, A> dedup(Scope scope, String... dedupLabels) {
    return __.<A>start().dedup(scope,dedupLabels);
  }

  public static <A> AuthorizedTraversal<A, A> has(String propertyKey, P<?> predicate) {
    return __.<A>start().has(propertyKey,predicate);
  }

  public static <A> AuthorizedTraversal<A, A> has(T accessor, P<?> predicate) {
    return __.<A>start().has(accessor,predicate);
  }

  public static <A> AuthorizedTraversal<A, A> has(String propertyKey, Object value) {
    return __.<A>start().has(propertyKey,value);
  }

  public static <A> AuthorizedTraversal<A, A> has(T accessor, Object value) {
    return __.<A>start().has(accessor,value);
  }

  public static <A> AuthorizedTraversal<A, A> has(String label, String propertyKey, Object value) {
    return __.<A>start().has(label,propertyKey,value);
  }

  public static <A> AuthorizedTraversal<A, A> has(String label, String propertyKey,
      P<?> predicate) {
    return __.<A>start().has(label,propertyKey,predicate);
  }

  public static <A> AuthorizedTraversal<A, A> has(T accessor, Traversal<?, ?> propertyTraversal) {
    return __.<A>start().has(accessor,propertyTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> has(String propertyKey,
      Traversal<?, ?> propertyTraversal) {
    return __.<A>start().has(propertyKey,propertyTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> has(String propertyKey) {
    return __.<A>start().has(propertyKey);
  }

  public static <A> AuthorizedTraversal<A, A> hasNot(String propertyKey) {
    return __.<A>start().hasNot(propertyKey);
  }

  public static <A> AuthorizedTraversal<A, A> hasLabel(String label, String... otherLabels) {
    return __.<A>start().hasLabel(label,otherLabels);
  }

  public static <A> AuthorizedTraversal<A, A> hasLabel(P<String> predicate) {
    return __.<A>start().hasLabel(predicate);
  }

  public static <A> AuthorizedTraversal<A, A> hasId(Object id, Object... otherIds) {
    return __.<A>start().hasId(id,otherIds);
  }

  public static <A> AuthorizedTraversal<A, A> hasId(P<Object> predicate) {
    return __.<A>start().hasId(predicate);
  }

  public static <A> AuthorizedTraversal<A, A> hasKey(String label, String... otherLabels) {
    return __.<A>start().hasKey(label,otherLabels);
  }

  public static <A> AuthorizedTraversal<A, A> hasKey(P<String> predicate) {
    return __.<A>start().hasKey(predicate);
  }

  public static <A> AuthorizedTraversal<A, A> hasValue(Object value, Object... values) {
    return __.<A>start().hasValue(value,values);
  }

  public static <A> AuthorizedTraversal<A, A> hasValue(P<Object> predicate) {
    return __.<A>start().hasValue(predicate);
  }

  public static <A> AuthorizedTraversal<A, A> where(String startKey, P<String> predicate) {
    return __.<A>start().where(startKey,predicate);
  }

  public static <A> AuthorizedTraversal<A, A> where(P<String> predicate) {
    return __.<A>start().where(predicate);
  }

  public static <A> AuthorizedTraversal<A, A> where(Traversal<?, ?> whereTraversal) {
    return __.<A>start().where(whereTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> is(P<A> predicate) {
    return __.<A>start().is(predicate);
  }

  public static <A> AuthorizedTraversal<A, A> is(Object value) {
    return __.<A>start().is(value);
  }

  public static <A> AuthorizedTraversal<A, A> not(Traversal<?, ?> notTraversal) {
    return __.<A>start().not(notTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> coin(double probability) {
    return __.<A>start().coin(probability);
  }

  public static <A> AuthorizedTraversal<A, A> range(long low, long arg1) {
    return __.<A>start().range(low,arg1);
  }

  public static <A> AuthorizedTraversal<A, A> range(Scope scope, long low, long arg2) {
    return __.<A>start().range(scope,low,arg2);
  }

  public static <A> AuthorizedTraversal<A, A> limit(long limit) {
    return __.<A>start().limit(limit);
  }

  public static <A> AuthorizedTraversal<A, A> limit(Scope scope, long limit) {
    return __.<A>start().limit(scope,limit);
  }

  public static <A> AuthorizedTraversal<A, A> tail() {
    return __.<A>start().tail();
  }

  public static <A> AuthorizedTraversal<A, A> tail(long limit) {
    return __.<A>start().tail(limit);
  }

  public static <A> AuthorizedTraversal<A, A> tail(Scope scope) {
    return __.<A>start().tail(scope);
  }

  public static <A> AuthorizedTraversal<A, A> tail(Scope scope, long limit) {
    return __.<A>start().tail(scope,limit);
  }

  public static <A> AuthorizedTraversal<A, A> simplePath() {
    return __.<A>start().simplePath();
  }

  public static <A> AuthorizedTraversal<A, A> cyclicPath() {
    return __.<A>start().cyclicPath();
  }

  public static <A> AuthorizedTraversal<A, A> sample(int amountToSample) {
    return __.<A>start().sample(amountToSample);
  }

  public static <A> AuthorizedTraversal<A, A> sample(Scope scope, int amountToSample) {
    return __.<A>start().sample(scope,amountToSample);
  }

  public static <A> AuthorizedTraversal<A, A> drop() {
    return __.<A>start().drop();
  }

  public static <A> AuthorizedTraversal<A, A> sideEffect(Consumer<Traverser<A>> consumer) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, A> sideEffect(Traversal<?, ?> sideEffectTraversal) {
    return __.<A>start().sideEffect(sideEffectTraversal);
  }

  public static <A, B> AuthorizedTraversal<A, B> cap(String sideEffectKey,
      String... sideEffectKeys) {
    return __.<A>start().cap(sideEffectKey,sideEffectKeys);
  }

  public static <A> AuthorizedTraversal<A, Edge> subgraph(String sideEffectKey) {
    return __.<A>start().subgraph(sideEffectKey);
  }

  public static <A> AuthorizedTraversal<A, A> aggregate(String sideEffectKey) {
    return __.<A>start().aggregate(sideEffectKey);
  }

  public static <A> AuthorizedTraversal<A, A> group(String sideEffectKey) {
    return __.<A>start().group(sideEffectKey);
  }

  public static <A> AuthorizedTraversal<A, A> groupV3d0(String sideEffectKey) {
    return __.<A>start().groupV3d0(sideEffectKey);
  }

  public static <A> AuthorizedTraversal<A, A> groupCount(String sideEffectKey) {
    return __.<A>start().groupCount(sideEffectKey);
  }

  public static <A> AuthorizedTraversal<A, A> timeLimit(long timeLimit) {
    return __.<A>start().timeLimit(timeLimit);
  }

  public static <A> AuthorizedTraversal<A, A> tree(String sideEffectKey) {
    return __.<A>start().tree(sideEffectKey);
  }

  public static <A, V, U> AuthorizedTraversal<A, A> sack(BiFunction<V, U, V> sackOperator) {
    return __.<A>start().sack(sackOperator);
  }

  public static <A, V, U> AuthorizedTraversal<A, A> sack(BiFunction<V, U, V> sackOperator,
      String elementPropertyKey) {
    return __.<A>start().sack(sackOperator,elementPropertyKey);
  }

  public static <A> AuthorizedTraversal<A, A> store(String sideEffectKey) {
    return __.<A>start().store(sideEffectKey);
  }

  /*
   * Security risk by adding userAuthorization for other users
   */
  public static <A> AuthorizedTraversal<A, A> property(Object key, Object value,
      Object... keyValues) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, A> property(VertexProperty.Cardinality cardinality,
      Object key, Object value, Object... keyValues) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A, M, B> AuthorizedTraversal<A, B> branch(Function<Traverser<A>, M> function) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A, M, B> AuthorizedTraversal<A, B> branch(Traversal<?, M> traversalFunction) {
    return __.<A>start().branch(traversalFunction);
  }

  public static <A, B> AuthorizedTraversal<A, B> choose(Predicate<A> choosePredicate,
      Traversal<?, B> trueChoice, Traversal<?, B> falseChoice) {
    return __.<A>start().choose(choosePredicate,trueChoice,falseChoice);
  }

  public static <A, B> AuthorizedTraversal<A, B> choose(Predicate<A> choosePredicate,
      Traversal<?, B> trueChoice) {
    return __.<A>start().choose(choosePredicate,trueChoice);
  }

  public static <A, M, B> AuthorizedTraversal<A, B> choose(Function<A, M> choiceFunction) {
    return __.<A>start().choose(choiceFunction);
  }

  public static <A, M, B> AuthorizedTraversal<A, B> choose(Traversal<?, M> traversalFunction) {
    return __.<A>start().choose(traversalFunction);
  }

  public static <A, M, B> AuthorizedTraversal<A, B> choose(Traversal<?, M> traversalPredicate,
      Traversal<?, B> trueChoice, Traversal<?, B> falseChoice) {
    return __.<A>start().choose(traversalPredicate,trueChoice,falseChoice);
  }

  public static <A, M, B> AuthorizedTraversal<A, B> choose(Traversal<?, M> traversalPredicate,
      Traversal<?, B> trueChoice) {
    return __.<A>start().choose(traversalPredicate,trueChoice);
  }

  public static <A> AuthorizedTraversal<A, A> optional(Traversal<?, A> optionalTraversal) {
    return __.<A>start().optional(optionalTraversal);
  }

  public static <A, B> AuthorizedTraversal<A, B> union(Traversal<?, B>... traversals) {
    return __.<A>start().union(traversals);
  }

  public static <A, B> AuthorizedTraversal<A, B> coalesce(Traversal<?, B>... traversals) {
    return __.<A>start().coalesce(traversals);
  }

  public static <A> AuthorizedTraversal<A, A> repeat(Traversal<?, A> traversal) {
    return __.<A>start().repeat(traversal);
  }

  public static <A> AuthorizedTraversal<A, A> emit(Traversal<?, ?> emitTraversal) {
    return __.<A>start().emit(emitTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> emit(Predicate<Traverser<A>> emitPredicate) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, A> until(Traversal<?, ?> untilTraversal) {
    return __.<A>start().until(untilTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> until(Predicate<Traverser<A>> untilPredicate) {
    throw(new RuntimeException(blockMessage));
  }

  public static <A> AuthorizedTraversal<A, A> times(int maxLoops) {
    return __.<A>start().times(maxLoops);
  }

  public static <A> AuthorizedTraversal<A, A> emit() {
    return __.<A>start().emit();
  }

  public static <A, B> AuthorizedTraversal<A, B> local(Traversal<?, B> localTraversal) {
    return __.<A>start().local(localTraversal);
  }

  public static <A> AuthorizedTraversal<A, A> as(String label, String... labels) {
    return __.<A>start().as(label,labels);
  }

  public static <A> AuthorizedTraversal<A, A> barrier() {
    return __.<A>start().barrier();
  }

  public static <A> AuthorizedTraversal<A, A> barrier(int maxBarrierSize) {
    return __.<A>start().barrier(maxBarrierSize);
  }

  public static <A> AuthorizedTraversal<A, A> barrier(Consumer<TraverserSet<Object>> barrierConsumer) {
    throw(new RuntimeException(blockMessage));
  }
}
