package vn.edu.usth.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import vn.edu.usth.model.Image;

@ApplicationScoped
public class ImageRepository implements PanacheRepository<Image> {
    public Image getImageFromId(int id) {
        return find("id", id).firstResult();
    }
}
