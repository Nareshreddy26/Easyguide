package com.gova.EasyGuide.entities.db1;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistartionDto {

    private String dtoUsername;

    private String dtoUserPassword;

    private String dtoUseremail;
}
