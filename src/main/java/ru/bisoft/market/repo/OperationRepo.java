package ru.bisoft.market.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.market.mvc.domain.Operation;

@Transactional(readOnly = true)
public interface OperationRepo extends JpaRepository<Operation, Integer> {

}
