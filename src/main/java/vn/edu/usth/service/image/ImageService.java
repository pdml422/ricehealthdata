package vn.edu.usth.service.image;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import vn.edu.usth.exception.DataNotFoundException;
import vn.edu.usth.model.Image;
import vn.edu.usth.model.MapPoint;
import vn.edu.usth.payload.ImageRGBFromHyper;
import vn.edu.usth.payload.SearchMap;
import vn.edu.usth.repository.ImageRepository;
import vn.edu.usth.repository.MapRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@ApplicationScoped
public class ImageService {
    private final ImageRepository imageRepository;
    private final MapRepository mapRepository;

    @Inject
    public ImageService(ImageRepository imageRepository, MapRepository mapRepository) {
        this.imageRepository = imageRepository;
        this.mapRepository = mapRepository;
    }

    public List<Image> getImageFromUserId(int userId) {
        return imageRepository.getImageFromUserId(userId);
    }

    public Image getImageFromId(int id) {
        return imageRepository.getImageFromId(id);
    }

    public String getImageRGBFromHyper(ImageRGBFromHyper request) {
        StringBuilder res = new StringBuilder();
        try {
            String imagePath = getImageFromId(request.id).getPath();
            String pythonPath = "python src/main/resources/hyper.py "
                    + request.id + " " + imagePath + " " + request.red + " " + request.green + " " + request.blue;
            Process p = Runtime.getRuntime().exec(pythonPath);

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res.toString();
    }

    public List<MapPoint> getMapPointFromImageId(int imageId) {
        return mapRepository.getMapPointFromImageId(imageId);
    }

    @Transactional
    public MapPoint addData(MapPoint mapPoint) {
        mapRepository.persist(mapPoint);
        return mapPoint;
    }

    public MapPoint getDataById(int id) throws DataNotFoundException {
        MapPoint m = mapRepository.findById((long) id);
        if (m == null) {
            throw new DataNotFoundException("Not on Database");
        }
        return m;
    }

    @Transactional
    public MapPoint updateData(int id, MapPoint mapPoint) throws DataNotFoundException {
        MapPoint m = getDataById(id);

        m.setX(mapPoint.getX());
        m.setY(mapPoint.getY());
        m.setImageId(mapPoint.getImageId());
        m.setDataId(mapPoint.getDataId());
        mapRepository.persist(m);
        return m;
    }

    @Transactional
    public void deleteData(int id) throws DataNotFoundException {
        mapRepository.delete(getDataById(id));
    }

}