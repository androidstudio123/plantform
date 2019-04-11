package cn.crm.service.materiel.impl;

import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.materiel.RepairGoodsImportEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.mapper.materiel.RepairGoodsImportMapper;
import cn.crm.mapper.materiel.RepairRepertoryAdminConfigMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.BaseServiceImpl;
import cn.crm.service.materiel.RepairGoodsImportService;
import cn.crm.service.materiel.RepairRepertoryAdminConfigService;
import cn.crm.util.IdGenerator;
import cn.crm.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class RepairRepertoryAdminConfigServiceImpl extends BaseServiceImpl<RepairRepertoryAdminConfigEntity> implements RepairRepertoryAdminConfigService {
	
	@Autowired
	private RepairRepertoryAdminConfigMapper repairRepertoryAdminConfigMapper;


    @Override
    public List<RepairRepertoryAdminConfigEntity> findConfigByAdmin(Integer adminId) {
        if(adminId == null){
           return  null;
        }
        Example example = new Example(RepairRepertoryAdminConfigEntity.class);
        example.createCriteria().andEqualTo("admin_id",adminId);
        List<RepairRepertoryAdminConfigEntity> repairRepertoryAdminConfigEntities = repairRepertoryAdminConfigMapper.selectByExample(example);
        return repairRepertoryAdminConfigEntities;
    }

//    @Override
//    public List<RepairRepertoryAdminConfigEntity> findRepertoryAdminConfig(Integer adminId) {
//        List<RepairRepertoryAdminConfigEntity> repertoryAdminConfig = repairRepertoryAdminConfigMapper.findRepertoryAdminConfig(adminId);
//        return repertoryAdminConfig;
//    }

    @Override
    public ResultData saveRepairRepertoryAdminConfig(Integer repertory_name_id,String admin_ids,Integer repertory_type_id, HttpServletRequest request) {
        //获取当前登陆人信息
//        SysAdminEntity userEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
        if(repertory_name_id == null || repertory_name_id<0){
            return new ResultData(20002,false,"请选择库名称");
        }
        if(StringUtils.isEmpty(admin_ids)){
            return new ResultData(20003,false,"请选择库管人员");
        }
        if(repertory_type_id == null || repertory_type_id < 0){
            return new ResultData(20004,false,"请选择库类型");
        }
        //分割库管人员id
        String[] admin_id = admin_ids.split(",");
        //开始新增
        RepairRepertoryAdminConfigEntity repairRepertoryAdminConfigEntity = new RepairRepertoryAdminConfigEntity();
        for(int i = 0; i<admin_id.length;i++){
            repairRepertoryAdminConfigEntity.setGroup_no(IdGenerator.idGen());
            repairRepertoryAdminConfigEntity.setRepertory_name_id(repertory_name_id);
            repairRepertoryAdminConfigEntity.setAdmin_id(Integer.parseInt(admin_id[i]));
            repairRepertoryAdminConfigEntity.setRepertory_type_id(repertory_type_id);
            int res = repairRepertoryAdminConfigMapper.insertSelective(repairRepertoryAdminConfigEntity);
            if(res<1){
                return new ResultData(20005,false,"执行失败，请重试");
            }
        }
        return new ResultData(20000,true,"执行成功");
    }

    @Override
    public ResultData updateRepairRepertoryAdminConfig(Integer repertory_config_id,Integer repertory_name_id,String admin_ids ,Integer repertory_type_id,HttpServletRequest request) {
        if(repertory_config_id == null || repertory_config_id<0){
            return new ResultData(20002,false,"信息不能为空，请重试");
        }
        //获取当前登陆人信息
        SysAdminEntity userEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
        //先根据id删除库管配置信息，再进行添加库管配置信息
        int result = repairRepertoryAdminConfigMapper.deleteByPrimaryKey(repertory_config_id);
        if(result<1){
            return new ResultData(20003,false,"删除失败，请重试");
        }
        //分割库管人员id
        String[] admin_id = admin_ids.split(",");
        //开始新增
        RepairRepertoryAdminConfigEntity repairRepertoryAdminConfigEntity = new RepairRepertoryAdminConfigEntity();
        for(int i = 0; i<admin_id.length;i++){
            repairRepertoryAdminConfigEntity.setGroup_no(IdGenerator.idGen());
            repairRepertoryAdminConfigEntity.setRepertory_name_id(repertory_name_id);
            repairRepertoryAdminConfigEntity.setAdmin_id(Integer.parseInt(admin_id[i]));
            repairRepertoryAdminConfigEntity.setRepertory_type_id(repertory_type_id);
            int res = repairRepertoryAdminConfigMapper.insertSelective(repairRepertoryAdminConfigEntity);
            if(res<1){
                return new ResultData(20005,false,"执行失败，请重试");
            }
        }
        return new ResultData(20001,false,"网络错误，请重试");
    }

    @Override
    public ResultData deleteRepairRepertoryAdminConfig(Integer id, HttpServletRequest request) {
        //获取当前登陆人信息
        SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("userEntity");
        if(sysAdminEntity == null){
            return new ResultData(20001,false,"身份过期，请重新登录");
        }
        if(id == null || id < 0){
            return new ResultData(20002,false,"请选择库管配置信息");
        }
        int i = repairRepertoryAdminConfigMapper.deleteByPrimaryKey(id);
        if(i>0){
            return new ResultData(20000,true,"执行成功");
        }
        return new ResultData(20000,true,"网络错误，请重试");
    }
}
