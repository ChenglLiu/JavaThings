package NetWorkPrograming.UDP.Chat;

public class Liangzai {
    public static void main(String[] args) {
        Thread sender = new Thread(new Sender(6666, "localhost", 7777));
        sender.start();

        Thread receiver = new Thread(new Receiver(8888));
        receiver.start();
    }
}
