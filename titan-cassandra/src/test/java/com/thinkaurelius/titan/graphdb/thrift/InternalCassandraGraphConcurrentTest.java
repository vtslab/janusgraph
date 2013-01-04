package com.thinkaurelius.titan.graphdb.thrift;

import com.thinkaurelius.titan.CassandraStorageSetup;
import com.thinkaurelius.titan.diskstorage.cassandra.embedded.CassandraDaemonWrapper;
import com.thinkaurelius.titan.graphdb.TitanGraphConcurrentTest;
import org.junit.BeforeClass;

public class InternalCassandraGraphConcurrentTest extends TitanGraphConcurrentTest {

    public InternalCassandraGraphConcurrentTest() {
        super(CassandraStorageSetup.getCassandraThriftGraphConfiguration());
    }

    @BeforeClass
    public static void beforeClass() {
        CassandraDaemonWrapper.start(CassandraStorageSetup.cassandraYamlPath);
    }
}