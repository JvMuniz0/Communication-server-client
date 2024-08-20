/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectaps.servico;

import com.mycompany.projectaps.newpackage.telaMain;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Joao Vitor
 */
public class LoginDAO {

    // CADASTRAR CLIENTE NO BANCO DE DADOS
    public void cadastrarUser(String nome, String email, String senha) throws SQLException {
        Connection conexao = new Conexao().getConnection();
        String sql = "insert into login (nome, email, senha) values ('" + nome + "', '" + email + "', '" + senha + "')";
        // Mandar isso para o servidor
        System.out.println("Usuário cadastrado: " + sql);
        PreparedStatement statment = conexao.prepareStatement(sql);
        statment.execute();
        conexao.close();
    }

    public void loginUser(String nome, String senha) throws SQLException {
        Connection conexao = new Conexao().getConnection();
        //telaMain principal = new telaMain();
        String sql = "select nome, senha from login where nome = '"+nome+"' and senha ='"+senha+"'";
        // Mandar isso para o servidor
        PreparedStatement statment = conexao.prepareStatement(sql);
        //ResultSet rs = statment.executeQuery();  
        
        conexao.close();
    }

}
