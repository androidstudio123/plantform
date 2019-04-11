package cn.crm.controller.materiel;


import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.materiel.RepairGoodsRepertoryNameEntity;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepairGoodsRepertoryNameService;
import cn.crm.service.materiel.RepairGoodsRepertoryTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value="/repairgoodsrepertoryname")
@Api(description = "库名称配置")
public class RepairGoodsRepertoryNameController {


	@Autowired
	private RepairGoodsRepertoryNameService repairGoodsRepertoryNameService;

	@Autowired
	private RepairGoodsRepertoryTypeService repairGoodsRepertoryTypeService;
	/**
	 * 查询所有库名称
	 * @param pageNum
	 * @param pageSize
	 * @param repertory_name
	 * @return
	 */
	@GetMapping("findRepairGoodsRepertoryName")
	@ApiOperation(value = "查询库名称", notes = "查询库名称")
	public ResultData findRepairGoodsRepertoryName(Integer pageNum,Integer pageSize,String repertory_name){
		ResultData resultData = repairGoodsRepertoryNameService.findRepairGoodsRepertoryName(pageNum,pageSize,repertory_name);
		return resultData;
	}

	/**
	 * 查询库名称，不分页
	 * @return
	 */
	@GetMapping("findRepairRepertoryName")
	@ApiOperation(value = "查询库名称不分页", notes = "查询库名称不分页")
	public ResultData findRepairRepertoryName(){
		List<RepairGoodsRepertoryNameEntity> repairGoodsRepertoryNameEntities = repairGoodsRepertoryNameService.queryAll();
		return new ResultData(20000,true,"查询成功",repairGoodsRepertoryNameEntities);
	}

	/**
	 * 新增库管
	 * @param repairGoodsRepertoryNameEntity
	 * @return
	 */
	@PostMapping("saveRepairGoodsRepertoryName")
	@ApiOperation(value = "添加库名称", notes = "添加库名称")
	public ResultData saveRepairGoodsRepertoryName(@RequestBody RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity){
		ResultData resultData = repairGoodsRepertoryNameService.saveRepairGoodsRepertoryName(repairGoodsRepertoryNameEntity);
		return resultData;
	}

	/**
	 * 编辑库管
	 * @param repairGoodsRepertoryNameEntity
	 * @return
	 */
	@PostMapping("updateRepairGoodsRepertoryName")
	@ApiOperation(value = "编辑库名称", notes = "编辑库名称")
	public ResultData updateRepairGoodsRepertoryName(@RequestBody RepairGoodsRepertoryNameEntity repairGoodsRepertoryNameEntity){
		ResultData resultData = repairGoodsRepertoryNameService.updateRepairGoodsRepertoryName(repairGoodsRepertoryNameEntity);
		return resultData;
	}

	/**
	 * 删除库管
	 * @param repertory_name_id
	 * @return
	 */
	@PostMapping("deleteRepairGoodsRepertoryName")
	@ApiOperation(value = "删除库名称", notes = "删除库名称")
	public ResultData deleteRepairGoodsRepertoryName(Integer repertory_name_id){
		ResultData resultData = repairGoodsRepertoryNameService.deleteRepairGoodsRepertoryName(repertory_name_id);
		return resultData;
	}

	/**
	 * 根据微信用户ID查询其管理的库(手机端出库使用)
	 * @param userId  前台微信用户ID
	 * @return
	 */
	@GetMapping("/findRepertoryNameByUserId")
	@ApiOperation(value = "根据微信用户ID查询其管理的库(手机端出库使用)", notes = "根据微信用户ID查询其管理的库(手机端出库使用)")
	@ApiImplicitParam(name = "userId", value = "前台微信用户ID", required = false, dataType = "int", paramType = "query")
	public ResultData findRepertoryNameByUserId(Integer userId){
		ResultData resultData = repairGoodsRepertoryNameService.findRepertoryNameByUserId(userId);
		return resultData;
	}

	/**
	 * 查询当前管理员及其下级管理员管理的库名称
	 * @param request
	 * @return
	 */
	@GetMapping("findRepertoryNameByAdmin")
	public ResultData findRepertoryNameByAdmin(HttpServletRequest request){
		//获取登录人信息
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20001,false,"身份过期，请重试");
		}
		ResultData resultData = repairGoodsRepertoryNameService.findRepertoryNameByAdmin(sysAdminEntity.getAdmin_id());
		return resultData;
	}

	/**
	 * 根据选中的库名称查询对应的库类型
	 * @param repertory_name_id
	 * @param request
	 * @return
	 */
	@GetMapping("findRepertoryTypeByNameId")
	public ResultData findRepertoryTypeByNameId(Integer repertory_name_id,HttpServletRequest request){
		//获取用户身份信息
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20001,false,"身份过期，请重试");
		}
		ResultData resultData = repairGoodsRepertoryTypeService.findRepertoryNameByAdmin(repertory_name_id,sysAdminEntity.getAdmin_id());
		return resultData;
	}
}
