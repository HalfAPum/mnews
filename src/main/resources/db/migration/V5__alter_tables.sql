ALTER TABLE News DROP COLUMN author;
ALTER TABLE News ADD author_id int;
ALTER TABLE News ADD CONSTRAINT FK_UserNewsAuthor FOREIGN KEY (author_id) REFERENCES Users(id);