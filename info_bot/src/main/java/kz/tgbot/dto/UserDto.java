package kz.tgbot.dto;

public class UserDto {

    public Integer id;
    public Boolean is_bot;
    public String first_name;
    public String last_name;
    public String username;
    public String language_code;
    public Boolean can_join_groups;
    public Boolean can_read_all_group_messages;
    public Boolean supports_inline_queries;

    public UserDto() {
    }
}
