ALTER TABLE Chat_Message DROP CONSTRAINT chat_message_chat_id_fkey;

ALTER TABLE Chat_Room DROP CONSTRAINT PK_TwoUserIds;
ALTER TABLE Chat_Room DROP CONSTRAINT UC_Chat_Room_id;
ALTER TABLE Chat_Room ADD PRIMARY KEY (id);
ALTER TABLE Chat_Room ADD CONSTRAINT UC_Chat_Room_two_user_ids UNIQUE(first_user_id, second_user_id);

ALTER TABLE Chat_Message ADD CONSTRAINT chat_message_chat_id_fkey FOREIGN KEY (room_id) REFERENCES Chat_Room(id);