package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract  void doUpdate(SK key, Resume r);
    protected abstract  void doSave(SK key, Resume r);
    protected abstract  Resume doGet(SK key) throws IOException;
    protected abstract  void doDelete(SK key) throws IOException;

    protected abstract SK getSearchKey(String uuid);
    protected abstract boolean isExist(SK key);
    protected abstract List<Resume> doCopyAll() throws IOException;

    @Override
    abstract public int size() throws IOException;

    private SK getExistSearchKey(String uuid){
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)){
            throw new NotExistStorageException("Not "+uuid);
        }
        return searchKey;
    }

    private SK getNotExistSearchKey(String uuid){
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)){
            throw new ExistStorageException("Exist "+uuid);
        }
        return searchKey;
    }

    @Override
    public void update(Resume r){
        SK existSearchKey = getExistSearchKey(r.getUuid());
        doUpdate(existSearchKey, r);
    }

    @Override
     public void save(Resume r){
        SK notExistSearchKey = getNotExistSearchKey(r.getUuid());
        doSave(notExistSearchKey, r);
    }

    @Override
     public Resume get(String uuid) throws IOException {
        SK existSearchKey = getExistSearchKey(uuid);
        return   doGet(existSearchKey);
    }

    @Override
    public void delete(String uuid) throws IOException {
        SK ExistSearchKey = getExistSearchKey(uuid);
        doDelete(ExistSearchKey);
    }

    @Override
    public List<Resume> getAllSorted() throws IOException {
        List<Resume> resumes = doCopyAll();
        Collections.sort(resumes);
        return resumes;
    }

}
