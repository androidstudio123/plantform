package cn.crm.controller.materiel;

import cn.crm.entity.materiel.RepairGoodsTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepairGoodsService;
import cn.crm.service.materiel.RepairGoodsTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value="/repairgoodstype")
@Api(description = "物料类型配置")
public class RepairGoodsTypeController {


	@Autowired
	private RepairGoodsTypeService repairGoodsTypeService;

	/**
	 * 查询所有商品类型
	 * @param goods_type_name
	 * @return
	 */
	@GetMapping("findRepairGoodsType")
	@ApiOperation(value = "查询物料类型", notes = "查询物料类型")
	public ResultData findRepairGoodsType(String goods_type_name){
		ResultData resultData = repairGoodsTypeService.findRepairGoodsType(goods_type_name);
		return resultData;
	}

	/**
	 * 添加物料类型
	 * @param repairGoodsTypeEntity
	 * @return
	 */
	@PostMapping("saveRepairGoodsType")
	@ApiOperation(value = "新增物料类型", notes = "新增物料类型")
	public ResultData saveRepairGoodsType(@RequestBody RepairGoodsTypeEntity repairGoodsTypeEntity){
		ResultData resultData = repairGoodsTypeService.saveRepairGoodsType(repairGoodsTypeEntity);
		return resultData;
	}

	/**
	 * 编辑物料类型
	 * @param repairGoodsTypeEntity
	 * @return
	 */
	@PostMapping("updateRepairGoodsType")
	@ApiOperation(value = "编辑物料类型", notes = "编辑物料类型")
	public ResultData updateRepairGoodsType(@RequestBody RepairGoodsTypeEntity repairGoodsTypeEntity){
		ResultData resultData = repairGoodsTypeService.updateRepairGoodsType(repairGoodsTypeEntity);
		return resultData;
	}

	/**
	 * 删除物料类型
	 * @param goods_type_id
	 * @return
	 */
	@PostMapping("deleteRepairGoodsType")
	@ApiOperation(value = "删除物料类型", notes = "删除物料类型")
	public ResultData deleteRepairGoodsType(Integer goods_type_id){
		ResultData resultData = repairGoodsTypeService.deleteRepairGoodsType(goods_type_id);
		return resultData;
	}

	/**
	 * 根据库名称与库类型查询对应的物品分类(手机端出库使用)
	 * @param repertoryNameId  库名称ID
	 * @param repertoryTypeId  库类型ID
	 * @return
	 */
	@ApiOperation(value = "根据库名称与库类型查询对应的物品分类(手机端出库使用)", notes = "根据库名称与库类型查询对应的物品分类(手机端出库使用)")
	@GetMapping("/findByCondition")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "repertoryTypeId", value = "库类型ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "repertoryNameId", value = "库名称ID", required = false, dataType = "int", paramType = "query")
	})
	public ResultData findByCondition(Integer repertoryNameId,Integer repertoryTypeId ){
		ResultData resultData = repairGoodsTypeService.findByCondition(repertoryNameId, repertoryTypeId);
		return resultData;
	}

}
