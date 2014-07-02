package com.dio.calendar.datastore;

import com.dio.calendar.Entry;
import com.dio.calendar.EntryWrapper;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by iovchynnikov on 5/26/14.
 */
public class FileSystemHelperImpl implements FileSystemHelper {

    @Override
    public boolean exists(Path fileName) {
        return Files.exists(fileName);
    }

    @Override
    public Path getPath(UUID id, Path dbPath) {
        return Paths.get(dbPath.toString(), id.toString() + ".xml");
    }

    @Override
    public Path getPath(String dbPath) {
        return Paths.get(dbPath);
    }

    @Override
    public List<Path> getListFiles(Path dbPath) {
        ListFilesVisitor visitor = new ListFilesVisitor();
        try {
            Files.walkFileTree(dbPath, visitor); // move visitor and method to helper?
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return visitor.getFiles();
    }

    @Override
    public boolean delete(Path file) throws IOException {
        return Files.deleteIfExists(file);
    }

    @Override
    public EntryWrapper getEntryWrapper(Entry entry) {
        return new EntryWrapper(entry);
    }

    public class ListFilesVisitor extends SimpleFileVisitor<Path> {
        private List<Path> files = new ArrayList<>();

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.toString().toLowerCase().endsWith(".xml")) {
                files.add(file);
            }
            return FileVisitResult.CONTINUE;
        }

        public List<Path> getFiles() {
            return files;
        }
    }


}
