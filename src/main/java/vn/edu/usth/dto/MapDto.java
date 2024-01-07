package vn.edu.usth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import vn.edu.usth.model.MapPoint;

@Getter
@Setter
@Schema(name = "MapDTO", description = "create a data")
public class MapDto {
    @NotNull
    @Schema(name = "X", required = true)
    private int x;
    @NotNull
    @Schema(name = "Y", required = true)
    private int y;

    public MapPoint toMap() {
        MapPoint m = new MapPoint();

        m.setX(x);
        m.setY(y);

        return m;
    }
}
