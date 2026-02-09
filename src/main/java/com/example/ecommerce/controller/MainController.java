package com.example.ecommerce.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class MainController {

    @FXML
    private BorderPane root;

    @FXML
    public void abrirHome() {
        carregarTela("Home.fxml");
    }

    @FXML
    public void abrirProdutos() {
        carregarTela("Produtos.fxml");
    }

    @FXML
    public void abrirClientes() {
        carregarTela("Clientes.fxml");
    }

    @FXML
    private void abrirAdicionarVenda(ActionEvent event) { carregarTela("NovaVenda.fxml"); }

    @FXML
    public void abrirRelatorio() {carregarTela("RelatorioDeVendas.fxml");}

    public void abrirAjuda(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sobre o sistema");
        alert.setHeaderText("Informações do sistema");
        alert.setContentText("Nome: Sistema de Reservas\n" + "Versão: 2.0\n" + "Desenvolvedor: Matheus Araújo\n" + "Ano: 2026");
        alert.showAndWait();
    }

    @FXML
    private void carregarTela(String fxml) {
        try {
            root.setCenter(
                    FXMLLoader.load(getClass().getResource("/fxml/" + fxml))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
