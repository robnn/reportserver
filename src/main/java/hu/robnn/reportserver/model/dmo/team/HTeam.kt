package hu.robnn.reportserver.model.dmo.team

import com.fasterxml.jackson.annotation.JsonIgnore
import hu.robnn.auth.dao.model.User
import hu.robnn.commons.interfaces.UuidHolder
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "rs_team", schema = "public")
open class HTeam : UuidHolder {
    override fun setUuid(p0: String?) {
        if(!p0.isNullOrEmpty()) {
            uuid = p0!!
        }
    }

    override fun getUuid(): String {
        return uuid
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rs_team_seq")
    @SequenceGenerator(name = "rs_team_seq", sequenceName = "rs_team_seq", allocationSize = 1)
    @Column(name = "id")
    @JsonIgnore
    var id: Long? = null

    @Column(name = "uuid")
    private var uuid: String = UUID.randomUUID().toString()

    open var name: String? = null

    @ManyToMany(targetEntity = User::class)
    @JoinTable(
            name = "rs_user_teams",
            joinColumns = [JoinColumn(name = "team_id")],
            inverseJoinColumns = [JoinColumn(name = "user_id")])
    open var users: Set<User> = mutableSetOf()


}