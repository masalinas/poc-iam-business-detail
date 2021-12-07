package io.oferto.businessdetail.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.oferto.businessdetail.domain.Recomendation;

@RestController
@RequestMapping("api/recomendations")
public class RecomendationController {
	Logger log = LoggerFactory.getLogger(RecomendationController.class);
	
	private List<Recomendation> recomendations = new ArrayList<Recomendation>(
			Arrays.asList(new Recomendation("Recomendation 01", LocalDateTime.now(), "001"),
						  new Recomendation("Recomendation 02", LocalDateTime.now(), "001"),
						  new Recomendation("Recomendation 03", LocalDateTime.now(), "003")));
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public List<Recomendation> getRecomendationsByProduct(@PathVariable String code) throws Exception {
        log.info("Executing getRecomendationsByProduct");
                    	
        return recomendations.stream()
        		.filter(recomendation -> code.equals(recomendation.getProductCode()))
        		.collect(Collectors.toList());      
    }
	
	@PreAuthorize("hasAnyRole('admin','operator')")
	@RequestMapping(value = "/{code}", method = RequestMethod.POST)
    public Recomendation addRecomendation(@PathVariable String code, @RequestBody String description) throws Exception {
        log.info("Executing addRecomendation");
                    	
        Recomendation recomendation = new Recomendation(description, LocalDateTime.now(), code);
        
        recomendations.add(recomendation);
        
        return recomendation;
    }
}
