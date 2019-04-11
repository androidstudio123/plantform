package cn.crm.controller.repair;


import java.util.List;


import javax.servlet.http.HttpServletRequest;

import cn.crm.service.repair.DefaultFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




/**
 * TODO 在此加入类描述
 * @copyright {@link hzg}
 * @author hzg
 * @version  2019-03-26 09:19:29
 * @see cn.yr.controller.DefaultFunction
 */
@RestController
@RequestMapping(value="/defaultfunction")
public class DefaultFunctionController {


	@Autowired
	private DefaultFunctionService defaultFunctionService;
   


}
