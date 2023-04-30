package dwr.MiniaturowaTablica.api.assemblers;

import dwr.MiniaturowaTablica.api.models.User;
import dwr.MiniaturowaTablica.api.payload.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserAssembler {
    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getUsername(), user.getEmail(), user.getRoles());
    }
}
