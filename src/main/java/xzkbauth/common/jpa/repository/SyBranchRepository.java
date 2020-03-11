package xzkbauth.common.jpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import xzkbauth.common.jpa.entity.SyBranch;

public interface SyBranchRepository extends JpaRepository<SyBranch,Long> {

    SyBranch findByBranchCod(String branchCod);
}
