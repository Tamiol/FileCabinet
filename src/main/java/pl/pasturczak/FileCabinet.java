package pl.pasturczak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class FileCabinet implements Cabinet {

    private static final Logger logger = LoggerFactory.getLogger(FileCabinet.class);

    private final List<Folder> folders = new ArrayList<>();

    public FileCabinet(List<Folder> folders) {
        collectFolders(folders, this.folders);
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {

        if (Objects.isNull(name)) {
            return Optional.empty();
        }

        return folders.stream()
                .filter( e -> e.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {

        if (Objects.isNull(size)) {
            return Collections.emptyList();
        }

        boolean sizeExists = Arrays.stream(FolderSize.values())
                .anyMatch(e -> e.name().equals(size));

        if (!sizeExists) {
            logger.warn("Invalid folder size: {}", size);
            return Collections.emptyList();
        }

        return folders.stream()
                .filter(e -> e.getSize().equals(size))
                .toList();
    }

    @Override
    public int count() {
        return folders.size();
    }

    private void collectFolders(List<Folder> folders, List<Folder> result) {
        Deque<Folder> stack = new ArrayDeque<>(folders);

        while (!stack.isEmpty()) {
            Folder folder = stack.pop();
            result.add(folder);

            if (folder instanceof MultiFolder) {
                stack.addAll(((MultiFolder) folder).getFolders());
            }
        }
    }
}
