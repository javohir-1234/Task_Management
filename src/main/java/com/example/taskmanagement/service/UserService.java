package com.example.taskmanagement.service;

import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.entity.dto.SignInDto;
import com.example.taskmanagement.entity.dto.UpdateUserRoleDto;
import com.example.taskmanagement.entity.dto.UserRequestDto;
import com.example.taskmanagement.entity.dto.UserResponseDto;
import com.example.taskmanagement.entity.enumType.UserRole;
import com.example.taskmanagement.exceptions.DataNotFound;
import com.example.taskmanagement.exceptions.NotCorrectEmail;
import com.example.taskmanagement.exceptions.UserNotVerified;
import com.example.taskmanagement.repository.UserRepository;
import com.example.taskmanagement.service.jwt.JWTService;
import com.example.taskmanagement.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService extends BaseService<
        User,
        UUID,
        UserRepository,
        UserResponseDto,
        UserRequestDto>{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private Validation validation;
    private Random random = new Random();



    public UserResponseDto singUp(UserRequestDto userRequestDto) throws NotCorrectEmail {
        User user = requestToEntity(userRequestDto);
        user.setRoles(Set.of(UserRole.USER));
        user.setCode(random.nextInt(1000, 10000));
        if (!validation.checkingEmail(user)){
            throw new NotCorrectEmail("User email is not valid");
        }
        userRepository.save(user);
        return entityToResponse(user);
    }


    public UserResponseDto singIn(SignInDto user) throws DataNotFound, UserNotVerified {
        User foundUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new DataNotFound("User Not Found"));

        if (!foundUser.isVerify()){
            throw new UserNotVerified("Not verified user");
        }

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword()))
            throw new RuntimeException();

        if (!foundUser.isActive()){
            throw new DataNotFound("User Not Found");
        }
        return entityToResponse(foundUser);
    }

    public boolean verify(String email, int code) throws DataNotFound {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFound("User Not Found"));

        if (user.getCode() == code){
            user.setVerify(true);
            userRepository.save(user);
            return true;
        }
        throw new RuntimeException("Wrong code");
    }


    public UserResponseDto updateUserRole(UpdateUserRoleDto userRoleDto) throws DataNotFound {
        User user = userRepository.findById(userRoleDto.getUserId())
                .orElseThrow(() -> new DataNotFound("User Not Found"));
        if (user.isActive()){
            user.setRoles(userRoleDto.getRoles());
            userRepository.save(user);
            return entityToResponse(user);
        }

        throw new DataNotFound("User Not found");

    }

    public void sendVerificationCode(String email) throws DataNotFound {
        System.out.println(email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFound("Data Not Found"));
        if (user.isActive()) {
            user.setCode(random.nextInt(1000, 10000));
            userRepository.save(user);
            notificationService.sendVerificationCode(user.getEmail(), user.getCode());
        }else {
            throw new DataNotFound("User Not Found");
        }
    }

    public String generateToken(UserRequestDto userRequestDto) throws DataNotFound {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new DataNotFound("User Not Found"));
        if (user.isActive())
            return jwtService.generateToken(user);

        throw new RuntimeException("User was deleted");
    }

    @Override
    public ModelMapper modelMapper() {
        return modelMapper;
    }

    @Override
    public UserRepository repository() {
        return userRepository;
    }

    @Override
    public User requestToEntity(UserRequestDto request) {
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

    @Override
    public UserResponseDto entityToResponse(User entity) {
        return modelMapper.map(entity, UserResponseDto.class);
    }
}
