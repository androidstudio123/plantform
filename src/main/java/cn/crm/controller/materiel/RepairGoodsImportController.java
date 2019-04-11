package cn.crm.controller.materiel;





import cn.crm.entity.SysAdminEntity;
import cn.crm.entity.materiel.*;
import cn.crm.result.ResultData;
import cn.crm.service.materiel.*;
import cn.crm.util.IdGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
@RequestMapping(value="/repairgoodsimport")
@Api(description = "入库配置")
public class RepairGoodsImportController {


	@Autowired
	private RepairGoodsImportService repairGoodsImportService;
	@Autowired
	private RepairGoodsProducerService repairGoodsProducerService;
	@Autowired
	private RepairGoodsRepertoryTypeService repairGoodsRepertoryTypeService;
	@Autowired
	private RepairGoodsRepertoryNameService repairGoodsRepertoryNameService;
	@Autowired
	private RepairGoodsTypeService repairGoodsTypeService;

	/**
	 * 查询当前登录人员入库信息
	 * @param request
	 * @return
	 */
	@GetMapping("findRepairGoodsImportAll")
	@ApiOperation(value = "查询当前登录人员入库信息", notes = "查询当前登录人员入库信息")
	public ResultData findRepairGoodsImportAll(HttpServletRequest request,String goods_name){
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20008,false,"身份过期，请重试");
		}
		ResultData resultData = repairGoodsImportService.findRepairGoodsImportAll(sysAdminEntity.getAdmin_id(),goods_name);
		return resultData;
	}

	/**
	 * 新增入库信息
	 * @param repairGoodsImportEntity
	 * @param request
	 * @return
	 */
	@PostMapping("saveRepairGoodsImport")
	@ApiOperation(value = "新增入库信息", notes = "新增入库信息")
	public ResultData saveRepairGoodsImport(@RequestBody RepairGoodsImportEntity repairGoodsImportEntity, HttpServletRequest request){
//		if(repairGoodsImportEntity == null){
//			return new ResultData(20001,false,"信息不能为空，请重试");
//		}
		//获取用户信息
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20003,false,"身份过期，请重新登录");
		}
		//查询生产厂家名称
		if(repairGoodsImportEntity.getProducer_id()==null){
			repairGoodsImportEntity.setProducer_name("");
		}
		Integer producer_id = repairGoodsImportEntity.getProducer_id();
		RepairGoodsProducerEntity byPrimaryKey = repairGoodsProducerService.findByPrimaryKey(producer_id);
		repairGoodsImportEntity.setProducer_name(byPrimaryKey.getProducer_name());
		//查询库类型
		if(repairGoodsImportEntity.getRepertory_type_id()==null){
			repairGoodsImportEntity.setRepertory_type_name("");
		}
		Integer repertory_type_id = repairGoodsImportEntity.getRepertory_type_id();
		RepairGoodsRepertoryTypeEntity byPrimaryKey1 = repairGoodsRepertoryTypeService.findByPrimaryKey(repertory_type_id);
		repairGoodsImportEntity.setRepertory_type_name(byPrimaryKey1.getRepertory_type_name());
		//查询库名称
		if(repairGoodsImportEntity.getRepertory_name_id()==null){
			repairGoodsImportEntity.setRepertory_name("");
		}
		Integer repertory_name_id = repairGoodsImportEntity.getRepertory_name_id();
		RepairGoodsRepertoryNameEntity byPrimaryKey2 = repairGoodsRepertoryNameService.findByPrimaryKey(repertory_name_id);
		repairGoodsImportEntity.setRepertory_name(byPrimaryKey2.getRepertory_name());
		//查询物料名称
		if(repairGoodsImportEntity.getGoods_type_id()==null){
			repairGoodsImportEntity.setGoods_type_name("");
		}
		Integer goods_type_id = repairGoodsImportEntity.getGoods_type_id();
		RepairGoodsTypeEntity byPrimaryKey3 = repairGoodsTypeService.findByPrimaryKey(goods_type_id);
		repairGoodsImportEntity.setGoods_type_name(byPrimaryKey3.getGoods_type_name());

		repairGoodsImportEntity.setAdmin_id(sysAdminEntity.getAdmin_id());
		repairGoodsImportEntity.setAdmin_name(sysAdminEntity.getAdmin_name());
		repairGoodsImportEntity.setImport_time(new Date());
		repairGoodsImportEntity.setAdmin_phone(sysAdminEntity.getAdmin_phone());
		repairGoodsImportEntity.setGoods_no(IdGenerator.idGen());
		ResultData resultData = repairGoodsImportService.saveRepairGoodsImport(repairGoodsImportEntity);
		return resultData;
	}

	/**
	 * 编辑入库信息
	 * @param repairGoodsImportEntity
	 * @param request
	 * @return
	 */
	@PostMapping("updateRepairGoodsImport")
	@ApiOperation(value = "编辑入库信息", notes = "编辑入库信息")
	public ResultData updateRepairGoodsImport(@RequestBody RepairGoodsImportEntity repairGoodsImportEntity, HttpServletRequest request){
		if(repairGoodsImportEntity == null){
			return new ResultData(20001,false,"信息不能为空，请重试");
		}
		//获取用户信息
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20002,false,"身份过期，请重新登录");
		}
		//判断是否能修改
		if(sysAdminEntity.getAdmin_id() != repairGoodsImportEntity.getAdmin_id()){
			return new ResultData(20003,false,"你无权限修改此信息");
		}
		//查询生产厂家名称
		Integer producer_id = repairGoodsImportEntity.getProducer_id();
		RepairGoodsProducerEntity byPrimaryKey = repairGoodsProducerService.findByPrimaryKey(producer_id);
		repairGoodsImportEntity.setProducer_name(byPrimaryKey.getProducer_name());
		//查询库类型
		Integer repertory_type_id = repairGoodsImportEntity.getRepertory_type_id();
		RepairGoodsRepertoryTypeEntity byPrimaryKey1 = repairGoodsRepertoryTypeService.findByPrimaryKey(repertory_type_id);
		repairGoodsImportEntity.setRepertory_type_name(byPrimaryKey1.getRepertory_type_name());
		//查询库名称
		Integer repertory_name_id = repairGoodsImportEntity.getRepertory_name_id();
		RepairGoodsRepertoryNameEntity byPrimaryKey2 = repairGoodsRepertoryNameService.findByPrimaryKey(repertory_name_id);
		repairGoodsImportEntity.setRepertory_name(byPrimaryKey2.getRepertory_name());
		//查询物料名称
		Integer goods_type_id = repairGoodsImportEntity.getGoods_type_id();
		RepairGoodsTypeEntity byPrimaryKey3 = repairGoodsTypeService.findByPrimaryKey(goods_type_id);
		repairGoodsImportEntity.setGoods_type_name(byPrimaryKey3.getGoods_type_name());
		ResultData resultData = repairGoodsImportService.updateRepairGoodsImport(repairGoodsImportEntity);
		return resultData;
	}

	/**
	 * 删除入库信息
	 * @param import_id
	 * @param request
	 * @return
	 */
	@PostMapping("deleteRepairGoodsImport")
	@ApiOperation(value = "删除入库信息", notes = "删除入库信息")
	public ResultData deleteRepairGoodsImport(Integer import_id,HttpServletRequest request){
		//获取用户信息
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20001,false,"身份过期，请重新登录");
		}
		if(import_id == null || import_id < 0){
			return new ResultData(20002,false,"请选择入库信息");
		}
		ResultData resultData = repairGoodsImportService.deleteRepairGoodsImport(import_id);
		return resultData;
	}

	/**
	 * 提交入库信息
	 * @param request
	 * @return
	 */
	@PostMapping("submitRepairGoodsImport")
	@ApiOperation(value = "提交入库信息", notes = "提交入库信息")
	public ResultData submitRepairGoodsImport(@RequestBody RepairGoodsImportEntity repairGoodsImportEntity,HttpServletRequest request){
		//获取登录信息
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20001,false,"身份过期，请重新登录");
		}
		//查询生产厂家名称
		if(repairGoodsImportEntity.getProducer_id()==null){
			repairGoodsImportEntity.setProducer_name("");
		}else{
			Integer producer_id = repairGoodsImportEntity.getProducer_id();
			RepairGoodsProducerEntity byPrimaryKey = repairGoodsProducerService.findByPrimaryKey(producer_id);
			repairGoodsImportEntity.setProducer_name(byPrimaryKey.getProducer_name());
		}
		//查询库类型
		if(repairGoodsImportEntity.getRepertory_type_id()==null){
			repairGoodsImportEntity.setRepertory_type_name("");
		}else{
			Integer repertory_type_id = repairGoodsImportEntity.getRepertory_type_id();
			RepairGoodsRepertoryTypeEntity byPrimaryKey1 = repairGoodsRepertoryTypeService.findByPrimaryKey(repertory_type_id);
			repairGoodsImportEntity.setRepertory_type_name(byPrimaryKey1.getRepertory_type_name());
		}
		//查询库名称
		if(repairGoodsImportEntity.getRepertory_name_id()==null){
			repairGoodsImportEntity.setRepertory_name("");
		}else{
			Integer repertory_name_id = repairGoodsImportEntity.getRepertory_name_id();
			RepairGoodsRepertoryNameEntity byPrimaryKey2 = repairGoodsRepertoryNameService.findByPrimaryKey(repertory_name_id);
			repairGoodsImportEntity.setRepertory_name(byPrimaryKey2.getRepertory_name());
		}
		//查询物料名称
		if(repairGoodsImportEntity.getGoods_type_id()==null){
			repairGoodsImportEntity.setGoods_type_name("");
		}else{
			Integer goods_type_id = repairGoodsImportEntity.getGoods_type_id();
			RepairGoodsTypeEntity byPrimaryKey3 = repairGoodsTypeService.findByPrimaryKey(goods_type_id);
			repairGoodsImportEntity.setGoods_type_name(byPrimaryKey3.getGoods_type_name());
		}
		repairGoodsImportEntity.setAdmin_id(sysAdminEntity.getAdmin_id());
		repairGoodsImportEntity.setAdmin_name(sysAdminEntity.getAdmin_name());
		repairGoodsImportEntity.setImport_time(new Date());
		repairGoodsImportEntity.setAdmin_phone(sysAdminEntity.getAdmin_phone());
		repairGoodsImportEntity.setGoods_no(IdGenerator.idGen());
		ResultData resultData = repairGoodsImportService.submitRepairGoodsImport(repairGoodsImportEntity,sysAdminEntity.getAdmin_id());
		return resultData;
	}
	@PostMapping("submitUpdateGoodsImport")
	@ApiOperation(value = "提交入库信息", notes = "提交入库信息")
	public ResultData submitNotSaveGoodsImport(@RequestBody RepairGoodsImportEntity repairGoodsImportEntity, HttpServletRequest request){
		SysAdminEntity sysAdminEntity = (SysAdminEntity)request.getSession().getAttribute("sysAdminEntity");
		if(sysAdminEntity == null){
			return new ResultData(20001,false,"身份过期，请重新登录");
		}
		//查询生产厂家名称
		if(repairGoodsImportEntity.getProducer_id()==null){
			repairGoodsImportEntity.setProducer_name("");
		}else{
			Integer producer_id = repairGoodsImportEntity.getProducer_id();
			RepairGoodsProducerEntity byPrimaryKey = repairGoodsProducerService.findByPrimaryKey(producer_id);
			repairGoodsImportEntity.setProducer_name(byPrimaryKey.getProducer_name());
		}
		//查询库类型
		if(repairGoodsImportEntity.getRepertory_type_id()==null){
			repairGoodsImportEntity.setRepertory_type_name("");
		}else{
			Integer repertory_type_id = repairGoodsImportEntity.getRepertory_type_id();
			RepairGoodsRepertoryTypeEntity byPrimaryKey1 = repairGoodsRepertoryTypeService.findByPrimaryKey(repertory_type_id);
			repairGoodsImportEntity.setRepertory_type_name(byPrimaryKey1.getRepertory_type_name());
		}
		//查询库名称
		if(repairGoodsImportEntity.getRepertory_name_id()==null){
			repairGoodsImportEntity.setRepertory_name("");
		}else{
			Integer repertory_name_id = repairGoodsImportEntity.getRepertory_name_id();
			RepairGoodsRepertoryNameEntity byPrimaryKey2 = repairGoodsRepertoryNameService.findByPrimaryKey(repertory_name_id);
			repairGoodsImportEntity.setRepertory_name(byPrimaryKey2.getRepertory_name());
		}
		//查询物料名称
		if(repairGoodsImportEntity.getGoods_type_id()==null){
			repairGoodsImportEntity.setGoods_type_name("");
		}else{
			Integer goods_type_id = repairGoodsImportEntity.getGoods_type_id();
			RepairGoodsTypeEntity byPrimaryKey3 = repairGoodsTypeService.findByPrimaryKey(goods_type_id);
			repairGoodsImportEntity.setGoods_type_name(byPrimaryKey3.getGoods_type_name());
		}
		repairGoodsImportEntity.setAdmin_id(sysAdminEntity.getAdmin_id());
		repairGoodsImportEntity.setAdmin_name(sysAdminEntity.getAdmin_name());
		repairGoodsImportEntity.setImport_time(new Date());
		repairGoodsImportEntity.setAdmin_phone(sysAdminEntity.getAdmin_phone());
		repairGoodsImportEntity.setGoods_no(IdGenerator.idGen());
		ResultData resultData = repairGoodsImportService.submitNotSaveGoodsImport(repairGoodsImportEntity);
		return resultData;
	}

}
