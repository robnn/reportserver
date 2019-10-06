package hu.robnn.reportserver.mapper

import hu.robnn.auth.dao.UserDao
import hu.robnn.reportserver.model.dmo.team.HTeam
import hu.robnn.reportserver.model.dto.Team
import org.springframework.stereotype.Component
import java.util.*

@Component
class TeamMapper(private val userDao: UserDao) {
    fun map(source: Team, target: HTeam?): HTeam {
        var realTarget = target
        if (realTarget == null) {
            realTarget = HTeam()
        }
        realTarget.users = source.userNames.map { userDao.findByUsername(it) }.filter { it.isPresent }.map { it.get() }.toMutableSet()
        realTarget.name = source.name

        return realTarget
    }

    fun map(source: HTeam): Team {
        val target = Team()
        target.uuid = UUID.fromString(source.uuid)
        target.name = source.name
        target.userNames = source.users.map { it.username!! }
        return target
    }
}