package utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.Optional;

public class GenerateID {

    public static String generateSimpleID(String prefix, String tableName, String id, EntityManager em) {
        int sequence = 1;

        try {
            String sql = String.format("select max(cast(substring(e.%s, length(?1) + 1, 5) as int)) from %s e", id, tableName);

            Query q = em.createNativeQuery(sql);
            q.setParameter(1, prefix);

            Long maxSequence = (Long) Optional.ofNullable(q.getSingleResult()).orElse(0L);
            sequence = maxSequence.intValue() + 1;

            return prefix + String.format("%05d", sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


