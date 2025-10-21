package br.com.cairu.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPAUtil {

    private static final String PERSISTENCE_UNIT_NAME = "PassagensRodoviariaPU";
    private static final Logger LOGGER = Logger.getLogger(JPAUtil.class.getName());
    private static EntityManagerFactory factory;

    static {
        try {
            LOGGER.info("Inicializando EntityManagerFactory com persistence unit: " + PERSISTENCE_UNIT_NAME);
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            LOGGER.info("EntityManagerFactory inicializado com sucesso!");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "ERRO CRÍTICO ao criar EntityManagerFactory", e);
            System.err.println("========================================");
            System.err.println("ERRO ao criar EntityManagerFactory:");
            System.err.println("Mensagem: " + e.getMessage());
            System.err.println("Causa: " + (e.getCause() != null ? e.getCause().getMessage() : "N/A"));
            System.err.println("========================================");
            e.printStackTrace();
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null) {
            String errorMsg = "EntityManagerFactory não foi inicializado corretamente! Verifique os logs do servidor.";
            LOGGER.severe(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
        return factory.createEntityManager();
    }

    public static void closeEntityManagerFactory() {
        if (factory != null && factory.isOpen()) {
            LOGGER.info("Fechando EntityManagerFactory...");
            factory.close();
        }
    }
}
