package com.example.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {

    private int id;
    private Cliente cliente;
    private LocalDateTime data;
    private double valorTotal;

    private List<ItemVenda> itens = new ArrayList<>();

    public Venda() {
        this.data = LocalDateTime.now();
    }

    public Venda(Cliente cliente) {
        this.cliente = cliente;
        this.data = LocalDateTime.now();
    }


    public void adicionarItem(ItemVenda item) {
        itens.add(item);
        calcularTotal();
    }

    private void calcularTotal() {
        valorTotal = 0;
        for (ItemVenda item : itens) {
            valorTotal += item.getSubtotal();
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public CharSequence getItens() {
        return itens.toString();
    }
}
