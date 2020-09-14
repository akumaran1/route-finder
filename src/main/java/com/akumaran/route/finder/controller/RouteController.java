package com.akumaran.route.finder.controller;

import com.akumaran.route.finder.model.CityPair;
import com.akumaran.route.finder.util.RoadMapper;
import com.akumaran.route.finder.util.Transit;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
public class RouteController {

    private final Log LOG = LogFactory.getLog(RouteController.class);

    @Autowired
    RoadMapper county;

    @ApiOperation(value = "Find if two cities are connected, Returns 'yes' if cites connected. Returns 'no' if not connected or any unexpected error.",
            response = String.class)
    @ApiResponse(code = 400, message = "Internal error", response = Exception.class)

    @GetMapping(value = "/connected", produces = "text/plain")
    public String connected(
            @ApiParam(name = "origin", value = "Origin CityPair", required = true) @RequestParam String origin,
            @ApiParam(name = "destination", value = "Destination CityPair", required = true) @RequestParam String destination) {

        if(StringUtils.isEmpty(origin) || StringUtils.isEmpty(destination))
            return ResponseEntity.badRequest().toString();

        CityPair originCity = county.getCity(origin);
        CityPair destCity = county.getCity(destination);

        return String.valueOf(Transit.traverse(originCity, destCity));
    }
}
