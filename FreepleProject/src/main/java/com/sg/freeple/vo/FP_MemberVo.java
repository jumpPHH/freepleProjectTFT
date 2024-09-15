package com.sg.freeple.vo;

import java.util.Date;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FP_MemberVo {

    private int mb_no;

    private String mb_id;
    private String mb_pw;

    private String mb_nick;
    private String mb_gender;
    private String mb_phone;
    private String mb_email;

    private int mb_login_failures;

    private String mb_image_link;
    private String mb_image_original_filename;

    private String mb_host_info;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date mb_birth;

    private Date mb_joindate;
    private Date mb_logindate;

}
