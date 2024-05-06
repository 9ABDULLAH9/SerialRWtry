import com.fazecast.jSerialComm.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Seri portları listele
        SerialPort[] ports = SerialPort.getCommPorts();
        System.out.println("Bulunan seri portlar:");
        for (int i = 0; i < ports.length; i++) {
            System.out.println((i + 1) + ". " + ports[i].getSystemPortName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Hangi port numarası (1-" + ports.length + "):");
        int portNumber = scanner.nextInt();

        if (portNumber < 1 || portNumber > ports.length) {
            System.err.println("Geçersiz port numarası.");
            return;
        }

        // Seçilen portun dizideki indeksi
        int selectedIndex = portNumber - 1;
        SerialPort selectedPort = ports[selectedIndex];
        System.out.println("Seçilen port numarası:" + portNumber);

        selectedPort.openPort();

        // Seri porttan veri okumak için bir dinleyici oluştur
        selectedPort.addDataListener(new SerialPortDataListener() {
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[selectedPort.bytesAvailable()];
                int numRead = selectedPort.readBytes(newData, newData.length);
                System.out.println("Okunan veri: " + new String(newData));
            }
        });

//deneme
        // Programın sonlanmasını engellemek için beklet
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
