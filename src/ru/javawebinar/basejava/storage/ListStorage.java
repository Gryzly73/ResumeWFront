package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListStorage extends AbstractStorage<Integer> {

   private List<Resume> resumes = new ArrayList<>();

    @Override
    public void clear() {
        resumes.clear();
    }


 @Override
 protected void doUpdate(Integer key, Resume r) {
  resumes.add(key, r);
 }

 @Override
 protected void doSave(Integer key, Resume r) {
     resumes.add(r);
 }

 @Override
 protected Resume doGet(Integer key) {
  return resumes.get(key);
 }

 @Override
 protected void doDelete(Integer key) {
     resumes.remove(key);
 }

 @Override
 protected Integer getSearchKey(String uuid) {
     for (Resume r : resumes){
      if (r.getUuid().equals(uuid)){
      return resumes.indexOf(r);
      }
     }
  return null;
 }

 @Override
 protected boolean isExist(Integer key) {
  return key != null;
 }

 @Override
 protected List<Resume> doCopyAll() {
  return resumes;
 }

    @Override
    public int size() {
        return resumes.size();
    }
}
