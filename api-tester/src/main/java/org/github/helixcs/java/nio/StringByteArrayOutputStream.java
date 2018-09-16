package org.github.helixcs.java.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class StringByteArrayOutputStream extends ByteArrayOutputStream {
    private Charset charset = Charset.defaultCharset();

    public StringByteArrayOutputStream(){}

    public StringByteArrayOutputStream(Charset charset){
        this.charset = charset;
    }

    public void write(String string) throws IOException {
        if(string==null||string.length()==0){
            return;
        }
        this.write(string.getBytes(this.charset));
    }

    public void write(String string, Charset charset) throws IOException {
        this.charset = charset;
        this.write(string);
    }

}