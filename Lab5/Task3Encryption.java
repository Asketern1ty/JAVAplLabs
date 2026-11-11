import java.io.*;
import java.util.Scanner;

class EncryptWriter extends FilterWriter {
    private int key;

    public EncryptWriter(Writer out, char keyChar) {
        super(out);
        this.key = (int) keyChar;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + key);
    }
}

class DecryptReader extends FilterReader {
    private int key;

    public DecryptReader(Reader in, char keyChar) {
        super(in);
        this.key = (int) keyChar;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        if (c == -1) return -1;
        return c - key;
    }
}

public class Task3Encryption {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the path to the file to save the encrypted text: ");
        String filePath = scanner.nextLine();
        
        System.out.print("Enter the key character: ");
        char keyChar = scanner.nextLine().charAt(0);
        
        String originalText = "Secret message for encryption";

        try (EncryptWriter writer = new EncryptWriter(new FileWriter(filePath), keyChar)) {
            for (char c : originalText.toCharArray()) {
                writer.write(c);
            }
            System.out.println("Text successfully encrypted and saved.");
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }

        try (DecryptReader reader = new DecryptReader(new FileReader(filePath), keyChar)) {
            System.out.print("Decrypted text: ");
            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}