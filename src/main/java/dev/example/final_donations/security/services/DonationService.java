package dev.example.final_donations.security.services;

import dev.example.final_donations.models.Donation;

import dev.example.final_donations.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    private final DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public List<Donation> getDonationsByOrdinalNumber(int ordinalNumber) {
        return donationRepository.findByOrdinalNumber(ordinalNumber);
    }

    public List<Donation> getDonationsByUserId(String userId) {
        return donationRepository.findByUserId(userId);
    }


    public Donation getDonationById(String id) {
        Optional<Donation> donation = donationRepository.findById(id);
        return donation.orElse(null);
    }

    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public Donation updateDonation(String id, Donation donation) {
        Optional<Donation> donationData = donationRepository.findById(id);

        if (donationData.isPresent()) {
            Donation existingDonation = donationData.get();
            existingDonation.setOrdinalNumber(donation.getOrdinalNumber());
            existingDonation.setDate(donation.getDate());
            existingDonation.setProblems(donation.getProblems());
            return donationRepository.save(existingDonation);
        } else {
            return null;
        }
    }

    public boolean deleteDonation(String id) {
        Optional<Donation> donation = donationRepository.findById(id);

        if (donation.isPresent()) {
            donationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteAllDonations() {
        donationRepository.deleteAll();
    }
}


