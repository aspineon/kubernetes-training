package pl.training.microservices.identity;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    User toUser(UserTo userTo);

    UserTo toUserTo(User user);

}
