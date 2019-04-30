package ru.javawebinar.basejava;

import java.io.*;
import java.util.UUID;

public class MainString {
    public static void main(String[] args) {
        String[] strArray = new String[]{"1", "2", "3", "4", "5"};

        StringBuilder sb = new StringBuilder();
        for (String str : strArray) {
            sb.append(str).append(", ");
        }
        /*
        System.out.println(sb.toString());

        String str1 = "abc";
        String str3 = "c";
        String str2 = ("ab" + str3).intern();
        System.out.println(str1 == str2);
        //UUID uuid = UUID.fromString("aaa");
        for (int i = 0; i<12; i++) {
            UUID randomUUID = UUID.randomUUID();
            //   System.out.println(uuid.toString());
            String s = randomUUID.toString();
            String replace = s.replace('-', ' ');
            replace.split(" ");
            //   System.out.println(replace.length());
            System.out.println(replace);
        }
        */

        File file_Gson = new File("c:\\storage\\Gson");
/*
        try {
            file_Gson.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        try (OutputStream fos = new FileOutputStream(file_Gson);Writer writer = new OutputStreamWriter(fos);) {
          //  byte[] buffer = sb.toString().getBytes();
          //  fos.write(buffer, 0, buffer.length);

            writer.write("[ekb kjgtc ,mtn gj djhjnfv");
        //   writer.close();
        }
                catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }
}
