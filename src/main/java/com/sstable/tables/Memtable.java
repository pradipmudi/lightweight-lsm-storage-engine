package com.sstable.tables;


import com.sstable.model.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

// Represents a memtable for storing pending writes
public class Memtable {
    private static final Logger logger = LoggerFactory.getLogger(Memtable.class);
    int threshold;
    List<KeyValuePair> entries;

    public Memtable(int threshold) {
        this.threshold = threshold;
        this.entries = new ArrayList<>();
        logger.info("Created Memtable with threshold: {}", threshold);
    }

    public int getThreshold() {
        return threshold;
    }

    public List<KeyValuePair> getEntries() {
        return entries;
    }

    public void addEntry(KeyValuePair entry) {
        entries.add(entry);
        logger.info("Added entry to Memtable: {}", entry.getKey());
    }

    public boolean isFull() {
        return entries.size() >= threshold;
    }

    public List<KeyValuePair> flushToSSTable(String filename) {
        // Logic to flush memtable entries to an SSTable file
        List<KeyValuePair> entriesToFlush = new ArrayList<>(entries);
        // Code to write entriesToFlush to disk with given filename
        logger.info("Flushing memtable to SSTable: {}", filename);
        logger.info("Entries flushed: {}", entriesToFlush.size());
        return entriesToFlush;
    }
}
