package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ListStorageTest extends AbstractStorageTest{

    public ListStorageTest() {
        super(new ListStorage());
    }
}