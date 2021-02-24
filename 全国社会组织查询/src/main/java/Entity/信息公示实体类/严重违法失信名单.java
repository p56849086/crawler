package Entity.信息公示实体类;

import lombok.Data;

@Data
public class 严重违法失信名单 {
    private String 序号;
    private String 列入时间;
    private String 列入事由;
    private String 移出时间;
    private String 移出事由;
}
