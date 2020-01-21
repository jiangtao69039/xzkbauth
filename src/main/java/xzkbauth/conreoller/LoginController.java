package xzkbauth.conreoller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xzkbauth.base.BaseController;
import xzkbauth.base.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiangtao
 */
@RestController
@RequestMapping("/auth")
public class LoginController extends BaseController {

    /**
     * 原cmbserver的login接口
     * sysUser登录
     * @return
     */
    @PostMapping("/system/login")
    public Result login(String userCod,String password, HttpServletRequest request, HttpServletResponse response){
        //TODO redis序列化方式和原先不同*
        return null;
    }
}
