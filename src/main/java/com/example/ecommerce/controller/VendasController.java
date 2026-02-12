package com.example.ecommerce.controller;

import com.example.ecommerce.dao.ClienteDAO;
import com.example.ecommerce.dao.ProdutoDAO;
import com.example.ecommerce.dao.VendaDAO;
import com.example.ecommerce.dao.ItemVendaDAO;
import com.example.ecommerce.model.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class VendasController {

    @FXML
    private ComboBox<Cliente> comboCliente;

    @FXML
    private ComboBox<Produto> comboProduto;

    @FXML
    private Spinner<Integer> spinnerQuantidade;

    @FXML
    private TableView<ItemVenda> tabelaItens;

    @FXML
    private TableColumn<ItemVenda, String> colProduto;

    @FXML
    private TableColumn<ItemVenda, Integer> colQuantidade;

    @FXML
    private TableColumn<ItemVenda, Double> colSubtotal;

    @FXML
    private Label labelTotal;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ItemVendaDAO itemVendaDAO = new ItemVendaDAO();

    private Venda vendaAtual = new Venda();

    @FXML
    public void initialize() {

        spinnerQuantidade.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1)
        );

        colProduto.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getProduto().getNome()
                )
        );

        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        carregarClientes();
        carregarProdutos();
    }

    private void carregarClientes() {
        try {
            comboCliente.getItems().setAll(clienteDAO.listarTodos());
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao carregar clientes");
        }
    }

    private void carregarProdutos() {
        try {
            comboProduto.getItems().setAll(produtoDAO.listarTodos());
        } catch (Exception e) {
            exibirAlerta("Erro", "Erro ao carregar produtos");
        }
    }

    @FXML
    public void adicionarItem() {

        Produto produto = comboProduto.getValue();
        int quantidade = spinnerQuantidade.getValue();

        if (produto == null) {
            exibirAlerta("Erro", "Selecione um produto");
            return;
        }

        ItemVenda item = new ItemVenda(produto, quantidade);

        vendaAtual.adicionarItem(item);

        tabelaItens.getItems().add(item);

        labelTotal.setText("Total: R$ " + vendaAtual.getValorTotal());
    }

    @FXML
    public void finalizarVenda() {

        try {

            Cliente cliente = comboCliente.getValue();

            if (cliente == null) {
                exibirAlerta("Erro", "Selecione um cliente");
                return;
            }

            if (vendaAtual.getItens().isEmpty()) {
                exibirAlerta("Erro", "Adicione pelo menos um item");
                return;
            }

            vendaAtual.setCliente(cliente);

            vendaDAO.salvar(vendaAtual); // precisa retornar ID

            itemVendaDAO.salvarItens(vendaAtual.getId(), vendaAtual.getCliente());

            limparVenda();

        } catch (Exception e) {
            exibirAlerta("Erro", e.getMessage());
        }
    }

    private void limparVenda() {
        vendaAtual = new Venda();
        tabelaItens.getItems().clear();
        comboCliente.setValue(null);
        comboProduto.setValue(null);
        labelTotal.setText("Total: R$ 0.00");
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensagem);
        alert.show();
    }
}
