package hu.robnn.reportserver.model.dto

import java.util.*

class Team(var uuid: UUID = UUID.randomUUID(),
           var name: String? = null,
           var userNames: List<String> = mutableListOf())

class TeamsResponse(var teams: List<Team> = mutableListOf())