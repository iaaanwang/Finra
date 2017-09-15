package net.antra.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IAN on 2017/9/15.
 */
public class FileUtility {
    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
    }
}
