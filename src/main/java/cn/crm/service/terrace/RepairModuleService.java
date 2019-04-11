package cn.crm.service.terrace;



import cn.crm.entity.terrace.RepairModuleEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * TODO 在此加入类描述 模块服务层接口
 * @copyright
 * @author MYZ
 * @version  2019-03-25 10:00:12
 */
public interface RepairModuleService extends BaseService<RepairModuleEntity>  {
    /**
     * 查询模块信息
     * @param module_name  可根据模块名模糊查询
     * @param  module_status  根据状态查询
     * @return
     */
    public ResultData findAllModule(String module_name,Integer module_status, HttpServletRequest request);

    /**
     * 查询模块信息
     * @return
     */
    public ResultData findModule(HttpServletRequest request);

    /**
     * 根据模块id查询一个模块信息
     *
     * @param id  模块id
     * @return
     */
    public ResultData findModuleById(Integer id);

    /**
     * 新增模块信息
     *
     * @param repairModuleEntity 模块实体类
     * @return
     */
    public ResultData addModule(RepairModuleEntity repairModuleEntity, HttpServletRequest request);

    /**
     * 修改模块信息
     *
     * @param repairModuleEntity  模块实体类
     * @return
     */
    public ResultData updateModule(RepairModuleEntity repairModuleEntity,HttpServletRequest request);

    /**
     * 删除一个模块信息
     * @param id  模块id
     * @return
     */
    public ResultData deleteModule (Integer id);

    /**
     * 上传模块图标
     * @param request
     * @param file
     * @return
     */
    public ResultData  uploadModuleImage(HttpServletRequest request, MultipartFile file);

    /**
     * 手机端查询模块信息
     * @param request
     * @return
     */
    public ResultData findAppModule(HttpServletRequest request);

}
