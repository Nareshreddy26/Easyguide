package com.gova.EasyGuide.entities.db1;


import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.Enums.SocailMedia;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public abstract class BaseUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String userName;

    @Column(unique = true)
    private String userEmail;

    private Long userContactNumber;

    private String profession;

    @ColumnDefault("'https.imageurl'")
    private String userImageUrl;


    private String about;

    private String userPassword;

    @Enumerated(EnumType.STRING)
    private Roles roles;

    @ElementCollection
    @CollectionTable(name = "user_social_medias" ,joinColumns = @JoinColumn(name="user_id"))
    @MapKeyColumn(name = "platform")
    @Column(name = "platform_url")
    private Map<SocailMedia,String > userSocailMedia = new HashMap<>();


    public BaseUser(String userName, String userEmail, String userPassword) {
        this.userName = userName;

        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }


    public BaseUser(String userEmail, String userPassword) {
        this.userEmail=userEmail;
        this.userPassword=userPassword;
    }
}




