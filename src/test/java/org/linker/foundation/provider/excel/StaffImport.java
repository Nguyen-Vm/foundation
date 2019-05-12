package org.linker.foundation.provider.excel;

import java.util.Date;

public class StaffImport {

    @ExcelColumn(title = "姓名")
    private String name;

    @ExcelColumn(title = "部门", index = 1)
    private String department;

    @ExcelColumn(title = "英文名", index = 2)
    private String englishName;

    @ExcelColumn(title = "工号", index = 3)
    private Integer cardNo;

    @ExcelColumn(title = "奖金", index = 4)
    private Double salary;

    @ExcelColumn(title = "创建时间", index = 5)
    private Date createTime;

    @ExcelColumn(title = "已毕业", index = 6)
    private Boolean graduate;

    @Override
    public String toString() {
        return "StaffImport{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", englishName='" + englishName + '\'' +
                ", cardNo=" + cardNo +
                ", salary=" + salary +
                ", createTime=" + createTime +
                ", graduate=" + graduate +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Integer getCardNo() {
        return cardNo;
    }

    public void setCardNo(Integer cardNo) {
        this.cardNo = cardNo;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getGraduate() {
        return graduate;
    }

    public void setGraduate(Boolean graduate) {
        this.graduate = graduate;
    }
}
