package com.tsingfu.jvm;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyClassLoader extends ClassLoader {

    private static int ByteLen = 50;

    @Override
    protected Class<?> findClass(String name) {
        ClassLoader classLoader = Utils.getClassLoader(MyClassLoader.class);
        InputStream inputStream = classLoader.getResourceAsStream(name);
        List<byte[]> byteArray = new ArrayList<byte[]>();
        byte[] bytes;
        int len;
        int totalLen = 0;
        try {
            while ((len = inputStream.read((bytes = new byte[ByteLen]))) != -1) {
                byteArray.add(bytes);
                totalLen += len;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return defineClass(decode(byteArray, totalLen), 0, totalLen);
    }

    public byte[] decode(List<byte[]> list, int totalLen) {
        byte[] bytes = new byte[totalLen];
        for (int i=0;i<totalLen;i++) {
            bytes[i] = (byte) (255 - list.get(i/ByteLen)[i%ByteLen]);
        }
        return bytes;
    }

}
