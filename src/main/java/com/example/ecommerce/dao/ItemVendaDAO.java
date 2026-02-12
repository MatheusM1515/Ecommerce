package com.example.ecommerce.dao;

import com.example.ecommerce.model.Cliente;
import com.example.ecommerce.model.ItemVenda;
import com.example.ecommerce.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemVendaDAO {

    private Connection connection;

    public ItemVendaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void salvar(ItemVenda item) throws SQLException {

        String sql =  "INSERT INTO item_venda (venda_id, produto_id, quantidade, valor_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, item.getProduto().getId());
            stmt.setInt(2, item.getQuantidade());
            stmt.setDouble(3, item.getSubtotal());

            stmt.executeUpdate();
        }
    }


    public void salvarItensDaVenda(int vendaId, java.util.List<ItemVenda> itens) throws SQLException {

        String sql = "INSERT INTO item_venda (venda_id, produto_id, quantidade, valor_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (ItemVenda item : itens) {

                stmt.setInt(1, vendaId);
                stmt.setInt(2, item.getProduto().getId());
                stmt.setInt(3, item.getQuantidade());
                stmt.setDouble(4, item.getSubtotal());

                stmt.addBatch();
            }

            stmt.executeBatch();
        }
    }

    public void salvarItens(int id, Cliente cliente) {
    }
}
