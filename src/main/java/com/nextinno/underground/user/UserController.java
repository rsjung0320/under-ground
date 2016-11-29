package com.nextinno.underground.user;

import com.nextinno.underground.api.API;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author rsjung
 *
 */
@Controller
@RequestMapping(API.USER)
@Transactional
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    // TODO GET으로 변경하도록 한다.
    @RequestMapping(method = POST)
    @ResponseBody
    public ResponseEntity findByEmail(@RequestBody final User reqUser) {
        User user = userRepository.findByEmail(reqUser.getEmail());
        return new ResponseEntity<>(modelMapper.map(user, UserDto.ResponseUser.class), HttpStatus.OK);
    }
}
