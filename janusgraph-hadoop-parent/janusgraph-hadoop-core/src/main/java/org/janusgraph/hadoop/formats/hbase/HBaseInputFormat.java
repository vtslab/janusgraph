// Copyright 2017 JanusGraph Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.janusgraph.hadoop.formats.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.exceptions.IllegalArgumentIOException;
import org.apache.hadoop.hbase.mapreduce.TableSplit;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.janusgraph.diskstorage.Entry;
import org.janusgraph.diskstorage.StaticBuffer;
import org.janusgraph.hadoop.formats.util.GiraphInputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HBaseInputFormat extends GiraphInputFormat {

    private static final Logger log = LoggerFactory.getLogger(HBaseInputFormat.class);
    public HBaseInputFormat() {
        super(new HBaseBinaryInputFormat());
    }

    public static final String NUM_MAPPERS_PER_REGION = "hbase.mapreduce.tableinput.mappers.per.region";

    @Override
    public List<InputSplit> getSplits(JobContext context) throws IOException, InterruptedException {
        final int MAX_NUM_MAPPERS_PER_REGION = 10000;
        final List<InputSplit> splitSplits = new ArrayList<>();
        int n;

        InputFormat<StaticBuffer, Iterable<Entry>> inputFormat = getInputFormat();
        List<InputSplit> inputSplits = inputFormat.getSplits(context);

        try {
            n = Integer.parseInt(context.getConfiguration().get(NUM_MAPPERS_PER_REGION));
        } catch (Exception e) {
            n = 0;
        }
        if (n < 2 || n > MAX_NUM_MAPPERS_PER_REGION) {
            splitSplits.addAll(inputSplits);
        } else {
            for (InputSplit split : inputSplits) {
                splitSplits.addAll(createNInputSplitsUniform(split, n));
            }
            log.debug(String.format("Input splits with NUM_MAPPERS_PER_REGION set: %s", splitSplits));
        }
        return splitSplits;
    }

    // Copied, cq backported from merged commit of Apache HBase:
    // https://github.com/apache/hbase/commit/16d483f9003ddee71404f37ce7694003d1a18ac4
    /**
     * Create n splits for one InputSplit, For now only support uniform distribution
     * @param split A TableSplit corresponding to a range of rowkeys
     * @param n     Number of ranges after splitting.  Pass 1 means no split for the range
     *              Pass 2 if you want to split the range in two;
     * @return A list of TableSplit, the size of the list is n
     * @throws IllegalArgumentIOException
     */
    protected List<InputSplit> createNInputSplitsUniform(InputSplit split, int n)
        throws IllegalArgumentIOException {
        if (split == null || !(split instanceof TableSplit)) {
            throw new IllegalArgumentIOException(
                "InputSplit for CreateNSplitsPerRegion can not be null + "
                    + "and should be instance of TableSplit");
        }
        //if n < 1, then still continue using n = 1
        n = n < 1 ? 1 : n;
        List<InputSplit> res = new ArrayList<>(n);
        if (n == 1) {
            res.add(split);
            return res;
        }

        // Collect Region related information
        TableSplit ts = (TableSplit) split;
        TableName tableName = ts.getTable();
        String regionLocation = ts.getRegionLocation();
        long regionSize = ts.getLength();
        byte[] startRow = ts.getStartRow();
        byte[] endRow = ts.getEndRow();

        // For special case: startRow or endRow is empty
        if (startRow.length == 0 && endRow.length == 0){
            startRow = new byte[1];
            endRow = new byte[1];
            startRow[0] = 0;
            endRow[0] = -1;
        }
        if (startRow.length == 0 && endRow.length != 0){
            startRow = new byte[1];
            startRow[0] = 0;
        }
        if (startRow.length != 0 && endRow.length == 0){
            endRow =new byte[startRow.length];
            for (int k = 0; k < startRow.length; k++){
                endRow[k] = -1;
            }
        }

        // Split Region into n chunks evenly
        Scan scan = new Scan();
        byte[][] splitKeys = Bytes.split(startRow, endRow, true, n-1);
        for (int i = 0; i < splitKeys.length - 1; i++) {
            //notice that the regionSize parameter may be not very accurate
            TableSplit tsplit =
                new TableSplit(tableName, scan, splitKeys[i], splitKeys[i + 1],
                    regionLocation, regionSize / n);
            res.add(tsplit);
        }
        return res;
    }

}
