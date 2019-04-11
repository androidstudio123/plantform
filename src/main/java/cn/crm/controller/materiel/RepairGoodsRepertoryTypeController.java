package cn.crm.controller.materiel;





import cn.crm.entity.materiel.RepairGoodsRepertoryTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepairGoodsImportService;
import cn.crm.service.materiel.RepairGoodsRepertoryTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/repairgoodsrepertorytype")
@Api(description = "库类型配置")
public class RepairGoodsRepertoryTypeController {


	@Autowired
	private RepairGoodsRepertoryTypeService repairGoodsRepertoryTypeService;

	/**
	 * 查询所有库类型
	 * @param pageNum
	 * @param pageSize
	 * @param repertory_type_name
	 * @return
	 */
	@GetMapping("findRepairGoodsRepertoryTypeAll")
	@ApiOperation(value = "查询库类型", notes = "查询库类型")
	public ResultData findRepairGoodsRepertoryTypeAll(Integer pageNum,Integer pageSize,String repertory_type_name){
		ResultData resultData = repairGoodsRepertoryTypeService.findRepairGoodsRepertoryTypeAll(pageNum,pageSize,repertory_type_name);
		return resultData;
	}

	/**
	 * 新增库类型
	 * @param repairGoodsRepertoryTypeEntity
	 * @return
	 */
	@PostMapping("saveRepairGoodsRepertoryType")
	@ApiOperation(value = "新增库类型", notes = "新增库类型")
	public ResultData saveRepairGoodsRepertoryType(@RequestBody RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity){
		ResultData resultData = repairGoodsRepertoryTypeService.saveRepairGoodsRepertoryType(repairGoodsRepertoryTypeEntity);
		return resultData;
	}

	/**
	 * 编辑库类型
	 * @param repairGoodsRepertoryTypeEntity
	 * @return
	 */
	@PostMapping("updateRepairGoodsRepertoryType")
	@ApiOperation(value = "编辑库类型", notes = "编辑库类型")
	public ResultData updateRepairGoodsRepertoryType(@RequestBody RepairGoodsRepertoryTypeEntity repairGoodsRepertoryTypeEntity){
		ResultData resultData = repairGoodsRepertoryTypeService.updateRepairGoodsRepertoryType(repairGoodsRepertoryTypeEntity);
		return resultData;
	}

	/**
	 * 删除库类型
	 * @param repertory_type_id
	 * @return
	 */
	@PostMapping("deleteRepairGoodsRepertoryType")
	@ApiOperation(value = "删除库类型", notes = "删除库类型")
	public ResultData deleteRepairGoodsRepertoryType(Integer repertory_type_id){
		ResultData resultData = repairGoodsRepertoryTypeService.deleteRepairGoodsRepertoryType(repertory_type_id);
		return resultData;
	}

	/**
	 * 根据微信用户ID与库名ID查询其拥有的库类型(手机端出库使用)
	 * @param userId  微信用户ID
	 * @param repertoryNameId  库名称ID
	 * @return
	 */
	@GetMapping("/findRepertoryTypeByCondition")
	@ApiOperation(value = "根据微信用户ID与库名ID查询其拥有的库类型(手机端出库使用)", notes = "根据微信用户ID与库名ID查询其拥有的库类型(手机端出库使用)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "微信用户ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "repertoryNameId", value = "库名称ID", required = false, dataType = "int", paramType = "query")
	})
	public ResultData findRepertoryTypeByCondition(Integer userId,Integer repertoryNameId){
		ResultData resultData = repairGoodsRepertoryTypeService.findRepertoryTypeByCondition(userId, repertoryNameId);
		return resultData;
	}
}
