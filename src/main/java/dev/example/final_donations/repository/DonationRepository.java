package dev.example.final_donations.repository;

import dev.example.final_donations.models.Donation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface DonationRepository extends MongoRepository<Donation, String> {
    List<Donation> findByOrdinalNumber(int ordinalNumber);
    List<Donation> findByDate(Date date);
    List<Donation> findByUserId(String userId);
}
