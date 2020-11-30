package interview.wbox.atm.repository;

import interview.wbox.atm.model.Role;
import interview.wbox.atm.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by ioana on 11/29/2020.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select r from Role r where r.name in (:roles)")
    Set<Role> find(@Param("roles") Set<RoleType> roles);
}
