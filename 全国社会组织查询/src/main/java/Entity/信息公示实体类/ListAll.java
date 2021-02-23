package Entity.信息公示实体类;

import lombok.Data;

import java.util.List;

@Data
public class ListAll {
    private 社会组织基本信息 organization;
    private List<成立公示> list1;
    private List<变更公示> list2;
    private List<注销公示> list3;
    private List<年检结果> list4;
    private List<评估信息> list5;
    private List<表彰信息> list6;
    private List<中央财政支持项目> list7;
    private List<行政处罚> list8;
    private List<异常活动名录信息> list9;
    private List<严重违法失信名单> list10;
}
