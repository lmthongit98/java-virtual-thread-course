package com.tma.training.riderservice.services;

import com.tma.training.riderservice.models.RiderAssignmentRequest;
import com.tma.training.riderservice.models.RiderAssignmentResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RiderService {

    public RiderAssignmentResponse assignRider(RiderAssignmentRequest request) {
        try {
            String riderMessage = processRiderAssignment(request);
            return new RiderAssignmentResponse(request.orderId(), riderMessage);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new RiderAssignmentResponse(request.orderId(), "Rider assignment failed: " + e.getMessage());
        }
    }

    private String processRiderAssignment(RiderAssignmentRequest request) throws InterruptedException {
        Thread.sleep(500); // Simulate delay
        return "Rider assigned successfully with Rider ID: " + UUID.randomUUID();
    }

}