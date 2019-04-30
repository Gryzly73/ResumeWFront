package ru.javawebinar.basejava.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {

    private Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String FULL_NAME_1 = "full_name1";

    private static final String UUID_2 = "uuid2";
    private static final String FULL_NAME_2 = "full_name2";

    private static final String UUID_3 = "uuid3";
    private static final String FULL_NAME_3 = "full_name3";

    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME_4 = "full_name4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
        RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
        RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
        RESUME_4 = new Resume(UUID_4, FULL_NAME_4);

        RESUME_1.addContact(ContactType.MAIL, "mail1@ya.ru");
        RESUME_1.addContact(ContactType.PHONE, "11111");
        RESUME_2.addContact(ContactType.SKYPE, "skype2");
        RESUME_2.addContact(ContactType.PHONE, "22222");


        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        RESUME_1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        RESUME_2.addContact(ContactType.SKYPE, "skype2");
        RESUME_2.addContact(ContactType.PHONE, "22222");
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
    }



    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws IOException {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws IOException, SQLException {
        storage.update(new Resume(UUID_2, "Dummy"));
        Assert.assertEquals("Dummy", storage.get(UUID_2).getFullName());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws IOException, SQLException {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws IOException {
        storage.delete("dummy");
    }

    //@Test(expected = PSQLException.class)
    @Test(expected = StorageException.class)
    public void saveNotExist() throws IOException, SQLException {
        storage.save(RESUME_1);
    }

    @Test
    public void save() throws IOException, SQLException {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(FULL_NAME_4, storage.get(UUID_4).getFullName());
    }

    @Test
    public void get() throws IOException, SQLException {
        Assert.assertEquals(storage.get(UUID_1), RESUME_1);
        Assert.assertEquals(storage.get(UUID_2), RESUME_2);
        Assert.assertEquals(storage.get(UUID_3), RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws SQLException, IOException {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test
    public void getAllSorted() throws IOException {
        List<Resume> allSorted = storage.getAllSorted();
        Assert.assertEquals(allSorted.get(0), RESUME_1);
    }

    @Test
    public void size() throws IOException {
        Assert.assertEquals(3, storage.size());
    }
}