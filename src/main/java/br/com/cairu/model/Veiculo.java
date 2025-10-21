package br.com.cairu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "veiculos")
public class Veiculo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Número do veículo é obrigatório")
    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @NotBlank(message = "Placa é obrigatória")
    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @NotBlank(message = "Nome do motorista é obrigatório")
    @Column(nullable = false, length = 100)
    private String motorista;

    @NotBlank(message = "Modelo é obrigatório")
    @Column(nullable = false, length = 50)
    private String modelo;

    @NotNull(message = "Data de compra é obrigatória")
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dataCompra;

    @NotNull(message = "Quantidade de poltronas é obrigatória")
    @Min(value = 1, message = "Quantidade de poltronas deve ser maior que zero")
    @Column(nullable = false)
    private Integer qtdPoltronas;

    // Construtores
    public Veiculo() {
    }

    public Veiculo(String numero, String placa, String motorista, String modelo, Date dataCompra, Integer qtdPoltronas) {
        this.numero = numero;
        this.placa = placa;
        this.motorista = motorista;
        this.modelo = modelo;
        this.dataCompra = dataCompra;
        this.qtdPoltronas = qtdPoltronas;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    public Integer getQtdPoltronas() {
        return qtdPoltronas;
    }

    public void setQtdPoltronas(Integer qtdPoltronas) {
        this.qtdPoltronas = qtdPoltronas;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        Veiculo other = (Veiculo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return numero + " - " + modelo + " (" + placa + ")";
    }
}
