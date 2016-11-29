package com.nextinno.underground.login;

import com.nextinno.underground.api.API;
import com.nextinno.underground.global.domain.ErrorResponse;
import com.nextinno.underground.token.Token;
import com.nextinno.underground.token.TokenDto;
import com.nextinno.underground.user.AlreadyExistUserException;
import com.nextinno.underground.user.User;
import com.nextinno.underground.user.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author rsjung
 */
@Controller
@RequestMapping(API.LOGIN)
@Transactional
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     * @param reqLogin
     * @param result
     * @return 성공시 200, 실패시 NotEqualPasswordException or UserNotFoundException
     */
    @RequestMapping(value = "/signin", method = POST)
    @ResponseBody
    public ResponseEntity singin(@RequestBody @Valid final LoginDto.SignIn reqLogin, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Login login = new Login(reqLogin);

        Token token = loginService.getToken(login);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * @param reqUser
     * @return 성공시 201, 실패시 AlreadyExistUserException
     * @throws Exception
     */
    @RequestMapping(value = "/signup", method = POST)
    @ResponseBody
    public ResponseEntity signup(@RequestBody @Valid final UserDto.CreateUser reqUser, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User(reqUser);

        User resultUser = loginService.saveUser(user);
        return new ResponseEntity<>(modelMapper.map(resultUser, UserDto.ResponseUser.class), HttpStatus.CREATED);
    }

    /**
     * refresh token이 인증이 되면 token과 refresh token을 재 발행 한다.
     *
     * @param reqToken
     * @param result
     * @return 성공시 200, 실패시 InvalidTokenException
     */
    @RequestMapping(value = "/token/refresh", method = POST)
    @ResponseBody
    public ResponseEntity refreshToken(@RequestBody @Valid final TokenDto.RefreshToken reqToken, BindingResult result){
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Token token = loginService.refreshToken(reqToken);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * 1시간 마다 클라이언트에서  token을 regenerate 하기 위해 request를 보내는대, 그것을 처리하기 위한 함수이다.
     * @param reqToken
     * @param result 성공시 200, 실패시
     * @return
     */
    @RequestMapping(value = "token/regenerate", method = POST)
    @ResponseBody
    public ResponseEntity regenerateToken(@RequestBody @Valid final TokenDto.RegenerateToken reqToken, BindingResult result){
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Token token = loginService.regenerateToken(reqToken);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /* Exceptions */

    @ExceptionHandler(AlreadyExistUserException.class)
    @ResponseBody
    public ResponseEntity handleAlreadyExistUserException(AlreadyExistUserException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getEmail() + "]은 이미 존재합니다.");
        errorResponse.setCode("already.exist.user.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEqualPasswordException.class)
    @ResponseBody
    public ResponseEntity handleNotEqualPasswordException(NotEqualPasswordException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getEmail() + "]가 입력한 패스워드가 다릅니다.");
        errorResponse.setCode("not.equal.password.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getEmail() + "]은 존재하지 않습니다.");
        errorResponse.setCode("user.not.found.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseBody
    public ResponseEntity handleInvalidTokenException(InvalidTokenException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getEmail() + "]의 토큰이 유효하지 않습니다.");
        errorResponse.setCode("invalid.token.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
