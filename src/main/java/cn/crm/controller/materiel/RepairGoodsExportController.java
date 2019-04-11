package cn.crm.controller.materiel;


import cn.crm.entity.materiel.RepairGoodsExportEntity;
import cn.crm.mapper.materiel.RepairGoodsExportMapper;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.RepairGoodsExportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cn.crm.util.PageUtil;
import cn.crm.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
@RequestMapping(value="/repairgoodsexport")
@Api(description = "手机端出库相关操作")
public class RepairGoodsExportController {


	@Autowired
	private RepairGoodsExportService repairGoodsExportService;

	@Autowired
	private RepairGoodsExportMapper repairGoodsExportMapper;
	/**
	 * 从仓库里出货物
	 * @param exportEntity
	 * @return
	 */
    @ApiOperation(value = "从仓库里出货物", notes = "从仓库里出货物")
    @PostMapping("/saveExportGoods")
	public ResultData saveExportGoods(@RequestBody RepairGoodsExportEntity exportEntity){
		ResultData resultData = repairGoodsExportService.saveExportGoods(exportEntity);
		return resultData;

	}

    /**
     * 根据维修人员查询出库记录
     * @param userId    用户ID
     * @param goodsTypeId  物品分类ID
     * @param repertoryTypeId  库类型ID
     * @param repertoryNameId   库名称ID
     * @param flag  维修类型标识 0维修 1换新
     * @return
     */
    @GetMapping("/findByUser")
    @ApiOperation(value = "根据维修人员查询出库记录", notes = "根据维修人员查询出库记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag", value = "维修类型标识 0维修 1换新", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodsTypeId", value = "物品分类ID", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "repertoryTypeId", value = "库类型ID", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "repertoryNameId", value = "库名称ID", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = false, dataType = "int", paramType = "query")
    })
	public ResultData findByUser(Integer userId,Integer goodsTypeId,Integer repertoryTypeId,Integer repertoryNameId,Integer flag){
        ResultData resultData = repairGoodsExportService.findByUser(userId, goodsTypeId, repertoryTypeId, repertoryNameId, flag);
        return resultData;
    }


	/**
	 * 查询所有的出库纪录信息
	 * @param flag 0 换新  1 维修
	 * @param goods_name
	 * @param goods_type_id
	 * @param producer_id
	 * @param repertory_name_id
	 * @param repertory_type_id
	 * @return
	 */
	@GetMapping("findRepairGoodsExportAll")
	@ApiOperation(value = "查询出库纪录", notes = "查询出库纪录")
	public ResultData findRepairGoodsExportAll(Integer flag,String goods_name,Integer goods_type_id,Integer producer_id,Integer repertory_name_id,Integer repertory_type_id ){
		Example example = new Example(RepairGoodsExportEntity.class);
		Example.Criteria criteria = example.createCriteria();
		if(flag == 1){
		    //查询出每一条入库纪录
            criteria.andEqualTo("flag",1);
            if(StringUtils.isNotEmpty(goods_name)){
                criteria.andLike("goods_name","%"+goods_name+"%");
            }
            if(producer_id !=null){
                criteria.andEqualTo("producer_id",producer_id);
            }
            if(repertory_name_id !=null){
                criteria.andEqualTo("repertory_name_id",repertory_name_id);
            }
            if(goods_type_id !=null){
                criteria.andEqualTo("goods_type_id",goods_type_id);
            }
            if(repertory_type_id !=null){
                criteria.andEqualTo("repertory_type_id",repertory_type_id);
            }
            PageUtil.startPage();
            PageInfo<RepairGoodsExportEntity> repairGoodsExportEntityPageInfo = repairGoodsExportService.queryPageForListByEX(example);
            return new ResultData(20000,true,"查询成功",repairGoodsExportEntityPageInfo);
        }
		PageUtil.startPage();
		List<RepairGoodsExportEntity> exportRecordCount = repairGoodsExportMapper.findExportRecordCount(goods_name,producer_id,repertory_name_id,goods_type_id,repertory_type_id);
		return new ResultData(20000,true,"查询成功",new PageInfo<>(exportRecordCount));
	}
	@GetMapping("findExportCount")
	@ApiOperation(value = "查询出库汇总", notes = "查询出库汇总")
	public ResultData findExportCount(Integer flag,String goods_name,Integer goods_type_id,Integer repertory_name_id,Integer repertory_type_id,Integer producer_id ){
		if(flag == 1){
			PageUtil.startPage();
			List<RepairGoodsExportEntity> exportSumCount = repairGoodsExportMapper.findExportSumCount(goods_name, goods_type_id, repertory_name_id, repertory_type_id, producer_id);
			return new ResultData(20000,true,"查询成功",new PageInfo<>(exportSumCount));
		}
		PageUtil.startPage();
		List<RepairGoodsExportEntity> exportRecordCount = repairGoodsExportMapper.findExportRecordCount(goods_name,producer_id,repertory_name_id,goods_type_id,repertory_type_id);
		return new ResultData(20000,true,"查询成功",new PageInfo<>(exportRecordCount));
	}
	@GetMapping("findSum")
	@ApiOperation(value = "查询换新总数量", notes = "查询换新总数量")
	public ResultData findSum(){
		Integer sum = repairGoodsExportMapper.findSum();
		return new ResultData(20000,true,"查询成功",sum);
	}
	@GetMapping("findCount")
	@ApiOperation(value = "查询维修总次数", notes = "查询维修总次数")
	public ResultData findCount(){
		Integer count = repairGoodsExportMapper.findCount();
		return new ResultData(20000,true,"查询成功",count);
	}

}
