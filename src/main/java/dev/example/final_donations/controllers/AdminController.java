package dev.example.final_donations.controllers;

import dev.example.final_donations.models.Donation;
import dev.example.final_donations.models.User;
import dev.example.final_donations.repository.UserRepository;
import dev.example.final_donations.security.services.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserRepository userRepository;
    private final DonationService donationService;

    @Autowired
    public AdminController(UserRepository userRepository, DonationService donationService) {
        this.userRepository = userRepository;
        this.donationService = donationService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/{userId}/donations")
    public ResponseEntity<Donation> addDonationToUser(@PathVariable("userId") String userId,
                                                      @RequestBody Donation donation) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Donation newDonation = new Donation(donation.getOrdinalNumber(), donation.getDate(), donation.getProblems(), userId);
            Donation createdDonation = donationService.createDonation(newDonation);

            return new ResponseEntity<>(createdDonation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

