package dev.example.final_donations.controllers;

import dev.example.final_donations.models.Donation;
import dev.example.final_donations.security.services.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/donations")
public class DonationsController{
    private final DonationService donationService;

    @Autowired
    public DonationsController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations(@RequestParam(required = false) Integer ordinalNumber,
                                                          @RequestParam(required = false) String userId) {
        try {
            List<Donation> donations = new ArrayList<>();

            if (ordinalNumber != null) {
                donations = donationService.getDonationsByOrdinalNumber(ordinalNumber);
            } else if (userId != null) {
                donations = donationService.getDonationsByUserId(userId);
            } else {
                donations = donationService.getAllDonations();
            }

            if (donations.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(donations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation, @RequestParam String userId) {
        try {
            Donation newDonation = new Donation(donation.getOrdinalNumber(), donation.getDate(), donation.getProblems(), userId);
            Donation createdDonation = donationService.createDonation(newDonation);
            return new ResponseEntity<>(createdDonation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") String id) {
        Donation donation = donationService.getDonationById(id);

        if (donation != null) {
            return new ResponseEntity<>(donation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable("id") String id, @RequestBody Donation donation) {
        Donation updatedDonation = donationService.updateDonation(id, donation);

        if (updatedDonation != null) {
            return new ResponseEntity<>(updatedDonation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDonation(@PathVariable("id") String id) {
        boolean deleted = donationService.deleteDonation(id);

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllDonations() {
        donationService.deleteAllDonations();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
