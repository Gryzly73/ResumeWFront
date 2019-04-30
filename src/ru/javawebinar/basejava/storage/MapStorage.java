package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

   private Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
    }


    @Override
    protected void doUpdate(String key, Resume r) {
        map.put(key, r);
    }

    @Override
    protected void doSave(String key, Resume r) {
        map.put(key, r);
    }

    @Override
    protected Resume doGet(String key) {
       return map.get(key);
    }

    @Override
    public void doDelete(String uuid){
        map.remove(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String key) {
        return map.containsKey(key);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }


    @Override
    public int size() {
        return map.size();
    }
}
