package vn.edu.usth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import vn.edu.usth.model.Statistical;

@Getter
@Setter
@Schema(name = "StatisticalDTO", description = "Create a data")
public class StatisticalDto {
    @NotBlank
    @Schema(name = "replicate", required = true)
    private String replicate;
    @NotNull
    @Schema(name = "chlorophyll", required = true)
    private Double chlorophyll;
    @NotBlank
    @Schema(name = "longitude", required = true)
    private String longitude;
    @NotBlank
    @Schema(name = "latitude", required = true)
    private String latitude;
    @NotNull
    @Schema(name = "pConc", required = true)
    private Double pConc;
    @NotNull
    @Schema(name = "kConc", required = true)
    private Double kConc;
    @NotNull
    @Schema(name = "nConc", required = true)
    private Double nConc;
    @NotNull
    @Schema(name = "wetWeight", required = true)
    private Double wetWeight;
    @NotNull
    @Schema(name = "driedWeight", required = true)
    private Double driedWeight;
    @NotNull
    @Schema(name = "moiture", required = true)
    private Double moiture;
    @NotNull
    @Schema(name = "digesion", required = true)
    private Double digesion;
    @NotBlank
    @Schema(name = "date", required = true)
    private String date;
    @NotBlank
    @Schema(name = "subReplicate", required = true)
    private String subReplicate;

    public Statistical toStatistical(){
        Statistical statistical = new Statistical();

        statistical.setReplicate(replicate);
        statistical.setSubReplicate(subReplicate);
        statistical.setDate(date);
        statistical.setLongitude(longitude);
        statistical.setLatitude(latitude);
        statistical.setChlorophyll(chlorophyll);
        statistical.setPConc(pConc);
        statistical.setKConc(kConc);
        statistical.setNConc(nConc);
        statistical.setWetWeight(wetWeight);
        statistical.setDriedWeight(driedWeight);
        statistical.setMoiture(moiture);
        statistical.setDigesion(digesion);

        return statistical;
    }

}
