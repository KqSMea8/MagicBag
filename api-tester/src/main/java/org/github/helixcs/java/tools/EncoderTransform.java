package org.github.helixcs.java.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class EncoderTransform {
    public static void main(String[] args) throws Exception {
        File path = new File("D:\\future_hotel\\psbuac-all\\psbuac\\psbuac-service\\src\\main\\java\\com\\alitrip\\psbuac\\service\\utils");
        if (!path.exists()) {
            throw new Exception("path not exist!");
        }
        handle(path);
    }

    private static void handle(File sf) {
        if (sf.isFile() && sf.getAbsolutePath().endsWith(".java")) {
            try {
                forceChangeEnode(sf, "GBK", "UTF-8");
                System.out.println(sf.getAbsoluteFile() + " ==> done!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // dir
        else if (sf.isDirectory()) {
            Arrays.stream(Objects.requireNonNull(sf.listFiles())).forEach(EncoderTransform::handle);
        }
    }

    private static synchronized void forceChangeEnode(final File file, final String sourceEncoding, final String targetEncoding) throws IOException {
        String content;
        try {
            content = FileUtils.readFileToString(file, sourceEncoding);
        } catch (IOException e) {
            throw e;
        }
        FileUtils.write(file, content, targetEncoding);
    }
}
