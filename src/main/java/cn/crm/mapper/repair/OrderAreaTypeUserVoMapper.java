package cn.crm.mapper.repair;

import cn.crm.entity.repair.DefaultFunctionEntity;
import cn.crm.vo.OrderAreaTypeUserVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;


/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-26 09:19:29
 */

public interface OrderAreaTypeUserVoMapper extends Mapper<OrderAreaTypeUserVo>,MySqlMapper<OrderAreaTypeUserVo>{

    List<OrderAreaTypeUserVo> findOrderManager(@Param("order_status")Integer order_status,@Param("order_no")String order_no,Integer pageNum,Integer pageSize);



}

