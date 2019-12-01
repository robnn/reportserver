package hu.robnn.reportserver.dao;

import hu.robnn.reportserver.model.dmo.team.HTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface TeamRepository extends JpaRepository<HTeam, Long> {
    HTeam findByUuid(String uuid);

    @Query(value = "SELECT t.* FROM rs_team t JOIN rs_user_teams ut on t.id = ut.team_id join au_user u on u.id = ut.user_id",
    nativeQuery = true)
    Set<HTeam> findByUsername(String username);
}
