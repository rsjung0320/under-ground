package com.nextinno.underground.login;

import com.nextinno.underground.token.Token;
import com.nextinno.underground.token.TokenDto;
import com.nextinno.underground.user.User;

/**
 * Created by rsjung on 2016-11-25.
 */
public interface LoginService {
    User saveUser(User user);
    Token getToken(Login login);
    Token refreshToken(TokenDto.RefreshToken token);
    Token regenerateToken(TokenDto.RegenerateToken token);
}
