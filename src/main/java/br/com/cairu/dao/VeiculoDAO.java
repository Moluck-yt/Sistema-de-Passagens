package br.com.cairu.dao;

import br.com.cairu.model.Veiculo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class VeiculoDAO extends GenericDAO<Veiculo, Long> {

    public VeiculoDAO() {
        super(Veiculo.class);
    }

    public Veiculo buscarPorPlaca(String placa) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Veiculo> query = em.createQuery(
                "SELECT v FROM Veiculo v WHERE v.placa = :placa", Veiculo.class);
            query.setParameter("placa", placa);
            List<Veiculo> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public Veiculo buscarPorNumero(String numero) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Veiculo> query = em.createQuery(
                "SELECT v FROM Veiculo v WHERE v.numero = :numero", Veiculo.class);
            query.setParameter("numero", numero);
            List<Veiculo> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Veiculo> buscarPorMotorista(String motorista) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Veiculo> query = em.createQuery(
                "SELECT v FROM Veiculo v WHERE LOWER(v.motorista) LIKE LOWER(:motorista) ORDER BY v.motorista",
                Veiculo.class);
            query.setParameter("motorista", "%" + motorista + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Verifica se um veiculo esta disponivel em uma data especifica
     * (nao pode estar alocado para 2 roteiros na mesma data)
     */
    public boolean isVeiculoDisponivelNaData(Long veiculoId, Date data) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Passagem p " +
                         "WHERE p.veiculo.id = :veiculoId AND p.dataSaida = :data";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("veiculoId", veiculoId);
            query.setParameter("data", data);
            Long count = query.getSingleResult();
            return count == 0;
        } finally {
            em.close();
        }
    }

    /**
     * Verifica se o veiculo possui passagens cadastradas
     */
    public boolean temPassagens(Long veiculoId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Passagem p WHERE p.veiculo.id = :veiculoId";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("veiculoId", veiculoId);
            Long count = query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
