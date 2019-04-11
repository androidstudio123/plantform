package cn.crm.controller.materiel;





import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.materiel.RepairRepertoryAdminConfigEntity;
import cn.crm.mapper.materiel.RepairRepertoryAdminConfigVOMapper;
import cn.crm.result.ResultCode;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepairGoodsImportService;
import cn.crm.service.materiel.RepairRepertoryAdminConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value="/repairrepertoryadminconfig")
@Api(description = "库管理员配置")
public class RepairRepertoryAdminConfigController {


	@Autowired
	private RepairRepertoryAdminConfigService repairRepertoryAdminConfigService;

	@Autowired
	private RepairRepertoryAdminConfigVOMapper repairRepertoryAdminConfigVOMapper;
	/**
	 * 根据管理员信息,查询其管理的仓库集合
	 * @param adminId  管理员ID
	 * @return
	 */
	@GetMapping("/adminId")
	public ResultData findConfigByAdmin(Integer adminId){
		List<RepairRepertoryAdminConfigEntity> configByAdmin = repairRepertoryAdminConfigService.findConfigByAdmin(adminId);
		return new ResultData(ResultCode.SUCCESS.getCode(),true,ResultCode.SUCCESS.getMessage(),configByAdmin);
	}

	/**
	 * 查询所有库配置信息
	 * @param request
	 * @return
	 */
	@GetMapping("findRepairRepertoryAdminConfigAll")
	@ApiOperation(value = "查询所有库配置信息", notes = "查询所有库配置信息")
	public ResultData findRepairRepertoryAdminConfigAll(HttpServletRequest request){
//		SysAdminEntity userEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
//		if(userEntity == null){
//			return new ResultData(20001,false,"登录过期请重试");
//		}
		List<RepairRepertoryAdminConfigEntity> repertoryAdminConfig = repairRepertoryAdminConfigVOMapper.findRepertoryAdminConfig();
		return new ResultData(20000,true,"查询成功",repertoryAdminConfig);
	}

	/**
	 * 新增库管配置信息
	 * @param request
	 * @return
	 */
	@PostMapping("saveRepairRepertoryAdminConfig")
	@ApiOperation(value = "新增库管配置信息", notes = "新增库管配置信息")
	public ResultData saveRepairRepertoryAdminConfig(Integer repertory_name_id,String admin_ids ,Integer repertory_type_id,HttpServletRequest request){
		ResultData resultData = repairRepertoryAdminConfigService.saveRepairRepertoryAdminConfig(repertory_name_id,admin_ids,repertory_type_id,request);
		return resultData;
	}

	/**
	 * 编辑库管配置信息
	 * @param request
	 * @return
	 */
	@PostMapping("updateRepairRepertoryAdminConfig")
	@ApiOperation(value = "编辑库管配置信息", notes = "编辑库管配置信息")
	public ResultData updateRepairRepertoryAdminConfig(Integer repertory_config_id,Integer repertory_name_id,String admin_ids ,Integer repertory_type_id,HttpServletRequest request){
		ResultData resultData = repairRepertoryAdminConfigService.updateRepairRepertoryAdminConfig(repertory_config_id,repertory_name_id,admin_ids,repertory_type_id,request);
		return resultData;
	}

	/**
	 * 删除库管配置信息
	 * @param id
	 * @param request
	 * @return
	 */
	@PostMapping("deleteRepairRepertoryAdminConfig")
	@ApiOperation(value = "删除库管配置信息", notes = "删除库管配置信息")
	public ResultData deleteRepairRepertoryAdminConfig(Integer id,HttpServletRequest request){
		ResultData resultData = repairRepertoryAdminConfigService.deleteRepairRepertoryAdminConfig(id,request);
		return resultData;
	}


}
