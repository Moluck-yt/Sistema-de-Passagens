package br.com.cairu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "cidades")
public class Cidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da cidade é obrigatório")
    @Column(nullable = false, length = 100)
    private String nomeCidade;

    @NotBlank(message = "ID IBGE é obrigatório")
    @Column(nullable = false, unique = true, length = 10)
    private String idCidade; // Sigla IBGE

    @NotBlank(message = "UF é obrigatório")
    @Column(nullable = false, length = 2)
    private String uf;

    // Construtores
    public Cidade() {
    }

    public Cidade(String nomeCidade, String idCidade, String uf) {
        this.nomeCidade = nomeCidade;
        this.idCidade = idCidade;
        this.uf = uf;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(String idCidade) {
        this.idCidade = idCidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
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
        Cidade other = (Cidade) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return nomeCidade + " - " + uf;
    }
}
