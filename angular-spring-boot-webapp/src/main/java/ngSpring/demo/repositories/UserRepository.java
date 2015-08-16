package ngSpring.demo.repositories;

import ngSpring.demo.domain.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, String> {

    @Query(nativeQuery = true, value = "select u.user_name from user u where u.customer_id = :customerId and exists (select * from user_roles where user_id = u.user_id and role_id = 2)")
    String findControllerUserNameByCustomerId(
            @Param("customerId") String customerId);


    @Query(nativeQuery = true, value = "select u.user_name from user u, user_branches ub where u.user_id = ub.user_id and ub.branch_id = :branchId")
    String findPlanerUserNameByBranchId(@Param("branchId") String branchId);

    User findByUsernameAndDeletedFalse(String username);

    User findByUserIdAndDeletedFalse(String userId);
}
