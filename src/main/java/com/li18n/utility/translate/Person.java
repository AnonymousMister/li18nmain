package com.li18n.utility.translate;

import java.io.Serializable;

public class Person implements Serializable {



        private static final long serialVersionUID = -3930910593527340005L;
        private String name;
        private Integer age;
        private String sex;
        private String contry;



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getContry() {
            return contry;
        }

        public void setContry(String contry) {
            this.contry = contry;
        }

        public Person(String name, Integer age, String sex, String contry) {
            super();
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.contry = contry;
        }

        @Override
        public String toString() {
            return "Person [name=" + name + ", age=" + age + ", sex=" + sex + ", contry=" + contry + "]";
        }

}
