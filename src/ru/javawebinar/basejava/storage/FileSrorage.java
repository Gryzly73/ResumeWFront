package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileSrorage extends AbstractStorage<File>{

    private File directory;
    private StreamSerializer streamSerializer;

    public FileSrorage(File directory, StreamSerializer streamSerializer) {

        Objects.requireNonNull(directory, "OOOO!");

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected void doUpdate(File file, Resume r) {

        try(OutputStream os = new FileOutputStream(file)) {
            streamSerializer.doWrite(r, os);
        } catch (IOException e) {
            throw  new StorageException(e.getMessage(), file.getName());
        }
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
            doUpdate(file, r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Resume doGet(File file)  {

        try(InputStream is = new FileInputStream(file)) {
          Resume  resume = streamSerializer.doRead(is);
          return resume;
        } catch (IOException e) {
           throw  new StorageException(e.getMessage(), file.getName());
        }
    }

    @Override
    protected void doDelete(File file) {
      if (!file.delete()) {
          throw new StorageException("File not found ", file.getName());
      }
    }

    @Override
    protected File getSearchKey(String uuid) {
            return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
      return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        Arrays.asList(files);
        return null;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        for (File file : files){
            file.delete();
        }
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }
}
