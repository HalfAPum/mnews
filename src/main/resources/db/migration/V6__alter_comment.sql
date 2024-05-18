ALTER TABLE Comment ADD author_id int;
ALTER TABLE Comment ADD CONSTRAINT FK_UserCommentAuthor FOREIGN KEY (author_id) REFERENCES Users(id);