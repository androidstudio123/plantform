package cn.crm.controller.materiel;





import cn.crm.entity.materiel.RepairGoodsProducerEntity;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepairGoodsImportService;
import cn.crm.service.materiel.RepairGoodsProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/repairgoodsproducer")
@Api(description = "生产厂家配置")
public class RepairGoodsProducerController {


	@Autowired
	private RepairGoodsProducerService repairGoodsProducerService;

	/**
	 * 查询所有生产厂家
	 * @param producer_name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("findRepairGoodsProducerAll")
	@ApiOperation(value = "查询所有生产厂家", notes = "查询所有生产厂家")
	public ResultData findRepairGoodsProducerAll(String producer_name,Integer pageNum,Integer pageSize){
		ResultData resultData = repairGoodsProducerService.findRepairGoodsProducerAll(producer_name,pageNum,pageSize);
		return resultData;
	}

	/**
	 * 新增生产厂家信息
	 * @param repairGoodsProducerEntity
	 * @return
	 */
	@PostMapping("saveRepairGoodsProducer")
	@ApiOperation(value = "新增生产厂家信息", notes = "新增生产厂家信息")
	public ResultData saveRepairGoodsProducer(@RequestBody RepairGoodsProducerEntity repairGoodsProducerEntity){
		ResultData resultData = repairGoodsProducerService.saveRepairGoodsProducer(repairGoodsProducerEntity);
		return resultData;
	}

	/**
	 * 编辑生产厂家信息
	 * @param repairGoodsProducerEntity
	 * @return
	 */
	@PostMapping("updateRepairGoodsProducer")
	@ApiOperation(value = "编辑生产厂家信息", notes = "编辑生产厂家信息")
	public ResultData updateRepairGoodsProducer(@RequestBody RepairGoodsProducerEntity repairGoodsProducerEntity){
		ResultData resultData = repairGoodsProducerService.updateRepairGoodsProducer(repairGoodsProducerEntity);
		return resultData;
	}

	/**
	 * 删除生产厂家信息
	 * @param producer_id
	 * @return
	 */
	@PostMapping("deleteRepairGoodsProducer")
	@ApiOperation(value = "删除生产厂家信息", notes = "删除生产厂家信息")
	public ResultData deleteRepairGoodsProducer(Integer producer_id){
		ResultData resultData = repairGoodsProducerService.deleteRepairGoodsProducer(producer_id);
		return resultData;
	}

	/**
	 * 根据库名称,库类型以及物品类型查询对应的生产厂家(手机端出库使用)
	 * @param repertoryNameId 库名称ID
	 * @param repertoryTypeId 库类型ID
	 * @param goodsTypeId  物品分类ID
	 * @return
	 */
	@GetMapping("/findByCondition")
	@ApiOperation(value = "根据库名称,库类型以及物品类型查询对应的生产厂家(手机端出库使用)", notes = "根据库名称,库类型以及物品类型查询对应的生产厂家(手机端出库使用)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "repertoryNameId", value = "库名称ID", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "repertoryTypeId", value = "库类型ID", required = true, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "goodsTypeId", value = "物品分类ID", required = true, dataType = "int", paramType = "query")
	})
	public ResultData findByCondition(Integer repertoryNameId,Integer repertoryTypeId,Integer goodsTypeId){
		ResultData resultData = repairGoodsProducerService.findByCondition(repertoryNameId, repertoryTypeId, goodsTypeId);
		return resultData;
	}

}
