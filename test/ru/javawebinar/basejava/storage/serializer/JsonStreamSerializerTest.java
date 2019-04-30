package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.storage.AbstractStorageTest;
import ru.javawebinar.basejava.storage.FileSrorage;
import ru.javawebinar.basejava.storage.PathStorage;
import ru.javawebinar.basejava.storage.Storage;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class JsonStreamSerializerTest extends AbstractStorageTest {

    public JsonStreamSerializerTest() {
        super(new PathStorage(Paths.get("c:\\storage"), new JsonStreamSerializer()));
    }
}