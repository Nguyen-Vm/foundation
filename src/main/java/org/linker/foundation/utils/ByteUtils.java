package org.linker.foundation.utils;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import static com.alibaba.fastjson.util.IOUtils.close;

/**
 * @author RWM
 * @date 2018/3/21
 * @description:
 */
public final class ByteUtils {

    public static final Charset UTF8 = Charset.forName("UTF-8");
    private static final int EOF = -1;

    public static String string(final byte[] body) {
        return null == body ? StringUtils.EMPTY : new String(body, UTF8);
    }

    public static String string(final InputStream input){
        return string(input, UTF8);
    }

    private static String string(final InputStream input, final Charset charset) {
        final StringBuilderWriter sw = new StringBuilderWriter();
        final InputStreamReader in = new InputStreamReader(input, charset);
        try {
            copyLarge(in, sw, new char[4096]);
            return sw.toString();
        } catch (Exception e) {
            throw new RuntimeException("input to string error", e);
        } finally {
            close(in);
            close(sw);
        }
    }

    private static long copyLarge(final Reader input, final Writer output, final char[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
