package com.mine.gublog.test;


import com.mine.gublog.dto.BamsArticleDTO;

import java.lang.reflect.Field;

import static com.mine.gublog.utils.ClassUtil.*;

public class TestMain {
    public static void main(String[] args) {
        foreachField(BamsArticleDTO.class,(field)->{
            String name = ((Field)field).getName();
            System.out.println("if(this."+name+"!= null){");
            System.out.println("list.add(this."+name+")}");
        });
    }
}
