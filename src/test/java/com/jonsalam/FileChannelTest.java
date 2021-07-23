package com.jonsalam;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author jonsalam
 * @date 2021/07/23
 */
public class FileChannelTest {
    @Test
    public void givenFile_whenReadWithFileChannelUsingRandomAccessFile_thenCorrect() throws IOException {
        try (RandomAccessFile reader = new RandomAccessFile("src/test/resources/hello.text", "r");
             FileChannel channel = reader.getChannel();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            int bufferSize = 1024;
            if (bufferSize > channel.size()) {
                bufferSize = (int) channel.size();
            }
            ByteBuffer buff = ByteBuffer.allocate(bufferSize);

            while (channel.read(buff) > 0) {
                out.write(buff.array(), 0, buff.position());
                buff.clear();
            }

            String fileContent = new String(out.toByteArray(), StandardCharsets.UTF_8);

            Assertions.assertThat(fileContent).isEqualTo("hello world");
        }
    }
}
