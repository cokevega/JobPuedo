package jobpuedo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.Language;

@Repository
public interface ILanguageRepository extends JpaRepository<Language, Integer> {

}
