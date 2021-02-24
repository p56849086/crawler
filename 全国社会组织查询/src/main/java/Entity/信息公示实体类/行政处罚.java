package Entity.信息公示实体类;

import lombok.Data;

@Data
public class 行政处罚 {
    private String 序号;
    private String 决定书文号;
    private String 处罚事由;
    private String 处罚依据;
    private String 处罚结果;
    private String 处罚决定日期;
}
