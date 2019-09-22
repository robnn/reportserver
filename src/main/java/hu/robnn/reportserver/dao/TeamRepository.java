package hu.robnn.reportserver.dao;

import hu.robnn.reportserver.model.dmo.team.HTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<HTeam, Long> {
    HTeam findByUuid(String uuid);
    Optional<HTeam> findByName(String name);
}
