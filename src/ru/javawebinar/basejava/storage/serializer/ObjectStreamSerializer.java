package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) {

        try (ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {

        try (ObjectInputStream ois = new ObjectInputStream(is)){
          Object  o = ois.readObject();
            return (Resume) o;
        }
       catch (ClassNotFoundException e) {
            throw new StorageException(e.getMessage(), "Error");
        }
    }
}
