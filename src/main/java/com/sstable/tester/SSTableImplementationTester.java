package com.sstable.tester;

import com.sstable.storage.SSTableStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSTableImplementationTester {
    private static final Logger logger = LoggerFactory.getLogger(SSTableImplementationTester.class);

    public static void main(String[] args) {
        // Example usage for ad targeting data
        SSTableStorage storage = new SSTableStorage(3); // Threshold set to 3 entries for demonstration

        // User IDs and ad interaction data represented as JSON strings
        // UserAdData class not used to avoid structural changes
        String user1Data = "{\"user_id\": \"user_id_1\", \"browsing_history\": [\"travel\", \"photography\"]}";
        String user2Data = "{\"user_id\": \"user_id_2\", \"browsing_history\": [\"sports\", \"finance\"]}";
        String user3Data = "{\"user_id\": \"user_id_3\", \"ad_interactions\": [\"viewed travel ad\"]}";
        String user4Data = "{\"user_id\": \"user_id_1\", \"ad_interactions\": [\"clicked fashion ad\"]}";

        // Writing user data as JSON strings
        storage.write("user_id_1", user1Data);
        storage.write("user_id_2", user2Data);
        storage.write("user_id_3", user3Data);
        storage.write("user_id_1", user4Data); // Update for user_id_1 with new interaction

        // After writes, memtable will be flushed automatically based on threshold
        // SSTables will be created and stored
        logger.info("SSTables created: {}", storage.getSstables().size());

        // Reading user data as JSON strings
        String userData = storage.read("user_id_2");
        logger.info("Ad targeting data for 'user_id_2': {}", userData);

        // Merging SSTables (combining and eliminating redundancy)
        storage.merge();

        // Compacting SSTables (optimizing storage and read performance)
        storage.compact();
    }
}


