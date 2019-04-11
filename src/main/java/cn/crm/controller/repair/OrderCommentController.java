package cn.crm.controller.repair;

import cn.crm.service.repair.OrderCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName OrderCommentController
 * @Author HJW
 * @Date 2019/3/21 15:21
 */
@RestController
@RequestMapping(value="/ordercomment")
public class OrderCommentController {

    private OrderCommentService orderCommentService;
}
