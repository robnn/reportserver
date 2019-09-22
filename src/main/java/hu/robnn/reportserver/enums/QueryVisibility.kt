package hu.robnn.reportserver.enums

enum class QueryVisibility {
    PUBLIC, //everybody can access the query
    TEAM, //the creator users can pick teams from his/her team to grant access to the query
    PRIVATE, //only the creator user can access the query
}