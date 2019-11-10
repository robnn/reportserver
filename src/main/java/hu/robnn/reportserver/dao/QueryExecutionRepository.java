package hu.robnn.reportserver.dao;

import hu.robnn.reportserver.model.dmo.query.HQueryExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryExecutionRepository extends JpaRepository<HQueryExecution, Long> {
    HQueryExecution findByUuid(String uuid);
}
