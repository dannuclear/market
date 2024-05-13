package ru.bisoft.market.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import ru.bisoft.market.domain.Share;

@Transactional(readOnly = true)
public interface InstrumentRepo extends JpaRepository<Share, UUID> {

}
