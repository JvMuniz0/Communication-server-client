/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectaps;
// SERVIDOR

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Joao Vitor
 */
public class Mensagens implements Serializable {

    private String text;
    private File file;
    private String nome;
    private String nReservado;
    private Set<String> setOnlines = new HashSet<>();
    private Action action;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getnReservado() {
        return nReservado;
    }

    public void setnReservado(String nReservado) {
        this.nReservado = nReservado;
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public enum Action {
        desconectar, conectar, enviaUm, envia, usuariosOn, enviaArq
    }
}
