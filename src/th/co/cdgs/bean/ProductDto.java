package th.co.cdgs.bean;
import java.math.BigDecimal; 


public class ProductDto {
    private Long productId;
    private String productName;
    private String productDesc;
    private BigDecimal price;
    private String active;
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getActive() {
        return active;
    }
    public void setActive(String active) {
        this.active = active;
    }




}