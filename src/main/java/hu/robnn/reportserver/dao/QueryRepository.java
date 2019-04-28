package hu.robnn.reportserver.dao;

import hu.robnn.reportserver.model.dmo.HQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryRepository extends JpaRepository<HQuery, Long> {
    HQuery findByQueryName(String name);
    HQuery findByUuid(String uuid);
}
