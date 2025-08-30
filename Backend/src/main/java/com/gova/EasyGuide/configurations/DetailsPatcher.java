package com.gova.EasyGuide.configurations;


import com.gova.EasyGuide.entities.db1.BaseUser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class DetailsPatcher {

    public static void internPatcher(BaseUser existingUSer, BaseUser incompleteUser) throws IllegalAccessException {

        //GET THE COMPILED VERSION OF THE CLASS
        Class<?> baseUserClass= BaseUser.class;
        Field[] userFields=baseUserClass.getDeclaredFields();
        System.out.println(userFields.length);
        for(Field field : userFields){
            System.out.println(field.getName());
            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING INTERN
            Object value=field.get(incompleteUser);
            if(value!=null){
                field.set(existingUSer,value);
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }

    }
}
