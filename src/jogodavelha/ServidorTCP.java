package jogodavelha;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServidorTCP {

    public static void main(String args[]) throws Exception {
        String textoDoCliente;
        String textoModificado;

        //Socket de escuta
        ServerSocket welcomeSocket = new ServerSocket(5000); //escutando...

        while (true) {
            //Socket de conexao com o cliente
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Cliente conectou..." + connectionSocket.getInetAddress());

            //Objeto de recebimento de mensagens do cliente
            Scanner inFromClient = new Scanner(connectionSocket.getInputStream());

            //Objeto de envio de mensagens p/ o cliente
            PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true);

            //recebendo msg do cliente
            textoDoCliente = inFromClient.nextLine();
            System.out.println("Cliente enviou: " + textoDoCliente);

            //Modificando os caract�res para mai�sculo
            textoModificado = textoDoCliente.toUpperCase();

            //respondendo msg p/ o cliente
            outToClient.println(textoModificado);
            System.out.println("Cliente fechou!");

        }
    }
}
