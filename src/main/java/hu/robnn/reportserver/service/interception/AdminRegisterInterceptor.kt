package hu.robnn.reportserver.service.interception

import hu.robnn.auth.dao.RoleDao
import hu.robnn.auth.dao.UserDao
import hu.robnn.auth.dao.model.User
import hu.robnn.auth.service.interceptors.RegisterInterceptor
import org.springframework.stereotype.Component

/**
 * This interceptor makes the firstly registered user to admin, no need to manually insert it to the db
 */
@Component
class AdminRegisterInterceptor(private val userDao: UserDao,
                               private val roleDao: RoleDao): RegisterInterceptor {

    override fun executeAfterRegistration(user: User) {
        super.executeAfterRegistration(user)
        if (user.id == 1L) {
            // the user is the first user in the DB so make it admin
            val adminRoles = user.roles.toMutableSet()
            val newRole = roleDao.findByRoleCode("ADMIN")
            if (newRole.isPresent) {
                adminRoles.add(newRole.get())
                user.roles = adminRoles
                userDao.save(user)
            }
        }
    }
}