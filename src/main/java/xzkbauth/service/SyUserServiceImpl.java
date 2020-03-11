package xzkbauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xzkbauth.common.jpa.entity.SyUser;
import xzkbauth.common.jpa.repository.SyUserRepository;

@Service("syUserService")
public class SyUserServiceImpl {

    @Autowired
    SyUserRepository syUserRepository;
    public SyUser findByUserCod(String userCod){
        return syUserRepository.findByUserCod(userCod);
    }

}
