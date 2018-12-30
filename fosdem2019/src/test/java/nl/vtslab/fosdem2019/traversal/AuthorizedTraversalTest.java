package nl.vtslab.fosdem2019.traversal;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static nl.vtslab.fosdem2019.traversal.AuthorizedTraversalSource.onceMessage;
import static nl.vtslab.fosdem2019.traversal.AuthorizedTraversalSource.orderMessage;
import static nl.vtslab.fosdem2019.traversal.DefaultAuthorizedTraversal.blockMessage;
import static org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality.set;
import static org.junit.Assert.*;


public class AuthorizedTraversalTest {

    private static String src1Authz = "biz;3";
    private static String src2Authz = "fb;2";
    private static String src3Authz = "fin;3";
    private static String src4Authz = "fin;2";
    private static String src5Authz = "fin;4";
    private static TinkerGraph graph;

    @BeforeClass
    public static void createGraph() {
        graph = TinkerGraph.open();
        GraphTraversalSource g = graph.traversal();
        g.addV("Person").property("name", "p0").property("authz", src1Authz)
                .property(set, "authz", src2Authz).next();
        g.addV("Person").property("name", "p1").property("authz", src3Authz).next();
        g.addV("Person").property("name", "p2").property("authz", src4Authz).next();
        g.addV("Event").property("name", "v1").property("authz", src1Authz).next();
        g.addV("Event").property("name", "v2").property("authz", src4Authz).next();
        g.V().has("name", "v1").as("a").
                V().has("name", "p0").addE("Visits").to("a").property("name", "e01").property("authz", src1Authz).
                V().has("name", "p1").addE("Visits").to("a").property("name", "e11").property("authz", src3Authz).
                next();
        g.V().has("name", "v2").as("a").
                V().has("name", "p1").addE("Visits").to("a").property("name", "e12").property("authz", src3Authz).
                V().has("name", "p2").addE("Visits").to("a").property("name", "e22").property("authz", src5Authz).
                next();
    }

    private AuthorizedTraversalSource g() {

        // Needed for testing because the withAuthorization method can only be called once
        return graph.traversal(AuthorizedTraversalSource.class);
    }

    @Test
    public void normalOperationTest() {

        int size;

        size = g().withAuthorization(Arrays.asList(src1Authz)).V().toList().size();
        assertEquals(2, size);

        size = g().withAuthorization(Arrays.asList(src3Authz)).V().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(src1Authz, src3Authz)).V().toList().size();
        assertEquals(3, size);

        size = g().withAuthorization(Arrays.asList(src1Authz)).E().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(src3Authz)).E().toList().size();
        assertEquals(2, size);

        size = g().withAuthorization(Arrays.asList(src1Authz)).V().has("name", "v1").inE().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(src3Authz)).V().has("name", "p1").outE().toList().size();
        assertEquals(2, size);

        size = g().withAuthorization(Arrays.asList(src1Authz)).V().has("name", "v1").in().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(src3Authz, src4Authz)).
                V().has("name", "p1").out().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(src1Authz)).V().where(__.inE()).toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(src3Authz, src4Authz)).
                V().where(__.bothE()). toList().size();
        assertEquals(2, size);

    }

    @Test
    public void traversalSourceAccessTest() {
        List<Vertex> result;

        try {
            g().V().toList();
            fail("Unauthorized query should fail");
        } catch (RuntimeException exception) {
            assertEquals(exception.getMessage(), orderMessage);
        }

        try {
            g().withAuthorization(Arrays.asList(src1Authz)).
                withAuthorization(Arrays.asList(src1Authz, src3Authz)).V().toList();
            fail("Query with second withAuthorization() call should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), onceMessage);
        }

        try {
            g().withSideEffect("userAuthorization", Collections.singletonList(src1Authz)).V().toList();
            fail("Query with userAuthorizations provided by withSideEffect() should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), orderMessage);
        }

        // Addition of "userAuthorization" sideEffect() should be ignored
        result = g().withAuthorization(Arrays.asList(src1Authz)).
            withSideEffect("userAuthorization", Arrays.asList(src3Authz)).V().toList();
        assertEquals(2, result.size());

        try {
            g().withSideEffect("userAuthorization", Arrays.asList(src3Authz)).
                withAuthorization(Arrays.asList(src1Authz)).V().toList();
            fail("Calling withSideEffect() before withAuthorization() should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), orderMessage);
        }

        try {
            // The cast is required to allow for the inject() step
            ((AuthorizedTraversal<Vertex,Object>)(Object)g().withAuthorization(Arrays.asList(src1Authz)).V()).
                as("x").inject(src3Authz).store("userAuthorization").select("x").toList();
            fail("Faking userAuthorizations using the store() step should fail");
        } catch (Exception exception) {
            assertTrue(exception instanceof UnsupportedOperationException);
        }

        try {
            // The cast is required to allow for the inject() step
            ((AuthorizedTraversal<Vertex,Object>)(Object)g().withAuthorization(Arrays.asList(src1Authz)).V()).
                as("x").inject(src3Authz).aggregate("userAuthorization").select("x").toList();
            fail("Faking userAuthorizations using the aggregate() step should fail");
        } catch (Exception exception) {
            assertTrue(exception instanceof UnsupportedOperationException);
        }
    }

    /*
     * For the following blocked methods unauthorized access would have been possible, but not provided as test:
     *  - addV, addE, property
     *  - program, sideEffect(Consumer<Traverser>)
     *  - addStart, addStarts, addStep, removeStep
    */
    @Test
    public void traversalAccessTest() {
        // Without blocking getGraph() all vertices would be returned
        try {
            g().withAuthorization(Collections.singletonList(src1Authz)).V().getGraph().get().traversal().V().toList();
            fail("Accessing DefaultAuthorizedTraversal.getGraph() should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), blockMessage);
        }
    }

    @Test
    public void reflectionAccessTest() {
        // Beware: Activating the java security manager to have this test pass can require a more elaborate java policy
        // (in other words, activating the default security manager like below can make other tests fail)
        // It also shows that applications relying on AuthorizedTraversal need the java SecurityManager to be set.
        Vertex[] vertices = new Vertex[]{};
        SecurityManager securityManager = new SecurityManager();
        System.setSecurityManager(securityManager);

        try {
            AuthorizedTraversalTest testObject;
            Class testClass = Class.forName("nl.vtslab.fosdem2019.traversal.AuthorizedTraversalTest");
            testClass.getDeclaredField("graph").setAccessible(true);
            testObject = (AuthorizedTraversalTest) testClass.getConstructor().newInstance();
            vertices = testObject.graph.traversal().V().toList().toArray(vertices);
            fail("Accessing the graph using reflection should fail");
        } catch (Exception exception) {
            assertTrue(exception instanceof AccessControlException);
        }

        // This is the innocent-looking gremlin query under test
        // (just comment out the security manager and fail statement above)
        // map(v -> vertices) would have the same effect if map(Function) were not blocked for other reasons
        List<Vertex> result = g().withAuthorization(Collections.singletonList(src1Authz)).V().
            inject(vertices).dedup().toList();
        assertEquals(2, result.size());
    }


    /*
     * For the following blocked method variants that provide Traversers, analogous ways for unauthorized access would
     * have been possible, but these are not provided as test:
     *  - barrier, branch, flatMap, filter
     *  - emit, sideEffect, until
    */
    @Test
    public void lambdaAccessTest() {
        // Without blocking map(Function<Traverser>), unauthorized vertices would be returned
        try {
            List<String> authorizations = Arrays.asList(src1Authz, src3Authz);
            List<Vertex> result = g().withAuthorization(Arrays.asList(src1Authz)).V().map(t -> {
                t.sideEffects("userAuthorization", authorizations);
                return t.get();
            }).toList();
            fail("Accessing AuthorizedTraversal.map(Function) should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), blockMessage);
        }
    }

    /*
     * Ways of unauthorized access using anonymous traversals are analogous to those on the main traversal
     * Only a few checks are made:
     *  - for traversals that should not expose unauthorized graph elements
     *  - whether steps that have blocked variants still work properly
     */
    @Test
    public void anonymousAccessTest() {

        // Anonymous __.V() should inherit userAuthorization from parent
        // This also verifies proper working of the map(Traversal) variant
        int size = g().withAuthorization(Arrays.asList(src1Authz)).V().
            map(__.V().fold()).unfold().dedup().toList().size();
        assertEquals(2, size);

        // Anonymous __.inE() should inherit userAuthorization from parent
        size = g().withAuthorization(Arrays.asList(src1Authz)).V().has("name", "v1").
            map(__.inE().fold()).unfold().dedup().toList().size();
        assertEquals(1, size);

        // Anonymous __.emit(Traversal) should still work
        size = g().withAuthorization(Arrays.asList(src1Authz)).V().has("name", "v1").
            map(__.repeat(__.inE()).times(1).emit(__.values("name"))).toList().size();
        assertEquals(1, size);
    }
}
