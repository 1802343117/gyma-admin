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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


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

    @Override
    public boolean userLogin(LoginDto loginDto) {
        LoginDto result =  tUserMapper.userLogin(loginDto.getAccount());
        // 判断查询结果是否为空，如果为空，说明没有该用户
        if ( result != null ) {
            // 判断用户登录状态
            if ( !result.getStatus() ) {
                String pass = Md5Util.getMd5(loginDto.getPassword(), true, 32);
                if ( result.getPassword().equals(pass) ) {
                    return true;
                } else {
                    System.out.println("密码错误");
                    throw new CustomException("密码错误", ResultCode.USER_PASSWORD_ERROR);
                }
            } else {
                System.out.println("用户已登录");
                throw new CustomException("认证失败", ResultCode.USER_AUTH_ERROR);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        tUser.setDeleteFlag(false);
        tUser.setGmtCreate(localDateTime);
        tUser.setGmtModified(localDateTime);
        tUser.setRoleId(1);
        tUser.setSalt(null);
        tUser.setStatus(false);
        tUser.setUserGender(null);
        Random random = new Random(1);
        String str = Integer.toString(random.nextInt(100));
        String str1 = Integer.toString(random.nextInt(100));
        String str2 = Integer.toString(random.nextInt(100));
        tUser.setUserPath(str + str1 + str2);
        tUser.setUserPhone(null);
        // 密码加密
        String pass = Md5Util.getMd5(tUser.getPassword(), true, 32);
        tUser.setPassword(pass);

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

    /**
     * 根据ID查看用户信息
     * @param userId
     * @return
     */
    @Override
    public TUser selectById(Integer userId) {
        return tUserMapper.selectById(userId);
    }

}
