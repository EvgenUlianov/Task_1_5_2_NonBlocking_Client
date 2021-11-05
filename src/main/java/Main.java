import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Задача 2. Долой пробелы (client)");
        //C:\Windows\System32\drivers\etc\hosts
        String host = "netology.homework";//"localhost";//"127.0.0.1";
        int port = 9533;

        // Определяем сокет сервера
        InetSocketAddress socketAddress = new InetSocketAddress(host, port);
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            //  подключаемся к серверу
            socketChannel.connect(socketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Получаем входящий и исходящий потоки информации
        try (Scanner scanner = new Scanner(System.in)) {
            //  Определяем буфер для получения данных
            final ByteBuffer buffer = ByteBuffer.allocate(2 << 10);

            String text;
            while (true) {
                System.out.println("Введите long-число для сервера (либо \"end\" для выхода):");
                text = scanner.nextLine();
                if ("end".equals(text))
                    break;

                socketChannel.write(ByteBuffer.wrap(text.getBytes(StandardCharsets.UTF_8)));

                final int timeOut = 5_000;
                BasicFunctions.sleep(timeOut);

                int bytesCount = socketChannel.read(buffer);
                String result = new String(buffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim();
                System.out.println(result);
                buffer.clear();



            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
