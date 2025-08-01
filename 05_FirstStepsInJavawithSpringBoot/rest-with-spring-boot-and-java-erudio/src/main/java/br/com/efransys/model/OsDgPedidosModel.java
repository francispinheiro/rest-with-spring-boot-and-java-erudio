package br.com.efransys.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "os_dg_pedidos")
public class OsDgPedidosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_pedido", nullable = false, unique = true)
    private String numeroPedido;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "status")
    private String status;

    @Column(name = "valor_total")
    private Double valorTotal;

    // Default constructor
    public OsDgPedidosModel() {
    }

    // Constructor with parameters
    public OsDgPedidosModel(String numeroPedido, String descricao, String status, Double valorTotal) {
        this.numeroPedido = numeroPedido;
        this.descricao = descricao;
        this.status = status;
        this.valorTotal = valorTotal;
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "OsDgPedidosModel{" +
                "id=" + id +
                ", numeroPedido='" + numeroPedido + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", status='" + status + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}