package jobpuedo.api.repository;

import org.springframework.data.repository.CrudRepository;

import jobpuedo.api.entity.Role;

public interface IProfileRepository extends CrudRepository<Role, Integer> {

}
