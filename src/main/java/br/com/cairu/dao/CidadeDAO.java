package br.com.cairu.dao;

import br.com.cairu.model.Cidade;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CidadeDAO extends GenericDAO<Cidade, Long> {

    public CidadeDAO() {
        super(Cidade.class);
    }

    public Cidade buscarPorIdIBGE(String idCidade) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cidade> query = em.createQuery(
                "SELECT c FROM Cidade c WHERE c.idCidade = :idCidade", Cidade.class);
            query.setParameter("idCidade", idCidade);
            List<Cidade> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Cidade> buscarPorUF(String uf) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cidade> query = em.createQuery(
                "SELECT c FROM Cidade c WHERE c.uf = :uf ORDER BY c.nomeCidade", Cidade.class);
            query.setParameter("uf", uf);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cidade> buscarPorNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cidade> query = em.createQuery(
                "SELECT c FROM Cidade c WHERE LOWER(c.nomeCidade) LIKE LOWER(:nome) ORDER BY c.nomeCidade",
                Cidade.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Verifica se a cidade possui passagens cadastradas (origem ou destino)
     */
    public boolean temPassagens(Long cidadeId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(p) FROM Passagem p " +
                         "WHERE p.cidadeOrigem.id = :cidadeId OR p.cidadeDestino.id = :cidadeId";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("cidadeId", cidadeId);
            Long count = query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
}
