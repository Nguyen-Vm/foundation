package org.linker.foundation.provider.excel;

import java.util.Date;

public class StaffExport {

    @ExcelColumn(title = "姓名")
    private String name;

    @ExcelColumn(title = "部门", index = 1)
    private String dept;

    @ExcelColumn(title = "生日", index = 2)
    private Date birthday;

    @ExcelColumn(title = "工号", index = 3)
    private Integer cardNo;

    @ExcelColumn(title = "奖金", index = 4)
    private Double salary;

    @ExcelColumn(title = "已毕业", index = 5)
    private Boolean graduated;

    public StaffExport() {}

    public StaffExport(String name, String dept, Date birthday, Integer cardNo, Double salary, Boolean graduated) {
        this.name = name;
        this.dept = dept;
        this.birthday = birthday;
        this.cardNo = cardNo;
        this.salary = salary;
        this.graduated = graduated;
    }

    @Override
    public String toString() {
        return "StaffExport{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", birthday=" + birthday +
                ", cardNo=" + cardNo +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public Boolean getGraduated() {
        return graduated;
    }

    public void setGraduated(Boolean graduated) {
        this.graduated = graduated;
    }
}
