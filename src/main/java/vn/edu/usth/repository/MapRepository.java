package vn.edu.usth.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.usth.model.Mapp;
import vn.edu.usth.payload.SearchMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ApplicationScoped
public class MapRepository implements PanacheRepository<Mapp> {
    public List<Mapp> getMapPointFromImageId(int imageId) {
        return find("imageId", imageId).list();
    }
    public List<Mapp> search(SearchMap searchMap, int imageId) {
        Map<String,Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder("SELECT m FROM Map m where m.imageId =:imageId");
        params.put("imageId", imageId);

        if (searchMap.getLabel() != null) {
            query.append(" AND m.label = :label");
            params.put("Label", searchMap.getLabel());
        }
        PanacheQuery<Mapp> panacheQuery = this.find(query.toString(), params);
        return panacheQuery.list();
    }
}
