package jobpuedo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobpuedo.api.entity.FileDB;

@Repository
public interface IFileDBRepository extends JpaRepository<FileDB, String> {

}
