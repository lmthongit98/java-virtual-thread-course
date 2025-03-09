package com.tma.training.riderservice.controllers;

import com.tma.training.riderservice.models.RiderAssignmentRequest;
import com.tma.training.riderservice.models.RiderAssignmentResponse;
import com.tma.training.riderservice.services.RiderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rider")
public class RiderController {

    private static final Logger log = LoggerFactory.getLogger(RiderController.class);

    private final RiderService riderService;

    public RiderController(RiderService riderService) {
        this.riderService = riderService;
    }

    @PostMapping("/assign")
    public RiderAssignmentResponse assignRider(@RequestBody RiderAssignmentRequest request) {
        log.info("Started assign order {} to rider, {}", request.orderId(), Thread.currentThread().isVirtual());
        return riderService.assignRider(request);
    }

}