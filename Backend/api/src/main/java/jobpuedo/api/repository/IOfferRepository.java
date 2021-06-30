package jobpuedo.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.Offer;
import jobpuedo.api.entity.User;
import jobpuedo.api.enumeration.OfferStatus;

@Repository
public interface IOfferRepository extends JpaRepository<Offer, Integer> {
	List<Offer> findByStatusOrderByDateDesc(OfferStatus status);
	List<Offer> findByEnterprise(User user);
}