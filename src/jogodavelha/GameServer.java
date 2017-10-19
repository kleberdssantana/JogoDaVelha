package jogodavelha;

import java.util.List;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServer {

    List<PrintWriter> escritores = new ArrayList<>();

    public GameServer() {
        try {
            ServerSocket server = new ServerSocket(5000);
            while (true) {
                Socket socket = server.accept();
                new Thread(new EscutaCliente(socket)).start();
                PrintWriter p = new PrintWriter(socket.getOutputStream());
                escritores.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void encaminhaParaTodos(String texto) {
        for (PrintWriter w : escritores) {
            try {
                w.println(texto);
                w.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    private class EscutaCliente implements Runnable {

        Scanner leitor;

        public EscutaCliente(Socket socket) {
            try {
                leitor = new Scanner(socket.getInputStream());
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        @Override
        public void run() {
            String texto;
            while ((texto = leitor.nextLine()) != null) {
                System.out.println(texto);
                encaminhaParaTodos(texto);
            }
        }
    }

    public static void main(String[] args) {
        new GameServer();
    }

}
