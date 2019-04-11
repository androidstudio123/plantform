package cn.crm.service.sys.impl;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.SysAdminRoleEntity;
import cn.crm.entity.SysLogEntity;
import cn.crm.entity.SysRoleEntity;
import cn.crm.mapper.sys.SysAdminMapper;
import cn.crm.mapper.sys.SysRoleMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.sys.SysRoleService;
import cn.crm.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleEntity> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Override
    public List<SysRoleEntity> findAllSysRole(Integer role_isActive, String role_name, Integer admin_id) {
        //创建查询条件
        Example example = new Example(SysRoleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("role_createId", admin_id);
        //判断用户名是否为空
        if (StringUtils.isNotEmpty(role_name)) {
            criteria.andLike("role_name", "%" +
                    role_name + "%");
        }
        if (role_isActive != null) {
            //判断状态是否为空
            criteria.andEqualTo("role_isActive", role_isActive);
        }
        //调用查询方法
        PageUtil.startPage("role_createTime", "desc");
        List<SysRoleEntity> sysRoleEntities = sysRoleMapper.selectByExample(example);
        return sysRoleEntities;
    }

    /**
     * 修改角色信息
     *
     * @param sysRoleEntity
     * @return
     */
    @Override
    public ResultData updateRole(SysRoleEntity sysRoleEntity) {
        if (sysRoleEntity == null) {
            return new ResultData(20004, false, "数据错误,修改失败");
        }
        //查询数据库是否存在
        Example example = new Example(SysRoleEntity.class);
        Example.Criteria criteria = example.createCriteria();
        //添加条件角色名
        criteria.andEqualTo("role_name", sysRoleEntity.getRole_name());
        criteria.andNotEqualTo("role_id", sysRoleEntity.getRole_id());
        List<SysRoleEntity> sysRoleEntities = sysRoleMapper.selectByExample(example);
        //判断数据库查询到的条数
        if (sysRoleEntities.size() == 0) {
            //调用修改方法
            int i = sysRoleMapper.updateByPrimaryKeySelective(sysRoleEntity);
            //判断返回结果
            if (i > 0) {
                return new ResultData(20000, true, "修改成功");
            }
            return new ResultData(20002, false, "网络错误,修改失败");
        }
        //数据库存在多条数据
        return new ResultData(20003, false, "角色名已存在,修改失败");
    }

    /**
     * 根据角色ID删除一个角色
     *
     * @param role_id
     * @return
     */
    @Transactional
    @Override
    public ResultData deleteRole(Integer role_id) {
        //存在管理员绑定该角色，不能删除
        Example example = new Example(SysAdminEntity.class);
        example.createCriteria().andEqualTo("role_id", role_id);
        List<SysAdminEntity> sysAdminEntities = sysAdminMapper.selectByExample(example);
        if (sysAdminEntities.size() > 0) {
            return new ResultData(20005, false, "存在管理员绑定该角色，请先解绑。");
        }
        //删除角色
        int res = sysRoleMapper.deleteByPrimaryKey(role_id);
        if (res > 0) {
            return new ResultData(20000, true, "删除成功");
        }
        return new ResultData(20001, true, "删除失败");
    }

    /**
     * 根据角色id查询角色信息
     *
     * @param roleId
     * @return
     */
    @Override
    public SysRoleEntity findRoleByid(Integer roleId) {
        return sysRoleMapper.selectByPrimaryKey(roleId);
    }

    /**
     * 新增一个角色
     *
     * @param sysRoleEntity
     * @return
     */

    @Override
    public ResultData addRole(SysRoleEntity sysRoleEntity, HttpServletRequest request) {
        SysAdminEntity sysAdminEntity = (SysAdminEntity) request.getSession().getAttribute("sysAdminEntity");
        if (sysAdminEntity == null) {
            return new ResultData(ResultCode.OUTDATE.getCode(), false, ResultCode.OUTDATE.getMessage());
        }
        //判断传递信息是否为空
        if (sysRoleEntity == null) {
            return new ResultData(20001, false, "新增数据为空,新增失败");
        }
        if (StringUtils.isNotEmpty(sysRoleEntity.getRole_name())) {
            Example example = new Example(SysRoleEntity.class);
            example.createCriteria().andEqualTo("role_name", sysRoleEntity.getRole_name());
            List<SysRoleEntity> sysRoleEntities = sysRoleMapper.selectByExample(example);
            //判断数据库是否存在
            if (sysRoleEntities.size() == 0) {
                //设置创建时间
                sysRoleEntity.setRole_createId(sysAdminEntity.getAdmin_id());
                sysRoleEntity.setRole_isActive(1);
                sysRoleEntity.setRole_createTime(new Date());
                //调用保存方法
                int i = sysRoleMapper.insertSelective(sysRoleEntity);
                //判断返回结果
                if (i > 0) {
                    return new ResultData(20000, true, "新增成功");
                }
                return new ResultData(20004, false, "网络错误,新增失败");
            } else {
                //存在数据
                return new ResultData(20002, false, "角色名已存在,新增失败");
            }
        }
        return new ResultData(20003, false, "角色名为空,新增失败");
    }
}
