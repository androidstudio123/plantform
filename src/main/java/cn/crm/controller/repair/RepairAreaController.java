package cn.crm.controller.repair;


import cn.crm.entity.repair.RepairAreaEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.result.ResultData;
import cn.crm.service.repair.RepairAreaService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/repairarea")
public class RepairAreaController {


	@Autowired
	private RepairAreaService repairAreaService;

	/**
	 * 查询所有报修区域信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
 	@GetMapping("findRepairArea")
	@SystemLog(methods = "查询报修区域",module = "查询报修区域")
	@ApiImplicitParam(name = "school_id",value = "学校id",dataType = "int",paramType = "query")
	public ResultData findRepairArea(Integer pageNum,Integer pageSize,Integer school_id){
		ResultData resultData = repairAreaService.findRepairArea(pageNum,pageSize,school_id);
 		return resultData;
	}

	/**
	 * 新增报修区域
	 * @param repairAreaEntity
	 * @return
	 */
	@PostMapping("saveReapirArea")
	@SystemLog(methods = "新增报修区域",module = "新增报修区域")
	@ApiImplicitParam(name = "repairAreaEntity",value = "报修区域信息",dataType = "RepairAreaEntity",paramType = "form")
	public ResultData saveRepairArea(@RequestBody RepairAreaEntity repairAreaEntity){
		ResultData resultData = repairAreaService.saveRepairArea(repairAreaEntity);
 		return resultData;
	}
	/**
	 * 编辑报修区域
	 * @param repairAreaEntity
	 * @return
	 */
	@PostMapping("updateReapirArea")
	@SystemLog(methods = "编辑报修区域",module = "编辑报修区域")
	@ApiImplicitParam(name = "repairAreaEntity",value = "报修区域信息",dataType = "RepairAreaEntity",paramType = "form")
	public ResultData updateReapirArea(@RequestBody RepairAreaEntity repairAreaEntity){
		ResultData resultData = repairAreaService.updateReapirArea(repairAreaEntity);
		return resultData;
	}

	/**
	 * 删除报修区域
	 * @param id
	 * @return
	 */
	@PostMapping("deleteRepairArea")
	@SystemLog(methods = "删除报修区域",module = "删除报修区域")
	@ApiImplicitParam(name = "id",value = "报修区域id",dataType = "int",paramType = "form")
	public ResultData deleteReapirArea(Integer id){
		ResultData resultData = repairAreaService.deleteReapirArea(id);
		return resultData;
	}
}
