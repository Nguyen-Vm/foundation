package org.linker.foundation.utils;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class ByteUtilsTest {

    @Test
    public void string() {
        String name = "阮威敏";
        System.out.println(ByteUtils.string(name.getBytes()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(name.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        System.out.println(ByteUtils.string(is));
    }
}
