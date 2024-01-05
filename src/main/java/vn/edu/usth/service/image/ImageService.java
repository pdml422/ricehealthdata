package vn.edu.usth.service.image;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.edu.usth.model.Image;
import vn.edu.usth.model.Map;
import vn.edu.usth.payload.ImageRGBFromHyper;
import vn.edu.usth.payload.ViewMap;
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

        } catch(Exception e) {
            e.printStackTrace();
        }

        return res.toString();
    }

    public List<Map> getMapPointFromImageId(int imageId) {
        return mapRepository.getMapPointFromImageId(imageId);
    }

}
