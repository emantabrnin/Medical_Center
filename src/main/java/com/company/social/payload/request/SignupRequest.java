package com.company.social.payload.request;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    // @NotBlank
    // @Size(min = 3, max = 20)
    private String username;

    // private Set<String> role;

    // @NotBlank
    // @Size(min = 6, max = 400)
    private String password;
    private String email;


    private Boolean status;
    private Boolean isActive;
    private String firstName;
    private String lastName;
    private String mobile;
    private String gender;
    private String job;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Long score;
    private String zipCode;
    private String socialStatus;

}
