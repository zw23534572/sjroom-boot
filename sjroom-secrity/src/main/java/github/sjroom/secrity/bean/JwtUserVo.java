package github.sjroom.secrity.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created by echisan
 * on 2018/6/23
 */
@Data
public class JwtUserVo {

    /**
     * 用户名
     */
    @NotBlank
    private String username;
    /**
     * 用户密码
     */
	@NotBlank
    private String password;
    /**
     * 是否记住
     */
    private Integer rememberMe;
}
