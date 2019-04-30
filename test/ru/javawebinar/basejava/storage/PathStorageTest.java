package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.ObjectStreamSerializer;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PathStorageTest extends AbstractStorageTest{

    public PathStorageTest() {
        super(new PathStorage(Paths.get("c:\\storage"), new ObjectStreamSerializer()));
    }
}