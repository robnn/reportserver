package hu.robnn.reportserver.dao;

import hu.robnn.reportserver.model.dmo.HDriver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<HDriver, Long> {
    HDriver findByName(String name);
    HDriver findByUuid(String uuid);
}
