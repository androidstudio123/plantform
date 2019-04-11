package cn.crm.controller.repair;


import cn.crm.entity.repair.RepairAreaEntity;
import cn.crm.entity.repair.RepairRoleEntity;
import cn.crm.entity.repair.RepairRoleFunTypeAreaEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.mapper.repair.RepairFunctionMapper;
import cn.crm.mapper.repair.RepairRoleFunTypeAreaMapper;
import cn.crm.mapper.repair.RepairRoleMapper;
import cn.crm.result.ResultData;
import cn.crm.service.repair.RepairAreaService;
import cn.crm.service.repair.RepairFunctionService;
import cn.crm.service.repair.RepairRoleFunAreaService;
import cn.crm.service.repair.RepairRoleService;
import cn.crm.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/repairrole")
public class RepairRoleController {


	@Autowired
	private RepairRoleService repairRoleService;
	@Autowired
	private RepairRoleFunAreaService repairRoleFunAreaService;
	@Autowired
	private RepairFunctionMapper repairFunctionMapper;
	@Autowired
	private RepairAreaService repairAreaService;
	/**
	 * 新增报修角色
	 * @param roleName
	 * @param fids
	 * @param otids
	 * @param pids
	 * @return
	 */
	@PostMapping("saveRepairRole")
	@SystemLog(methods = "新增报修角色信息",module = "新增报修角色信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "角色名称",value = "roleName",dataType = "String",paramType = "form"),
			@ApiImplicitParam(name = "功能id",value = "fids",dataType = "String",paramType = "form"),
			@ApiImplicitParam(name = "工单类型id",value = "otids",dataType = "String",paramType = "form"),
			@ApiImplicitParam(name = "工单区域id",value = "pids",dataType = "String",paramType = "form")
	})
	public ResultData saveRepairRole(String roleName,String fids,String otids,String pids,Integer is_default,Integer role_flag){
		ResultData resultData = repairRoleService.saveRepairRole(roleName,fids,otids,pids,is_default,role_flag);
		return resultData;
	}

	/**
	 * 编辑角色配置信息
	 * @param id
	 * @param roleName
	 * @param fids
	 * @param otids
	 * @param pids
	 * @return
	 */
	@PostMapping("updateRepairRole")
	@SystemLog(methods = "编辑报修角色信息",module = "编辑报修角色信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "角色id",value = "id",dataType = "int",paramType = "form"),
			@ApiImplicitParam(name = "角色名称",value = "roleName",dataType = "String",paramType = "form"),
			@ApiImplicitParam(name = "功能id",value = "fids",dataType = "String",paramType = "form"),
			@ApiImplicitParam(name = "工单类型id",value = "otids",dataType = "String",paramType = "form"),
			@ApiImplicitParam(name = "工单区域id",value = "pids",dataType = "String",paramType = "form")
	})
	public ResultData updateRepairRole(Integer id,String roleName,String fids,String otids,String pids,Integer is_default,Integer role_flag){
		ResultData resultData = repairRoleService.updateRepairRole(id,roleName,fids,otids,pids,role_flag,is_default);
		return resultData;
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@PostMapping("deleteRepairRole")
	@SystemLog(methods = "删除报修角色信息",module = "删除报修角色信息")
	@ApiImplicitParam(name = "角色id",value = "id",dataType = "int",paramType = "form")
	public ResultData deleteRepairRole(Integer id){
		ResultData resultData = repairRoleService.deleteRepair(id);
		return resultData;
	}

	/**
	 * 查询角色配置信息
	 * @return
	 */
	@GetMapping("findRepairRole")
	@SystemLog(methods = "查询角色配置信息",module = "查询角色配置信息")
	public ResultData findRepairRole(String roleName){
		List<RepairRoleFunTypeAreaEntity> repairRole = null;
		repairRole = repairRoleFunAreaService.findRepairRole(roleName);
//		for(int i = 0; i<repairRole.size(); i++){
//			RepairRoleFunTypeAreaEntity repairRoleFunTypeAreaEntity = repairRole.get(i);
//			String a_id = repairRoleFunTypeAreaEntity.getA_id();
//			if(StringUtils.isNotEmpty(a_id)){
//				//分割区域字符串
//				String[] aid = a_id.split(",");
//				//根据aid查询区域表中的数据
//				RepairAreaEntity repairAreaEntity = repairAreaService.findByPrimaryKey(aid);
//                RepairAreaEntity allCatName = getAllCatName(repairAreaEntity);
//                //根据aid查询所有子级菜单名称
//
//            }
//		}
		return new ResultData(20000,true,"查询成功",repairRole);
	}

    /**
     * 根据子类id查询所有父类的id和名称
     * @return
     */
    public RepairAreaEntity getAllCatName(RepairAreaEntity repairAreaEntity){
        try {
            //父级分类id
            Integer parent_id = repairAreaEntity.getParent_id();
            String area_name = repairAreaEntity.getArea_name();
            if(repairAreaEntity!=null && parent_id!=0){
                //父级分类名称
                String areaParentName=repairAreaEntity.getArea_name();
                //拼接所有分类名称
                area_name=areaParentName+"/"+area_name;
                repairAreaEntity.setArea_name(area_name);
                return getAllCatName(repairAreaEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public RepairAreaEntity getChildrenName(RepairAreaEntity repairAreaEntity){
        try{
            //获取区域id
            Integer area_id = repairAreaEntity.getArea_id();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
