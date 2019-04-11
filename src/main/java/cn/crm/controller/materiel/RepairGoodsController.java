package cn.crm.controller.materiel;

import cn.crm.entity.materiel.RepairGoodsEntity;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepairGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value="/repairgoods")
@Api(description = "物料管理相关操作")
public class RepairGoodsController {


	@Autowired
	private RepairGoodsService repairGoodsService;

	/**
	 * 根据当前登录管理员信息,查询其管理的库存
	 * @param request
	 * @param repertoryNameId  库ID
	 * @return
	 */
	@ApiOperation(value = "根据当前登录管理员信息,查询其管理的库存", notes = "根据当前登录管理员信息,查询其管理的库存")
	@ApiImplicitParam(name = "repertoryNameId", value = "库ID", required = false, dataType = "int", paramType = "query")
	@GetMapping("/findAllGoodsByAdmin")
	public ResultData findAllGoodsByAdmin(HttpServletRequest request,Integer repertoryNameId){
		ResultData resultData = repairGoodsService.findAllGoodsByAdmin(request, repertoryNameId);
		return resultData;
	}

	/**
	 * 根据当前登录管理员信息,统计物品数量
	 * @param goodsName   物品名称
	 * @param goodsTypeId  物品分类ID
	 * @param producerId  生产厂家ID
	 * @param repertoryNameId  库名称ID
	 * @param repertoryTypeId 库类型ID
	 * @return
	 */
	@ApiOperation(value = "根据当前登录管理员信息,统计物品数量", notes = "根据当前登录管理员信息,统计物品数量")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "goodsName", value = "物品名称", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "goodsTypeId", value = "物品分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "producerId", value = "生产厂家ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "repertoryNameId", value = "库名称ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "repertoryTypeId", value = "库类型ID", required = false, dataType = "int", paramType = "query")
	})
	@GetMapping("/countGoodsByAdmin")
	public ResultData countGoodsByAdmin(String goodsName,Integer goodsTypeId,Integer producerId,
										Integer repertoryNameId,Integer repertoryTypeId,HttpServletRequest request){
		ResultData resultData = repairGoodsService.countGoodsByAdmin(goodsName, goodsTypeId, producerId, repertoryNameId, repertoryTypeId, request);
		return resultData;
	}

	/**
	 * 根据库名称,库类型,生产厂家以及物品类型查询对应的物品(手机端出库使用)
	 * @param repertoryNameId  库名称ID
	 * @param repertoryTypeId 库类型ID
	 * @param goodsTypeId  物品类型ID
	 * @param producerId  生产厂家ID
	 * @return
	 */
	@GetMapping("/findByCondition")
	@ApiOperation(value = "根据库名称,库类型,生产厂家以及物品类型查询对应的物品(手机端出库使用)", notes = "根据库名称,库类型,生产厂家以及物品类型查询对应的物品(手机端出库使用)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "goodsTypeId", value = "物品分类ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "producerId", value = "生产厂家ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "repertoryNameId", value = "库名称ID", required = false, dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "repertoryTypeId", value = "库类型ID", required = false, dataType = "int", paramType = "query")
	})
	public ResultData findByCondition(Integer repertoryNameId,Integer repertoryTypeId,Integer producerId,Integer goodsTypeId){
		ResultData resultData = repairGoodsService.findByCondition(repertoryNameId, repertoryTypeId, producerId, goodsTypeId);
		return resultData;
	}



	@GetMapping("findRepairGoodsByName")
	public ResultData findRepairGoodsByName(String goods_name){
	    if(goods_name == null || goods_name == ""){
            List<RepairGoodsEntity> repairGoodsEntities = repairGoodsService.queryAll();
            return new ResultData(20000,true,"查询成功",repairGoodsEntities);
        }
		Example example = new Example(RepairGoodsEntity.class);
		example.createCriteria().andLike("goods_name","%"+goods_name+"%");
		List<RepairGoodsEntity> repairGoodsEntities = repairGoodsService.queryExampleForList(example);
		return new ResultData(20000,true,"查询成功",repairGoodsEntities);
	}

}
