package cn.crm.util;

import cn.crm.entity.SysAdminEntity;
import cn.crm.mapper.sys.SysAdminMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class AdminEntityUtil {


    private static SysAdminMapper adminMapper;
    @Autowired
    public  void setAdminMapper(SysAdminMapper adminMapper) {
        AdminEntityUtil.adminMapper = adminMapper;
    }

    public static final String ADMIN_SESSION_KEY = "sysAdminEntity";


    /**
     * 获取当前登录管理员信息
     * @return
     */
    public static SysAdminEntity getAdminFromSession() {
        Session session = SecurityUtils.getSubject().getSession();
        return (SysAdminEntity) session.getAttribute(ADMIN_SESSION_KEY);
    }

    /**
     * 获取当前登录管理员ID
     * @return
     */
    public static Integer getAdminIdFromSession() {
        SysAdminEntity adminEntity = getAdminFromSession();
        if(null == adminEntity){
            return 0;
        }else{
            return adminEntity.getAdmin_id();
        }
    }

    /**
     * 查询所有父级ID（包括传入的ID）
     * @param adminId
     * @return
     */
    public static void getAllParentAdminId(Set<Integer> parentIds,Integer adminId){
        if(parentIds != null){
            SysAdminEntity sysAdminEntity = adminMapper.selectByPrimaryKey(adminId);
            if(null != sysAdminEntity){
                Integer parentId = sysAdminEntity.getAdmin_parentId() == null ? 0 : sysAdminEntity.getAdmin_parentId();
                if(parentId > 0){
                    boolean add = parentIds.add(parentId);
                    // 判断集合是否添加成功，如果没添加成功说明数据重复，继续查询会陷入死循环查询
                    if(add){
                        getAllParentAdminId(parentIds,parentId);
                    }
                }
            }

        }
    }

    /**
     * 查询所有子类ADMINID
     * @param childIds
     * @param adminIds
     */
    public static void getAllChildAdminId(Set<Integer> childIds,List<Integer> adminIds){
        if(childIds != null && adminIds != null){
            List<Integer> iterList = new ArrayList<>();
            adminIds.forEach(e ->{
                SysAdminEntity entity = new SysAdminEntity();
                entity.setAdmin_parentId(e);
                List<SysAdminEntity> select = adminMapper.select(entity);
                select.forEach(admin ->{
                    Integer admin_id = admin.getAdmin_id();
                    boolean add = childIds.add(admin_id);
                    if(add){
                        iterList.add(admin_id);
                    }
                });
            });
            if(iterList.size() > 0){
                getAllChildAdminId(childIds,iterList);
            }
        }
    }
}
