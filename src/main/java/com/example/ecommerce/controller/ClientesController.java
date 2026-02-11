package com.example.ecommerce.controller;

import com.example.ecommerce.dao.ClienteDAO;
import com.example.ecommerce.model.Cliente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;


public class ClientesController {

    @FXML
    private TextField textNome;

    @FXML
    private TextField textCpf;

    @FXML
    private TextField textTelefone;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textEndereco;

    @FXML
    private TableView<Cliente> tabelaClientes;

    @FXML
    private TableColumn<Cliente, Integer> colId;

    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colCpf;

    @FXML
    private TableColumn<Cliente, String> colTelefone;

    @FXML
    private TableColumn<Cliente, String> colEmail;

    @FXML
    private TableColumn<Cliente, String> colEndereco;


    private final ClienteDAO dao = new ClienteDAO();
    private Cliente clienteSelecionado;


    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        atualizarTabela();
    }

    private void atualizarTabela() {
        try {
            tabelaClientes.getItems().setAll(dao.listarTodos());
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro", "Erro ao carregar clientes");
        }
    }


    @FXML
    public void salvarCliente() {
        try {
            String nome = textNome.getText();
            String cpf = textCpf.getText();
            String telefone = textTelefone.getText();
            String email = textEmail.getText();
            String endereco = textEndereco.getText();

            if (clienteSelecionado == null) {
                Cliente novo = new Cliente(nome, cpf, telefone, email, endereco);
                dao.salvar(novo);
            } else {
                clienteSelecionado.setNome(nome);
                clienteSelecionado.setCpf(cpf);
                clienteSelecionado.setTelefone(telefone);
                clienteSelecionado.setEmail(email);
                clienteSelecionado.setEndereco(endereco);
                dao.atualizar(clienteSelecionado);
            }

            atualizarTabela();
            limparCliente();

        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }


    @FXML
    public void excluirCliente() {
        if (clienteSelecionado != null) {
            try {
                dao.deletar(clienteSelecionado.getId());
                atualizarTabela();
                limparCliente();
            } catch (Exception e) {
                exibirAlerta("Erro", e.getMessage());
            }
        }
    }

    @FXML
    public void selecionarItem() {
        clienteSelecionado = tabelaClientes
                .getSelectionModel()
                .getSelectedItem();

        if (clienteSelecionado != null) {
            textNome.setText(clienteSelecionado.getNome());
            textCpf.setText(clienteSelecionado.getCpf());
            textTelefone.setText(clienteSelecionado.getTelefone());
            textEmail.setText(clienteSelecionado.getEmail());
            textEndereco.setText(clienteSelecionado.getEndereco());
        }
    }

    @FXML
    public void limparCliente() {
        textNome.clear();
        textCpf.clear();
        textTelefone.clear();
        textEmail.clear();
        textEndereco.clear();
        clienteSelecionado = null;
        tabelaClientes.getSelectionModel().clearSelection();

    }

    @FXML
    private void voltarCliente() {
        System.out.println("Botão voltar clicado");
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }


}

