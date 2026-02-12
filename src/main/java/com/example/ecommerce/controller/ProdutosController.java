package com.example.ecommerce.controller;

import com.example.ecommerce.dao.ProdutoDAO;
import com.example.ecommerce.model.Produto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdutosController {


    @FXML
    private TextField textNome;

    @FXML
    private TextField textPreco;

    @FXML
    private TextField textQuantidade;

    @FXML
    private TableView<Produto> tabelaProdutos;

    @FXML
    private TableColumn<Produto, Integer> colId;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TableColumn<Produto, Integer> colQuantidade;



    private final ProdutoDAO dao = new ProdutoDAO();
    private Produto produtoSelecionado;


    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        atualizarTabela();

    }


    private void atualizarTabela() {
        try {
            tabelaProdutos.setItems(
                    FXCollections.observableArrayList(dao.listarTodos())
            );
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta("Erro", "Erro ao carregar produtos");
        }
    }


    @FXML
    public void salvarProduto() {
        try {
            double preco = Double.parseDouble(textPreco.getText());
            int quantidade = Integer.parseInt(textQuantidade.getText());

            if (produtoSelecionado == null) {
                Produto novo = new Produto(textNome.getText(), preco, quantidade);
                dao.salvar(novo);
            } else {
                produtoSelecionado.setNome(textNome.getText());
                produtoSelecionado.setPreco(preco);
                produtoSelecionado.setQuantidade(quantidade);
                dao.atualizar(produtoSelecionado);
            }

            atualizarTabela();
            limparCampos();

        } catch (NumberFormatException e) {
            exibirAlerta("Erro", "Preço inválido");
        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }


    @FXML
    public void excluirProduto() {
        if (produtoSelecionado != null) {
            try {
                dao.deletar(produtoSelecionado.getId());
                atualizarTabela();
                limparCampos();
            } catch (Exception e) {
                exibirAlerta("Erro", e.getMessage());
            }
        }
    }


    @FXML
    public void selecionarItem() {
        produtoSelecionado = tabelaProdutos
                .getSelectionModel()
                .getSelectedItem();

        if (produtoSelecionado != null) {
            textNome.setText(produtoSelecionado.getNome());
            textPreco.setText(
                    String.valueOf(produtoSelecionado.getPreco())
            );
        }
    }


    @FXML
    public void limparCampos() {
        textNome.clear();
        textPreco.clear();
        produtoSelecionado = null;
        tabelaProdutos.getSelectionModel().clearSelection();
    }

    @FXML
    private void voltarProduto() {
        System.out.println("Botão voltar clicado");
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }


}