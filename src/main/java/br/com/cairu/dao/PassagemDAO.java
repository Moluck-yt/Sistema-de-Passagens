package br.com.cairu.dao;

import br.com.cairu.model.Passagem;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PassagemDAO extends GenericDAO<Passagem, Long> {

    public PassagemDAO() {
        super(Passagem.class);
    }

    /**
     * Verifica se uma poltrona já foi vendida para um veículo em uma data/hora específica
     */
    public boolean isPoltronaJaVendida(Long veiculoId, Integer poltrona, Date dataSaida, String horaSaida) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Passagem p " +
                         "WHERE p.veiculo.id = :veiculoId " +
                         "AND p.poltrona = :poltrona " +
                         "AND p.dataSaida = :dataSaida " +
                         "AND p.horaSaida = :horaSaida " +
                         "AND p.vendida = true";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("veiculoId", veiculoId);
            query.setParameter("poltrona", poltrona);
            query.setParameter("dataSaida", dataSaida);
            query.setParameter("horaSaida", horaSaida);
            Long count = query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    /**
     * Busca passagens vendidas em um período específico (para faturamento)
     */
    public List<Passagem> buscarPassagensVendidasPorPeriodo(Date dataInicio, Date dataFim) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT p FROM Passagem p " +
                         "WHERE p.vendida = true " +
                         "AND p.dataVenda BETWEEN :dataInicio AND :dataFim " +
                         "ORDER BY p.dataVenda";
            TypedQuery<Passagem> query = em.createQuery(jpql, Passagem.class);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Calcula o faturamento total em um período
     */
    public BigDecimal calcularFaturamentoPorPeriodo(Date dataInicio, Date dataFim) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COALESCE(SUM(p.valorPassagem), 0) FROM Passagem p " +
                         "WHERE p.vendida = true " +
                         "AND p.dataVenda BETWEEN :dataInicio AND :dataFim";
            TypedQuery<BigDecimal> query = em.createQuery(jpql, BigDecimal.class);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Busca passagens vendidas por roteiro (cidade origem e destino)
     */
    public List<Passagem> buscarPassagensPorRoteiro(Long cidadeOrigemId, Long cidadeDestinoId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT p FROM Passagem p " +
                         "WHERE p.cidadeOrigem.id = :origemId " +
                         "AND p.cidadeDestino.id = :destinoId " +
                         "AND p.vendida = true " +
                         "ORDER BY p.dataSaida, p.horaSaida";
            TypedQuery<Passagem> query = em.createQuery(jpql, Passagem.class);
            query.setParameter("origemId", cidadeOrigemId);
            query.setParameter("destinoId", cidadeDestinoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca todas as passagens de um veículo em uma data específica
     */
    public List<Passagem> buscarPassagensPorVeiculoEData(Long veiculoId, Date data) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT p FROM Passagem p " +
                         "WHERE p.veiculo.id = :veiculoId " +
                         "AND p.dataSaida = :data " +
                         "ORDER BY p.poltrona";
            TypedQuery<Passagem> query = em.createQuery(jpql, Passagem.class);
            query.setParameter("veiculoId", veiculoId);
            query.setParameter("data", data);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Conta quantas poltronas já foram vendidas para um veículo em uma data/hora
     */
    public long contarPoltronasVendidasPorVeiculo(Long veiculoId, Date dataSaida, String horaSaida) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Passagem p " +
                         "WHERE p.veiculo.id = :veiculoId " +
                         "AND p.dataSaida = :dataSaida " +
                         "AND p.horaSaida = :horaSaida " +
                         "AND p.vendida = true";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("veiculoId", veiculoId);
            query.setParameter("dataSaida", dataSaida);
            query.setParameter("horaSaida", horaSaida);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Lista passagens disponíveis (não vendidas) por roteiro e data
     */
    public List<Passagem> buscarPassagensDisponiveis(Long cidadeOrigemId, Long cidadeDestinoId, Date dataSaida) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT p FROM Passagem p " +
                         "WHERE p.cidadeOrigem.id = :origemId " +
                         "AND p.cidadeDestino.id = :destinoId " +
                         "AND p.dataSaida = :dataSaida " +
                         "AND p.vendida = false " +
                         "ORDER BY p.horaSaida, p.poltrona";
            TypedQuery<Passagem> query = em.createQuery(jpql, Passagem.class);
            query.setParameter("origemId", cidadeOrigemId);
            query.setParameter("destinoId", cidadeDestinoId);
            query.setParameter("dataSaida", dataSaida);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
