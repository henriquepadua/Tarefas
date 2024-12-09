package Classes;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import aed3.Registro;
import java.util.*;

public class lzw {
    // Compressão usando LZW
    public static List<Integer> compress(byte[] data) {
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put("" + (char) i, i);
        }
        int dictSize = 256;

        String current = "";
        List<Integer> compressed = new ArrayList<>();

        for (byte b : data) {
            char ch = (char) (b & 0xFF);
            String next = current + ch;

            if (dictionary.containsKey(next)) {
                current = next;
            } else {
                compressed.add(dictionary.get(current));
                dictionary.put(next, dictSize++);
                current = "" + ch;
            }
        }

        if (!current.isEmpty()) {
            compressed.add(dictionary.get(current));
        }
        return compressed;
    }

    // Descompressão usando LZW
    public static byte[] decompress(List<Integer> compressed) {
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, "" + (char) i);
        }
        int dictSize = 256;

        String current = dictionary.get(compressed.remove(0));
        StringBuilder result = new StringBuilder(current);

        for (int code : compressed) {
            String entry;
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code);
            } else if (code == dictSize) {
                entry = current + current.charAt(0);
            } else {
                throw new IllegalArgumentException("Código inválido na descompressão!");
            }

            result.append(entry);
            dictionary.put(dictSize++, current + entry.charAt(0));
            current = entry;
        }

        return result.toString().getBytes();
    }
}