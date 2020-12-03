package NetWorkPrograming.UDP.Chat;

public class Meinv {
    public static void main(String[] args) {
        Thread sender = new Thread(new Sender(8888, "localhost", 9999));
        sender.start();

        Thread receiver = new Thread(new Receiver(6666));
        receiver.start();
    }
}
