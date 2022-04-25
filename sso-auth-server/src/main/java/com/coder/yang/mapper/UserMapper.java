package com.coder.yang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coder.yang.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author ： coder.Yang
 * @date ： 2022/4/14 21:38
 * @description ：
 */
@Mapper
public interface UserMapper  extends BaseMapper<UserDO> {
}
