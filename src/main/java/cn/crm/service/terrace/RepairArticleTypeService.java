package cn.crm.service.terrace;


import cn.crm.entity.terrace.RepairArticleTypeEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;

import javax.servlet.http.HttpServletRequest;


/**
 * TODO 在此加入类描述  文章类型服务层接口
 *
 * @author MYZ
 * @version 2019-03-25 10:01:09
 * @copyright
 */
public interface RepairArticleTypeService extends BaseService<RepairArticleTypeEntity> {

    /**
     * @param request
     * @param articleType_title  文章类型名
     * @param articleType_state  文章类型状态
     * @Description :  查询文章类型(可根据类型状态和类型名查询)
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/27 14:37
     */
    public ResultData findAllArticleType(HttpServletRequest request, String articleType_title, Integer articleType_state);

    /**
     * @param request
     * @Description :  查询文章类型
     * @Return : cn.crm.result.ResultData
     * @Author : MYZ
     * @Date : 2019/3/27 14:37
     */
    public ResultData findArticleType(HttpServletRequest request);

    /**
     * 根据文章类型id查询一个类型信息
     *
     * @param id 文章类型id
     * @return
     */
    public ResultData findArticleTypeById(Integer id);

    /**
     * 新增文章类型信息
     *
     * @param repairArticleTypeEntity 文章类型实体类
     * @return
     */
    public ResultData addArticleType(RepairArticleTypeEntity repairArticleTypeEntity, HttpServletRequest request);

    /**
     * 修改文章类型信息
     *
     * @param repairArticleTypeEntity 文章类型实体类
     * @return
     */
    public ResultData updateArticleType(RepairArticleTypeEntity repairArticleTypeEntity, HttpServletRequest request);

    /**
     * 删除一个文章类型信息
     *
     * @param id 文章类型id
     * @return
     */
    public ResultData deleteArticleType(Integer id);

    /**
     * 手机端查询文章类型
     *
     * @param request
     * @return
     */
    public ResultData findAppArticleType(HttpServletRequest request);
}
