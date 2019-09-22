package hu.robnn.reportserver.service

import hu.robnn.auth.dao.UserDao
import hu.robnn.reportserver.dao.TeamRepository
import hu.robnn.reportserver.enums.TeamErrorCause
import hu.robnn.reportserver.exception.ReportServerMappedException
import hu.robnn.reportserver.mapper.TeamMapper
import hu.robnn.reportserver.model.dto.Team
import hu.robnn.reportserver.model.dto.TeamsResponse
import org.apache.commons.lang3.StringUtils.isBlank
import org.springframework.stereotype.Component

interface TeamService {
    fun listTeams(): TeamsResponse
    fun manageTeam(team: Team): Team
    fun listUsernames(): List<String>
}

@Component
class TeamServiceImpl(private val teamRepository: TeamRepository,
                      private val teamMapper: TeamMapper,
                      private val userDao: UserDao) : TeamService {
    override fun listTeams(): TeamsResponse {
        val response = TeamsResponse()
        response.teams = teamRepository.findAll().map { teamMapper.map(it) }
        return response
    }

    override fun manageTeam(team: Team): Team {
        validateTeam(team)
        val findByUuid = teamRepository.findByUuid(team.uuid.toString())
        val map = teamMapper.map(team, findByUuid)
        val saved = teamRepository.save(map)
        return teamMapper.map(saved)
    }

    private fun validateTeam(team: Team) {
        if (isBlank(team.name)) {
            throw ReportServerMappedException(TeamErrorCause.TEAM_NAME_MUST_BE_SUPPLIED)
        }
        if (team.userNames.isEmpty()) {
            throw ReportServerMappedException(TeamErrorCause.TEAM_MUST_HAVE_USERS_IN_IT)
        }
        if (teamRepository.findByName(team.name).isPresent) {
            throw ReportServerMappedException(TeamErrorCause.TEAM_WITH_NAME_ALREADY_EXISTS)
        }
    }

    override fun listUsernames() = userDao.findAll().map { it.username!! }
}