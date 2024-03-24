package com.sstable.storage;

import com.sstable.tables.Memtable;
import com.sstable.tables.SSTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sstable.model.KeyValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Represents the SSTable-based storage system
public class SSTableStorage {
    private static final Logger logger = LoggerFactory.getLogger(SSTableStorage.class);
    private final Memtable memtable;
    private final List<SSTable> sstables;

    public SSTableStorage(int memtableThreshold) {
        this.memtable = new Memtable(memtableThreshold);
        this.sstables = new ArrayList<>();
        logger.info("Created SSTableStorage with memtable threshold: {}", memtableThreshold);
    }

    public Memtable getMemtable() {
        return memtable;
    }

    public List<SSTable> getSstables() {
        return sstables;
    }

    public void write(String key, String value) {
        KeyValuePair entry = new KeyValuePair(key, value);
        memtable.addEntry(entry);

        if (memtable.isFull()) {
            flushMemtable();
        }
    }

    private void flushMemtable() {
        String filename = "sstable_" + (sstables.size() + 1) + ".json";
        List<KeyValuePair> entriesToFlush = memtable.flushToSSTable(filename);
        sstables.add(new SSTable(filename, entriesToFlush));
        memtable.getEntries().clear();
        logger.info("Memtable flushed, created SSTable: {}", filename);
    }

    public String read(String key){
        // Perform binary search on sorted SSTables
        logger.info("Reading key: {}", key);
        for (SSTable sstable : sstables) {
            int index = Collections.binarySearch(sstable.getContent(), new KeyValuePair(key, ""), Comparator.comparing(kv -> kv.getKey()));
            if (index >= 0) {
                return sstable.getContent().get(index).getValue();
            }
        }
        logger.error("No entries found for key: {}", key);
        return null; // Key not found
    }

    public void merge() {
        logger.info("Merging SSTables...");
        if (sstables.size() <= 1) {
            logger.info("Insufficient SSTables to merge.");
            return;
        }

        List<SSTable> mergedSSTables = new ArrayList<>();
        // Sort SSTables by filename to ensure chronological order
        sstables.sort(Comparator.comparing(sstable -> sstable.getFilename()));

        // Logic for merging SSTables
        SSTable currentSSTable = null;
        for (SSTable sstable : sstables) {
            if (currentSSTable == null) {
                currentSSTable = sstable;
            } else {
                // Merge contents of currentSSTable and sstable
                List<KeyValuePair> mergedContent = mergeContents(currentSSTable.getContent(), sstable.getContent());
                String mergedFilename = "merged_" + currentSSTable.getFilename() + "_" + sstable.getFilename();
                SSTable mergedSSTable = new SSTable(mergedFilename, mergedContent);
                mergedSSTables.add(mergedSSTable);
                currentSSTable = mergedSSTable;
            }
        }

        // Replace existing SSTables with merged ones
        sstables.clear();
        sstables.addAll(mergedSSTables);
        logger.info("SSTables merged successfully.");
    }

    private List<KeyValuePair> mergeContents(List<KeyValuePair> content1, List<KeyValuePair> content2) {
        // Logic to merge contents of two SSTables
        List<KeyValuePair> mergedContent = new ArrayList<>();
        int index1 = 0, index2 = 0;
        while (index1 < content1.size() && index2 < content2.size()) {
            KeyValuePair entry1 = content1.get(index1);
            KeyValuePair entry2 = content2.get(index2);
            if (entry1.getKey().compareTo(entry2.getKey()) <= 0) {
                mergedContent.add(entry1);
                index1++;
            } else {
                mergedContent.add(entry2);
                index2++;
            }
        }
        while (index1 < content1.size()) {
            mergedContent.add(content1.get(index1));
            index1++;
        }
        while (index2 < content2.size()) {
            mergedContent.add(content2.get(index2));
            index2++;
        }
        return mergedContent;
    }

    public void compact() {
        logger.info("Compacting SSTables...");
        List<KeyValuePair> compactedContent = new ArrayList<>();
        for (SSTable sstable : sstables) {
            compactedContent.addAll(sstable.getContent());
        }
        sstables.clear();
        sstables.add(new SSTable("compacted_sstable.json", compactedContent));
        logger.info("SSTables compacted successfully.");
    }

    // Other methods for read, delete, etc.

    @Override
    public String toString() {
        return "SSTableStorage{" +
                "memtable=" + memtable +
                ", sstables=" + sstables +
                '}';
    }
}
