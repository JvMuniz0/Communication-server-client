/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectaps.servico;

import com.mycompany.projectaps.newpackage.telaCadastro;
import com.mycompany.projectaps.newpackage.telaLogin;
import com.mycompany.projectaps.newpackage.telaMain;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Joao Vitor
 */
public class LoginControl {
    
    public void cadastroUser(telaCadastro view) throws SQLException {
        Connection conexao = new Conexao().getConnection();
        LoginDAO cadastro = new LoginDAO();
        
        cadastro.cadastrarUser(view.getTextNome().getText(), view.getTextEmail().getText(), view.getTextSenha().getText());
    }
    
    public void loginUser(telaMain view) throws SQLException {
        Connection conexao = new Conexao().getConnection();
        LoginDAO login = new LoginDAO();
        login.loginUser(view.getTxtUser().getText(), view.getTxtSenha().getText());
    }
    
}
