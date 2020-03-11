package xzkbauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzkbauth.common.jpa.entity.SyWarningPlatform;
import xzkbauth.common.jpa.repository.SyWarningPlatformRepository;

@Service("syWarningPlatformService")
public class SyWarningPlatformServiceImpl {
  @Autowired SyWarningPlatformRepository syWarningPlatformRepository;

  public SyWarningPlatform save(SyWarningPlatform syWarningPlatform) {
    return syWarningPlatformRepository.save(syWarningPlatform);
  }
}
