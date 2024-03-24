# SSTable Implementation

This Java project provides a basic implementation of an SSTable-based storage system. SSTables (Sorted String Tables) are critical data structures used in distributed databases and storage systems for efficient storage and retrieval of key-value pairs.

## Features

- **Key-Value Storage:** Organizes data as key-value pairs, sorted lexicographically by keys.
- **Jigsaw Pattern:** Implements a memtable for sequential and append-only write operations, following the Jigsaw pattern.
- **Immutable Structure:** Ensures immutability of data once written to SSTables, simplifying concurrency control.
- **Log-Structured Writes:** Utilizes memtables for buffering write operations before flushing to SSTable files.
- **Merge and Compaction:** Includes methods for merging and compacting SSTables to optimize storage and performance.

## Usage

To use the SSTable storage system:

1. **Initialize Storage:** Create an instance of `SSTableStorage` with a specified memtable threshold.
2. **Write Data:** Use the `write()` method to add key-value pairs to the storage system.
3. **Read Data:** Utilize the `read()` method to retrieve values associated with keys.
4. **Merge SSTables:** Call the `merge()` method periodically to merge existing SSTables.
5. **Compact SSTables:** Use the `compact()` method to compact SSTables for improved storage efficiency.

## Example

```
// Initialize storage with memtable threshold
SSTableStorage storage = new SSTableStorage(100);

// Write data
storage.write("key1", "value1");
storage.write("key2", "value2");

// Read data
String value = storage.read("key1");
System.out.println("Value for key 'key1': " + value);

// Merge SSTables
storage.merge();

// Compact SSTables
storage.compact();
```

## Blog
I also discuss about SSTable in details in my blog. Please feel free to explore the blog here: https://pradipmudi.substack.com/p/write-heavy-systems-sstables-sorted
