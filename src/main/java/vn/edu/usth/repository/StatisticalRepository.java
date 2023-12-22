package vn.edu.usth.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.usth.model.Statistical;
import vn.edu.usth.payload.SearchStatistical;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class StatisticalRepository implements PanacheRepository<Statistical> {

    public List<Statistical> search(SearchStatistical searchStatistical, int userId) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder("SELECT s FROM Statistical s WHERE s.userId = :userId");
        params.put("userId", userId);
        if (searchStatistical.getReplicate() != null) {
            query.append(" AND s.replicate = :replicate");
            params.put("replicate", searchStatistical.getReplicate());
        }
        if (searchStatistical.getSubReplicate() != null) {
            query.append(" AND s.subReplicate = :subReplicate");
            params.put("subReplicate", searchStatistical.getSubReplicate());
        }
        if (searchStatistical.getDate() != null) {
            query.append(" AND s.date = :date");
            params.put("date", searchStatistical.getDate());
        }
        PanacheQuery<Statistical> panacheQuery = this.find(query.toString(), params);
        return panacheQuery.list();

    }

    public List<Statistical> searchAll(int userId) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder("SELECT s FROM Statistical s WHERE s.userId = :userId");
        params.put("userId", userId);
        PanacheQuery<Statistical> panacheQuery = this.find(query.toString(), params);
        return panacheQuery.list();

    }
}
