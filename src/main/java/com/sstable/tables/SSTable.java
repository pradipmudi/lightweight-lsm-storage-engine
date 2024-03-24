package com.sstable.tables;

import com.sstable.model.KeyValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SSTable {
    private static final Logger logger = LoggerFactory.getLogger(SSTable.class);
    String filename;
    List<KeyValuePair> content;

    public SSTable(String filename, List<KeyValuePair> content) {
        this.filename = filename;
        this.content = content;
        logger.info("Created SSTable with filename: {}", filename);
    }

    public String getFilename() {
        return filename;
    }

    public List<KeyValuePair> getContent() {
        return content;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SSTable: ").append(filename).append("\n");
        for (KeyValuePair entry : content) {
            sb.append(entry.toString()).append("\n");
        }
        return sb.toString();
    }
}
