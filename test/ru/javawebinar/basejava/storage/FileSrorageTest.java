package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.ObjectStreamSerializer;

import java.io.File;

import static org.junit.Assert.*;

public class FileSrorageTest extends AbstractStorageTest{

    public FileSrorageTest() {
        super(new FileSrorage(new File("c:\\storage"), new ObjectStreamSerializer()));
    }
}