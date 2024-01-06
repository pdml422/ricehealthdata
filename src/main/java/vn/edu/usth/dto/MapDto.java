package vn.edu.usth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import vn.edu.usth.model.Mapp;

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
    @NotBlank
    @Schema(name = "label", required = true)
    private String label;

    public Mapp toMap() {
        Mapp m = new Mapp();

        m.setX(x);
        m.setY(y);
        m.setLabel(label);

        return m;
    }
}
