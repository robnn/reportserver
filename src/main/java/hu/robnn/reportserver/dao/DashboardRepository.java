package hu.robnn.reportserver.dao;

import hu.robnn.reportserver.model.dmo.HDashboard;
import hu.robnn.reportserver.model.dmo.team.HTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface DashboardRepository extends JpaRepository<HDashboard, Long> {
    HDashboard findByUuid(String uuid);

    @Query(value = "SELECT d.* FROM rs_dashboard d join au_user u on u.id = d.user_id limit 1",
    nativeQuery = true)
    Optional<HDashboard> findByUsername(String username);
}
