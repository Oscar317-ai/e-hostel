package com.guava.E_HOSTELS.payments;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class PaymentService {



    public ResponseEntity<String> processPayment(String phone) {
        // Simulate payment process with a delay (for testing only)
        try {
            Thread.sleep(3000); // Simulate 3 seconds delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Replace with actual payment processing logic in production
        boolean paymentSuccessful = simulatePayment(phone); // Simulate payment outcome

        if (paymentSuccessful) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.badRequest().body("Payment failed");
        }
    }

    // Simulate payment logic (replace with actual integration)
    private boolean simulatePayment(String phone) {
        // Simulate logic for successful/failed payment based on phone number
        // (This is just a placeholder for testing purposes)
        return phone.endsWith("0876");
    }

}
