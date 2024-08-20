/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectaps;

import com.mycompany.projectaps.Mensagens.Action;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ss
 *
 * @author Joao Vitor
 */
public class Instructions {

    private ServerSocket server;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlines = new HashMap<>();
    private InputStream inpStream;

    public Instructions() {
        try {
            server = new ServerSocket(0712);
            System.out.println("Servidor iniciado!");
            while (true) {
                socket = server.accept();
                inpStream = socket.getInputStream();
                System.out.println("Cliente conectado!" + socket.getLocalAddress());

                new Thread(new ListenerSocket(socket)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private class ListenerSocket implements Runnable {
        private ObjectOutputStream output;
        private ObjectInputStream input;

        public ListenerSocket(Socket socket) {
            try {
                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException ex) {
                Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            Mensagens msg = null;
            try {
                while ((msg = (Mensagens) input.readObject()) != null) {
                    Action action = msg.getAction();

                    if (action.equals(Action.conectar)) {
                        conectar(msg, output);
                        mapOnlines.put(msg.getNome(), output);
                        enviaConectados();
                    } else if (action.equals(Action.desconectar)) {
                        desconectar(msg);
                        enviaConectados();
                        return;
                    } else if (action.equals(Action.enviaUm)) {
                        enviaUm(msg);
                    } else if (action.equals(Action.envia)) {
                        enviaTodos(msg);
                    } else if (action.equals(Action.enviaArq)) {
                        recebeImg(msg);
                    }
                }
            } catch (IOException ex) {
                desconectar(msg);
                enviaConectados();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    private void receiveFile(Mensagens msg, ObjectInputStream input) {
        try {
            // Recebe o nome do arquivo e seu tamanho
            String fileName = (String) input.readObject();
            long fileSize = input.readLong();

            String saveDirectory = "D:/aps/apss";

            File file = new File(saveDirectory + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            bos.close();
            fos.close();

            Mensagens response = new Mensagens();
            response.setAction(Action.enviaArq);
            response.setText("Arquivo recebido com sucesso: " + fileName);
           // output.writeObject(response);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void conectar(Mensagens msg, ObjectOutputStream output) {
        enviaUm2(msg, output);
    }

    private void desconectar(Mensagens msg) {
        mapOnlines.remove(msg.getNome());
        msg.setText("saiu");
        msg.setAction(Action.enviaUm);
        enviaTodos(msg);
        System.out.println("Cliente " + msg.getNome() + " se desconectou!");
    }

    private void enviaUm(Mensagens msg) {
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (kv.getKey().equals(msg.getnReservado())) {
                try {
                    kv.getValue().writeObject(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void enviaUm2(Mensagens msg, ObjectOutputStream output) {
        try {
            output.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviaTodos(Mensagens msg) {
        // ACRESCENTAR DEPOIS: DEIXAR MENSAGEM DE QUEM ENVIOU NA DIREITA; 
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(msg.getNome())) {
                msg.setAction(Action.enviaUm);
                try {
                    kv.getValue().writeObject(msg);
                } catch (IOException ex) {
                    Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void enviaConectados() {
        Set<String> snomes = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            snomes.add(kv.getKey());
        }

        Mensagens message = new Mensagens();
        message.setAction(Action.usuariosOn);
        message.setSetOnlines(snomes);
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setNome(kv.getKey());
            try {
                kv.getValue().writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(Instructions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    public void recebeImg(Mensagens msg) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inpStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            byte[] imgData = buffer.toByteArray();

            FileOutputStream outStream = new FileOutputStream("imagemrecebida.jpg");
            outStream.write(imgData);
            outStream.close();

            inpStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
