package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void doUpdate(Integer index, Resume r) {
        storage[index] = r;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public void doSave(Integer index, Resume r) {
         if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
            insertElement(r, index);
            size++;
    }

    @Override
    public void doDelete(Integer index) {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
    }

    @Override
    public Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    protected abstract void fillDeletedElement(int index);
    protected abstract void insertElement(Resume r, int index);

}
