package cn.crm.controller.wxmessage;

import lombok.Data;

/**
 * @ClassName TemplateEntity
 * @Author HJW
 * @Date 2019/4/4 11:44
 */
@Data
public class TemplateEntity {
    public String  str1;
    public String  str2;
    public String  str3;
    public String  str4;

    public TemplateEntity(String str1, String str2, String str3, String str4) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
        this.str4 = str4;
    }
}
