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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class AuthorizedTraversalTest {

    static String bvhAuthz = "8;-;-";
    static String bvh2Authz = "8;2;-";
    static String summit3Authz = "9:3;11";
    static String summit4Authz = "9:4;11";
    static String summit5Authz = "9:5;11";
    static String authorized = "authorized";
    static String unauthorized = "unauthorized";
    private static TinkerGraph graph;

    @BeforeClass
    public static void createGraph() {
        graph = TinkerGraph.open();
        GraphTraversalSource g = graph.traversal();
        g.addV("NatuurlijkPersoon").property("name", "p0").property("generiekeAutorisatie", bvhAuthz)
                .property(set, "generiekeAutorisatie", bvh2Authz).next();
        g.addV("NatuurlijkPersoon").property("name", "p1").property("generiekeAutorisatie", summit3Authz).next();
        g.addV("NatuurlijkPersoon").property("name", "p2").property("generiekeAutorisatie", summit4Authz).next();
        g.addV("Incident").property("name", "v1").property("generiekeAutorisatie", bvhAuthz).next();
        g.addV("Incident").property("name", "v2").property("generiekeAutorisatie", summit4Authz).next();
        g.V().has("name", "v1").as("a").
                V().has("name", "p0").addE("VERDACHTE").to("a").property("name", "e01").property("generiekeAutorisatie", bvhAuthz).
                V().has("name", "p1").addE("VERDACHTE").to("a").property("name", "e11").property("generiekeAutorisatie", summit3Authz).
                next();
        g.V().has("name", "v2").as("a").
                V().has("name", "p1").addE("VERDACHTE").to("a").property("name", "e12").property("generiekeAutorisatie", summit3Authz).
                V().has("name", "p2").addE("VERDACHTE").to("a").property("name", "e22").property("generiekeAutorisatie", summit5Authz).
                next();

        // Vertices and edges for methodsBothInOutShouldNotSkipEdgeAuthorization()
        Vertex bothInOut0 = g.addV("NatuurlijkPersoon").property("name", "bothInOut0").property("generiekeAutorisatie", authorized).next();
        Vertex bothInOut1 = g.addV("NatuurlijkPersoon").property("name", "bothInOut1").property("generiekeAutorisatie", authorized).next();
        Vertex bothInOut2 = g.addV("NatuurlijkPersoon").property("name", "bothInOut2").property("generiekeAutorisatie", authorized).next();
        Vertex bothInOut3 = g.addV("NatuurlijkPersoon").property("name", "bothInOut3").property("generiekeAutorisatie", authorized).next();
        Vertex bothInOut4 = g.addV("NatuurlijkPersoon").property("name", "bothInOut4").property("generiekeAutorisatie", authorized).next();

        bothInOut0.addEdge("KIND", bothInOut1, "generiekeAutorisatie", authorized);
        bothInOut0.addEdge("KIND", bothInOut2, "generiekeAutorisatie", unauthorized);
        bothInOut3.addEdge("OUDER", bothInOut0, "generiekeAutorisatie", authorized);
        bothInOut4.addEdge("OUDER", bothInOut0, "generiekeAutorisatie", unauthorized);

    }

    private AuthorizedTraversalSource g() {
        return graph.traversal(AuthorizedTraversalSource.class);
    }

    @Test
    public void normalOperationTest() {

        int size;

        size = g().withAuthorization(Arrays.asList(bvhAuthz)).V().toList().size();
        assertEquals(2, size);

        size = g().withAuthorization(Arrays.asList(summit3Authz)).V().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(bvhAuthz, summit3Authz)).V().toList().size();
        assertEquals(3, size);

        size = g().withAuthorization(Arrays.asList(bvhAuthz)).E().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(summit3Authz)).E().toList().size();
        assertEquals(2, size);

        size = g().withAuthorization(Arrays.asList(bvhAuthz)).V().has("name", "v1").inE().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(summit3Authz)).V().has("name", "p1").outE().toList().size();
        assertEquals(2, size);

        size = g().withAuthorization(Arrays.asList(bvhAuthz)).V().has("name", "v1").in().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(summit3Authz, summit4Authz)).
                V().has("name", "p1").out().toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(bvhAuthz)).V().where(__.inE()).toList().size();
        assertEquals(1, size);

        size = g().withAuthorization(Arrays.asList(summit3Authz, summit4Authz)).
                V().where(__.bothE()). toList().size();
        assertEquals(2, size);

    }

    @Test
    public void traversalSourceAttackTest() {
        List<Vertex> result;

        try {
            g().V().toList();
            fail("Unauthorized query should fail");
        } catch (RuntimeException exception) {
            assertEquals(exception.getMessage(), orderMessage);
        }

        try {
            g().withAuthorization(Arrays.asList(bvhAuthz)).
                withAuthorization(Arrays.asList(bvhAuthz, summit3Authz)).V().toList();
            fail("Query with second withAuthorization() call should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), onceMessage);
        }

        try {
            g().withAuthorization(Arrays.asList(bvhAuthz)).clone().
                withAuthorization(Arrays.asList(bvhAuthz, summit3Authz)).V().toList();
            fail("Query with second withAuthorization() call should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), onceMessage);
        }

        try {
            g().withSideEffect("userAuthorization", Collections.singletonList(bvhAuthz)).V().toList();
            fail("Query with userAuthorizations provided by withSideEffect() should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), orderMessage);
        }

        // Addition of "userAuthorization" sideEffect() should be ignored
        result = g().withAuthorization(Arrays.asList(bvhAuthz)).
            withSideEffect("userAuthorization", Arrays.asList(summit3Authz)).V().toList();
        assertEquals(2, result.size());

        try {
            g().withSideEffect("userAuthorization", Arrays.asList(summit3Authz)).
                withAuthorization(Arrays.asList(bvhAuthz)).V().toList();
            fail("Calling withSideEffect() before withAuthorization() should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), orderMessage);
        }

        try {
            // The cast is required to allow for the inject() step
            ((AuthorizedTraversal<Vertex,Object>)(Object)g().withAuthorization(Arrays.asList(bvhAuthz)).V()).
                as("x").inject(summit3Authz).store("userAuthorization").select("x").toList();
            fail("Faking userAuthorizations using the store() step should fail");
        } catch (Exception exception) {
            assertTrue(exception instanceof UnsupportedOperationException);
        }

        try {
            // The cast is required to allow for the inject() step
            ((AuthorizedTraversal<Vertex,Object>)(Object)g().withAuthorization(Arrays.asList(bvhAuthz)).V()).
                as("x").inject(summit3Authz).aggregate("userAuthorization").select("x").toList();
            fail("Faking userAuthorizations using the aggregate() step should fail");
        } catch (Exception exception) {
            assertTrue(exception instanceof UnsupportedOperationException);
        }
    }

    /*
     * For the following blocked methods attacks are possible, but not provided as test:
     *  - addV, addE, property
     *  - program, sideEffect(Consumer<Traverser>)
     *  - addStart, addStarts, addStep, removeStep
    */
    @Test
    public void traversalAttackTest() {
        // Without blocking getGraph() all vertices would be returned
        try {
            g().withAuthorization(Collections.singletonList(bvhAuthz)).V().getGraph().get().traversal().V().toList();
            fail("Accessing DefaultAuthorizedTraversal.getGraph() should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), blockMessage);
        }
    }

    @Test
    public void reflectionAttackTest() {
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
        List<Vertex> result = g().withAuthorization(Collections.singletonList(bvhAuthz)).V().
            inject(vertices).dedup().toList();
        assertEquals(2, result.size());
    }


    /*
     * For the following blocked method variants that provide Traversers, analogous attacks are possible as for map,
     * but these are not provided as test:
     *  - barrier, branch, flatMap, filter
     *  - emit, sideEffect, until
    */
    @Test
    public void lambdaAttackTest() {
        // Without blocking map(Function<Traverser>), unauthorized vertices would be returned
        try {
            List<String> authorizations = Arrays.asList(bvhAuthz, summit3Authz);
            List<Vertex> result = g().withAuthorization(Arrays.asList(bvhAuthz)).V().
            map(t -> {t.sideEffects("userAuthorization", authorizations); return t.get();}).toList();
            fail("Accessing AuthorizedTraversal.map(Function) should fail");
        } catch (Exception exception) {
            assertEquals(exception.getMessage(), blockMessage);
        }
    }

    /*
     * Attacks using anonymous traversals are analogous to those on the main traversal
     * Only a few checks are made:
     *  - for traversals that should not expose unauthorized graph elements
     *  - whether steps that have blocked variants still work properly
     */
    @Test
    public void anonymousAttackTest() {

        // Anonymous __.V() should inherit userAuthorization from parent
        // This also verifies proper working of the map(Traversal) variant
        int size = g().withAuthorization(Arrays.asList(bvhAuthz)).V().
            map(__.V().fold()).unfold().dedup().toList().size();
        assertEquals(2, size);

        // Anonymous __.inE() should inherit userAuthorization from parent
        size = g().withAuthorization(Arrays.asList(bvhAuthz)).V().has("name", "v1").
            map(__.inE().fold()).unfold().dedup().toList().size();
        assertEquals(1, size);

        // Anonymous __.emit(Traversal) should still work
        size = g().withAuthorization(Arrays.asList(bvhAuthz)).V().has("name", "v1").
            map(__.repeat(__.inE()).times(1).emit(__.values("name"))).toList().size();
        assertEquals(1, size);
    }
}
