package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Storage {

    void clear() throws IOException;

    void update(Resume r) throws SQLException;

    void save(Resume r) throws SQLException;

    Resume get(String uuid) throws SQLException, IOException;

    void delete(String uuid) throws IOException;

    List<Resume> getAllSorted() throws IOException;

    int size() throws IOException;
}
