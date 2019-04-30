package ru.javawebinar.basejava.storage;

import com.google.gson.Gson;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MainJson {
    private static final Gson GSON = new Gson();

    public static void main (String... args) throws IOException {
        Resume resume2 = new Resume(UUID.randomUUID().toString(), "new_full");
        resume2.addContact(ContactType.PHONE, "000-111");
        resume2.addContact(ContactType.MAIL, "@@@@");

        Resume resume3 = new Resume(UUID.randomUUID().toString(), "new_full1");
        resume3.addContact(ContactType.MAIL, "iiii@ooo");
        resume3.addContact(ContactType.SKYPE, "098980-0909");

        File file_Gson = new File("c:\\storage\\Gson");
        file_Gson.createNewFile();

        String gson1 = GSON.toJson(new Resume(UUID.randomUUID().toString(), "full_name1"));

       // Writer writer = null;

        try (OutputStream os = new FileOutputStream(file_Gson);
         Writer   writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);){
     //    GSON.toJson(resume2, writer);
         writer.write(GSON.toJson(resume2));
            String s = writer.toString();

            System.out.println(s);

            //   writer.write(gson1);
        }
        catch (IOException e) {
           throw new RuntimeException();
        //    e.getMessage();
        }

/*
       String gson2 = GSON.toJson(resume2, resume2.getClass());
        String gson3 = GSON.toJson(resume3, resume3.getClass());


        System.out.println(gson1);
        System.out.println(gson2);
        System.out.println(gson3);

        Resume resumeFrom1 = GSON.fromJson(gson1, Resume.class);
        Resume resumeFrom2 = GSON.fromJson(gson2, Resume.class);
        Resume resumeFrom3 = GSON.fromJson(gson3, Resume.class);

        System.out.println(resumeFrom1.toString());
        System.out.println(resumeFrom2.toString());
        System.out.println(resumeFrom3.toString());

        System.out.println(resume2 == resumeFrom2);
        System.out.println(resume3.equals(resumeFrom3));
*/



    }
}
