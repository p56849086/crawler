package Entity.信息公示实体类;

import lombok.Data;

@Data
public class 变更公示 {
    private String 序号;
    private String 业务类型;
    private String 变更前;
    private String 变更后;
    private String 办结日期;
}
