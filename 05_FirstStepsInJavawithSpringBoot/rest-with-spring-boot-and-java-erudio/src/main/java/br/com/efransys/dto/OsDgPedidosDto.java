package br.com.efransys.dto;

import java.time.LocalDateTime;

/**
 * DTO (Data Transfer Object) for OsDgPedidos
 * This class is used to transfer data between layers without exposing
 * the internal structure of the domain model (OsDgPedidosModel)
 */
public class OsDgPedidosDto {

    private Long id;
    private String numeroPedido;
    private String descricao;
    private LocalDateTime dataCriacao;
    private String status;
    private Double valorTotal;

    // Default constructor
    public OsDgPedidosDto() {
    }

    // Constructor with parameters (excluding id for creation)
    public OsDgPedidosDto(String numeroPedido, String descricao, String status, Double valorTotal) {
        this.numeroPedido = numeroPedido;
        this.descricao = descricao;
        this.status = status;
        this.valorTotal = valorTotal;
        this.dataCriacao = LocalDateTime.now();
    }

    // Full constructor
    public OsDgPedidosDto(Long id, String numeroPedido, String descricao, LocalDateTime dataCriacao, String status, Double valorTotal) {
        this.id = id;
        this.numeroPedido = numeroPedido;
        this.descricao = descricao;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.valorTotal = valorTotal;
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
        return "OsDgPedidosDto{" +
                "id=" + id +
                ", numeroPedido='" + numeroPedido + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataCriacao=" + dataCriacao +
                ", status='" + status + '\'' +
                ", valorTotal=" + valorTotal +
                '}';
    }
}