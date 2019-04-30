package ru.javawebinar.basejava.storage.serializer;

import com.google.gson.Gson;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StreamSerializer {

    private static Gson GSON = new Gson();

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (Writer writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);){
            JsonParser.write(r, writer);
        }
    }

    public  Resume doRead(InputStream is) throws IOException {
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {

            return JsonParser.read(reader, Resume.class);

        }
    }
}
