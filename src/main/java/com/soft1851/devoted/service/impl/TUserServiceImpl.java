package com.soft1851.devoted.service.impl;

import com.soft1851.devoted.common.ResultCode;
import com.soft1851.devoted.domain.dto.LoginDto;
import com.soft1851.devoted.domain.entity.TUser;
import com.soft1851.devoted.exception.CustomException;
import com.soft1851.devoted.mapper.TUserMapper;
import com.soft1851.devoted.service.TUserService;
import com.soft1851.devoted.util.Md5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Tull
 * @since 2020-06-09
 */
@Service
public class TUserServiceImpl implements TUserService {
    @Resource
    private TUserMapper tUserMapper;

//    public ResponseResult userLogin(LoginDto loginDto) {
//        //通过用户账号获取用户
//        LoginDto dbUser = tUserMapper.userLogin(loginDto.getAccount());
//
//        String password = loginDto.getPassword();
//        //获取失败
//        if (dbUser == null) {
//            return ResponseResult.success(ResultCode.USER_NOT_FOUND);//用户不存在
//        }//则获取成功
//        else if (!dbUser.getPassword().equals(Md5Util.getMd5(password, true, 32))) {
//            return ResponseResult.success(ResultCode.USER_PASSWORD_ERROR);//密码错误
//        } else {
//            return ResponseResult.success(ResultCode.SUCCESS);//成功
//        }
//
//    }

    @Override
    public boolean userLogin(LoginDto loginDto) {
        LoginDto result =  tUserMapper.userLogin(loginDto.getAccount());
        if ( result != null ) {
            String pass = Md5Util.getMd5(loginDto.getPassword(), true, 32);
            if ( result.getPassword().equals(pass) ) {
                return true;
            } else {
                System.out.println("密码错误");
                throw new CustomException("密码错误", ResultCode.USER_PASSWORD_ERROR);
            }
        } else {
            System.out.println("用户名不存在");
            throw new CustomException("用户名不存在", ResultCode.USER_NOT_FOUND);
        }
    }

    @Override
    public List<TUser> selectUser() {
        return tUserMapper.selectUser();
    }

    @Override
    public void addUser(TUser tUser) {
        tUserMapper.addUser(tUser);
    }

    @Override
    public Integer deleteByIdUser(Integer id) {
        return tUserMapper.deleteByIdUser(id);
    }

    @Override
    public void updateUser(TUser tUser) {
        tUserMapper.updateUser(tUser);
    }

}
