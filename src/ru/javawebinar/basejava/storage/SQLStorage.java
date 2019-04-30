package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLStorage implements Storage {

    private SQLHelper sqlHelper;

    private static final String DELETE_ALL = "DELETE FROM resume";
    private static final String DELETE_RESUME = "DELETE FROM resume where uuid = ?";
    private static final String DELETE_CONTACT = "DELETE FROM contact where resume_uuid = ?";

    private static final String UPDATE = "update resume set fullname = ? where uuid = ?";

    private static final String INSERT_3 = "insert into contact (type, value, resume_uuid) VALUES (?, ?, ?)";
    private static final String INSERT_2 = "insert into resume (uuid, fullname) VALUES (?, ?)";

    private static final String SELECT_ALL = "select count(*) from resume";
    private static final String SELECT = "select * from resume r left join contact c on r.uuid = c.resume_uuid where uuid= ?";
    private static final String SELECT_ORDER = "select * from resume order by (fullname, uuid)";



    public SQLStorage(String url, String user, String password) throws SQLException {
        this.sqlHelper = new SQLHelper(DriverManager.getConnection(url, user, password));
    }

    @Override
    public void clear() {
        try {
            sqlHelper.execute(DELETE_ALL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Resume r) throws SQLException {

        sqlHelper.executeTransaction(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setObject(1, r.getFullName());
                    ps.setObject(2, r.getUuid());
                    if (ps.executeUpdate() != 1) {
                        throw new NotExistStorageException(r.getUuid());
                    }
                }

            try (PreparedStatement ps = conn.prepareStatement(DELETE_CONTACT)) {
                ps.setString(1, r.getUuid());
            }

                for(Map.Entry<ContactType, String> item : r.getContacts().entrySet()) {
                    try (PreparedStatement ps = conn.prepareStatement(INSERT_3)) {
                        ps.setObject(1, item.getKey().name());
                        ps.setObject(2, item.getValue());
                        ps.setObject(3, r.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new ExistStorageException(r.getUuid());
                        }
                    }
                }
                return null;
        }
            );
    }

    @Override
    public void save (Resume r) throws SQLException {

        sqlHelper.executeTransaction(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(INSERT_2)) {
                        ps.setObject(1, r.getUuid());
                        ps.setObject(2, r.getFullName());
                        ps.execute();
                    }

                    for (Map.Entry<ContactType, String> item : r.getContacts().entrySet()) {

                        try (PreparedStatement ps = conn.prepareStatement(INSERT_3)) {
                            ps.setObject(1, item.getKey().name());
                            ps.setObject(2, item.getValue());
                            ps.setObject(3, r.getUuid());
                        }
                    }
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) throws SQLException {

        Resume resume = new Resume();
         return (Resume)  sqlHelper.executeTransaction(conn ->{
            try(PreparedStatement ps = conn.prepareStatement(SELECT)){
                ps.setObject(1, uuid);
                ResultSet resultSet = ps.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException("");
                }
                resume.setUuid(uuid);
                resume.setFullName(resultSet.getString(2));
                do{
                    String type = resultSet.getString("type");
                    if(type != null) {
                        ContactType contactType = ContactType.valueOf(type);
                        resume.addContact(contactType, resultSet.getString("value"));
                    }
                } while (resultSet.next());
                return resume;
            }});
    }

    @Override
    public void delete(String uuid) {

         sqlHelper.execute(DELETE_RESUME, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted()  {

        List<Resume> resumes = new ArrayList<>();

     return  (List<Resume>)  sqlHelper.execute(SELECT_ORDER, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("fullname")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        Object execute = sqlHelper.execute(SELECT_ALL, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
           return resultSet.next() ? resultSet.getInt(1) : 0;
        });
        return (Integer) execute;
    }
}
