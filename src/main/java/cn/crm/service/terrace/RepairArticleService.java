package cn.crm.service.terrace;


import cn.crm.entity.terrace.RepairArticleEntity;
import cn.crm.result.ResultData;
import cn.crm.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;



/**
 * TODO 在此加入类描述  文章信息服务层接口
 *
 * @author MYZ
 * @version 2019-03-25 10:00:30
 */
public interface RepairArticleService extends BaseService<RepairArticleEntity> {
    /**
     * 查询文章信息
     * @param typeId  可根据类型id查询
     * @return
     */
    public ResultData findAllArticle(String  article_title,Integer typeId,HttpServletRequest request);

    /**
     * 根据文章id查询一条文章信息
     *
     * @param id  文章id
     * @return
     */
    public RepairArticleEntity findArticleById(Integer id);

    /**
     * 新增文章信息
     *
     * @param repairArticleEntity 文章实体类
     * @return
     */
    public ResultData addArticle(RepairArticleEntity repairArticleEntity, HttpServletRequest request);

    /**
     * 修改文章信息
     *
     * @param repairArticleEntity  文章实体类
     * @return
     */
    public ResultData updateArticle(RepairArticleEntity repairArticleEntity,HttpServletRequest request);

    /**
     * 删除一条文章信息
     * @param id  文章id
     * @return
     */
    public ResultData deleteArticle (Integer id);

    /**
     * 上传轮播图
     * @param request
     * @param file
     * @return
     */
    public ResultData  uploadMRotationchart(HttpServletRequest request, MultipartFile file);

    /**
     * 手机端查询文章信息
     * @param request
     * @param typeId  文章类型查询
     * @return
     */
    public ResultData findAppArticle(HttpServletRequest request,Integer typeId);
}
