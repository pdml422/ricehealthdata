package vn.edu.usth.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.usth.model.MapPoint;
import vn.edu.usth.payload.SearchMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ApplicationScoped
public class MapRepository implements PanacheRepository<MapPoint> {
    public List<MapPoint> getMapPointFromImageId(int imageId) {
        return find("imageId", imageId).list();
    }

}
