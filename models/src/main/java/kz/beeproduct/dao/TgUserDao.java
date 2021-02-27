package kz.beeproduct.dao;

import kz.beeproduct.dto.TgUserDto;

import java.util.List;

public interface TgUserDao {

    TgUserDto create(TgUserDto user);

    List<TgUserDto> findByRole(String role);

    TgUserDto findByChatId(Integer chatId);
}
