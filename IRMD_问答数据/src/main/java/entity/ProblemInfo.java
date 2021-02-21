package entity;

/**
 * 公司：上海经禾信息技术有限公司
 * 作者：程存淦
 * 功能：
 * 时间：2020年7月1日16:41:57
 */
public class ProblemInfo {
    private String stockCode;//股票代码
    private String companyName;//公司简称
    private String visiterOrFormal;//浏览用户/注册用户
    private String userName;//用户名
    private String questionTime;//提问时间
    private String questionContent;//提问内容
    private String isRequest;//上市公司是否回复
    private String requestTime;//回复时间
    private String requestContent;//回复内容

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getVisiterOrFormal() {
        return visiterOrFormal;
    }

    public void setVisiterOrFormal(String visiterOrFormal) {
        this.visiterOrFormal = visiterOrFormal;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuestionTime() {
        return questionTime;
    }

    public void setQuestionTime(String questionTime) {
        this.questionTime = questionTime;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(String isRequest) {
        this.isRequest = isRequest;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }
}
