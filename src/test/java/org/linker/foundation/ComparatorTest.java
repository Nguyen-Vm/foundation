package org.linker.foundation;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

/**
 * @author RWM
 * @date 2018/8/27
 */
public class ComparatorTest {

    private static class Student {
        // 姓名
        public String name;
        // 学号
        public String no;

        public String getName() {
            return name;
        }
        public String getNo() {
            return no;
        }
    }

    @Test
    public void main() {
        List<Student> students = Lists.newArrayList();

        Student student4 = new Student();
        student4.name = "Java3";
        student4.no = "20";
        students.add(student4);

        Student student1 = new Student();
        student1.name = "Java1";
        student1.no = "36";
        students.add(student1);

        Student student3 = new Student();
        student3.name = "Java2";
        student3.no = "22";
        students.add(student3);

        Student student2 = new Student();
        student2.name = "Java1";
        student2.no = "35";
        students.add(student2);


        System.out.println("排序前:");
        students.forEach(student -> System.out.println(student.name + "  " + student.no));
        // 按名字升序排序，名字相同按学号升序排序;
        students.sort(Comparator.comparing(Student::getName).thenComparing(Student::getNo));
        System.out.println("排序后:");
        students.forEach(student -> System.out.println(student.name + "  " + student.no));
    }
}
