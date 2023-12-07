package vn.edu.usth.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import vn.edu.usth.model.Statistical;
import vn.edu.usth.service.data.StatisticalService;

import java.util.Date;
import java.util.List;

public class StatisticalController {
    private final StatisticalService statisticalService;

    @Inject
    public StatisticalController(StatisticalService statisticalService) {
        this.statisticalService = statisticalService;
    }

}
