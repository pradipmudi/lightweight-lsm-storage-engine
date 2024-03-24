package com.sstable.tester;

import com.sstable.storage.SSTableStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSTableImplementationTester {
    private static final Logger logger = LoggerFactory.getLogger(SSTableImplementationTester.class);
    public static void main(String[] args) {
        // Example usage
        SSTableStorage storage = new SSTableStorage(3); // Threshold set to 3 entries for demonstration

        // Writing some entries
        storage.write("post_id_1", "Great day at the beach!");
        storage.write("post_id_2", "Just finished a fantastic book.");
        storage.write("post_id_3", "Another example post.");
        storage.write("post_id_4", "Excited to share my recent travel experience!");

        // After writes, memtable will be flushed automatically based on threshold
        // SSTables will be created and stored
        logger.info("SSTables created: {}", storage.getSstables().size());

        // Reading an entry
        String value = storage.read("post_id_2");
        logger.info("Value for key 'post_id_2': {}", value);

        // Merging SSTables
        storage.merge();

        // Compacting SSTables
        storage.compact();
    }
}

