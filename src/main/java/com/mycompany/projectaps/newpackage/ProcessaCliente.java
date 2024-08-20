/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectaps.newpackage;

import com.mycompany.projectaps.Mensagens;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joao Vitor
 */
public class ProcessaCliente {

    //public Mensagens mensagem;
    private Socket socket;
    private ObjectOutputStream output;

    public Socket conectar() {
        try {
            String ipCliente = InetAddress.getLocalHost().getHostAddress();
            this.socket = new Socket(ipCliente, 0712);
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ProcessaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return socket;

    }

    public void enviaMensagem(Mensagens msg) {
        try {
            output.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(ProcessaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     private void sendFile(File file) throws IOException {
        byte[] buffer = new byte[4096];
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = socket.getOutputStream()) {
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

        /*try {
            // Cria uma mensagem para informar sobre o envio do arquivo
            Mensagens msg = new Mensagens();
            msg.setNome(nome);
            msg.setnReservado(destinatario);
            msg.setAction(Mensagens.Action.enviaArquivo);
            msg.setText(arquivo.getName());

            // Envia a mensagem inicial
            output.writeObject(msg);

            // Envia o arquivo em si
            FileInputStream fileInput = new FileInputStream(arquivo);
            OutputStream socketOut = socket.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInput.read(buffer)) != -1) {
                socketOut.write(buffer, 0, bytesRead);
            }
            fileInput.close();

            // Indica o fim da transmissão do arquivo
            socketOut.flush();
            System.out.println("Arquivo enviado: " + arquivo.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(ProcessaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
