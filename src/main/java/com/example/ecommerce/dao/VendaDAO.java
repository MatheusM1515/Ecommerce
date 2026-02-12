package com.example.ecommerce.dao;

import com.example.ecommerce.model.Cliente;
import com.example.ecommerce.model.Venda;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    private Connection connection;

    public VendaDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void salvar(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (data, fk_id_cliente, valor_total) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(venda.getData()));
            stmt.setInt(2, venda.getCliente().getId());
            stmt.setDouble(3, venda.getValorTotal());
            stmt.executeUpdate();
        }
    }

    public List<Venda> listarTodos() throws SQLException {
        List<Venda> vendas = new ArrayList<>();

        String sql = "SELECT * FROM vendas";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Venda v = new Venda();

                v.setId(rs.getInt("id"));
                v.setData(rs.getTimestamp("data").toLocalDateTime());
                v.setValorTotal(rs.getDouble("valor_total"));

                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("fk_id_cliente"));
                v.setCliente(cliente);

                vendas.add(v);
            }
        }

        return vendas;
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM vendas WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public void atualizar(Venda venda) throws SQLException {
        String sql = "UPDATE vendas SET data = ?, fk_id_cliente = ?, valor_total = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(venda.getData()));
            stmt.setInt(2, venda.getCliente().getId());
            stmt.setDouble(3, venda.getValorTotal());
            stmt.setInt(4, venda.getId());
            stmt.executeUpdate();
        }
    }
}
