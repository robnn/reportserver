package hu.robnn.reportserver.dao;

import hu.robnn.reportserver.model.dmo.HConnectionDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionDescriptorRepository extends JpaRepository<HConnectionDescriptor, Long> {
    HConnectionDescriptor findByUuid(String uuid);
}
