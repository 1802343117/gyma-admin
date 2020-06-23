package com.soft1851.devoted.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author Tull
 * @since 2020-06-09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMessages {

    private Integer mId;


    private Boolean deleteFlag;


    private LocalDateTime gmtCreate;


    private LocalDateTime gmtModified;

    private String mContent;


    private String mFromUserid;


    private String userPath;

    private Map<String, Integer> mTime;

    private String mToUserid;


}
