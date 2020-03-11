package xzkbauth.common.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import xzkbauth.common.jpa.entity.SyUser;


public interface SyUserRepository extends JpaRepository<SyUser,Long> {

    SyUser findByUserCod(String userCod);

}
