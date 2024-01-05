package vn.edu.usth.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.usth.model.Map;

import java.util.List;

@ApplicationScoped
public class MapRepository implements PanacheRepository<Map> {
    public List<Map> getMapPointFromImageId(int imageId) {
        return find("imageId", imageId).list();
    }

}
