package cn.crm.controller.repair;


import cn.crm.entity.repair.RepairFunctionEntity;
import cn.crm.logaop.SystemLog;
import cn.crm.result.ResultData;
import cn.crm.service.repair.RepairFunctionService;
import cn.crm.util.*;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value="/repairfunction")
public class RepairFunctionController {


	@Autowired
	private RepairFunctionService repairFunctionService;

	/**
	 * 查询所有报修功能
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@GetMapping("findRepairFunction")
	@SystemLog(methods = "查询报修功能",module = "查询报修功能")
	public ResultData findRepairFunction(Integer pageNum,Integer pageSize){
		ResultData resultData = repairFunctionService.findRepairFunction(pageNum,pageSize);
		return resultData;
	}

	/**
	 * 新增报修功能
	 * @param repairFunctionEntity
	 * @return
	 */
	@PostMapping("saveRepairFunction")
	@SystemLog(methods = "新增报修功能",module = "新增报修功能")
	public ResultData saveRepairFunction(@RequestBody RepairFunctionEntity repairFunctionEntity){
		ResultData resultData = repairFunctionService.saveRepairFunction(repairFunctionEntity);
		return resultData;
	}

	/**
	 * 编辑报修功能
	 * @param repairFunctionEntity
	 * @return
	 */
	@PostMapping("updateRepairFunction")
	@SystemLog(methods = "编辑报修功能",module = "编辑报修功能")
	public ResultData updateRepairFunction(@RequestBody RepairFunctionEntity repairFunctionEntity){
		ResultData resultData = repairFunctionService.updateRepairFunction(repairFunctionEntity);
		return resultData;
	}

	/**
	 * 删除报修功能
	 * @param id
	 * @return
	 */
	@PostMapping("deleteRepairFunction")
	@SystemLog(methods = "删除报修功能",module = "删除报修功能")
	public ResultData deleteRepairFunction(Integer id){
		ResultData resultData = repairFunctionService.deleteRepairFunction(id);
		return resultData;
	}

	/**
	 * 根据用户id查询该用户所拥有的功能
	 * @return
	 */
	@GetMapping("findRepairFunctionByUserId")
	@SystemLog(methods = "根据用户id查询该用户所拥有的功能",module = "根据用户id查询该用户所拥有的功能")
	public ResultData findRepairFunctionByUserId(Integer userId){
		ResultData resultData = repairFunctionService.findRepairFunctionByUserId(userId);
		return resultData;
	}

	/**
	 * 上传功能图标
	 * @param request
	 * @param file 图标
	 * @return
	 */
	@PostMapping("uploadFunctionIcon")
	@SystemLog(module = "合同附件",methods = "合同附件")
	@ApiImplicitParam(name = "file",value = "合同附件",required = false,dataType = "MultipartFile",paramType = "form")
	public Map<String,Object> UploadTemplate(HttpServletRequest request, MultipartFile file){
		Map<String, Object> map = new HashMap<>();
		InputStream is = null;
		try{
			is = file.getInputStream();
		}catch(Exception e){
		}
		//源文件类型
		String sourceFileName = file.getOriginalFilename();
		String type  = sourceFileName.indexOf(".") != -1 ? sourceFileName.substring(sourceFileName.lastIndexOf(".") + 1, sourceFileName.length()) : null;
		//保存附件名和路径
		String saveName = IdGenerator.idGen()+"."+type;
		String targetPath = PropertiesUtil.getValue("AVATAR_IMAGES");
		String upload_port = PropertiesUtil.getValue("FTP_PORT");
		boolean result = new FTPUtil().uploadFile(targetPath, saveName, is);
		if(result){
			map.put("code",20000);
			map.put("msg","上传的文件成功！");
			map.put("url", upload_port+targetPath + "/" + saveName);
			map.put("name", sourceFileName);
			return map;
		}
		map.put("code",2);
		map.put("msg","上传失败，请检查网络！");
		return map;
	}
}
