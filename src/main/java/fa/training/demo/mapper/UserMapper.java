package fa.training.demo.mapper;

import fa.training.demo.entity.UserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "role", column = "role"),
            @Result(property = "enabled", column = "enabled")
    })
    Optional<UserEntity> getUserById(@Param("id") Long id);

    @Select("SELECT * FROM users WHERE username = #{username}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "role", column = "role"),
            @Result(property = "enabled", column = "enabled")
    })
    Optional<UserEntity> getUserByUsername(@Param("username") String username);

    @Insert("INSERT INTO users (username, password, email, role, enabled) VALUES (#{user.username}, #{user.password}, #{user.email}, #{user.role}, #{user.enabled})")
    void insertUser(@Param("user") UserEntity user);

    @Update("UPDATE users SET username = #{user.username}, password = #{user.password}, email = #{user.email}, role = #{user.role}, enabled = #{user.enabled} WHERE id = #{user.id}")
    void updateUser(@Param("user") UserEntity user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(@Param("id") Long id);

    @Select("SELECT * FROM users")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "email", column = "email"),
            @Result(property = "role", column = "role"),
            @Result(property = "enabled", column = "enabled")
    })
    List<UserEntity> getAllUsers();

    @Select("SELECT * FROM users WHERE username = #{username}")
    int checkUsernameExists(@Param("username") String username);

    @Select("SELECT * FROM users WHERE email = #{email}")
    int checkEmailExists(@Param("email") String email);
}