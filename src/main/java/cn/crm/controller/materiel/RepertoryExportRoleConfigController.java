package cn.crm.controller.materiel;

import cn.crm.service.materiel.RepertoryExportRoleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/repertoryexportroleconfig")
public class RepertoryExportRoleConfigController {

    @Autowired
    private RepertoryExportRoleConfigService roleConfigService;
}
