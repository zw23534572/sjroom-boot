package github.sjroom.secrity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import github.sjroom.core.code.BaseErrorCode;
import github.sjroom.core.response.RespVo;
import github.sjroom.core.response.ResponseMessageResolver;
import github.sjroom.secrity.bean.JwtUser;
import github.sjroom.secrity.bean.UserReqVo;
import github.sjroom.secrity.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法 ,
 * attemptAuthentication：接收并解析用户凭证。
 * successfulAuthentication：用户成功登录后，这个方法会被调用，我们在这个方法里生成token并返回。
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
        try {
            UserReqVo loginUser = new ObjectMapper().readValue(request.getInputStream(), UserReqVo.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        String token = JwtTokenUtil.generateToken(jwtUser.getUsername());
        log.info("successfulAuthentication jwtUser:{} token:{}", jwtUser, token);
        HashMap result = new HashMap();
        result.put(JwtTokenUtil.HEADER_TOKEN, token);
        ResponseMessageResolver.successResolve(request, response, RespVo.success(result));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
        log.error("JWTAuthenticationFilter unsuccessfulAuthentication ex:{}", ex);
        ResponseMessageResolver.failResolve(request, response, BaseErrorCode.UNAUTHORIZED_ERROR, ex.getMessage());
    }
}
