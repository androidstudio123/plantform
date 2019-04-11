package cn.crm.controller.materiel;


import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepertoryExportRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/repertoryexportrole")
@Api(description = "库管角色配置相关操作")
public class RepertoryExportRoleController {


	@Autowired
	private RepertoryExportRoleService repertoryExportRoleService;


	/**
	 * 新增一个库管角色
	 * @param roleName  库管角色名称
	 * @param roleConfig 角色配置字符串
	 * @return
	 */
	@PostMapping("saveExportRole")
	@ApiOperation(value = "新增一个库管角色", notes = "新增一个库管角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleName", value = "库管角色名称", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roleConfig", value = "角色配置字符串", required = false, dataType = "string", paramType = "query")
	})
	public ResultData saveExportRole(String roleName,String roleConfig,HttpServletRequest request){
		ResultData resultData = repertoryExportRoleService.saveExportRole(roleName,roleConfig,request);
		return resultData;
	}

	/**
	 * 删除一个库管角色
	 * @param roleId  角色ID
	 * @return
	 */
	@GetMapping("/deleteExportRole")
	@ApiOperation(value = "删除一个库管角色", notes = "删除一个库管角色")
	@ApiImplicitParam(name = "roleId", value = "要删除的角色ID", required = false, dataType = "int", paramType = "query")
	public ResultData deleteExportRole(Integer roleId){
		ResultData resultData = repertoryExportRoleService.deleteExportRole(roleId);
		return resultData;
	}

	/**
	 * 修改一个库管角色
	 * @param roleId  角色ID
	 * @param roleName 库管角色名称
	 * @param roleConfig  角色配置字符串
	 * @return
	 */
	@ApiOperation(value = "修改一个库管角色", notes = "修改一个库管角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleName", value = "库管角色名称", required = false, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "roleId", value = "角色ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "roleConfig", value = "角色配置字符串", required = false, dataType = "string", paramType = "query")
	})
	@PostMapping("/updateExportRole")
	public ResultData updateExportRole(Integer roleId,String roleName,String roleConfig){
		ResultData resultData = repertoryExportRoleService.updateExportRole(roleId, roleName, roleConfig);
		return resultData;
	}

	/**
	 * 根据当前登录的管理员查询出库角色信息
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据当前登录的管理员查询出库角色信息", notes = "根据当前登录的管理员查询出库角色信息")
	@GetMapping("/findExportRoleByAdmin")
	public ResultData findExportRoleByAdmin(HttpServletRequest request,String exportRoleName ){
		ResultData resultData = repertoryExportRoleService.findExportRoleByAdmin(request,exportRoleName);
		return resultData;
	}

	/**
	 * 查询指定库管角色的详情
	 * @param roleId 库管角色ID
	 * @return
	 */
	@ApiOperation(value = "查询指定库管角色的详情", notes = "查询指定库管角色的详情")
	@GetMapping("/findExportRoleDetail")
	@ApiImplicitParam(name = "roleId", value = "库管角色ID", required = false, dataType = "int", paramType = "query")
	public ResultData findExportRoleDetail(Integer roleId){
		ResultData resultData = repertoryExportRoleService.findExportRoleDetail(roleId);
		return resultData;
	}

}
