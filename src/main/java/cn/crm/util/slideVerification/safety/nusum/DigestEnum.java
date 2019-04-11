package cn.crm.util.slideVerification.safety.nusum;

/**
 * 枚举类
 */
public enum DigestEnum {
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 构造方法
    DigestEnum(String name) {
        this.name = name;
    }
}
