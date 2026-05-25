package mm.com.mytel.articleapi.mapper;

import mm.com.mytel.articleapi.dto.UserResponse;
import mm.com.mytel.articleapi.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
