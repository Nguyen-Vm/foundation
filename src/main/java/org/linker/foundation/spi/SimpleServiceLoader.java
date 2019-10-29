package org.linker.foundation.spi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleServiceLoader {

    private static final String PREFIX = "/META-INF/services/";

    public static <T>List<T> load(Class<T> cls) {
        List<String> implClasses = readServiceFile(cls);
        List<T> implList = new ArrayList<>();
        for (String implClass : implClasses) {
            Class<T> c = null;
            try {
                c = (Class<T>) Class.forName(implClass);
                implList.add(c.newInstance());
            } catch (Exception e) {
                System.out.println("reflection instantiation failed: " + implClass);
                return new ArrayList<>();
            }
        }
        return implList;
    }

    private static <T> List<String> readServiceFile(Class<T> cls) {
        String infName = cls.getCanonicalName();
        String filename = cls.getResource(PREFIX + infName).getPath();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            List<String> implClasses = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                implClasses.add(line);
            }
            return implClasses;
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return new ArrayList<>();
        } catch (IOException e) {
            System.out.println("File read failed: " + filename);
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) {
        List<Ishot> implList = load(Ishot.class);
        if (implList != null && !implList.isEmpty()) {
            for (Ishot ishot : implList) {
                ishot.shot();
            }
        }
    }
}
