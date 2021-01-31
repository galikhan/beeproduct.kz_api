package kz.beeproduct.dao;

import kz.beeproduct.dto.UsersDto;

public interface UsersDao {

    UsersDto save(UsersDto user);

    UsersDto create(UsersDto user);

    UsersDto findByLogin(String login);

    UsersDto findBySession(String sessionId);

}
