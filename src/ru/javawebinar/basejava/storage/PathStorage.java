package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    private StreamSerializer streamSerializer;

    public PathStorage(Path directory, StreamSerializer streamSerializer) {

        Objects.requireNonNull(directory, "OOOO!");

        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toString() + " is not readable/writable");
        }
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toString() + " is not directory");
        }

        this.directory = directory;
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            OutputStream outputStream = Files.newOutputStream(path);
            streamSerializer.doWrite(r, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        doUpdate(path, r);
    }

    @Override
    protected Resume doGet(Path path) //throws IOException
    {
        try(InputStream inputStream = Files.newInputStream(path)) {
            Resume resume = streamSerializer.doRead(inputStream);
            return resume;
        } catch (IOException e) {
           throw new ExistStorageException(path.toString());
        }
    }

    @Override
    protected void doDelete(Path path) throws IOException {
        Files.delete(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {

       return directory.resolve(uuid);

    }

    @Override
    protected boolean isExist(Path path) {
       return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> doCopyAll() throws IOException {

        List<Resume> collect = createStream().map(this::doGet).collect(Collectors.toList());

        return collect;
    }

    @Override
    public void clear() throws IOException {

        createStream().forEach(path -> {
            try {
                doDelete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int size() throws IOException {
        return (int) createStream().count();
    }

    private Stream<Path> createStream() throws IOException {
        return Files.list(directory);
    }
}
