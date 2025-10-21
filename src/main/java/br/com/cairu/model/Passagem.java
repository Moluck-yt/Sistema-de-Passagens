package br.com.cairu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "passagens")
public class Passagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPassagem;

    @NotNull(message = "Veículo é obrigatório")
    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;

    @NotNull(message = "Número da poltrona é obrigatório")
    @Min(value = 1, message = "Número da poltrona deve ser maior que zero")
    @Column(nullable = false)
    private Integer poltrona;

    @NotNull(message = "Data de saída é obrigatória")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataSaida;

    @NotBlank(message = "Hora de saída é obrigatória")
    @Column(nullable = false, length = 10)
    private String horaSaida;

    @NotNull(message = "Cidade de origem é obrigatória")
    @ManyToOne
    @JoinColumn(name = "cidadeOrigem_id", nullable = false)
    private Cidade cidadeOrigem;

    @NotNull(message = "Cidade de destino é obrigatória")
    @ManyToOne
    @JoinColumn(name = "cidadeDestino_id", nullable = false)
    private Cidade cidadeDestino;

    @NotNull(message = "Valor da passagem é obrigatório")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPassagem;

    @Column(nullable = false)
    private boolean vendida = false;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dataVenda")
    private Date dataVenda;

    // Construtores
    public Passagem() {
    }

    public Passagem(Veiculo veiculo, Integer poltrona, Date dataSaida, String horaSaida,
                   Cidade cidadeOrigem, Cidade cidadeDestino, BigDecimal valorPassagem) {
        this.veiculo = veiculo;
        this.poltrona = poltrona;
        this.dataSaida = dataSaida;
        this.horaSaida = horaSaida;
        this.cidadeOrigem = cidadeOrigem;
        this.cidadeDestino = cidadeDestino;
        this.valorPassagem = valorPassagem;
    }

    // Getters e Setters
    public Long getIdPassagem() {
        return idPassagem;
    }

    public void setIdPassagem(Long idPassagem) {
        this.idPassagem = idPassagem;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getPoltrona() {
        return poltrona;
    }

    public void setPoltrona(Integer poltrona) {
        this.poltrona = poltrona;
    }

    public Date getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Cidade getCidadeOrigem() {
        return cidadeOrigem;
    }

    public void setCidadeOrigem(Cidade cidadeOrigem) {
        this.cidadeOrigem = cidadeOrigem;
    }

    public Cidade getCidadeDestino() {
        return cidadeDestino;
    }

    public void setCidadeDestino(Cidade cidadeDestino) {
        this.cidadeDestino = cidadeDestino;
    }

    public BigDecimal getValorPassagem() {
        return valorPassagem;
    }

    public void setValorPassagem(BigDecimal valorPassagem) {
        this.valorPassagem = valorPassagem;
    }

    public boolean isVendida() {
        return vendida;
    }

    public void setVendida(boolean vendida) {
        this.vendida = vendida;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getRoteiro() {
        if (cidadeOrigem != null && cidadeDestino != null) {
            return cidadeOrigem.getNomeCidade() + " → " + cidadeDestino.getNomeCidade();
        }
        return "";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPassagem == null) ? 0 : idPassagem.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Passagem other = (Passagem) obj;
        if (idPassagem == null) {
            if (other.idPassagem != null)
                return false;
        } else if (!idPassagem.equals(other.idPassagem))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Passagem [" + idPassagem + "] " + getRoteiro() + " - Poltrona " + poltrona;
    }
}
