package xzkbauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzkbauth.common.jpa.entity.SyBranch;
import xzkbauth.common.jpa.repository.SyBranchRepository;

@Service("syBranchService")
public class SyBranchServiceImpl {
  @Autowired SyBranchRepository syBranchRepository;

  public SyBranch findByBranchCod(String branchCod) {
    return syBranchRepository.findByBranchCod(branchCod);
  }
}
