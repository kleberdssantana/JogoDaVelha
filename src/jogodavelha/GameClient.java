package jogodavelha;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GameClient extends JFrame {

    public static String textoParaEnviar;
    public static PrintWriter escritor;
    public static Socket socket;
    public static String textoRecebido;
    public static Scanner leitor;
   
    public GameClient() {
        super();
        configuraRede();
    }

    public static String getTextoParaEnviar() {
        return textoParaEnviar;
    }

    public static void setTextoParaEnviar(String textoParaEnviar) {
        GameClient.textoParaEnviar = textoParaEnviar;
    }

    public static String getTextoRecebido() {
        return textoRecebido;
    }

    public static void setTextoRecebido(String textoRecebido) {
        GameClient.textoRecebido = textoRecebido;
    }
    
    public static class EnviarListiner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            escritor.println(textoParaEnviar);
        }
    }

    public static class EscutaServidor implements Runnable {

        String texto;

        @Override
        public void run() {
            try {
                while ((texto = leitor.nextLine()) != null) {
                    setTextoRecebido(texto);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void configuraRede() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            escritor = new PrintWriter(socket.getOutputStream());
            leitor = new Scanner(socket.getInputStream());
            new Thread(new EscutaServidor()).start();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        new GameClient();
    }

}
